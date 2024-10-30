#include <stdio.h>
#define FIELDSIZE 5
#include <stdlib.h>
#include <time.h>

#define YELLOW  "\x1b[33m"
#define BLUE    "\x1b[34m"
#define RESET   "\x1b[0m"

int main(void) {
    srand(time(0));
    int field[FIELDSIZE][FIELDSIZE] = {{0}};
    int randField[FIELDSIZE][FIELDSIZE] = {{0}};
    int howMany = 0, x = 0, y = 0, versuche = 0;
    int min = 1;
    int max = 2;


    for (int i = 0; i < FIELDSIZE; i++) {
        for (int j = 0; j < FIELDSIZE; j++) {
            int random_number = min + rand() % (max - min + 1);
            if (random_number == 1) {
                randField[i][j] = 1;
            }
        }
    }


    while (1) {
        versuche++;

        printf("  ");
        for (int i = 0; i < FIELDSIZE; i++) {
            printf(" %d ", i + 1);
        }
        printf("  X\n");


        for (int i = 0; i < FIELDSIZE; i++) {
            printf("%d ", i + 1);
            for (int j = 0; j < FIELDSIZE; j++) {
                if (randField[i][j] == 1) {
                    field[i][j] = 1;
                }
                if (field[i][j] == 1) {
                    printf(YELLOW " %d " RESET, field[i][j]);
                } else {
                    printf(BLUE " %d " RESET, field[i][j]);
                }
            }
            printf("\n");
        }
        printf("Y\n");
        printf("Versuch: %d\n", versuche);
        printf("Wo willst du einschalten?\n");

        printf("X: ");
        scanf("%d", &x);
        printf("Y: ");
        scanf("%d", &y);

        x--;
        y--;


        if (x < FIELDSIZE && y < FIELDSIZE && x >= 0 && y >= 0) {

            field[y][x] = !field[y][x];


            if (x > 0)
                field[y][x-1] = !field[y][x-1];
            if (x < FIELDSIZE-1)
                field[y][x+1] = !field[y][x+1];
            if (y > 0)
                field[y-1][x] = !field[y-1][x];
            if (y < FIELDSIZE-1)
                field[y+1][x] = !field[y+1][x];
        } else {
            printf("Bitte gib gültige Zahlen ein (1-%d)\n", FIELDSIZE);
            continue;
        }


        howMany = 0;
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                if (field[i][j] == 1) {
                    howMany++;
                }
            }
        }


        if (howMany == FIELDSIZE * FIELDSIZE || howMany == 0) {
            printf("\nGewonnen in %d Versuchen!\n", versuche);
            break;
        }
    }

    return 0;
}