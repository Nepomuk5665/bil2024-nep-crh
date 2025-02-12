//
//  Encryption.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//

import Foundation
import CryptoKit

enum SimpleEncryption {
    private static let salt = "SportFreienApp123"
    
    static var key: SymmetricKey {
        let keyString = "SportFreienSecretKey123\(salt)"
        let keyData = keyString.data(using: .utf8)!
        return SymmetricKey(data: SHA256.hash(data: keyData))
    }
    
    static func encrypt(_ string: String) -> String? {
        do {
            guard let data = string.data(using: .utf8) else {
                print("Failed to convert string to data for encryption")
                return nil
            }
            
            let encrypted = try AES.GCM.seal(data, using: key)
            guard let combined = encrypted.combined else {
                print("Failed to get combined data after encryption")
                return nil
            }
            
            let result = combined.base64EncodedString()
            print("Successfully encrypted message. Length: \(result.count)")
            return result
        } catch {
            print("Encryption error: \(error)")
            return nil
        }
    }
    
    static func decrypt(_ string: String) -> String? {
        do {
            guard let data = Data(base64Encoded: string) else {
                print("Failed to decode base64 string for decryption")
                return nil
            }
            
            let sealedBox = try AES.GCM.SealedBox(combined: data)
            let decryptedData = try AES.GCM.open(sealedBox, using: key)
            
            guard let result = String(data: decryptedData, encoding: .utf8) else {
                print("Failed to convert decrypted data to string")
                return nil
            }
            
            print("Successfully decrypted message. Result length: \(result.count)")
            return result
        } catch {
            print("Decryption error: \(error)")
            return nil
        }
    }
}
