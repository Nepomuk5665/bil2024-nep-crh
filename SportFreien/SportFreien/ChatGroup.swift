//
//  ChatGroup.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation

struct ChatGroup: Identifiable, Codable {
    let id: String
    let code: String
    var members: [String]
    
    enum CodingKeys: String, CodingKey {
        case id
        case code
        case members
    }
}
