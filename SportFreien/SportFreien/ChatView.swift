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
        VStack {
            ScrollView {
                LazyVStack(alignment: .leading, spacing: 10) {
                    ForEach(chatManager.messages) { message in
                        HStack {
                            if message.senderUsername == username {
                                Spacer()
                            }
                            
                            VStack(alignment: message.senderUsername == username ? .trailing : .leading) {
                                Text(message.senderUsername)
                                    .font(.caption)
                                    .foregroundColor(.gray)
                                
                                Text(message.content)
                                    .padding(10)
                                    .background(message.senderUsername == username ? Color.yellow : Color.gray.opacity(0.2))
                                    .cornerRadius(10)
                            }
                            
                            if message.senderUsername != username {
                                Spacer()
                            }
                        }
                        .padding(.horizontal)
                    }
                }
            }
            
            HStack {
                TextField("Message", text: $messageText)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)
                
                Button(action: {
                    if !messageText.isEmpty {
                        chatManager.sendMessage(messageText, from: username)
                        messageText = ""
                    }
                }) {
                    Image(systemName: "arrow.up.circle.fill")
                        .font(.system(size: 30))
                        .foregroundColor(.yellow)
                }
                .padding(.trailing)
            }
            .padding(.vertical)
        }
        .navigationTitle("Chat")
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
