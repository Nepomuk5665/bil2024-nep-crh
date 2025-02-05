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
        TextField("group Code", text: $code)
        
            
    }
}

#Preview {
    Chats()
}
