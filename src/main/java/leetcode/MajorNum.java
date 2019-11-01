package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/11/1 17:38.
 */
@EnableTemplateString
public class MajorNum {

    class Solution {

        public int major(int[] arr) {
            int count = 0;
            int major = 0;
            for (int i = 0, len = arr.length; i < len; i++) {
                if (count == 0) {
                    count = 1;
                    major = arr[i];
                }
                else if (major == arr[i]) {
                    count++;
                }
                else {
                    count--;
                }
            }
            if (count == 0) {
                return -1;
            }
            count = 0;
            for (int i = 0, len = arr.length; i < len; i++) {
                if (major == arr[i]) {
                    count++;
                }
            }
            return count > arr.length / 2 ? major : -1;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "major", "major"]
                [[], [0,5,5,3,5,1,5,5], [0,5,5,3,5,1,5,7]]
                [null, 5, -1]
                */)
        );
    }

}
