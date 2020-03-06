//
// Created by xiyuan_fengyu on 2019/11/23.
//

#include <iostream>
#include<string>

using namespace std;

typedef struct node {
    char data[10];
    struct node * left, *right;
} BTree;

typedef struct {
    string content;
    bool isData;
} Exp;

Exp midExp_(BTree& root) {
    bool isData = true;
    string content;
    if (root.left != nullptr) {
        isData = false;
        Exp sub = midExp_(*root.left);
        if (sub.isData) {
            content += sub.content;
        }
        else {
            content += "(" + sub.content + ")";
        }
    }
    content += root.data;
    if (root.right != nullptr) {
        isData = false;
        Exp sub = midExp_(*root.right);
        if (sub.isData) {
            content += sub.content;
        }
        else {
            content += "(" + sub.content + ")";
        }
    }
    return Exp {content, isData};
}

string midExp(BTree& root) {
    return midExp_(root).content;
}

int main() {
    BTree root {"*"};
    BTree n1 {"+"};
    BTree n2 {"*"};
    BTree n3 {"a"};
    BTree n4 {"b"};
    BTree n5 {"c"};
    BTree n6 {"-"};
    BTree n7 {"d"};
    root.left = &n1;
    root.right = &n2;
    n1.left = &n3;
    n1.right = &n4;
    n2.left = &n5;
    n2.right = &n6;
    n6.right = &n7;
    cout << midExp(root) << endl;
    return 0;
}
