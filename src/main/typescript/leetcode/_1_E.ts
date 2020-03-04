/**
 * @param {number[]} nums
 * @param {number} target
 * @return {number[]}
 */
function twoSum(nums: number[], target: number): [number, number] {
    const existed = {};
    for (let i = 0, len = nums.length; i < len; i++) {
        const num = nums[i];
        const existedIndex = existed[target - num];
        if (existedIndex != null) {
            return [existedIndex, i];
        }
        existed[num] = i;
    }
    return [-1, -1];
}
