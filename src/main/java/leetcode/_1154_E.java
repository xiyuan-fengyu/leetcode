package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/14 9:42.
 * https://leetcode-cn.com/problems/ordinal-number-of-date/submissions/
 */
@EnableTemplateString
public class _1154_E {

    private static class Solution {

        private static final int[] daysOfMonth = {
                0, 31, 59, 90, 120, 151, 181, 212,
                243, 273, 304, 334
        };

        public int dayOfYear(String date) {
            // 自行解析年月日，速度比 String.split + Integer.parseInt 效率要高很多
            int[] yearMonthDay = {0, 0, 0};
            for (int i = 0, len = date.length(), splitI = 0; i < len; i++) {
                char c = date.charAt(i);
                if (c == '-') {
                    splitI++;
                    continue;
                }
                yearMonthDay[splitI] = yearMonthDay[splitI] * 10 + (c - '0');
            }

            int year = yearMonthDay[0];
            int month = yearMonthDay[1];
            int dayOfY = daysOfMonth[month - 1] + yearMonthDay[2];
//            if (month > 2 && (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))) {
//                dayOfY++;
//            }
            // 这里采用多层 if 嵌套效率比上面的效率要高一丢丢
            if (month > 2) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        dayOfY++;
                    }
                }
                else if (year % 4 == 0) {
                    dayOfY++;
                }
            }
            return dayOfY;
        }

    }

    public static void main(String[] args) {
        Tester.test(r(/*
        ["Solution","dayOfYear","dayOfYear","dayOfYear","dayOfYear","dayOfYear","dayOfYear"]
        [[],["2019-01-09"],["2019-02-10"],["2003-03-01"],["2004-03-01"],["2016-02-29"],["1900-03-01"]]
        [null,9,41,60,61,60,60]
        */), (solutionName, params) -> new Solution(), (solution, method, params) -> params);
    }

}
