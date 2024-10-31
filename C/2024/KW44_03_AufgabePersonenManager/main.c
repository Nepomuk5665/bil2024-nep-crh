#include <stdio.h>
#include <string.h>

#include <stdlib.h>

struct Person {
    char name[20 + 1];
    int birthyear;
    int weightKG;
    int heightCM;
    struct {
        char name[30 + 1];
        char thema[20 + 1];
    } verein;
    struct Person *bestFriend;
};

void initFamily(struct Person p[]);
void printFamily(struct Person p[]);
void findOldestFamilyMember(struct Person p[]);
void sortFamily(struct Person p[], int criteria);
void swap(struct Person *p1, struct Person *p2);

int main(void) {
    struct Person p[4];
    int criteria = 0;

    initFamily(p);

    printf("Sortier Kriterium:\n");
    printf("1. Name\n");
    printf("2. Birthyear\n");
    printf("3. Weight\n");
    printf("4. Height\n");
    scanf("%d", &criteria);

    sortFamily(p, criteria);
    printFamily(p);
    findOldestFamilyMember(p);

    return 0;
}

void printFamily(struct Person p[]) {
    for (int i = 0; i < 4; i++) {
        printf("Name: %s, ", p[i].name);
        printf("Birth year: %d, ", p[i].birthyear);
        printf("Weight: %dkg, ", p[i].weightKG);
        printf("Height: %dcm, ", p[i].heightCM);
        printf("Verein: %s, ", p[i].verein.name);
        printf("Verein Thema: %s", p[i].verein.thema);
        if (p[i].bestFriend != NULL) {
            printf(", Best Friend: %s", p[i].bestFriend->name);
        }
        printf("\n");
    }
}

void findOldestFamilyMember(struct Person p[]) {
    int index = 0;
    int year = p[0].birthyear;
    for (int i = 1; i < 4; i++) {
        if (year > p[i].birthyear) {
            year = p[i].birthyear;
            index = i;
        }
    }
    printf("oldest person:\n");
    printf("Name: %s, ", p[index].name);
    printf("Birth year: %d, ", p[index].birthyear);
    printf("Weight: %dkg, ", p[index].weightKG);
    printf("Height: %dcm, ", p[index].heightCM);
    printf("Verein: %s, ", p[index].verein.name);
    printf("Verein Thema: %s", p[index].verein.thema);
    printf("\n");
}

void initFamily(struct Person p[]) {
    // alles zuerst zu NULL machen
    for (int i = 0; i < 4; i++) {
        p[i].bestFriend = NULL;
    }

    // machen dass es genug Speicher hat mit malloc
    for (int i = 0; i < 4; i++) {
        p[i].bestFriend = malloc(408); // 50 + 1 * 5 zum 50 character speichern
        if (p[i].bestFriend == NULL) {
            printf("nd funktioniert bro!\n");
            exit(1);

        }
    }

    strcpy(p[0].name, "John Doe");
    strcpy(p[1].name, "Jane Doe");
    strcpy(p[2].name, "Jack Doe");
    strcpy(p[3].name, "Judy Doe");

    strcpy(p[0].bestFriend->name, "NepoCute");
    strcpy(p[1].bestFriend->name, "Michel");
    strcpy(p[2].bestFriend->name, "Johantan");
    strcpy(p[3].bestFriend->name, "2FA typ");

    strcpy(p[0].verein.name, "Verein Name 1");
    strcpy(p[1].verein.name, "Verein Name 2");
    strcpy(p[2].verein.name, "Verein Name 3");
    strcpy(p[3].verein.name, "Verein Name 4");

    strcpy(p[0].verein.thema, "Verein Thema 1");
    strcpy(p[1].verein.thema, "Verein Thema 2");
    strcpy(p[2].verein.thema, "Verein Thema 3");
    strcpy(p[3].verein.thema, "Verein Thema 4");

    p[0].birthyear = 1981; p[0].weightKG = 91; p[0].heightCM = 196;
    p[1].birthyear = 1980; p[1].weightKG = 64; p[1].heightCM = 178;
    p[2].birthyear = 2008; p[2].weightKG = 40; p[2].heightCM = 163;
    p[3].birthyear = 2015; p[3].weightKG = 30; p[3].heightCM = 124;
}

void sortFamily(struct Person p[], int criteria) {
    for (int i = 0; i < 3; i++) {
        for (int j = i + 1; j < 4; j++) {
            switch (criteria) {
                case 1:
                    if (strcmp(p[i].name, p[j].name) > 0) {
                        swap(&p[i], &p[j]);
                    }
                    break;
                case 2:
                    if (p[i].birthyear > p[j].birthyear) {
                        swap(&p[i], &p[j]);
                    }
                    break;
                case 3:
                    if (p[i].weightKG > p[j].weightKG) {
                        swap(&p[i], &p[j]);
                    }
                    break;
                case 4:
                    if (p[i].heightCM > p[j].heightCM) {
                        swap(&p[i], &p[j]);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

void swap(struct Person *p1, struct Person *p2) {
    struct Person temp = *p1;
    *p1 = *p2;
    *p2 = temp;
}