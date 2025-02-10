//
//  ContentView.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 03.02.2025.
//

import SwiftUI

struct ContentView: View {
    @AppStorage("username") var username = ""
    @StateObject private var appState = AppState()
    @State private var showAlert = false
    
    var body: some View {
        NavigationStack {
            if !appState.hasUsername {
                // Username Setup View
                VStack {
                    TextField("Username", text: $username)
                        .autocorrectionDisabled(true)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                        .padding()
                        .onChange(of: username) {
                            showAlert = false
                        }
                    
                    if showAlert {
                        Text("🔝You need a username🔝")
                            .foregroundStyle(.red)
                    }
                    
                    Button(action: {
                        if !username.isEmpty {
                            appState.hasUsername = true
                        } else {
                            showAlert = true
                        }
                    }) {
                        ZStack {
                            Rectangle()
                                .frame(width: 200, height: 50)
                                .cornerRadius(20)
                                .foregroundStyle(.yellow)
                            
                            Text("Continue")
                                .foregroundStyle(.black)
                        }
                    }
                }
                .padding()
            } else {
                // Main Chat Flow
                Chats()
            }
        }
    }
}




#Preview {
    ContentView()
}
