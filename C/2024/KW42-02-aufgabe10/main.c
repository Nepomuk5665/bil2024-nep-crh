#include <stdio.h>

int main() {
    int a;
    int *pt;

    a = 10;      // Die Variable 'a' wird mit dem Wert 10 initialisiert
    pt = &a;     // 'pt' speichert die Adresse von 'a', d.h. 'pt' zeigt auf 'a'


    printf("a : %d\n", a);          // Ausgabe: 10


    printf("pt : %p\n", pt);        // Ausgabe: Adresse von 'a' (z.B. 0x16cebf378)


    printf("*pt : %d\n", *pt);      // Ausgabe: 10 (der Wert, auf den 'pt' zeigt)


    printf("&pt : %p\n", &pt);      // Ausgabe: Adresse von 'pt'


    printf("&a : %p\n", &a);        // Ausgabe: Adresse von 'a'


    printf("&*pt: %p\n", &*pt);     // Ausgabe: Adresse von 'a'

    return 0;
}
