//
// Created by Nepomuk Crhonek on 06.11.2024.
//

#include "cute.h"

#include <stdio.h>
#include <stdlib.h>


TNode* createNode(int value) {
    TNode* newNode = (TNode*)malloc(sizeof(TNode));
    if (newNode != NULL) {
        newNode->data = value;
        newNode->next = NULL;
    }
    return newNode;
}


void printList(TNode* head) {
    TNode* current = head;
    while (current != NULL) {
        printf("%d -> ", current->data);
        current = current->next;
    }
    printf("NULL\n");
}


void appendNode(TNode** head, int value) {
    TNode* newNode = createNode(value);
    if (*head == NULL) {
        *head = newNode;
        return;
    }

    TNode* current = *head;
    while (current->next != NULL) {
        current = current->next;
    }
    current->next = newNode;
}


void deleteNodeAtIndex(TNode** head, int index) {
    if (*head == NULL || index < 0) return;

    if (index == 0) {
        TNode* temp = *head;
        *head = (*head)->next;
        free(temp);
        return;
    }

    TNode* current = *head;
    TNode* prev = NULL;
    int currentIndex = 0;

    while (current != NULL && currentIndex < index) {
        prev = current;
        current = current->next;
        currentIndex++;
    }

    if (current != NULL) {
        prev->next = current->next;
        free(current);
    }
}


void insertNodeAtIndex(TNode** head, int index, int value) {
    if (index < 0) return;

    if (index == 0) {
        TNode* newNode = createNode(value);
        newNode->next = *head;
        *head = newNode;
        return;
    }

    TNode* current = *head;
    int currentIndex = 0;

    while (current != NULL && currentIndex < index - 1) {
        current = current->next;
        currentIndex++;
    }

    if (current != NULL) {
        TNode* newNode = createNode(value);
        newNode->next = current->next;
        current->next = newNode;
    }
}


int getNodeValue(TNode* head, int index) {
    if (head == NULL || index < 0) return -1;

    TNode* current = head;
    int currentIndex = 0;

    while (current != NULL && currentIndex < index) {
        current = current->next;
        currentIndex++;
    }

    return (current != NULL) ? current->data : -1;
}


void setNodeValue(TNode* head, int index, int value) {
    if (head == NULL || index < 0) return;

    TNode* current = head;
    int currentIndex = 0;

    while (current != NULL && currentIndex < index) {
        current = current->next;
        currentIndex++;
    }

    if (current != NULL) {
        current->data = value;
    }
}


void setAllNodes(TNode* head, int value) {
    TNode* current = head;
    while (current != NULL) {
        current->data = value;
        current = current->next;
    }
}


int getListLength(TNode* head) {
    int length = 0;
    TNode* current = head;
    while (current != NULL) {
        length++;
        current = current->next;
    }
    return length;
}


void deleteList(TNode** head) {
    TNode* current = *head;
    while (current != NULL) {
        TNode* next = current->next;
        free(current);
        current = next;
    }
    *head = NULL;
}

