
function pileBox(boxes: [number, number, number][]) {
    let maxH = 0;
    if (boxes.length) {
        boxes.sort((box1, box2) => box1[0] != box2[0] ? box1[0] - box2[0] :
            box1[1] != box2[1] ? box1[1] - box2[1] : box1[2] - box2[2]);
        const cache = new Array(boxes.length);
        cache[0] = maxH = boxes[0][2];
        for (let i = 1, len = boxes.length; i < len; i++) {
            const box = boxes[i];
            let localMaxH = 0;
            for (let j = 0; j < i; j++) {
                const prevBox = boxes[j];
                if (prevBox[0] < box[0] && prevBox[1] < box[1] && prevBox[2] < box[2]) {
                    if (localMaxH < cache[j]) {
                        localMaxH = cache[j];
                    }
                }
            }
            maxH = Math.max(maxH, cache[i] = localMaxH + box[2]);
        }
    }
    return maxH;
}

console.log(pileBox([[1, 1, 1], [2, 3, 4], [2, 6, 7], [3, 4, 5]]));
