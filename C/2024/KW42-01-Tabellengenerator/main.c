#include <stdio.h>
#include <string.h>
int main(void) {
    printf("Das Programm wurde für  gemacht\n\n");

    int zeilen;
    int spalten;
    int pixel;
    int wahl;
    int änderungZeile;
    int änderungSpalte;


    printf("Wie viele Spalten?\n");
    scanf("%d", &spalten);

    printf("Wie viele Zeilen?\n");
    scanf("%d", &zeilen);

    printf("Border in pixel: ");
    scanf("%d", &pixel);

    do{
    printf("\nWillst du einzelne Zellen anpassen? 1 Yes, 2 No \n");
    scanf("%d", &wahl);
        if(wahl != 2 && wahl != 1) {
            printf("falsche eingabe");
        }
    }while(wahl != 2 && wahl != 1);
    if(wahl == 1) {
        printf("In welcher Zeile?\n");
        scanf("%d", &änderungZeile);
        printf("Wie viele Spalte?\n");
        scanf("%d", &änderungSpalte);
    }
    FILE *index;
    index = fopen("../index.html", "w");

    if (index == NULL) {
        perror("Error opening file");
        return 1;
    }

    fprintf(index, "<!DOCTYPE html>\n");
    fprintf(index, "<html lang=\"de\">\n");
    fprintf(index, "  <head>\n");
    fprintf(index, "      <style>\n");
    fprintf(index, "          table, th, td {\n");
    fprintf(index, "              border: %dpx solid;\n", pixel);
    fprintf(index, "          }\n");
    fprintf(index, "            #change {\n");
    fprintf(index, "                /*chnage here*/\n");
    fprintf(index, "            }\n");
    fprintf(index, "      </style>\n");
    fprintf(index, "    <meta charset=\"utf-8\">\n");
    fprintf(index, "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
    fprintf(index, "    <title>Tabelle</title>\n");
    fprintf(index, "  </head>\n");
    fprintf(index, "  <body>\n");
    fprintf(index, "    \n");
    fprintf(index, "    <h1>einfache Tabelle von C</h1>\n");
    fprintf(index, "    <table>\n");

    if (zeilen != 0 && zeilen != 0) {
        fprintf(index, "        <tr>\n");
        for (int i = 0; i < spalten; i++) {
            if (änderungSpalte == i && änderungZeile == 1) {
                fprintf(index, "            <th>Header %d</th>\n", i+1);
            }else {
                fprintf(index, "            <th id=\"change\"Header %d</th>\n", i+1);
            }
        }
        fprintf(index, "        </tr>\n");

        for (int i = 0; i < zeilen-1; i++) {
            fprintf(index, "        <tr>\n");
            for (int j = 0; j < spalten; j++) {
                fprintf(index, "            <th>Hello World</th>\n");
            }
            fprintf(index, "        </tr>\n");
        }
        printf("\nDkument mit tabelle wurde erstellt");
    }else {
        if (index != NULL) {
            printf("\nHTML dokument ohne Tabelle erstellt");
        }
    }

    fprintf(index, "    </table>\n");
    fprintf(index, "  </body>\n");
    fprintf(index, "</html>\n");

    fclose(index);

    return 0;
}