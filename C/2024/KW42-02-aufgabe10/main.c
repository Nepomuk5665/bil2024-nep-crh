#include <stdio.h>
#define SIZE 100


void initArray(int arr[], int size);

int main() {
    int a;
    int *pt;
    int numbers[SIZE]; // Array

    initArray(numbers, SIZE); // Initialisiert das Array mit der Funktion initArray

    a = 10;      // 'a' wird mit dem Wert 10 initialisiert
    pt = &a;     // 'pt' speichert die Adresse von 'a', somit zeigt 'pt' auf 'a'


    printf("a : %d\n", a);            // Ausgabe: 10
    printf("pt : %p\n", pt);          // Ausgabe: Adresse von 'a' (z.B. 0x16cebf378)
    printf("*pt : %d\n", *pt);        // Ausgabe: 10 (der Wert, auf den 'pt' zeigt)
    printf("&pt : %p\n", &pt);        // Ausgabe: Adresse von 'pt'
    printf("&a : %p\n", &a);          // Ausgabe: Adresse von 'a'
    printf("&*pt: %p\n", &*pt);       // Ausgabe: Adresse von 'a'

    return 0;
}

//Initialisierung
void initArray(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        arr[i] = i; // Jedes Element wird mit seinem Index initialisiert
    }
}
