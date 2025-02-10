# IPERKA Projektarbeit: iOS Messenger

## Informieren
Ich habe mich über die wesentlichen Grundlagen informiert, die für den Bau einer iOS-Messenger-App mit SwiftUI, Firebase und einer AES-256-Bit-Verschlüsselung notwendig sind. Dabei habe ich mich intensiv mit SwiftUI (inkl. MVVM-Architektur), dem Firebase-Ökosystem (Echtzeit-Datenbank, Authentifizierung, Cloud Messaging) sowie der Implementierung und Verwaltung eines symmetrischen Verschlüsselungsverfahrens (AES 256) beschäftigt. Außerdem habe ich die grundlegende Chat-Logik definiert, damit Nutzer per Code einem Chat beitreten können.

## Planen
Ich habe meine Arbeit in zwei Wochen aufgeteilt. In der ersten Woche liegen zwei Ferientage sowie ein Schnuppertag, an dem ich nicht an dem Projekt arbeite. In der zweiten Woche ist ebenfalls ein Schnuppertag. Der genaue Zeitplan sieht folgendermaßen aus:

### Erste Woche
- **Montag (Tag 1):** Projektstart, Grundlagen und Vorbereitung  
- **Dienstag (Tag 2):** Schnuppertag, keine Projektarbeit  
- **Mittwoch (Tag 3):** Fortsetzung des Projekts  
- **Donnerstag:** Ferien, keine Projektarbeit  
- **Freitag:** Ferien, keine Projektarbeit  

### Zweite Woche
- **Montag (Tag 4):** Projektarbeit  
- **Dienstag (Tag 5):** Projektarbeit  
- **Mittwoch (Tag 6):** Schnuppertag, keine Projektarbeit  
- **Donnerstag (Tag 7):** Projektarbeit  
- **Freitag (Tag 8):** Projektabschluss und Tests

Ich habe alle notwendigen Schritte festgelegt: von der Integration der Firebase-Services und AES-Verschlüsselung bis hin zu UI-Design und abschließenden Tests. Zudem habe ich mir Pufferzeiten eingeplant, um eventuelle Probleme beheben zu können.

## Entscheiden
Ich habe mich für SwiftUI als Framework entschieden, weil es moderne UI-Entwicklung für iOS ermöglicht und gut mit der MVVM-Architektur harmoniert. Firebase eignet sich als Backend, da es eine benutzerfreundliche Authentifizierung sowie eine Realtime-Datenbank bietet und sich mit Swift relativ einfach integrieren lässt. Für die sichere Übertragung und Speicherung der Chat-Nachrichten habe ich AES 256-Bit ausgewählt, da es ein bewährter und sicherer Standard für symmetrische Verschlüsselung ist. Der Chatbeitritt per Code ermöglicht eine einfache und gleichzeitig sichere Lösung für geschlossene Chat-Gruppen.

## Realisieren
Ich habe die Umsetzung in die einzelnen Arbeitstage unterteilt, wobei die Tage, an denen ich nicht am Projekt arbeite, explizit ausgelassen werden:

### Tag 1 (Montag, Woche 1)
- Vorbereitung der Dokumentationsstruktur (IPERKA)  
- Erstellung eines neuen Xcode-Projekts  
- Erste Konfiguration von SwiftUI-Views und Firebase (Projekt in der Firebase Console anlegen)  

### Tag 2 (Dienstag, Woche 1)
- Schnuppertag, keine Projektarbeit  

### Tag 3 (Mittwoch, Woche 1)
- Grundlegende Firebase-Anbindung in Swift (Auth, Firestore oder Realtime DB)  
- Planung der Verschlüsselungslogik (AES 256)  
- Code-Architektur vorbereiten (MVVM in SwiftUI)

### Donnerstag und Freitag (Woche 1)
- Ferien, keine Projektarbeit

### Tag 4 (Montag, Woche 2)
- Implementierung der Authentifizierung (Registrierung/Anmeldung) in der App  
- Einrichtung des Chatbeitritts per Code (Generierung und Validierung)  

### Tag 5 (Dienstag, Woche 2)
- Integration der AES-256-Verschlüsselung:  
  - Nachrichten bei Versand verschlüsseln  
  - Empfangenene Nachrichten entschlüsseln  
- Erstellung einer Schlüsselverwaltung (lokal und in Firebase)

### Tag 6 (Mittwoch, Woche 2)
- Schnuppertag, keine Projektarbeit  

### Tag 7 (Donnerstag, Woche 2)
- Testen der Chatfunktionen mit echten Geräten bzw. Simulatoren  
- Integration von Firebase Cloud Messaging (Push Notifications)  
- Fehlerbehebung und Performance-Optimierung  

### Tag 8 (Freitag, Woche 2)
- Abschlussarbeiten und finale Tests  
- Dokumentation vervollständigen (Erkenntnisse, Projektverlauf, Code-Struktur)  
- Vorbereitung einer möglichen Präsentation und Lessons Learned  

## Kontrollieren
Ich habe nach jedem Meilenstein getestet, ob die App wie geplant funktioniert. Authentifizierungen und Datenbankzugriffe habe ich durch Test-User geprüft. Die Verschlüsselung habe ich verifiziert, indem ich Nachrichten in Klartext abgefangen habe und sicherstellte, dass nur verschlüsselte Inhalte in der Datenbank landen. Auch die Einladungs-Codes habe ich in mehreren Szenarien getestet, um sicherzustellen, dass nur Nutzer mit gültigem Code dem Chat beitreten können.

## Analysieren
### Selbstanalyse
Ich habe meinen Zeitplan größtenteils einhalten können. Die Schnuppertage und Ferientage haben meine Arbeitszeit verkürzt, aber ich habe die fehlenden Stunden durch fokussierte Arbeit an den restlichen Tagen kompensieren können. Die Planung in IPERKA hat mir geholfen, den Überblick zu behalten und auf auftretende Probleme flexibel zu reagieren.

### Sicherheitslücken
Ich habe sichergestellt, dass die AES-Schlüssel sicher gespeichert und übertragen werden. Ich habe die Firebase-Regeln so eingerichtet, dass kein unbefugter Zugriff erfolgen kann. Ich habe den Authentifizierungsablauf nach gängigen Best Practices gestaltet und Passwortrichtlinien eingeführt. Den Chatbeitritts-Code habe ich mit ausreichend Komplexität versehen, damit Bruteforce-Angriffe erschwert werden.
