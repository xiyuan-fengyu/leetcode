/**
 * 字符串模式匹配算法
 * BM
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

        return -1;
    }

}
