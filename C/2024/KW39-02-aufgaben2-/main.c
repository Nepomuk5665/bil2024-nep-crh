#include <stdio.h>
#include <stdbool.h>
#include <math.h>

void aufgabe1() {
    printf("Nepomuk Crhonek\nBolleystrasse 9\n8006\nZürich\n+41782238760 ");
}

void aufgabe2() {
    printf("Speicherbedarf int: %zu Byte(s)\n", sizeof(int));
    /*
     *Der Operator Siceof dient dazu den  Speicherbedarf von sachen anzugeben wie hier von einem int
     *Es muss einen inhalt haben etwas zum printen
     *mit %zu
     */
    float f = 5.67;
    printf("\nSpeicherbedarf f: %zu Byte(s)\n", sizeof(f));
    // anzeigen speicherbedarf aller vars

    printf("\nspeicherbedarf int: %zu Byte(s)\nSpeicherbedarf char: %zu Byte(s)\nSpeicherbedarf bool: %zu Byte(s)\nspeicherbedarf double: %zu Byte(s)\nspeicherbedarf enum %zu Byte(s)\nspeicherbedarf float: %zu Byte(s)\nspeicherbedarf long: %zu Byte(s)\nSpeicherbedarf short: %zu Byte(s)\n",
           sizeof(int), sizeof(char), sizeof(bool), sizeof(double), sizeof(enum {A, B}), sizeof(float), sizeof(long), sizeof(short));
    //ja es gibt einen unterschied alles zeig was anderes auf
    char x = 69;
    printf("\nspeicherbedarf char: %zu-%c Byte(s)", sizeof(char), x);
    /*
    *%c give value in ASCII System
    *%zu give value in number
    */

    double d = 255;
    int i = 84;
    char c = 'x';
    unsigned char uc = 258;
    unsigned int ui = 4294967295;

    // table
    printf("WERTE DER VARIABLEN\n");
    printf("-------------------------------------------------------------------------\n");
    printf("Variable\t\tdez\thex\tokt\n");
    printf("-------------------------------------------------------------------------\n");
    printf("i [integer] =\t\t%d\t%x\t%o\n", i, i, i);
    printf("f [float] =\t\t%d\t%x\t%o\n", (int)f, (int)f, (int)f);
    printf("d [double] =\t\t%d\t%x\t%o\n", (int)d, (int)d, (int)d);
    printf("c [char] =\t\t%c\t%x\t%o\n", c, c, c);
    printf("uc [unsignedChar] =\t%c\t%x\t%o\n", uc, uc, uc);
    printf("ui [unsignedInteger] =\t%u\t%x\t%o\n", ui, ui, ui);

    //undigned char nimmt einfach ein bit mehr von speicher

}


void aufgabe3(){

    int zahl = 1000;

    printf("%d\n%o\n%x\n", zahl, zahl, zahl);

    float pi = 3.14159;
    double durchmesser = 10.7;

    float umfang;
    umfang = pi * durchmesser;

    double roundedumfang = round(umfang*100)/100;

    printf("roundedumfang = %.2f\n", roundedumfang);


    int hexdezimal = 0xff;

    printf("Hexdezimal = %d\n", hexdezimal);

    int octal = 023;
    printf("octal = %d\n", octal);

    //4.2.6 weil es undendlich lang ist


}

void aufgabe4() {
    int x = 23;
    int y = 7;
    double p = 3.333;
    double q = 3.001;
    int a = 1;
    int b = 2;
    int c = 3;

    printf("resultat: %d\n", 3+4*2-10);

    printf("resultat: %d\n", x/y);


    double rounded = round((double)x/y*100)/100;

    printf("rounded = %.2f\n", rounded);


    printf("p durch q= %0.2i\n", p/q);


    printf("Ausdruck a==b: %d\n", (a==b));

    printf("Ausdruck a != b: %d\n", (a != b));

    printf("Ausdruck b < 10: %d\n", (b < 10));

    printf("Ausdruck c >= 3: %d\n", (c >= 3));

    printf("Ausdruck (1 >= 1 && 2 == 2): %d\n", (1 >= 1 && 2 == 2));

    printf("Ausdruck (1 == 0 || 3 == 3): %d\n", (1 == 0 || 3 == 3));

    printf("Ausdruck !(1 == 0): %d\n", !(1 == 0));


    a = 1;
    printf("%d\n", ++a);
    printf("%d\n", a--);
    printf("%d\n", a++);
    printf("%d\n", --a);

    b = ++a + a++;

    printf("%d\n", a);
    printf("%d\n", b);


    int n = 0xF;
    int s = 0x4;


    int result = n & s;


    printf("Result (decimal): %d\n", result);
    printf("Result (hex): 0x%X\n", result);

}



int main() {
    aufgabe1();

    return 0;
}
