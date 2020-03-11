
function maximum(a: number, b: number): number {
    const sub = BigInt(a - b);
    const sign = Number(sub >> BigInt(63));
    return (1 + sign) * a + (-sign) * b;
}

console.log(maximum(2147483647, -2147483648));
