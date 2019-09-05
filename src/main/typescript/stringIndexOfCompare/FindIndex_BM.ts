/**
 * 字符串模式匹配算法
 * BM
 * https://www.jianshu.com/p/97374f072cc8
 * https://blog.csdn.net/chenhanzhun/article/details/39938989
 *
 * 坏字符规则，和 Horspool, Sunday相似
 * abcfo
 * cfdf
 * cd不匹配，移动后，将c对齐
 * abcfo
 *   cfdf
 *
 * 好后缀规则，和 kmp 相似，通过相同前后缀跳过一定距离
 * acefaboo
 * abedab
 * 移动后
 * acefaboo
 *     abedab
 */
export class FindIndex_BM {

    // 坏字符规则 辅助数组
    private static badCharLeftPos(pattern: string) {
        const arr = [];
        for (let i = 0; i < pattern.length; i++) {
            arr[pattern.charCodeAt(i)] = i;
        }
        return arr;
    }

    // 好后缀规则 辅助数组
    private static goodSuffixMove(pattern: string) {
        const suffix = [];
        const len = pattern.length;
        suffix[len - 1] = len;
        for (let i = len - 2, j; i >= 0; i--) {
            j = i;
            while (j >= 0 && pattern.charCodeAt(j) == pattern.charCodeAt(len - 1 - i + j)) {
                j--;
            }
            suffix[i] = i - j;
        }

        const arr = new Array(len);
        arr.fill(len);
        for (let i = len - 1, j = 0; i >= 0; i--) {
            if (suffix[i] == i + 1) {
                // 前缀 = 后缀
                for (; j < len - 1 - i; j++) {
                    arr[j] == len && (arr[j] = len - 1 - i);
                }
            }
        }

        for (let i = 0; i < len - 1; i++) {
            arr[len - 1 - suffix[i]] = len - 1 - i;
        }
        return arr;
    }

    static find(str: string, pattern: string): number {
        if (str == null || pattern == null) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        if (pattern.length > str.length) {
            return -1;
        }

        const badArr = this.badCharLeftPos(pattern);
        const goodArr = this.goodSuffixMove(pattern);
        let strI = 0;
        while (strI <= str.length - pattern.length) {
            let pI = pattern.length - 1;
            while (pI >= 0 && pattern.charCodeAt(pI) == str.charCodeAt(strI + pI)) {
                if (--pI < 0) {
                    return strI;
                }
            }
            let badCharLeftI = badArr[str.charCodeAt(strI + pI)];
            if (badCharLeftI == null) {
                badCharLeftI = -1;
            }
            let goodMove = goodArr[pI];
            strI += Math.max(pI - badCharLeftI, goodMove);
        }
        return -1;
    }

}
