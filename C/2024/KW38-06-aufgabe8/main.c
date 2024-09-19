#include <stdio.h>
#include <string.h>



void binPrint(int decNum) {
    unsigned int mask = 1 << (sizeof(int) * 8 - 1);
    int started = 0;

    for (int i = 0; i < sizeof(int) * 8; i++) {
        if (decNum & mask) {
            putchar('1');
            started = 1;
        } else if (started) {
            putchar('0');
        }
        mask >>= 1;
    }

    if (!started) {
        putchar('0');
    }
    putchar('\n');
}


int binRead() {
    char binStr[65];
    int decimal = 0;

    printf("Gib ein binäres Muster ein: ");
    scanf("%s", binStr);

    for (int i = 0; i < strlen(binStr); i++) {
        if (binStr[i] != '0' && binStr[i] != '1') {
            printf("Ungültige Eingabe.\n");
            return -1;
        }
        decimal = decimal * 2 + (binStr[i] - '0');
    }

    return decimal;
}


int main() {
    int num, result;


    printf("Gib eine Dezimalzahl ein: ");
    scanf("%d", &num);
    printf("Binäre Darstellung: ");
    binPrint(num);


    result = binRead();
    if (result != -1) {
        printf("Die Dezimalzahl ist: %d\n", result);
    }

    return 0;
}

