//
// Created by Nepomuk Crhonek on 06.11.2024.
//

#ifndef CUTE_H
#define CUTE_H



typedef struct Node {
    int data;
    struct Node* next;
} TNode;


TNode* createNode(int value);
void printList(TNode* head);
void appendNode(TNode** head, int value);
void deleteNodeAtIndex(TNode** head, int index);
void insertNodeAtIndex(TNode** head, int index, int value);
int getNodeValue(TNode* head, int index);
void setNodeValue(TNode* head, int index, int value);
void setAllNodes(TNode* head, int value);
int getListLength(TNode* head);
void deleteList(TNode** head);

#endif
