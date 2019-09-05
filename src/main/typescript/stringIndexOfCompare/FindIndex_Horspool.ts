/**
 * 字符串模式匹配算法
 * Horspool
 *
 * abcabcdefg
 * abcdbcd
 * 从后往前匹配，str(a)和pattern(d)不匹配，根据str中此时窗口最右边的字母d在pattern中从右往左下一个出现的位置(3)来决定移动的距离2
 * abcabcdefg
 *    abcdbcd
 */
export class FindIndex_Horspool {

    private static moveArr(pattern: string) {
        const arr = [];
        for (let i = 0, end = pattern.length - 2; i <= end; i++) {
            arr[pattern.charCodeAt(i)] = pattern.length - i - 1;
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

        const moves = this.moveArr(pattern);
        let strI = 0;
        let matchI;
        const end = str.length - pattern.length;
        while (strI <= end) {
            matchI = pattern.length - 1;
            while (str.charCodeAt(strI + matchI) == pattern.charCodeAt(matchI) && matchI >= 0) {
                if (--matchI < 0) {
                    return strI;
                }
            }
            const move = moves[str.charCodeAt(strI + pattern.length - 1)];
            strI += move != null ? move : pattern.length;
        }
        return -1;
    }

}
