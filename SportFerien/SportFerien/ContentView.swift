//
//  ContentView.swift
//  SportFerien
//
//  Created by Nepomuk Crhonek on 03.02.2025.
//

import SwiftUI

struct ContentView: View {
    @State private var username = ""
    var body: some View {
        VStack {
            TextField("Username", text: $username)
                .autocorrectionDisabled(true)
                .padding()
            Button(action:{
                print("Continued as \(username)")
            }, label: {
                ZStack{
                    Rectangle()
                        .frame(width: 200, height: 50)
                        .cornerRadius(20)
                        
                    Text("Continue")
                        .foregroundStyle(.black)
                    
                }
                
                
            })
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
