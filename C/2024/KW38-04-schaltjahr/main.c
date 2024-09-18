#include <stdio.h>

int isLeapYear(int year);
int daysInMonth(int month, int year);
void printDateInfo(int month, int year);

int main() {
    int month = 10;
    int year = 1996;
    printf("enter year:");
    scanf("%d", &year);
    printf("enter month:");
    scanf("%d", &month);
    printDateInfo(month, year);
    
    return 0;
}

int isLeapYear(int year){
    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {

        return 1;
    }else{return 0;}
}

int daysInMonth(int month, int year){

    switch(month) {
        case 1:
            return 31;
        break;
        case 2:
            return isLeapYear(year) ? 29 : 28;
        break;
        case 3:
            return 31;
        break;
        case 4:
            return 30;
        break;
        case 5:
            return 31;
        break;
        case 6:
            return 30;
        break;
        case 7:
            return 31;
        break;
        case 8:
            return 31;
        break;
        case 9:
            return 30;
        break;
        case 10:
            return 31;
        break;
        case 11:
            return 30;
        break;
        case 12:
            return 31;
        break;
        default:
            return 69;
        break;
    }

}

void printDateInfo(int month, int year){
    int days = daysInMonth(month, year);
    
    printf("Der Monat %d hat im Jahr %d %d Tage. \n", month, year, days);
    
    printf("Das Jahr %d ist ", year);
    if(!isLeapYear(year)){
        printf("kein ");
    }else{
        printf("ein ");
    }
    printf("Schaltjahr. \n");
}