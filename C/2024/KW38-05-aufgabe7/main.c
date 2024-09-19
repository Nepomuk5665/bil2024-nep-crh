#include <stdio.h>
#include <stdbool.h>

#define PI 3.141

void anrede1() {
    printf("Guten Tag Herr XY\n");
}

void anrede2(int param) {
    if (param == 0) {
        printf("Guten Tag Herr XY\n");
    } else {
        printf("Guten Tag Frau XY\n");
    }
}

void anrede3(int param);

bool anrede4(int param) {
    if (param == 0) {
        printf("Guten Tag Herr XY\n");
        return false;
    } else if (param == 1) {
        printf("Guten Tag Frau XY\n");
        return false;
    }
    return true;
}

float kreisflaeche(float radius) {
    return PI * radius * radius;
}

int minimum(int a, int b) {
    return (a < b) ? a : b;
}

int main() {
    anrede1();
    anrede2(0);
    anrede2(1);
    anrede3(0);
    anrede3(1);
    anrede3(2);

    if (anrede4(0)) {
        printf("Fehler bei anrede4\n");
    }
    if (anrede4(1)) {
        printf("Fehler bei anrede4\n");
    }
    if (anrede4(2)) {
        printf("Fehler bei anrede4\n");
    }

    printf("Kreisflaeche: %.2f\n", kreisflaeche(5));
    printf("Minimum: %d\n", minimum(10, 5));

    return 0;
}

void anrede3(int param) {
    if (param == 0) {
        printf("Guten Tag Herr XY\n");
    } else if (param == 1) {
        printf("Guten Tag Frau XY\n");
    } else {
        printf("Fehlermeldung\n");
    }
}
