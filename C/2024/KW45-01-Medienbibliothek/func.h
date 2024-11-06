// func.h
#ifndef FUNC_H
#define FUNC_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TITLE_LENGTH 100
#define MAX_AUTHOR_LENGTH 100
#define LIBRARY_SIZE 20

// Enum für Medientypen
typedef enum {
    BOOK,
    MOVIE,
    GAME,
    MUSIC,
    UNKNOWN
} MediaType;

// Struktur für ein Medium
typedef struct {
    MediaType type;
    char* title;
    char* author;
    int year;
} Media;

// Struktur für die Bibliothek
typedef struct {
    Media** items;  // Array von Zeigern auf Media
    int count;      // Aktuelle Anzahl der Medien
} Library;

// Funktionen-Deklarationen
Library* createLibrary();
void freeLibrary(Library* lib);
Media* createMedia(MediaType type, const char* title, const char* author, int year);
void freeMedia(Media* media);
int addMedia(Library* lib, Media* media);
void displayMedia(const Media* media);
void displayLibrary(const Library* lib);
void editMedia(Media* media);
const char* getMediaTypeString(MediaType type);
MediaType getMediaTypeFromInput();
void handleUserInput(Library* lib);

#endif