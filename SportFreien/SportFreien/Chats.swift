//
//  Chats.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 05.02.2025.
//

import SwiftUI

struct Chats: View {
    @AppStorage("username") var username = ""
    @State private var code: String = ""
    @StateObject private var chatManager = ChatManager()
    
    var body: some View {
        NavigationStack {
            VStack {
                if chatManager.currentGroup == nil {
                    VStack {
                        TextField("Group Code", text: $code)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .padding(22)
                        
                        Button(action: {
                            if !code.isEmpty {
                                chatManager.joinGroup(code: code, username: username)
                            }
                        }) {
                            ZStack {
                                Rectangle()
                                    .frame(width: 200, height: 50)
                                    .cornerRadius(20)
                                    .foregroundStyle(.yellow)
                                
                                Text("Join")
                                    .foregroundStyle(.black)
                            }
                        }
                    }
                } else {
                    ChatView(chatManager: chatManager)
                }
            }
        }
    }
}

#Preview {
    Chats()
}
