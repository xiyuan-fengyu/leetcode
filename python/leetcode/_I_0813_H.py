from typing import List


class Solution:
    def pileBox(self, boxes: List[List[int]]) -> int:
        maxH = 0
        boxesLen = len(boxes)
        if boxesLen > 0:
            # 注意 sorted 不是原地排序，而boxes.sort是原地排序
            # key=functools.cmp_to_key(lambda box1, box2: box1[0] - box2[0] if box1[0] != box2[0] else (
            #     box1[1] - box2[1] if box1[1] != box2[1] else box1[2] - box2[2]))
            # 默认的排序和上面的key=...的排序功能等效
            boxes.sort()
            cache = [0] * boxesLen
            cache[0] = maxH = boxes[0][2]
            for i in range(1, boxesLen):
                box = boxes[i]
                localMaxH = 0
                for j in range(0, i):
                    prevBox = boxes[j]
                    if prevBox[0] < box[0] and prevBox[1] < box[1] and prevBox[2] < box[2]:
                        if localMaxH < cache[j]:
                            localMaxH = cache[j]
                cache[i] = localMaxH + box[2]
                if maxH < cache[i]:
                    maxH = cache[i]
        return maxH


if __name__ == '__main__':
    print(Solution().pileBox([[9, 9, 10], [8, 10, 9], [9, 8, 10], [9, 8, 10], [10, 8, 8], [9, 8, 9], [9, 8, 8], [8, 9, 10], [10, 9, 10], [8, 8, 10], [10, 9, 10], [10, 9, 8], [8, 9, 9], [9, 10, 8], [8, 9, 9], [10, 10, 9], [8, 9, 10], [8, 10, 10], [8, 9, 10], [10, 10, 8], [10, 10, 9], [9, 10, 10], [10, 8, 9], [10, 10, 9], [8, 9, 10], [8, 8, 9], [8, 10, 10], [9, 9, 10], [10, 8, 8], [10, 10, 8], [8, 9, 9], [8, 9, 8], [10, 10, 8], [8, 10, 8], [10, 9, 10], [9, 9, 10], [9, 9, 9], [8, 9, 8], [9, 8, 8], [8, 9, 10], [10, 10, 8], [9, 9, 9], [10, 10, 10], [10, 9, 8], [9, 8, 9], [8, 8, 10], [8, 8, 8], [8, 8, 8], [8, 9, 10], [10, 9, 8], [8, 10, 8], [8, 10, 10], [9, 10, 10], [8, 8, 9], [9, 9, 9], [10, 8, 8], [8, 10, 10], [9, 10, 9], [9, 9, 8], [8, 10, 9], [9, 8, 8], [9, 9, 10], [9, 10, 10], [8, 8, 10]]))
