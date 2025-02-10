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
        db.collection("groups").whereField("code", isEqualTo: code).getDocuments { [weak self] snapshot, error in
            if let document = snapshot?.documents.first {
                // Join existing group
                let group = try? document.data(as: ChatGroup.self)
                self?.currentGroup = group
                self?.startListening()
            } else {
                // Create new group
                let newGroup = ChatGroup(id: UUID().uuidString, code: code, members: [username])
                try? self?.db.collection("groups").document(newGroup.id).setData(from: newGroup)
                self?.currentGroup = newGroup
                self?.startListening()
            }
        }
    }
    
    func sendMessage(_ content: String, from username: String) {
        guard let groupId = currentGroup?.id else { return }
        guard let encrypted = SimpleEncryption.encrypt(content) else { return }
        
        let message = Message(
            id: UUID().uuidString,
            content: encrypted,
            senderUsername: username,
            timestamp: Date()
        )
        
        try? db.collection("groups").document(groupId)
            .collection("messages")
            .document(message.id)
            .setData(from: message)
    }
    
    private func startListening() {
        guard let groupId = currentGroup?.id else { return }
        
        listener?.remove()
        
        listener = db.collection("groups").document(groupId)
            .collection("messages")
            .order(by: "timestamp")
            .addSnapshotListener { [weak self] snapshot, error in
                guard let documents = snapshot?.documents else { return }
                
                self?.messages = documents.compactMap { document -> Message? in
                    var message = try? document.data(as: Message.self)
                    if let decrypted = SimpleEncryption.decrypt(message?.content ?? "") {
                        message?.content = decrypted
                    }
                    return message
                }
            }
    }
    
    func leaveGroup() {
        listener?.remove()
        currentGroup = nil
        messages.removeAll()
    }
}
