class Solution:
    def longestPalindrome(self, s: str) -> str:
        sLen = len(s)
        if sLen <= 1:
            return s

        newLen = sLen * 2 + 1
        newS = ['\0'] * newLen
        for i, c in enumerate(s):
            newS[i * 2 + 1] = c

        posRadius = [0] * newLen
        pos = -1
        maxR = -1
        maxLen = -1
        maxPos = -1

        for i in range(newLen):
            if maxR < i:
                left = i - 1
                right = i + 1
                while left > -1 and right < newLen:
                    if newS[left] == newS[right]:
                        left -= 1
                        right += 1
                    else:
                        break
                pos = i
                maxR = right - 1
                posRadius[i] = right - i

                if maxLen < posRadius[i] - 1:
                    maxLen = posRadius[i] - 1
                    maxPos = i
            else:
                symmetryIRadius = posRadius[pos * 2 - i]
                if symmetryIRadius < maxR - i + 1:
                    posRadius[i] = symmetryIRadius
                else:
                    right = maxR + 1
                    left = i * 2 - right
                    while left > -1 and right < newLen:
                        if newS[left] == newS[right]:
                            left -= 1
                            right += 1
                        else:
                            break
                    pos = i
                    maxR = right - 1
                    posRadius[i] = right - i

                    if maxLen < posRadius[i] - 1:
                        maxLen = posRadius[i] - 1
                        maxPos = i

        if maxPos % 2 == 1:
            return s[maxPos // 2 - posRadius[maxPos] // 2 + 1 : maxPos // 2 - posRadius[maxPos] // 2 + posRadius[maxPos]]
        else:
            return s[(maxPos - 1) // 2 - posRadius[maxPos] // 2 + 1 : (maxPos - 1) // 2 - posRadius[maxPos] // 2 + posRadius[maxPos]]


if __name__ == '__main__':
    print(Solution().longestPalindrome("bababd"))
    print(Solution().longestPalindrome("cbbd"))
