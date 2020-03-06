
function longestPalindrome_v1(str: string): string {
    if (!str) {
        return str;
    }
    let longest = str.charAt(0);
    for (let i = 1, len = str.length; i < len; i++) {
        for (let j = 0, last = i - longest.length; j <= last; j++) {
            let left = j;
            let right = i;
            while (left < right) {
                if (str.charAt(left) == str.charAt(right)) {
                    left++;
                    right--;
                }
                else {
                    break;
                }
            }
            if (left >= right) {
                if (i - j + 1 > longest.length) {
                    longest = str.substring(j, i + 1);
                }
                break;
            }
        }
    }
    return longest;
}


/*
在原字符串中每两个字符之间插入一个*，使得字符串变为长度为奇数的字符串
*b*a*b*a*d*
 */
function longestPalindrome(str: string): string {
    if (str.length <= 1) {
        return str;
    }

    let newLen = str.length * 2 + 1;
    let newStr = new Array(newLen);
    for (let i = 0, len = str.length; i < len; i++) {
        newStr[i * 2 + 1] = str.charCodeAt(i);
    }

    let posRadius = new Array(newLen);
    let pos = -1;
    let maxR = -1;
    let maxPos = -1;
    let maxLen = -1;
    let left: number, right: number;

    for (let i = 0; i < newLen; i++) {
        if (maxR < i) {
            left = i - 1;
            right = i + 1;
            while (left > -1 && right < newLen) {
                if (newStr[left] == newStr[right]) {
                    left--;
                    right++;
                }
                else break;
            }
            pos = i;
            maxR = right - 1;
            posRadius[i] = right - i;

            if (maxLen < posRadius[i] - 1) {
                maxLen = posRadius[i] - 1;
                maxPos = i;
            }
        }
        else {
            let symmetryIRadius = posRadius[pos * 2 - i];
            if (symmetryIRadius < maxR - i + 1) {
                posRadius[i] = symmetryIRadius;
            }
            else {
                right = maxR + 1;
                left = i * 2 - right;
                while (left >= 0 && right < newLen) {
                    if (newStr[left] == newStr[right]) {
                        left--;
                        right++;
                    }
                    else break;
                }
                pos = i;
                maxR = right - 1;
                posRadius[i] = right - i;

                if (maxLen < posRadius[i] - 1) {
                    maxLen = posRadius[i] - 1;
                    maxPos = i;
                }
            }
        }

    }
    return maxPos % 2 == 1 ? str.substring(maxPos / 2 - posRadius[maxPos] / 2 + 1, maxPos / 2 - posRadius[maxPos] / 2 + posRadius[maxPos])
        : str.substring((maxPos - 1) / 2 - posRadius[maxPos] / 2 + 1, (maxPos - 1) / 2 - posRadius[maxPos] / 2 + posRadius[maxPos]);
}

console.log(longestPalindrome("ababad"));
console.log(longestPalindrome("cbbd"));
console.log(longestPalindrome("cb"));
console.log(longestPalindrome("c"));
console.log(longestPalindrome(""));
