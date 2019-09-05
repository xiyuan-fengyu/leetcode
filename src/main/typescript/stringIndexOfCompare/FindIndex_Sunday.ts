/**
 * 字符串模式匹配算法
 * Sunday
 *
 * abcdefghi
 * abca
 * e没有在abca中出现，abca需要向前移动5位
 * abcdefghi
 *      abca
 *
 * abcbcde
 * abcd
 * c在abcd中有出现，abcd需向前移动2位，使得c对齐
 * abcbcde
 *   abcd
 */
export class FindIndex_Sunday {

    private static moveArr(pattern: string) {
        // 计算一个字符在pattern中的最右边的位置距离最有边的距离
        const arr = [];
        for (let i = 0, len = pattern.length; i < len; i++) {
            arr[pattern.charCodeAt(i)] = len - i;
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
            matchI = 0;
            while (str.charCodeAt(strI + matchI) == pattern.charCodeAt(matchI) && matchI < pattern.length) {
                if (++matchI == pattern.length) {
                    return strI;
                }
            }
            const move = moves[str.charCodeAt(strI + pattern.length)];
            strI += move != null ? move : pattern.length;
        }
        return -1;
    }

}
