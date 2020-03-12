
function bestLine(points: [number, number][]) {
    const best: [number, number] = [0, 1];
    let maxPointNum = 2;
    // Map<斜率, [firstPointIndex, secondPointIndex, pointCount]>
    const lines = new Map<number, [number, number, number]>();
    for (let i = 0, len = points.length; i < len - maxPointNum; i++) {
        lines.clear();
        const firstPoint = points[i];
        let sameToFirst = 1;
        let localMaxNum = 0;
        let localMaxK = NaN;
        for (let j = 0; j < len; j++) {
            const secondPoint = points[j];
            const xd = secondPoint[0] - firstPoint[0];
            const yd = secondPoint[1] - firstPoint[1];
            let k: number;
            if (xd == 0) {
                if (yd == 0) {
                    sameToFirst++;
                    continue;
                }
                else {
                    k = Infinity;
                }
            }
            else if (yd == 0) {
                k = 0;
            }
            else {
                k = yd / xd;
            }
            let line = lines.get(k);
            if (line == null) {
                lines.set(k, line = [i, j, 0]);
            }
            line[2]++;
            if (localMaxNum < line[2]) {
                localMaxNum = line[2];
                localMaxK = k;
            }
        }
        if (maxPointNum < sameToFirst + localMaxNum) {
            maxPointNum = sameToFirst + localMaxNum;
            if (isNaN(localMaxK)) {
                best[0] = i;
                best[1] = i + 1;
            }
            else {
                const line = lines.get(localMaxK);
                best[0] = line[0];
                best[1] = line[1];
            }
        }
    }
    return best;
}

console.log(bestLine([[0, 0], [0,0], [0,0]]));
console.log(bestLine([[0,0],[1,1],[1,0],[2,0]]));
