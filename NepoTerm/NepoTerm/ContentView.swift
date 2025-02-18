//
//  ContentView.swift
//  NepoTerm
//
//  Created by Nepomuk Crhonek on 14.02.2025.
//

import SwiftUI
import Foundation
import Combine

class Shell: ObservableObject {
    private var process: Process?
    private var pipe: Pipe?
    @Published var output: String = ""
    
    func execute(_ command: String) {
        // Clean up any existing process
        terminate()
        
        // Create a new Process and Pipe
        process = Process()
        pipe = Pipe()
        
        // Configure the process
        process?.executableURL = URL(fileURLWithPath: "/bin/zsh")
        process?.arguments = ["-c", command]
        process?.standardOutput = pipe
        process?.standardError = pipe
        
        // Set up pipe reading
        let handler = pipe?.fileHandleForReading
        handler?.readabilityHandler = { [weak self] handle in
            let data = handle.availableData
            if data.isEmpty {
                // End of process output
                DispatchQueue.main.async {
                    handler?.readabilityHandler = nil
                }
                return
            }
            
            if let string = String(data: data, encoding: .utf8) {
                DispatchQueue.main.async {
                    self?.output += string
                }
            }
        }
        
        // Set up process termination
        process?.terminationHandler = { [weak self] process in
            DispatchQueue.main.async {
                handler?.readabilityHandler = nil
                self?.pipe = nil
                self?.process = nil
            }
        }
        
        do {
            try process?.run()
            process?.waitUntilExit()
        } catch {
            output += "\nError: \(error.localizedDescription)"
            terminate()
        }
    }
    
    func terminate() {
        if process?.isRunning == true {
            process?.terminate()
        }
        pipe?.fileHandleForReading.readabilityHandler = nil
        process = nil
        pipe = nil
    }
    
    deinit {
        terminate()
    }
}

struct TerminalLine: Identifiable, Equatable {
    let id = UUID()
    let content: String
    let isCommand: Bool
    
    static func == (lhs: TerminalLine, rhs: TerminalLine) -> Bool {
        lhs.id == rhs.id && lhs.content == rhs.content && lhs.isCommand == rhs.isCommand
    }
}

class TerminalState: ObservableObject {
    @Published var lines: [TerminalLine] = []
    @Published var currentInput: String = ""
    private let shell = Shell()
    private var currentDirectory: String = FileManager.default.currentDirectoryPath
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        shell.$output
            .filter { !$0.isEmpty }
            .removeDuplicates()
            .sink { [weak self] newOutput in
                self?.lines.append(TerminalLine(content: newOutput, isCommand: false))
            }
            .store(in: &cancellables)
    }
    
    func executeCommand(_ command: String) {
        lines.append(TerminalLine(content: "\(currentDirectory)$ \(command)", isCommand: true))
        
        // Handle cd command specially
        if command.hasPrefix("cd ") {
            let newPath = command.dropFirst(3).trimmingCharacters(in: .whitespaces)
            if let resolvedPath = (newPath as NSString).expandingTildeInPath as String? {
                do {
                    try FileManager.default.changeCurrentDirectoryPath(resolvedPath)
                    currentDirectory = FileManager.default.currentDirectoryPath
                } catch {
                    lines.append(TerminalLine(content: "cd: \(error.localizedDescription)", isCommand: false))
                }
            }
            return
        }
        
        DispatchQueue.global(qos: .userInitiated).async { [weak self] in
            self?.shell.execute(command)
        }
    }
}

struct ContentView: View {
    @StateObject private var terminalState = TerminalState()
    @FocusState private var isInputFocused: Bool
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            ScrollViewReader { proxy in
                ScrollView {
                    LazyVStack(alignment: .leading, spacing: 2) {
                        ForEach(terminalState.lines) { line in
                            Text(line.content)
                                .font(.system(.body, design: .monospaced))
                                .textSelection(.enabled)
                                .id(line.id)
                        }
                    }
                    .padding(.horizontal)
                }
                .onChange(of: terminalState.lines) { newLines in
                    if let lastLine = newLines.last {
                        proxy.scrollTo(lastLine.id, anchor: .bottom)
                    }
                }
            }
            
            Divider()
            
            HStack(spacing: 8) {
                Text("\(FileManager.default.currentDirectoryPath)$")
                    .foregroundColor(.green)
                    .font(.system(.body, design: .monospaced))
                
                TextField("", text: $terminalState.currentInput)
                    .textFieldStyle(.plain)
                    .font(.system(.body, design: .monospaced))
                    .focused($isInputFocused)
                    .onSubmit {
                        if !terminalState.currentInput.isEmpty {
                            terminalState.executeCommand(terminalState.currentInput)
                            terminalState.currentInput = ""
                        }
                    }
            }
            .padding()
        }
        .background(Color.black)
        .foregroundColor(.white)
        .onAppear {
            isInputFocused = true
        }
    }
}

#Preview {
    ContentView()
}
