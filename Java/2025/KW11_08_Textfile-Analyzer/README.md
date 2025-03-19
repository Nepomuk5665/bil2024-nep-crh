# Textfile Analyzer

A Java application that analyzes text files and provides useful information about word frequency and statistics.

## Features

- Reads text files of any size
- Counts the number of unique words
- Counts the total number of words
- Identifies the most common word
- Shows word frequency for all words
- Generates an evaluation file with the results

## How to Use

1. Run the application
2. Enter the path to the text file you want to analyze when prompted
3. The application will create a file named `<original_filename>_evaluation.txt` in the same directory as the input file
4. The evaluation file contains the analysis results including word frequency data

## Output Format

The generated evaluation file follows this format:

```
[Date / Time] [Name of the analyzed file]
--------------------------------------------
Number of unique words:	[Count]
Total number of words:	[Count]
Most common word:	[Word]
--------------------------------------------
[Word]	[Count]
[Word]	[Count]
...
```

## Architecture

The application is built with a clean separation of concerns:

- `Main`: Entry point of the application
- `TextFileAnalyzer`: Contains the main logic for analyzing text files
- `FileHandler`: Handles all file operations (reading/writing)
- `UserInterface`: Manages user interaction
- `AnalysisResult`: Stores the analysis results

This architecture ensures that file operations, user interactions, and text analysis are properly separated, making the code modular and maintainable.