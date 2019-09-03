/**
 * 字符串模式匹配算法
 * 通过 子串的hash对比，跳过不必要的子串对比
 * hash 计算过程: (a[0] * (2 ^ n) + a[1] * (2 ^ (n - 1)) + ...) % bigNum
 * 在窗口滑动的时候，左边移除，右边移入，新窗口的hash值可以有上一个窗口的hash值得出
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

        let mod = Math.pow(2, pattern.length - 1);
        let strH = 0;
        let patternH = 0;
        for (let i = 0; i < pattern.length; i++) {
            strH = (strH << 1) + str.charCodeAt(i);
            patternH = (patternH << 1) + pattern.charCodeAt(i);
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
            strH = ((strH - str.charCodeAt(i) * mod) << 1) + str.charCodeAt(i + pattern.length);
        }
        return -1;
    }

}
