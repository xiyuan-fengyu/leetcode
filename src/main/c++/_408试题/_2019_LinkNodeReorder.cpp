//
// Created by xiyuan_fengyu on 2019/11/22.
//

#include <iostream>

struct node {
    int data;
    struct node* next;
};

void reorder(node &head) {
    node* aHead = &head;
    node* bHead = head.next;
    node* aTail = aHead;
    node* p = nullptr;

    aHead->next = nullptr;
    if (bHead != nullptr) {
        p = bHead->next;
        bHead->next = nullptr;
    }

    while (p != nullptr) {
        aTail->next = p;
        aTail = p;
        node* next1 = p->next;
        p->next = nullptr;
        if (next1 != nullptr) {
            node* next2 = next1->next;
            node* temp = bHead;
            bHead = next1;
            next1->next = temp;
            p = next2;
        }
        else {
            p = nullptr;
        }
    }

    node* p1 = aHead;
    node* p2 = bHead;
    while (p1 != nullptr && p2 != nullptr) {
        node* temp1 = p1->next;
        node* temp2 = p2->next;
        p1->next = p2;
        p2->next = temp1;
        p1 = temp1;
        p2 = temp2;
    }
}

void printNodes(node& head) {
    node* p = &head;
    while (p != nullptr) {
        std::cout << p->data;
        if (p->next != nullptr) {
            std::cout << " -> ";
        }
        p = p->next;
    }
    std::cout << std::endl;
}

int main() {
    node head {0};
    node n1 {1};
    node n2 {2};
    node n3 {3};
    node n4 {4};
    head.next = &n1;
    n1.next = &n2;
    n2.next = &n3;
    n3.next = &n4;

    printNodes(head);
    reorder(head);
    printNodes(head);

    return 0;
}
