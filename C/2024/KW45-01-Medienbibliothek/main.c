
#include <stdio.h>
#include "func.h"

int main(void) {
    Library* lib = createLibrary();
    if (!lib) {
        printf("Fehler beim Erstellen der Bibliothek!\n");
        return 1;
    }

    // Beispiel-Medien zum Testen
    Media* book = createMedia(BOOK, "Der Herr der Ringe", "J.R.R. Tolkien", 1954);
    Media* movie = createMedia(MOVIE, "Matrix", "Wachowski", 1999);
    Media* game = createMedia(GAME, "The Witcher 3", "CD Projekt Red", 2015);

    addMedia(lib, book);
    addMedia(lib, movie);
    addMedia(lib, game);

    handleUserInput(lib);

    freeLibrary(lib);
    return 0;
}