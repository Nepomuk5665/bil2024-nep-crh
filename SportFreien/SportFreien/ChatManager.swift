//
//  ChatManager.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import SwiftUI
import FirebaseFirestore

class ChatManager: ObservableObject {
    @Published var messages: [Message] = []
    @Published var currentGroup: ChatGroup?
    
    private let db = Firestore.firestore()
    private var listener: ListenerRegistration?
    
    func joinGroup(code: String, username: String) {
        print("Attempting to join group with code: \(code)")
        
        db.collection("groups")
            .whereField("code", isEqualTo: code)
            .getDocuments { [weak self] snapshot, error in
                if let error = error {
                    print("Error fetching group: \(error)")
                    return
                }
                
                if let document = snapshot?.documents.first {
                    print("Found existing group")
                    do {
                        // Explicitly decode the group
                        let data = document.data()
                        let group = ChatGroup(
                            id: document.documentID,
                            code: data["code"] as? String ?? code,
                            members: data["members"] as? [String] ?? [username]
                        )
                        
                        // Update the group with the new member if needed
                        if !group.members.contains(username) {
                            var updatedGroup = group
                            updatedGroup.members.append(username)
                            try self?.db.collection("groups").document(document.documentID)
                                .setData(from: updatedGroup)
                        }
                        
                        // Set the current group
                        DispatchQueue.main.async {
                            self?.currentGroup = group
                            print("Set current group with ID: \(group.id)")
                            // Start listening after setting the group
                            self?.startListening()
                        }
                    } catch {
                        print("Error decoding group: \(error)")
                    }
                } else {
                    print("Creating new group")
                    let newGroup = ChatGroup(
                        id: UUID().uuidString,
                        code: code,
                        members: [username]
                    )
                    
                    do {
                        try self?.db.collection("groups").document(newGroup.id)
                            .setData(from: newGroup)
                        
                        DispatchQueue.main.async {
                            self?.currentGroup = newGroup
                            print("Set new group with ID: \(newGroup.id)")
                            // Start listening after setting the group
                            self?.startListening()
                        }
                    } catch {
                        print("Error creating group: \(error)")
                    }
                }
            }
    }
    
    func sendMessage(_ content: String, from username: String) {
        print("Attempting to send message: \(content)")
        guard let groupId = currentGroup?.id,
              let encrypted = SimpleEncryption.encrypt(content) else {
            print("Failed to encrypt message or no group ID")
            return
        }
        
        let message = Message(
            id: UUID().uuidString,
            content: encrypted,
            senderUsername: username,
            timestamp: Date()
        )
        
        do {
            try db.collection("groups")
                .document(groupId)
                .collection("messages")
                .document(message.id)
                .setData(from: message)
            print("Message sent successfully")
        } catch {
            print("Error sending message: \(error)")
        }
    }
    
    private func startListening() {
        guard let groupId = currentGroup?.id else {
            print("No group ID available for listening")
            return
        }
        
        print("Starting to listen for messages in group: \(groupId)")
        
        listener?.remove()
        
        listener = db.collection("groups")
            .document(groupId)
            .collection("messages")
            .order(by: "timestamp", descending: false)
            .addSnapshotListener { [weak self] snapshot, error in
                if let error = error {
                    print("Error listening for messages: \(error)")
                    return
                }
                
                guard let documents = snapshot?.documents else {
                    print("No documents in snapshot")
                    return
                }
                
                print("Received \(documents.count) messages")
                
                let messages = documents.compactMap { document -> Message? in
                    do {
                        var message = try document.data(as: Message.self)
                        if let decrypted = SimpleEncryption.decrypt(message.content) {
                            message.content = decrypted
                            return message
                        } else {
                            print("Failed to decrypt message: \(message.id)")
                            return nil
                        }
                    } catch {
                        print("Error decoding message: \(error)")
                        return nil
                    }
                }
                
                DispatchQueue.main.async {
                    print("Updating UI with \(messages.count) messages")
                    self?.messages = messages
                }
            }
    }
    
    func leaveGroup() {
        print("Leaving group")
        listener?.remove()
        listener = nil
        currentGroup = nil
        messages.removeAll()
    }
    
    deinit {
        listener?.remove()
    }
}



