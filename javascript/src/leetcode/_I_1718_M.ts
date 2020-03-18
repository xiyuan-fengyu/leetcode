function shortestSeq(big: number[], small: number[]): number[] {
    let minSeq = [];
    if (big.length < small.length) {
        return minSeq;
    }

    let minSeqLen = big.length + 1;
    let left = 0;
    let right = 0;
    let contains = 0;
    const numCounts = {};
    for (let number of small) {
        numCounts[number] = 0;
    }
    while (right < big.length) {
        while (right < big.length && contains != small.length) {
            let count = numCounts[big[right]];
            if (count > -1) {
                if (count == 0) {
                    contains++;
                }
                numCounts[big[right]] = count + 1;
            }
            right++;
        }
        if (contains == small.length) {
            while (left  < right) {
                let count = numCounts[big[left]];
                if (count > -1) {
                    if (contains + 1 == small.length && count == 1) {
                        break;
                    }
                    else if (count == 1) {
                        if (minSeqLen > right - left) {
                            minSeqLen = right - left;
                            minSeq = [left, right - 1];
                        }
                        contains--;
                    }
                    numCounts[big[left]] = count - 1;
                }
                left++;
            }
        }
    }
    return minSeq;
}

console.log(shortestSeq([7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7], [1,5,9]));
console.log(shortestSeq([842, 336, 777, 112, 789, 801, 922, 874, 634, 121, 390, 614, 179, 565, 740, 672, 624, 130, 555, 714, 9, 950, 887, 375, 312, 862, 304, 121, 360, 579, 937, 148, 614, 885, 836, 842, 505, 187, 210, 536, 763, 880, 652, 64, 272, 675, 33, 341, 101, 673, 995, 485, 16, 434, 540, 284, 567, 821, 994, 174, 634, 597, 919, 547, 340, 2, 512, 433, 323, 895, 965, 225, 702, 387, 632, 898, 297, 351, 936, 431, 468, 694, 287, 671, 190, 496, 80, 110, 491, 365, 504, 681, 672, 825, 277, 138, 778, 851, 732, 176], [950, 885, 702, 101, 312, 652, 555, 936, 842, 33, 634, 851, 174, 210, 287, 672, 994, 614, 732, 919, 504, 778, 340, 694, 880, 110, 777, 836, 365, 375, 536, 547, 887, 272, 995, 121, 225, 112, 740, 567, 898, 390, 579, 505, 351, 937, 825, 323, 874, 138, 512, 671, 297, 179, 277, 304]));
