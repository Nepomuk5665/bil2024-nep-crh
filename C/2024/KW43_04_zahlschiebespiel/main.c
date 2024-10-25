#include <stdio.h>
#include "utils.h"
#include "stdbool.h"
#define FIELD_SIZE 4

/**
 * This programm depicts an all-time classic, the sliding puzzle. The one and 
 * only 0 represents the empty slot in the sliding puzzle. Moves (sliding up, 
 * down, left, right) are always made relative to the empty slot e.g. in the
 * case of the initial state of the field, only the moves left (swap 0 with 15)
 * and up (swap 0 with 8) can be made. This programm doesn't detect whether the 
 * field is sorted. It runs as long as the user doesn't enter a 0.
 * 
 * @return 0 if the programm was successfully run.
 */




bool isPuzzleSolved(int field[FIELD_SIZE][FIELD_SIZE]) {
    int expectedValue = 0;
    for (int i = 0; i < FIELD_SIZE; i++) {
        for (int j = 0; j < FIELD_SIZE; j++) {
            if (field[i][j] != expectedValue) {
                return false;
            }
            expectedValue++;
        }
    }
    return true;
}






int main(void) {
    int field[FIELD_SIZE][FIELD_SIZE] = {
        { 0, 15,  1, 13},
        { 8,  5,  2,  3},
        {14,  7,  4,  9},
        {10, 11,  6, 12},
    };
    char input;
    int one;
    int two;
    int fails = 0;


    bool gameWon = false;

    do {
        bool isUserNormal = false;


        printField(FIELD_SIZE, field);




        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                if (field[y][x] == 0) {
                    one = y;
                    two = x;
                }
            }
        }
        while (!isUserNormal) {

            scanf("%c", &input);


        if(input == 'D' || input == 'd') {

            if (two+1==FIELD_SIZE) {
                printf("Bro du chasch das nd mache bisch du dumm\n");
                fails++;
            }else {
                swapValues(&field[one][two], &field[one][two+1]);
                isUserNormal = true;
            }
        }else if(input == 'A' || input == 'a') {
            if (two+1==1) {
                printf("Bro du chasch das nd mache bisch du dumm\n");
                fails++;
            }else {
                swapValues(&field[one][two], &field[one][two-1]);
                isUserNormal = true;
            }
        }else if(input == 'S' || input == 's') {
            if (one+1==FIELD_SIZE) {
                printf("Bro du chasch das nd mache bisch du dumm\n");
                fails++;
            }else {
                swapValues(&field[one][two], &field[one+1][two]);
                isUserNormal = true;
            }
        }else if(input == 'W' || input == 'w') {
            if (one+1==1) {
                printf("Bro du chasch das nd mache bisch du dumm\n");
                fails++;
            }else {
                swapValues(&field[one][two], &field[one-1][two]);
                isUserNormal = true;
            }

            //cheat-sort it for me with bubble sort
        }else if(input == '9') {
            printf("Cheat mode aktiviert! Das puzzle wird jetzt sortiert mit Bubble sort...\n");

            //  2D array into a 1D array
            int flatField[FIELD_SIZE * FIELD_SIZE];
            int index = 0;
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    flatField[index++] = field[i][j];
                }
            }

            // Bubble Sort
            for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
                for (int j = 0; j < FIELD_SIZE * FIELD_SIZE - i - 1; j++) {
                    if (flatField[j] > flatField[j + 1]) {
                        int temp = flatField[j];
                        flatField[j] = flatField[j + 1];
                        flatField[j + 1] = temp;
                    }
                }
            }

            // 1D array back into the 2D array
            index = 0;
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    field[i][j] = flatField[index++];
                }
            }

            printField(FIELD_SIZE, field);


            isUserNormal = true;
        }else if(input == '0' || input == '0') {
            isUserNormal = true;
        }




            if (isUserNormal && input != '0') {
                if (isPuzzleSolved(field)) {
                    printf("du hast gewonnen!\n");
                    gameWon = true;
                    input = '0';  // Exit the game loop
                    break;
                }
            }


        }

        // TODO: Implement the rules for the sliding puzzle. In other words, 
        // swap neighboured values (horizontally or vertically) in the 2D array 
        // based on user input, but only if the move is legal (bounds checking).

    } while (input != '0');


    if (!gameWon) {
        if (fails > 5) {
            printf("omg danke gehst du endlich");
        }else {
            printf("danke fürs spielen bro");
        }
    }

    return 0;
}