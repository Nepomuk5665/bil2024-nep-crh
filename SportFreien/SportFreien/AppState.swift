//
//  AppState.swift
//  SportFreien
//
//  Created by Nepomuk Crhonek on 10.02.2025.
//


import Foundation

class AppState: ObservableObject {
    @Published var hasUsername: Bool
    @Published var currentGroupCode: String?
    
    init() {
        // Prüft ob ein Username existiert und nicht leer ist
        let storedUsername = UserDefaults.standard.string(forKey: "username")
        self.hasUsername = storedUsername != nil && !storedUsername!.isEmpty
        
        self.currentGroupCode = UserDefaults.standard.string(forKey: "groupCode")
    }
}
