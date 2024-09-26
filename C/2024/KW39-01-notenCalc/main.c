#include <stdio.h>


int main() {
    int tests;
    printf("Enter number of tests: ");
    scanf("%d", &tests);
    int maxPoints[tests];
    int points[tests];
    for (int i = 0; i < tests; i++) {
        printf("Test number %d", i+1);
        printf("\nEnter number of max points: ");
        scanf("%d", &maxPoints[i]);
        printf("What are your points?");
        scanf("%d", &points[i]);
    }
    float prozent[tests];
    float note[tests];
    for (int i = 0; i < tests; i++) {
        printf("\nDein Resultat für Test %d: ", i+1);
        prozent[i] = ((float)points[i] / maxPoints[i]) * 100;
        printf("\n%.2f%%\n", prozent[i]);
        printf("Note: ");
        note[i] = (float)points[i] / maxPoints[i]*5+1;
        printf("%.2f", note[i]);


    }
    if (tests > 1){

        float zusammengerechnet = 0;
        float durchschnitt = 0;
        for (int i = 0; i < tests; i++) {
            zusammengerechnet = zusammengerechnet + note[i];
        }
        durchschnitt = (float)zusammengerechnet / tests;
        printf("\nDurchschnitt Note: %.2f\n", durchschnitt);
        if (durchschnitt >= 4) {
            printf("\nErreicht!");

        }
    }

}
