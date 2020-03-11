
class Solution:
    def maximum(self, a: int, b: int) -> int:
        sub = a - b
        sign = sub >> 63
        return (1 + sign) * a + (-sign) * b


if __name__ == '__main__':
    print(Solution().maximum(2147483647, -2147483648))
