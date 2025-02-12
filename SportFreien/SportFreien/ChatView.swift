//
//  ChatView.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import SwiftUI

struct ChatView: View {
    @ObservedObject var chatManager: ChatManager
    @AppStorage("username") var username = ""
    @State private var messageText = ""
    
    var body: some View {
        VStack(spacing: 0) {
            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(chatManager.messages) { message in
                        MessageView(message: message, isFromCurrentUser: message.senderUsername == username)
                            .id(message.id)
                    }
                }
                .padding(.horizontal)
                .padding(.top, 8)
            }
            
            // Debug Text
            if chatManager.messages.isEmpty {
                Text("No messages")
                    .foregroundColor(.gray)
                    .padding()
            }
            
            VStack(spacing: 0) {
                Divider()
                HStack(spacing: 12) {
                    TextField("Nachricht", text: $messageText)
                        .textFieldStyle(.roundedBorder)
                        .padding(.vertical, 8)
                    
                    Button(action: sendMessage) {
                        Image(systemName: "arrow.up.circle.fill")
                            .font(.system(size: 32))
                            .foregroundStyle(.yellow)
                    }
                    .disabled(messageText.isEmpty)
                }
                .padding(.horizontal)
                .padding(.vertical, 4)
            }
            .background(.bar)
        }
        .onAppear {
            print("ChatView appeared. Messages count: \(chatManager.messages.count)")
        }
    }
    
    private func sendMessage() {
        guard !messageText.isEmpty else { return }
        let messageToSend = messageText
        messageText = ""
        
        print("Sending message: \(messageToSend)")
        chatManager.sendMessage(messageToSend, from: username)
    }
}



struct MessageView: View {
    let message: Message
    let isFromCurrentUser: Bool
    
    var body: some View {
        HStack {
            if isFromCurrentUser { Spacer() }
            
            VStack(alignment: isFromCurrentUser ? .trailing : .leading, spacing: 4) {
                Text(message.senderUsername)
                    .font(.caption)
                    .foregroundStyle(.gray)
                
                Text(message.content)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 8)
                    .background(isFromCurrentUser ? Color.yellow : Color.gray.opacity(0.2))
                    .clipShape(RoundedRectangle(cornerRadius: 16))
            }
            
            if !isFromCurrentUser { Spacer() }
        }
    }
}



// Supporting Views
struct ConnectionErrorView: View {
    let message: String
    let onRetry: () -> Void
    
    var body: some View {
        HStack {
            Text("⚠️ \(message)")
                .font(.caption)
                .foregroundColor(.red)
            
            Button("Retry", action: onRetry)
                .font(.caption)
        }
        .padding(.horizontal)
    }
}

struct MessageInputView: View {
    @Binding var messageText: String
    let onSend: () -> Void
    
    var body: some View {
        HStack {
            TextField("Message", text: $messageText)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal)
            
            Button(action: onSend) {
                Image(systemName: "arrow.up.circle.fill")
                    .font(.system(size: 30))
                    .foregroundColor(.yellow)
            }
            .padding(.trailing)
            .disabled(messageText.isEmpty)
        }
        .padding(.vertical)
    }
}

struct MessageBubble: View {
    let message: Message
    let isCurrentUser: Bool
    
    var body: some View {
        VStack(alignment: isCurrentUser ? .trailing : .leading) {
            Text(message.senderUsername)
                .font(.caption)
                .foregroundColor(.gray)
            
            HStack {
                if isCurrentUser {
                    Spacer()
                }
                
                Text(message.content)
                    .padding(10)
                    .background(isCurrentUser ? Color.yellow : Color.gray.opacity(0.2))
                    .cornerRadius(10)
                
                if !isCurrentUser {
                    Spacer()
                }
            }
        }
    }
}
