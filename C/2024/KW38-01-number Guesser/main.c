#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

#define MAX_GUESSES 10
#define MIN_NUMBER 1
#define MAX_NUMBER 10

#define CHEAT 69

#define RED "\x1B[31m"

#define RESET "\x1B[0m"

int main(void) {
    int again = 1;
    while (again) {
        srand(time(NULL));
        int target = rand() % (MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;

        int guessedNumbers[MAX_GUESSES] = {0};
        int numberOfGuesses = 0;
        bool guessedCorrectly = false;
        bool usedcheat = false;
        printf("Errate eine nummer zwischen  %d und %d: ", MIN_NUMBER, MAX_NUMBER);

        while (numberOfGuesses < MAX_GUESSES && !guessedCorrectly) {
            int guess;
            if (scanf("%d", &guess) != 1) {
                while (getchar() != '\n');
                printf("tue normal. Gib e nummere ih: ");
                continue;
            }
            if (guess == CHEAT ) {


                int help = rand () % 3+1;
                int target1 = target-1;
                int target2 = target-3;
                if (!usedcheat) {
                    printf(RED"‼️🚨YOU HAVE ACCESSED THE CHEAT🚨‼️\n"RESET);
                    usedcheat = true;
                    switch (help) {
                        case 1:

                            printf("d nummer isch zwüsche %d und %d 🤫\n", target, target + 3);
                        printf("also was chönti es sie? ");
                        break;
                        case 2:

                            while (target1 < 1) {
                                target1++;
                            }
                        printf("d nummer isch zwüsche %d und %d 🤫\n", target1, target + 1);
                        printf("also was chönti es sie? ");
                        break;
                        case 3:

                            while (target2 < 1) {
                                target2++;
                            }

                        printf("d nummer isch zwüsche %d und %d 🤫\n", target2, target);
                        printf("also was chönti es sie? ");
                        break;
                        default:
                            printf("passiert nie");
                        break;
                    }
                } else {
                    printf("du dumme häsch scho benutzt\n");
                    printf("also jetzt probier mal ohmi: ");
                }
            }

            if (guess < MIN_NUMBER || guess > MAX_NUMBER && guess != CHEAT) {
                printf("Bro ich ha gseit e nummer zwüsche %d und %d: ", MIN_NUMBER, MAX_NUMBER);
                continue;
            }

            bool alreadyGuessed = false;
            for (int i = 0; i < numberOfGuesses; i++) {
                if (guessedNumbers[i] == guess) {
                    alreadyGuessed = true;
                    break;
                }
            }

            if (alreadyGuessed) {
                printf("Bro das isch di glich nummere. Mach e anderi ");
                continue;
            }
            if (guess != CHEAT) {
                guessedNumbers[numberOfGuesses++] = guess;
            }

            if (guess == target) {
                guessedCorrectly = true;
            } else if (guess < target && guess != CHEAT) {
                printf("Probier e höcheri nummere: ");
            } else if (guess != CHEAT){
                printf("Probier e tüferi Nummere: ");
            }
        }

        if (guessedCorrectly) {
            printf("Korrekt! Du häsch es richtig errate in %d versuche.\n", numberOfGuesses);
        } else {
            printf("Sorry, zu viel mal probiert. Die nummer war %d.\n", target);
        }

        printf("Nochmals? 1 = yes, 0 = no: ");
        scanf("%d", &again);
    }
    return 0;
}