function maxAliveYear(birth: number[], death: number[]): number {
    // 注意：.fill(0) 是关键操作
    const changes = new Array(102).fill(0);
    for (let i = 0, len = birth.length; i < len; i++) {
        changes[birth[i] - 1900]++;
        changes[death[i] - 1900 + 1]--;
    }
    let curAlive = 0;
    let maxAlive = 0;
    let maxAliveYear = 1900;
    for (let i = 0, len = changes.length; i < len; i++) {
        if ((curAlive += changes[i]) > maxAlive) {
            maxAlive = curAlive;
            maxAliveYear = 1900 + i;
        }
    }
    return maxAliveYear;
}

console.log(maxAliveYear([1900, 1901, 1950], [1948, 1951, 2000]));
