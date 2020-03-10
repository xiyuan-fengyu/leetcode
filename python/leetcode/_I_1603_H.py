from typing import List


class Solution:
    def sub(self, v1: List[int], v2: List[int]) -> List[int]:
        return [v1[0] - v2[0], v1[1] - v2[1]]

    def crossProduct(self, v1: List[int], v2: List[int]) -> int:
        return v1[0] * v2[1] - v1[1] * v2[0]

    def intersection(self, start1: List[int], end1: List[int], start2: List[int], end2: List[int]) -> List[float]:
        d1 = self.sub(end1, start1)
        d2 = self.sub(end2, start2)
        s1SubS2 = self.sub(start1, start2)
        s1SubS2_CP_d2 = self.crossProduct(s1SubS2, d2)
        s1SubS2_CP_d1 = self.crossProduct(s1SubS2, d1)
        d2_CP_d1 = self.crossProduct(d2, d1)
        if d2_CP_d1 != 0:
            # 相交
            r1 = s1SubS2_CP_d2 / d2_CP_d1
            r2 = s1SubS2_CP_d1 / d2_CP_d1
            if 0 <= r1 <= 1 and 0 <= r2 <= 1:
                # 线段内相交
                return [start1[0] + r1 * d1[0], start1[1] + r1 * d1[1]]
            return []
        else:
            if s1SubS2_CP_d2 == 0:
                # 共线
                points = [
                    start1 + [1],
                    end1 + [1],
                    start2 + [2],
                    end2 + [2]
                ]
                # 注意 sorted(points) 不是原地排序
                points.sort()
                if points[0][2] != points[1][2] or (points[1][0] == points[2][0] and points[1][1] == points[2][1]):
                    return points[1][:2]
                return []
            else:
                # 平行
                return []


if __name__ == '__main__':
    print(Solution().intersection([0, 0], [1, 0], [1, 1], [0, -1]))
    print(Solution().intersection([0, 0], [0, 1], [0, 0], [0,-1]))
