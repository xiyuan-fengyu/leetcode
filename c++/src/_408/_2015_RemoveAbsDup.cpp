//
// Created by xiyuan_fengyu on 2019/11/29.
//

#include <iostream>
#include <vector>

struct node {
    int value;
    node* next;
};

#define max(a, b) a >= b ? a : b
#define abs(a) a>= 0 ? a : -a

void removeAbsDup(node& head) {
    int n = 0;
    node* p = head.next;
    while (p != nullptr) {
        n = max(n, abs(p->value));
        p = p->next;
    }
    std::vector<int> existed(n + 1);
    p = head.next;
    node* pre = &head;
    while (p != nullptr) {
        int absV = abs(p->value);
        if (existed[absV] == 0) {
            existed[absV] = 1;
            pre = p;
            p = p->next;
        }
        else {
            pre->next = p->next;
            p = p->next;
        }
    }
}

void printNodes(node& head) {
    node* p = &head;
    while (p != nullptr) {
        std::cout << p->value;
        if (p->next != nullptr) {
            std::cout << " -> ";
        }
        p = p->next;
    }
    std::cout << std::endl;
}

int main() {
    node head {};
    node node0 {0};
    node node1 {1};
    node node2 {1};
    node node3 {2};
    head.next = &node0;
    node0.next = &node1;
    node1.next = &node2;
    node2.next = &node3;
    printNodes(head);
    removeAbsDup(head);
    printNodes(head);
}
