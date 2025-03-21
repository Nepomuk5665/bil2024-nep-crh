#include <stdio.h>

void makeUppercase(char *str);

int main(void) {
    char c1 = 'a';
    char c2 = '!';
    makeUppercase(&c1);
    makeUppercase(&c2);
    printf("%c%c", c1, c2); // prints A!
}

void makeUppercase(char *str) {
    if (*str >= 'a' && *str <= 'z') {
        *str = *str - 32;
    }
}
