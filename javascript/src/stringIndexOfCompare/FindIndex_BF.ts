/**
 * 字符串模式匹配算法
 * 爆破算法，穷举
 */
export class FindIndex_BF {

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

        for (let i = 0, end = str.length - pattern.length; i <= end; i++) {
            let j = 0;
            do {
                if (str.charCodeAt(i + j) != pattern.charCodeAt(j)) {
                    break;
                }
                if (++j == pattern.length) {
                    return i;
                }
            } while (true);
        }
        return -1;
    }

}
