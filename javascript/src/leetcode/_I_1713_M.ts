
class DictNode {
    val: number;
    end: boolean;
    children: DictNode[];

    constructor(val: number, end: boolean) {
        this.val = val;
        this.end = end;
    }

    addChild(val: number, end: boolean): DictNode {
        !this.children && (this.children = []);
        const index = val - 97;
        let childNode = this.children[index];
        if (!childNode) {
            this.children[index] = childNode = new DictNode(val, end);
        }
        else {
            childNode.end = childNode.end || end;
        }
        return childNode;
    }

    child(val: number): DictNode {
        return this.children ? this.children[val - 97] : null;
    }

}

class DictTree {
    private readonly root = new DictNode(0, false);

    constructor(dic: string[]) {
        for (let str of dic) {
            this.addWord(str);
        }
    }

    addWord(str: string) {
        let curNode = this.root;
        for (let i = 0, len = str.length; i < len; i++) {
            curNode = curNode.addChild(str.charCodeAt(i), i + 1 == len);
        }
    }

    check(str: string, from: number, dp: number[]): number {
        if (dp[from] > -1) {
            return dp[from];
        }

        const len = str.length;
        let minUnknown = len - from;
        let curNode = this.root;
        for (let i = from; curNode && i < len; i++) {
            curNode = curNode.child(str.charCodeAt(i));
            if (curNode && curNode.end) {
                minUnknown = Math.min(minUnknown, this.check(str, i + 1, dp));
            }
            else {
                minUnknown = Math.min(minUnknown, i - from + 1 + this.check(str, i + 1, dp));
            }
        }
        return dp[from] = minUnknown;
    }

}

function respace(dictionary: string[], sentence: string): number {
    const len = sentence.length;
    if (len == 0) {
        return 0;
    }

    const dictTree = new DictTree(dictionary);
    const dp = new Array(len + 1).fill(-1);
    dp[len] = 0;
    return dictTree.check(sentence, 0, dp);
}

console.log(respace(["looked","just","like","her","brother"], "jesslookedjustliketimherbrother"));
