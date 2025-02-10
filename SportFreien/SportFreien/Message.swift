//
//  Message.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation

struct Message: Identifiable, Codable {
    let id: String
    var content: String
    let senderUsername: String
    let timestamp: Date
}
