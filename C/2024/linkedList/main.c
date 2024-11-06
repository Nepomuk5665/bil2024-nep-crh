#include <stdio.h>
#include <stdlib.h>


typedef struct Node {
    int data;
    struct Node *next;
} CuteNode;

int main() {
    CuteNode head;
    CuteNode *p_cursor = (CuteNode *)malloc(sizeof(CuteNode));
    p_cursor = &head;

    p_cursor->data = 33;
    p_cursor->next = (CuteNode *)malloc(sizeof(CuteNode));

    p_cursor = p_cursor->next;
    p_cursor->data = 2;
    p_cursor->next = (CuteNode *)malloc(sizeof(CuteNode));
    p_cursor = p_cursor->next;
    p_cursor->data = 42;
    p_cursor->next = (CuteNode *)malloc(sizeof(CuteNode));
    p_cursor = p_cursor->next;
    p_cursor->data = 11;
    p_cursor->next = (CuteNode *)malloc(sizeof(CuteNode));
    p_cursor = p_cursor->next;
    p_cursor->data = 0;

    p_cursor = &head;

    for (int i = 0; i < 5; i++) {
        printf("%d\n", p_cursor->data);
        p_cursor = p_cursor->next;
    }



    free(p_cursor);

    p_cursor = NULL;
    return 0;

}