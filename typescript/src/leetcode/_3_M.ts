function lengthOfLongestSubstring(s: string) {
    let res: number = 0;
    let existedIndexes: any = {};
    for (let left = -1, right = 0, sLen = s.length; right < sLen; right++) {
        const ei = existedIndexes[s.charAt(right)];
        left = Math.max(left, ei == null ? -1 : ei);
        existedIndexes[s.charAt(right)] = right;
        res = Math.max(res, right - left);
    }
    return res;
}

console.log(lengthOfLongestSubstring("test"));
