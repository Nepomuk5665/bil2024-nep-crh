#include <stdio.h>
#define FILEDSIZE 5
#include <stdlib.h>
#include <time.h>


#define YELLOW  "\x1b[33m"
#define BLUE    "\x1b[34m"

#define RESET   "\x1b[0m"


int main(void) {
    srand(time(0));
    int field[FILEDSIZE][FILEDSIZE] = {{0}};
    int howMany = 0, x = 0, y = 0, versuche = 0;

    int min = 1;
    int max = 2;

    int randField[FILEDSIZE][FILEDSIZE] = {{0}};

    for (int i = 0; i < FILEDSIZE; i++) {
        for (int j = 0; j < FILEDSIZE; j++) {
            int random_number = min + rand() % (max - min + 1);
            if (random_number == 1) {
                randField[i][j] = 1;
            }

        }
    }

    while (howMany != FILEDSIZE*FILEDSIZE) {
        versuche++;
        printf("  ");
        for (int i = 0; i < FILEDSIZE; i++) {
            printf(" %d ", i + 1);
        }
        printf("  X");
        printf("\n");
        for (int i = 0; i < FILEDSIZE; i++) {
            printf("%d ", i + 1);
            for (int j = 0; j < FILEDSIZE; j++) {
                if(randField[i][j] == 1) {
                    field[i][j] = 1;
                }
                if (field[i][j] == 1) {
                    printf(YELLOW " %d "RESET, field[i][j]);
                }
                else {
                    printf(BLUE " %d "RESET, field[i][j]);
                }

            }
            printf("\n");
        }
        printf("Y\n");
        printf("versuch: %d\n", versuche);
        printf("Wo willst du einschalten?\n");

        printf("X: ");
        scanf("%d", &x);

        printf("Y: ");
        scanf("%d", &y);

        x--;
        y--;


        if (x<FILEDSIZE && y<FILEDSIZE && x>=0 && y>=0) {
            field[y][x] = 1;
            if (x-1>=0) {
                if (field[y][x-1] == 1) {
                    field[y][x-1] = 0;
                }else {
                    field[y][x-1] = 1;
                }
            }
            if (y-1>=0) {
                if (field[y-1][x] == 1) {
                    field[y-1][x] = 0;
                }else {
                    field[y-1][x] = 1;
                }

            }
            if (x+1 < FILEDSIZE -1) {
                if (field[y][x+1] == 1) {
                    field[y][x+1] = 0;
                }else {
                    field[y][x+1] = 1;
                }

            }
            if (y+1<FILEDSIZE -1) {
                if (field[y+1][x] == 1) {
                    field[y+1][x] = 0;
                }else {
                    field[y+1][x] = 1;
                }

            }
        }else {
            printf("Mach richtigi Zahle\n");
        }



        howMany = 0;
        for (int i = 0; i < FILEDSIZE; i++) {
            for (int j = 0; j < FILEDSIZE; j++) {
                if (field[i][j] == 1) {
                    howMany ++;
                }
            }
        }
    }

    printf("gewonnen");


    return 0;
}
