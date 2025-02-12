//
//  Message.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation

struct Message: Identifiable, Codable, Equatable {
    let id: String
    var content: String
    let senderUsername: String
    let timestamp: Date
    
    static func == (lhs: Message, rhs: Message) -> Bool {
        lhs.id == rhs.id &&
        lhs.content == rhs.content &&
        lhs.senderUsername == rhs.senderUsername &&
        lhs.timestamp == rhs.timestamp
    }
}
