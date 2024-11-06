#include <stdio.h>
#include <stdlib.h>
#include "cute.h"

int main() {

    TNode* head = NULL;


    appendNode(&head, 10);
    appendNode(&head, 20);
    appendNode(&head, 30);

    printf("Ursprüngliche Liste:\n");
    printList(head);


    insertNodeAtIndex(&head, 1, 15);
    printf("Nach Einfügen von 15 an Index 1:\n");
    printList(head);


    deleteNodeAtIndex(&head, 2);
    printf("Nach Löschen des Nodes an Index 2:\n");
    printList(head);


    int value = getNodeValue(head, 1);
    printf("Wert an Index 1: %d\n", value);


    setAllNodes(head, 100);
    printf("Nach Setzen aller Werte auf 100:\n");
    printList(head);


    printf("Listenlänge: %d\n", getListLength(head));


    deleteList(&head);
    printf("Nach Löschen der Liste:\n");
    printList(head);

    return 0;
}