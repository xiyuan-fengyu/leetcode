package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/11/1 17:19.
 */
@EnableTemplateString
public class MidOfTwoAscArr {

    class Solution {

        public int mid(int[] A, int[] B) {
            int midIndex = (A.length + B.length - 1) / 2;
            int aI = -1;
            int bI = -1;
            int index = -1;
            while (aI + 1 < A.length && bI + 1 < B.length) {
                if (A[aI + 1] < B[bI + 1]) {
                    aI++;
                    index++;
                    if (index == midIndex) {
                        return A[aI];
                    }
                }
                else {
                    bI++;
                    index++;
                    if (index == midIndex) {
                        return B[bI];
                    }
                }
            }
            if (aI + 1 == A.length) {
                return B[midIndex - index + bI];
            }
            else {
                return A[midIndex - index + aI];
            }
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "mid", "mid"]
                [[], [[11,13,15,17,19],[2,4,6,8,20]], [[1, 2, 3], [3, 4, 5]]]
                [null,11, 3]
                */)
        );
    }

}
