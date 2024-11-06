
#include "func.h"
#include <stdio.h>


Library* createLibrary() {
    Library* lib = (Library*)malloc(sizeof(Library));
    if (!lib) return NULL;

    lib->items = (Media**)malloc(LIBRARY_SIZE * sizeof(Media*));
    if (!lib->items) {
        free(lib);
        return NULL;
    }

    lib->count = 0;
    for (int i = 0; i < LIBRARY_SIZE; i++) {
        lib->items[i] = NULL;
    }

    return lib;
}

void freeLibrary(Library* lib) {
    if (!lib) return;

    for (int i = 0; i < lib->count; i++) {
        freeMedia(lib->items[i]);
    }
    free(lib->items);
    free(lib);
}

Media* createMedia(MediaType type, const char* title, const char* author, int year) {
    Media* media = (Media*)malloc(sizeof(Media));
    if (!media) return NULL;

    media->title = strdup(title);
    media->author = strdup(author);
    if (!media->title || !media->author) {
        freeMedia(media);
        return NULL;
    }

    media->type = type;
    media->year = year;

    return media;
}

void freeMedia(Media* media) {
    if (!media) return;
    free(media->title);
    free(media->author);
    free(media);
}

int addMedia(Library* lib, Media* media) {
    if (!lib || !media || lib->count >= LIBRARY_SIZE) {
        return 0;
    }

    lib->items[lib->count++] = media;
    return 1;
}

const char* getMediaTypeString(MediaType type) {
    switch (type) {
        case BOOK: return "Buch";
        case MOVIE: return "Film";
        case GAME: return "Spiel";
        case MUSIC: return "Musik";
        default: return "Unbekannt";
    }
}

void displayMedia(const Media* media) {
    if (!media) return;
    printf("Typ: %s\n", getMediaTypeString(media->type));
    printf("Titel: %s\n", media->title);
    printf("Autor/Künstler: %s\n", media->author);
    printf("Jahr: %d\n", media->year);
    printf("------------------------\n");
}

void displayLibrary(const Library* lib) {
    if (!lib) return;

    printf("\n=== Medienbibliothek (%d/%d) ===\n", lib->count, LIBRARY_SIZE);
    for (int i = 0; i < lib->count; i++) {
        printf("\nMedium %d:\n", i + 1);
        displayMedia(lib->items[i]);
    }
}

MediaType getMediaTypeFromInput() {
    printf("Medientyp auswählen:\n");
    printf("1. Buch\n");
    printf("2. Film\n");
    printf("3. Spiel\n");
    printf("4. Musik\n");

    int choice;
    scanf("%d", &choice);
    getchar();

    switch (choice) {
        case 1: return BOOK;
        case 2: return MOVIE;
        case 3: return GAME;
        case 4: return MUSIC;
        default: return UNKNOWN;
    }
}

void editMedia(Media* media) {
    if (!media) return;

    char buffer[MAX_TITLE_LENGTH];
    printf("Neuer Titel (Enter für keine Änderung): ");
    if (fgets(buffer, MAX_TITLE_LENGTH, stdin) != NULL && buffer[0] != '\n') {
        buffer[strcspn(buffer, "\n")] = 0;
        free(media->title);
        media->title = strdup(buffer);
    }

    printf("Neuer Autor (Enter für keine Änderung): ");
    if (fgets(buffer, MAX_AUTHOR_LENGTH, stdin) != NULL && buffer[0] != '\n') {
        buffer[strcspn(buffer, "\n")] = 0;
        free(media->author);
        media->author = strdup(buffer);
    }

    printf("Neues Jahr (0 für keine Änderung): ");
    int year;
    scanf("%d", &year);
    getchar();
    if (year != 0) {
        media->year = year;
    }
}

void handleUserInput(Library* lib) {
    while (1) {
        printf("\n=== Medienbibliothek Menu ===\n");
        printf("1. Medium hinzufügen\n");
        printf("2. Bibliothek anzeigen\n");
        printf("3. Medium bearbeiten\n");
        printf("4. Beenden\n");
        printf("Wählen Sie eine Option: ");

        int choice;
        scanf("%d", &choice);
        getchar();

        switch (choice) {
            case 1: {
                if (lib->count >= LIBRARY_SIZE) {
                    printf("Bibliothek ist voll!\n");
                    break;
                }

                MediaType type = getMediaTypeFromInput();

                char title[MAX_TITLE_LENGTH];
                printf("Titel: ");
                fgets(title, MAX_TITLE_LENGTH, stdin);
                title[strcspn(title, "\n")] = 0;

                char author[MAX_AUTHOR_LENGTH];
                printf("Autor/Künstler: ");
                fgets(author, MAX_AUTHOR_LENGTH, stdin);
                author[strcspn(author, "\n")] = 0;

                int year;
                printf("Erscheinungsjahr: ");
                scanf("%d", &year);
                getchar();

                Media* media = createMedia(type, title, author, year);
                if (addMedia(lib, media)) {
                    printf("Medium erfolgreich hinzugefügt!\n");
                } else {
                    printf("Fehler beim Hinzufügen des Mediums!\n");
                    freeMedia(media);
                }
                break;
            }
            case 2:
                displayLibrary(lib);
                break;
            case 3: {
                printf("Welches Medium möchten Sie bearbeiten? (1-%d): ", lib->count);
                int index;
                scanf("%d", &index);
                getchar();

                if (index > 0 && index <= lib->count) {
                    editMedia(lib->items[index-1]);
                    printf("Medium erfolgreich bearbeitet!\n");
                } else {
                    printf("Ungültiger Index!\n");
                }
                break;
            }
            case 4:
                return;
            default:
                printf("Ungültige Option!\n");
        }
    }
}