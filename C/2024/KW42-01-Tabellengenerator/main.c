#include <stdio.h>

int main(void) {
    int zeilen;
    int spalten;

    printf("Wie viele Zeilen?\n");
    scanf("%d", &zeilen);
    printf("Wie viele Spalten?\n");
    scanf("%d", &spalten);

    FILE *index;
    index = fopen("../index.html", "w");

    if (index == NULL) {
        perror("Error opening file");
        return 1;
    }

    fprintf(index, "<!DOCTYPE html>\n");
    fprintf(index, "<html lang=\"de\">\n");
    fprintf(index, "  <head>\n");
    fprintf(index, "    <meta charset=\"utf-8\">\n");
    fprintf(index, "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
    fprintf(index, "    <title>Tabelle</title>\n");
    fprintf(index, "  </head>\n");
    fprintf(index, "  <body>\n");
    fprintf(index, "    \n");
    fprintf(index, "    <p>Tabelle.</p>\n");
    fprintf(index, "    <table>\n");
    for (int i = 0; i < spalten; i++) {
        
    }
    fprintf(index, "    </table>\n");
    fprintf(index, "  </body>\n");
    fprintf(index, "</html>\n");

    fclose(index);

    return 0;
}