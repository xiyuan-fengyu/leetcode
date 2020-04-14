function missingTwo(nums: number[]) {
    let n = nums.length + 2;
    let sum = Math.floor((1 + n) * n / 2);
    let sumWithoutAB = 0;
    for (let num of nums) {
        sumWithoutAB += num;
    }
    let sumAB = sum - sumWithoutAB;
    let halfAB = Math.floor(sumAB / 2);
    let sum1 = 0;
    for (let num of nums) {
        if (num <= halfAB) {
            sum1 += num;
        }
    }
    let a = Math.floor((1 + halfAB) * halfAB / 2 - sum1);
    return [a, sumAB - a];
}

console.log(missingTwo([1]));
