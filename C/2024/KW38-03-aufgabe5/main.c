#include <stdio.h>

int main() {
    int x = 23, y = 7, a = 1, b = 2, c = 3;
    double p = 33.333, q = 3.001;

    printf("3 + 4 * 2 - 10 = %d\n", 3 + 4 * 2 - 10);
    printf("x / y = %d\n", x / y);
    printf("x %% y = %d\n", x % y);
    printf("p / q = %f\n", p / q);

    printf("Ausdruck a==b: %d\n", (a == b));
    printf("Ausdruck a!=b: %d\n", (a != b));
    printf("Ausdruck b < 10: %d\n", (b < 10));
    printf("Ausdruck c >= 3: %d\n", (c >= 3));
    printf("Ausdruck 1 >= 1 && 2 == 2: %d\n", (1 >= 1 && 2 == 2));
    printf("Ausdruck 1 == 0 || 3 == 3: %d\n", (1 == 0 || 3 == 3));
    printf("Ausdruck !(1 == 0): %d\n", !(1 == 0));

    a = 1;
    printf("%d\n", ++a);
    printf("%d\n", a--);
    printf("%d\n", a++);
    printf("%d\n", --a);

    b = ++a + a++;
    printf("%d\n", a);
    printf("%d\n", b);

    printf("0xF & 0x4 = %X\n", 0xF & 0x4);
    printf("0xF | 0x4 = %X\n", 0xF | 0x4);
    printf("0xF ^ 0x4 = %X\n", 0xF ^ 0x4);
    printf("0xF << 2 = %X\n", 0xF << 2);
    printf("0xF >> 2 = %X\n", 0xF >> 2);
    printf("~0xA = %X\n", ~0xA);
    printf("4 ein mal nach links geschoben = %d\n", 4 << 1);



    // Step 1: Variable definieren und initialisieren
    unsigned char uch = 0x5A; // Beliebiger Initialwert (hexadezimal 5A)

    // Step 2: Höchstwertiges Bit auf 1 setzen
    uch |= 0x80; // 0x80 = 1000 0000 (Binär)

    // Step 3: Jedes zweite Bit auf 0 setzen
    uch &= 0xAA; // 0xAA = 1010 1010 (Binär)

    // Step 4: Jedes zweite Bit invertieren
    uch ^= 0x55; // 0x55 = 0101 0101 (Binär)

    // Step 5: Die ersten vier Bits auf 1 setzen
    uch |= 0x0F; // 0x0F = 0000 1111 (Binär)

    // Step 6: IPv4-Adresse definieren
    unsigned char ip1 = 128;
    unsigned char ip2 = 230;
    unsigned char ip3 = 130;
    unsigned char ip4 = 10;

    // Step 7: CIDR Suffix definieren
    unsigned char sf = 28;

    // Step 8 & 9: Netzwerkmaske aus CIDR Suffix erzeugen
    unsigned int nm = 0xFFFFFFFF << (32 - sf);
    printf("Netzwerkmaske von CIDR Suffix %u ist %x\n", sf, nm);

    // Step 10 & 11: 32-Bit IPv4-Adresse zusammensetzen
    unsigned int ip32bit = (ip1 << 24) | (ip2 << 16) | (ip3 << 8) | ip4;
    printf("IP %u.%u.%u.%u als Hexadezimalwert: %x\n", ip1, ip2, ip3, ip4, ip32bit);

    // Step 12 & 13: Netzwerk- und Broadcastadresse berechnen
    unsigned int nwaddr = ip32bit & nm;
    unsigned int bcaddr = nwaddr | (~nm);
    printf("Netzwerkadresse: %x\n", nwaddr);
    printf("Broadcastadresse: %x\n", bcaddr);

    // Step 14 & 15: Netzwerk- und Broadcastadresse in dezimaler Darstellung
    printf("Netzwerkadresse: %u.%u.%u.%u\n",
           (nwaddr >> 24) & 0xFF, (nwaddr >> 16) & 0xFF, (nwaddr >> 8) & 0xFF, nwaddr & 0xFF);
    printf("Broadcastadresse: %u.%u.%u.%u\n",
           (bcaddr >> 24) & 0xFF, (bcaddr >> 16) & 0xFF, (bcaddr >> 8) & 0xFF, bcaddr & 0xFF);

    // Step 16 & 17: Anzahl Hosts berechnen
    unsigned int numhosts = (1 << (32 - sf)) - 2;
    printf("Aus dem CIDR Suffix %u ergeben sich %u Hosts\n", sf, numhosts);


    return 0;
}
