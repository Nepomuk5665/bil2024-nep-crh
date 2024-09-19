#include <stdio.h>
#include <string.h>
#include <stdbool.h>


const int SIZE = 5000;
int main(int argc, char **argv)
{
    int i;
    int zahlen[SIZE];
    int seed = 1000;
    int rand = -47;
    for(i = 0; i < SIZE; i++){
        rand = (rand * 381 + 1) % seed;
        zahlen[i] = rand;
    }
    int nummer3 = 0;
    bool nummer758 = false;
    int nummer900 = 0;
    int max = 0;
    int min = 0;
    for(i = 0; i < SIZE; i++) {
        if (zahlen[i] == 3) {
            nummer3++;
        }

        if (zahlen[i] == 758 && nummer758 == false) {
            printf("die nummer %d kam beim index %d das erste mal vor\n", zahlen[i], i);
            nummer758 = true;
        }

        if (zahlen[i] == 900 && nummer900 == false) {
            printf("die nummer %d kam beim index %d das erste mal vor\n", zahlen[i], i);
            nummer900 = true;
        }
        if (max<=zahlen[i]) {
            max = zahlen[i];
        }
        if (min>=zahlen[i]) {
            min = zahlen[i];
        }

    }
    printf("max = %d, min = %d\n", max, min);

    printf("die nummer 3 kam %d mal vor\n", nummer3);


    bool chaned = false;
    int temp;
    do  {
        chaned = false;
        for(i = 0; i < SIZE; i++) {
            if(zahlen[i] > zahlen[i+1]) {
                temp = zahlen[i+1];
                zahlen[i+1] = zahlen[i];
                zahlen[i] = temp;
                chaned = true;
            }
        }
    }while(chaned);
    for(i = 0; i < SIZE; i++) {
        printf("zahlen[%d] = %d\n", i, zahlen[i]);
    }
}

