#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

#define grösseSpielfeld 3
#define GREEN   "\x1b[32m"
#define BLUE    "\x1b[34m"
#define RED     "\x1b[31m"
#define MAGENTA "\x1b[35m"
#define RESET   "\x1b[0m"

// Struktur für einen Spielzug
typedef struct {
    int zeile;
    int spalte;
} Zug;

int max(int a, int b) {
    return (a > b) ? a : b;
}

int min(int a, int b) {
    return (a < b) ? a : b;
}

bool pruefeGewinner(int spieler[grösseSpielfeld][grösseSpielfeld]) {
    for(int i = 0; i < grösseSpielfeld; i++) {
        if(spieler[i][0] == 1 && spieler[i][1] == 1 && spieler[i][2] == 1) return true;
    }

    for(int j = 0; j < grösseSpielfeld; j++) {
        if(spieler[0][j] == 1 && spieler[1][j] == 1 && spieler[2][j] == 1) return true;
    }

    if(spieler[0][0] == 1 && spieler[1][1] == 1 && spieler[2][2] == 1) return true;
    if(spieler[0][2] == 1 && spieler[1][1] == 1 && spieler[2][0] == 1) return true;

    return false;
}

bool pruefeUnentschieden(int spielerEins[grösseSpielfeld][grösseSpielfeld], int spielerZwei[grösseSpielfeld][grösseSpielfeld]) {
    for(int i = 0; i < grösseSpielfeld; i++) {
        for(int j = 0; j < grösseSpielfeld; j++) {
            if(spielerEins[i][j] == 0 && spielerZwei[i][j] == 0) return false;
        }
    }
    return true;
}


bool istZugMoeglich(int spielerEins[grösseSpielfeld][grösseSpielfeld],
                   int spielerZwei[grösseSpielfeld][grösseSpielfeld],
                   int zeile, int spalte) {
    return (spielerEins[zeile][spalte] == 0 && spielerZwei[zeile][spalte] == 0);
}

// Bewertet den aktuellen Spielzustand (für Minimax)
int bewerteSituation(int spielerEins[grösseSpielfeld][grösseSpielfeld],
                    int spielerZwei[grösseSpielfeld][grösseSpielfeld]) {
    if(pruefeGewinner(spielerZwei)) return 10;  // Bot gewinnt
    if(pruefeGewinner(spielerEins)) return -10; // Mensch gewinnt
    if(pruefeUnentschieden(spielerEins, spielerZwei)) return 0;
    return 0;
}

// Minimax Algorithmus
int minimax(int spielerEins[grösseSpielfeld][grösseSpielfeld],
           int spielerZwei[grösseSpielfeld][grösseSpielfeld],
           int tiefe, bool istMaximierend) {

    int punktzahl = bewerteSituation(spielerEins, spielerZwei);

    if(punktzahl == 10) return punktzahl - tiefe;
    if(punktzahl == -10) return punktzahl + tiefe;
    if(pruefeUnentschieden(spielerEins, spielerZwei)) return 0;

    if(istMaximierend) {
        int besteWertung = -1000;
        for(int i = 0; i < grösseSpielfeld; i++) {
            for(int j = 0; j < grösseSpielfeld; j++) {
                if(istZugMoeglich(spielerEins, spielerZwei, i, j)) {
                    spielerZwei[i][j] = 1;
                    besteWertung = max(besteWertung,
                        minimax(spielerEins, spielerZwei, tiefe + 1, !istMaximierend));
                    spielerZwei[i][j] = 0;
                }
            }
        }
        return besteWertung;
    } else {
        int besteWertung = 1000;
        for(int i = 0; i < grösseSpielfeld; i++) {
            for(int j = 0; j < grösseSpielfeld; j++) {
                if(istZugMoeglich(spielerEins, spielerZwei, i, j)) {
                    spielerEins[i][j] = 1;
                    besteWertung = min(besteWertung,
                        minimax(spielerEins, spielerZwei, tiefe + 1, !istMaximierend));
                    spielerEins[i][j] = 0;
                }
            }
        }
        return besteWertung;
    }
}

// Findet den besten Zug für den Bot
Zug findeBestenZug(int spielerEins[grösseSpielfeld][grösseSpielfeld],
                   int spielerZwei[grösseSpielfeld][grösseSpielfeld]) {
    int besteWertung = -1000;
    Zug besterZug = {-1, -1};

    for(int i = 0; i < grösseSpielfeld; i++) {
        for(int j = 0; j < grösseSpielfeld; j++) {
            if(istZugMoeglich(spielerEins, spielerZwei, i, j)) {
                spielerZwei[i][j] = 1;
                int zugWertung = minimax(spielerEins, spielerZwei, 0, false);
                spielerZwei[i][j] = 0;

                if(zugWertung > besteWertung) {
                    besterZug.zeile = i;
                    besterZug.spalte = j;
                    besteWertung = zugWertung;
                }
            }
        }
    }
    return besterZug;
}



int main(void) {
    printf("Tick Tak Toe\n");
    printf("Bessere Version: https://apps.apple.com/us/app/netik/id1606215184\n\n");

    // Spielmodus auswählen
    int spielmodus;
    printf("Wähle den Spielmodus:\n");
    printf("1. Gegen einen Freund spielen\n");
    printf("2. Gegen den Computer spielen\n");
    printf("Deine Wahl (1 oder 2): ");
    scanf("%d", &spielmodus);

    int x;
    int y;
    int weristdran = 1;
    bool richtigeeingabe;
    bool gamefinished = false;
    int spielerEins[grösseSpielfeld][grösseSpielfeld] = {{0}};
    int spielerZwei[grösseSpielfeld][grösseSpielfeld] = {{0}};
    int Spielfeld[grösseSpielfeld][grösseSpielfeld] = {{0}};

    while (!gamefinished) {
        // Spielfeld anzeigen
        printf("\nDas spielfeld jetzt:\n");
        printf("  ");
        for(int a = 0; a < grösseSpielfeld; a++) {
            printf(RED " %d " , a+1 );
        }
        printf(" X"RESET);
        printf("\n");

        for(int i = 0; i < grösseSpielfeld; i++) {
            for(int a = 0; a < 1; a++) {
                printf(MAGENTA "%d " RESET , a+i+1);
            }

            for(int j = 0; j < grösseSpielfeld; j++) {
                if(spielerEins[i][j] == 1) {
                    printf("[");
                    printf(GREEN"X"RESET);
                    printf("]");
                }else if (spielerZwei[i][j] == 1) {
                    printf("[");
                    printf(BLUE"O"RESET);
                    printf("]");
                }else {
                    printf("[ ]");
                }
            }
            printf("\n");
        }
        printf(MAGENTA"Y\n" RESET);

        if(weristdran == 1) {
            // Spieler 1 ist immer ein Mensch
            bool schongewählt = true;
            while (schongewählt) {
                printf(GREEN "Spieler 1, wo willst du setzen?\n"RESET);
                printf(RED"X: "RESET);
                richtigeeingabe = false;
                while(!richtigeeingabe) {
                    scanf("%d", &x);
                    if (x >= 1 && x <= grösseSpielfeld){
                        richtigeeingabe = true;
                    }else {
                        printf(RED"\nBro gib eine Richtige Eingabe ein!\n" RESET);
                        printf(RED"X: " RESET);
                    }
                }
                richtigeeingabe = false;
                printf(MAGENTA"Y: " RESET);
                while(!richtigeeingabe) {
                    scanf("%d", &y);
                    if (y >= 1 && y <= grösseSpielfeld){
                        richtigeeingabe = true;
                    }else {
                        printf(RED"\nBro gib eine Richtige Eingabe ein!\n" RESET);
                        printf(MAGENTA"Y: ");
                    }
                }

                if(spielerEins[y-1][x-1] == 1 || spielerZwei[y-1][x-1] == 1) {
                    printf(RED"\nDieses Feld ist bereits belegt.\n"RESET);
                }else {
                    schongewählt = false;
                }
            }
            spielerEins[y-1][x-1] = 1;

            if(pruefeGewinner(spielerEins)) {
                printf(GREEN"\nSpieler 1 hat gewonnen!\n"RESET);
                gamefinished = true;
                continue;
            }
        } else {
            if(spielmodus == 1) {
                // Mensch vs Mensch: Spieler 2 ist ein Mensch
                bool schongewählt = true;
                while (schongewählt) {
                    printf(BLUE "Spieler 2, wo willst du setzen?\n"RESET);
                    printf(RED"X: "RESET);
                    richtigeeingabe = false;
                    while(!richtigeeingabe) {
                        scanf("%d", &x);
                        if (x >= 1 && x <= grösseSpielfeld){
                            richtigeeingabe = true;
                        }else {
                            printf(RED"\nBitte gib eine Richtige Eingabe ein!\n" RESET);
                            printf(RED"X: " RESET);
                        }
                    }
                    richtigeeingabe = false;
                    printf(MAGENTA"Y: " RESET);
                    while(!richtigeeingabe) {
                        scanf("%d", &y);
                        if (y >= 1 && y <= grösseSpielfeld){
                            richtigeeingabe = true;
                        }else {
                            printf(RED"\nBitte gib eine Richtige Eingabe ein!\n" RESET);
                            printf(MAGENTA"Y: ");
                        }
                    }

                    if(spielerEins[y-1][x-1] == 1 || spielerZwei[y-1][x-1] == 1) {
                        printf(RED"\nDieses Feld ist bereits belegt.\n"RESET);
                    }else {
                        schongewählt = false;
                    }
                }
                spielerZwei[y-1][x-1] = 1;
            } else {
                // Mensch vs Computer: Bot macht seinen Zug
                printf(BLUE"\nComputer ist am Zug...\n"RESET);
                Zug botZug = findeBestenZug(spielerEins, spielerZwei);
                spielerZwei[botZug.zeile][botZug.spalte] = 1;
                printf(BLUE"Computer setzt auf Position X:%d Y:%d\n"RESET,
                       botZug.spalte + 1, botZug.zeile + 1);
            }

            if(pruefeGewinner(spielerZwei)) {
                if(spielmodus == 1) {
                    printf(BLUE"\nSpieler 2 hat gewonnen!\n"RESET);
                } else {
                    printf(BLUE"\nDer Computer hat gewonnen!\n"RESET);
                }
                gamefinished = true;
                continue;
            }
        }

        if(pruefeUnentschieden(spielerEins, spielerZwei)) {
            printf("\nUnentschieden!\n");
            gamefinished = true;
            continue;
        }

        weristdran = (weristdran == 1) ? 2 : 1;
    }

    return 0;
}