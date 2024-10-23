#include <stdio.h>

int main() {
    // 11.2.1
    float x;  // Variable für den Fliesskommawert

    printf("Bitte geben Sie einen Fliesskommawert ein: ");
    scanf("%f", &x);  // %f formatiert die Eingabe als float und &x übergibt die Adresse von x

    printf("Der eingegebene Wert ist: %.2f\n", x);  // %.2f beschränkt die Ausgabe auf zwei Nachkommastellen



    // 11.2.2

    int zahl1;
    int zahl2;
    int zahl3;
    printf("Bitte geben Sie drei Zahlen ein: \n");
    scanf("%d %d %d", &zahl1, &zahl2, &zahl3);

    printf("Zahl 1: %d Zahl 2: %d Zahl3: %d", zahl1, zahl2, zahl3);

    // bei mir macht gar nichts einen Strich durch die rechnung bei mir funktioniert es
    char text[80];
    scanf("%s", text);
    printf("%s", text);

    return 0;
}
