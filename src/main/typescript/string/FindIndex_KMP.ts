/**
 * 字符串模式匹配算法
 * KMP
 */
export class FindIndex_KR {

    private static nextArr(): number[] {

        return null;
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

        const next = this.nextArr();

        return -1;
    }

}
