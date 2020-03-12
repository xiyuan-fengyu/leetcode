from typing import List, Dict


class Solution:
    def bestLine(self, points: List[List[int]]) -> List[int]:
        best = [0, 1]
        maxPointNum = 2
        # Dict[float, [firstPointIndex, secondPointIndex, count]]
        lines: Dict[float, List[int]] = {}
        firstI = 0
        pointNum = len(points)
        while firstI < pointNum - maxPointNum:
            lines.clear()
            sameToFirst = 1
            firstP = points[firstI]
            localMaxNum = 0
            localMaxK = None
            for secondI in range(firstI + 1, pointNum):
                secondP = points[secondI]
                xd = secondP[0] - firstP[0]
                yd = secondP[1] - firstP[1]
                if xd == 0:
                    if yd == 0:
                        sameToFirst += 1
                        continue
                    k = float('inf')
                elif yd == 0:
                    k = 0
                else:
                    k = yd / xd
                line = lines.get(k)
                if line is None:
                    lines[k] = line = [firstI, secondI, 0]
                line[2] += 1
                if localMaxNum < line[2]:
                    localMaxNum = line[2]
                    localMaxK = k
            if maxPointNum < sameToFirst + localMaxNum:
                maxPointNum = sameToFirst + localMaxNum
                if localMaxK is None:
                    best = [firstI, firstI + 1]
                else:
                    best = lines[localMaxK][:2]
            firstI += 1
        return best


if __name__ == '__main__':
    print(Solution().bestLine([[0,0],[1,1],[1,0],[2,0]]))
