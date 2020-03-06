/**
 * 合并排序的思路
 * @param nums1
 * @param nums2
 */
function findMedianSortedArrays_v1(nums1: number[], nums2: number[]) {
    let findIndex = Math.floor((nums1.length + nums2.length - 1) / 2);
    let findTwoAvg = (nums1.length + nums2.length) % 2 == 0;
    let index = -1;
    let index1 = -1;
    let index2 = -1;
    nums1.push(Number.MAX_VALUE);
    nums2.push(Number.MAX_VALUE);
    try {
        while (index < findIndex) {
            let v1 = nums1[index1 + 1];
            let v2 = nums2[index2 + 1];
            index++;
            if (v1 < v2) {
                index1++;
                if (index == findIndex) {
                    if (findTwoAvg) {
                        return (v1 + Math.min(nums1[index1 + 1], v2)) / 2;
                    }
                    else {
                        return v1;
                    }
                }
            }
            else {
                index2++;
                if (index == findIndex) {
                    if (findTwoAvg) {
                        return (v2 + Math.min(nums2[index2 + 1], v1)) / 2;
                    }
                    else {
                        return v2;
                    }
                }
            }
        }
    }
    finally {
        nums1.pop();
        nums2.pop();
    }
}

/**
 * 可以转换为求第k个数的问题，k = (nums1.length + nums2.length - 1) / 2，每次排除 k / 2 个数
 * @param nums1
 * @param nums2
 */
function findMedianSortedArrays(nums1: number[], nums2: number[]) {
    let findTwoAvg = (nums1.length + nums2.length) % 2 == 0;
    let k = Math.floor((nums1.length + nums2.length + 1) / 2);
    let offset1 = 0;
    let offset2 = 0;
    while (k != 1) {
        let kHalf = Math.floor(k / 2);
        let v1 = offset1 + kHalf - 1 < nums1.length ? nums1[offset1 + kHalf - 1] : Number.MAX_VALUE;
        let v2 = offset2 + kHalf - 1 < nums2.length ? nums2[offset2 + kHalf - 1] : Number.MAX_VALUE;
        if (v1 < v2) {
            offset1 += kHalf;
        }
        else {
            offset2 += kHalf;
        }
        k -= kHalf;
    }

    let v1 = offset1 < nums1.length ? nums1[offset1] : Number.MAX_VALUE;
    let v2 = offset2 < nums2.length ? nums2[offset2] : Number.MAX_VALUE;
    if (findTwoAvg) {
        if (v1 == v2) {
            return v1;
        }
        else if (v1 < v2) {
            const v1R = offset1 + 1 < nums1.length ? nums1[offset1 + 1] : Number.MAX_VALUE;
            return (v1 + Math.min(v1R, v2)) / 2;
        }
        else {
            const v2R = offset2 + 1 < nums2.length ? nums2[offset2 + 1] : Number.MAX_VALUE;
            return (v2 + Math.min(v2R, v1)) / 2;
        }
    }
    else {
        return Math.min(v1, v2);
    }
}
/*
1 3 4 5 6 9
2 4 6 8 9 10
 */
console.log(findMedianSortedArrays([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22],
    [0,6]));

