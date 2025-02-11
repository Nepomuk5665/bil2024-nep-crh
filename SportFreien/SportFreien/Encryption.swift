//
//  Encryption.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation
import CryptoKit

enum SimpleEncryption {
    // Ein statischer String als Salt für mehr Konsistenz
    private static let salt = "SportFreienApp123"
    
    // Statischer Schlüssel aus einem festen String
    static var key: SymmetricKey {
        let keyString = "SportFreienSecretKey123\(salt)"
        let keyData = keyString.data(using: .utf8)!
        return SymmetricKey(data: SHA256.hash(data: keyData))
    }
    
    static func encrypt(_ string: String) -> String? {
        do {
            let data = string.data(using: .utf8)!
            let encrypted = try AES.GCM.seal(data, using: key)
            return encrypted.combined?.base64EncodedString()
        } catch {
            print("Encryption error: \(error)")
            return nil
        }
    }
    
    static func decrypt(_ string: String) -> String? {
        do {
            guard let data = Data(base64Encoded: string) else { return nil }
            let sealedBox = try AES.GCM.SealedBox(combined: data)
            let decryptedData = try AES.GCM.open(sealedBox, using: key)
            return String(data: decryptedData, encoding: .utf8)
        } catch {
            print("Decryption error: \(error)")
            return nil
        }
    }
}
