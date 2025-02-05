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
    var body: some View {
        VStack{
            TextField("Group Code", text: $code)
                .padding(22)
            Button(action:{
                print("Continued as \(username)")
            }, label: {
                ZStack{
                    Rectangle()
                        .frame(width: 200, height: 50)
                        .cornerRadius(20)
                        .foregroundStyle(.yellow)
                        
                    Text("Join")
                        .foregroundStyle(.black)
                    
                }
                
                
            })
            // if no group exists w code should create one
        }
        
        
        
    }
}

#Preview {
    Chats()
}
