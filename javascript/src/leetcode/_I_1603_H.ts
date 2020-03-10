
function sub(v1: [number, number], v2: [number, number]): [number, number] {
    return [v1[0] - v2[0], v1[1] - v2[1]];
}

function crossProduct(v1: [number, number], v2: [number, number]): number {
    return v1[0] * v2[1] - v1[1] * v2[0];
}

function intersection(start1: [number, number], end1: [number, number], start2: [number, number], end2: [number, number]) {
    const d1 = sub(end1, start1);
    const d2 = sub(end2, start2);
    const s1SubS2 = sub(start1, start2);
    const s1SubS2_CP_d2 = crossProduct(s1SubS2, d2);
    const s1SubS2_CP_d1 = crossProduct(s1SubS2, d1);
    const d2_CP_d1 = crossProduct(d2, d1);
    if (d2_CP_d1 != 0) {
        // 相交
        const r1 = s1SubS2_CP_d2 / d2_CP_d1;
        const r2 = s1SubS2_CP_d1 / d2_CP_d1;
        if (0 <= r1 && r1 <= 1 && 0 <= r2 && r2 <= 1) {
            // 线段内相交
            return [start1[0] + r1 * d1[0], start1[1] + r1 * d1[1]];
        }
        else {
            // 延长线相交
            return [];
        }
    }
    else {
        if (s1SubS2_CP_d2 == 0) {
            // 共线
            const points = [
                [...start1, 1],
                [...end1, 1],
                [...start2, 2],
                [...end2, 2]
            ];
            points.sort();
            if (points[0][2] != points[1][2] || (points[1][0] == points[2][0] && points[1][1] == points[2][1])) {
                return [points[1][0], points[1][1]];
            }
            return [];
        }
        else {
            // 平行
            return [];
        }
    }
}

console.log(intersection([0, 0], [1, 0], [1, 1], [0, -1]));
console.log(intersection([0, 0], [0, 1], [0, 2], [0,3]));
