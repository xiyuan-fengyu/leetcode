/**
 * 字符串模式匹配算法
 * 通过 子串的hash对比，跳过不必要的子串对比
 * hash 计算过程: (a[0] * (2 ^ n) + a[1] * (2 ^ (n - 1)) + ...) % bigNum
 * 在窗口滑动的时候，左边移除，右边移入，新窗口的hash值可以有上一个窗口的hash值得出
 *
 * 由于采用2的冥次相乘，当 pattern 很长时，容越界或丢失精度，所以这里改为求和，也能避免一部分不必要的对比，最终效果没有 BF 效果好
 */
export class FindIndex_KR {

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

        let strH = 0;
        let patternH = 0;
        for (let i = pattern.length - 1; i > -1; --i) {
            strH += str.charCodeAt(i);
            patternH += pattern.charCodeAt(i);
        }

        for (let i = 0, end = str.length - pattern.length; i <= end; i++) {
            let match = true;
            if (strH == patternH) {
                for (let j = 0; j < pattern.length; j++) {
                    if (str.charCodeAt(i + j) != pattern.charCodeAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return i;
                }
            }

            strH = strH - str.charCodeAt(i) + str.charCodeAt(i + pattern.length);
        }
        return -1;
    }

}
