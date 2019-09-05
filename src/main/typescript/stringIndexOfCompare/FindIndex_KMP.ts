/**
 * 字符串模式匹配算法
 * KMP
 * 适合有重复子串的情况
 */
export class FindIndex_KMP {

    private static nextArr(pattern: string): number[] {
        /*
         chars    a b c a b c d
         index    0 1 2 3 4 5 6
         next     0 0 0 1 2 3 0
         index = 3 处，abca中最长的相同前后缀为a
         index = 4 处，abcab中最长的相同前后缀为ab

         现在，有两个指针，i表示匹配过程中表示str中的位置，j表示匹配过程中表示pattern中的位置
         当匹配到第j个位置str[i] != pattern[j]时，i位置不变，j移动到next[j - 1]的位置，继续匹配
         */
        const next = [0];
        const char0 = pattern.charCodeAt(0);
        for (let i = 1, j = 0; i < pattern.length; i++) {
            const charI = pattern.charCodeAt(i);
            while (j > 0 && charI != pattern.charCodeAt(j)) {
                j = next[ j - 1];
            }
            next.push(j > 0 || char0 == charI ? ++j : 0);
        }
        return next;
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

        const next = this.nextArr(pattern);
        const char0 = pattern.charCodeAt(0);
        for (let i = 0, j = 0; i + pattern.length - j - 1 < str.length; i++) {
            const charI = str.charCodeAt(i);
            while (j > 0 && charI != pattern.charCodeAt(j)) {
                j = next[j - 1];
            }
            if (j > 0 || char0 == charI) {
                if (++j == pattern.length) {
                    return i - j + 1;
                }
            }
        }
        return -1;
    }

}
