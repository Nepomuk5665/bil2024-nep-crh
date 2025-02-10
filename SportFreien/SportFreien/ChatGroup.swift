//
//  ChatGroup.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//



struct ChatGroup: Identifiable, Codable {
    let id: String
    let code: String
    var members: [String]
}
