//
//  Encryption.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation
import CryptoKit

enum SimpleEncryption {
    static let key = SymmetricKey(size: .bits256)
    
    static func encrypt(_ string: String) -> String? {
        guard let data = string.data(using: .utf8) else { return nil }
        guard let encrypted = try? AES.GCM.seal(data, using: key).combined else { return nil }
        return encrypted.base64EncodedString()
    }
    
    static func decrypt(_ string: String) -> String? {
        guard let data = Data(base64Encoded: string) else { return nil }
        guard let box = try? AES.GCM.SealedBox(combined: data) else { return nil }
        guard let decrypted = try? AES.GCM.open(box, using: key) else { return nil }
        return String(data: decrypted, encoding: .utf8)
    }
}
