import collections
from typing import List, Dict


# class DictNode:
#     def __init__(self, val: str, end: bool):
#         self.val = val
#         self.end = end
#         self.children = None
#
#     def addChild(self, val: str, end: bool):
#         if self.children is None:
#             self.children: Dict[str, DictNode] = {}
#         childNode = self.children.get(val)
#         if childNode is None:
#             self.children[val] = childNode = DictNode(val, end)
#         else:
#             childNode.end = childNode.end or end
#         return childNode
#
#     def child(self, val: int):
#         return self.children.get(val) if self.children else None
#
#
# class DictTree:
#     def __init__(self, dict: List[str]):
#         self.root = DictNode('', False)
#         for i, word in enumerate(dict):
#             self.addWord(word)
#
#     def addWord(self, word: str):
#         curNode = self.root
#         wordLen = len(word)
#         for i, c in enumerate(word):
#             curNode = curNode.addChild(c, i + 1 == wordLen)
#
#     def check(self, s: str, fromI: int, dp: List[int]):
#         if dp[fromI] > -1:
#             return dp[fromI]
#         sLen = len(s)
#         minUnknown = sLen - fromI
#         curNode = self.root
#         for i in range(fromI, sLen):
#             if curNode is None:
#                 break
#             curNode = curNode.child(s[i])
#             if curNode and curNode.end:
#                 minUnknown = min(minUnknown, self.check(s, i + 1, dp))
#             else:
#                 minUnknown = min(minUnknown, i - fromI + 1 + self.check(s, i + 1, dp))
#         dp[fromI] = minUnknown
#         return minUnknown
#
#
# class Solution_v1:
#     def respace(self, dictionary: List[str], sentence: str) -> int:
#         sLen = len(sentence)
#         if sLen == 0:
#             return 0
#         dictTree = DictTree(dictionary)
#         dp = [-1] * (sLen + 1)
#         dp[sLen] = 0
#         return dictTree.check(sentence, 0, dp)


class Solution:
    def respace(self, dictionary: List[str], sentence: str) -> int:
        sLen = len(sentence)
        if sLen == 0:
            return 0
        dic: Dict[str, List[str]] = collections.defaultdict(list)
        for i, word in enumerate(dictionary):
            dic[word[0]].append(word)
        dp = list(range(sLen + 1))
        for i, c in enumerate(sentence):
            words = dic.get(c)
            if words:
                for word in words:
                    wordLen = len(word)
                    if i + wordLen <= sLen and sentence[i:i + wordLen] == word:
                        dp[i + wordLen] = min(dp[i], dp[i + wordLen])
            dp[i + 1] = min(dp[i + 1], dp[i] + 1)
        return dp[sLen]


if __name__ == '__main__':
    print(Solution().respace(["looked","just","like","her","brother"], "jesslookedjustliketimherbrother"))

