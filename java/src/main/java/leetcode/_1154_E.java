package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/14 9:42.
 * https://leetcode-cn.com/problems/ordinal-number-of-date/
 */
@EnableTemplateString
public class _1154_E {

    /*
    1154. 一年中的第几天
    给你一个按 YYYY-MM-DD 格式表示日期的字符串 date，请你计算并返回该日期是当年的第几天。
    通常情况下，我们认为 1 月 1 日是每年的第 1 天，1 月 2 日是每年的第 2 天，依此类推。每个月的天数与现行公元纪年法（格里高利历）一致。

    示例 1：
    输入：date = "2019-01-09"
    输出：9

    示例 2：
    输入：date = "2019-02-10"
    输出：41

    示例 3：
    输入：date = "2003-03-01"
    输出：60

    示例 4：
    输入：date = "2004-03-01"
    输出：61

    提示：
    date.length == 10
    date[4] == date[7] == '-'，其他的 date[i] 都是数字。
    date 表示的范围从 1900 年 1 月 1 日至 2019 年 12 月 31 日。
     */

    class Solution {

        private final int[] daysOfMonth = {
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
        [[],"2019-01-09","2019-02-10","2003-03-01","2004-03-01","2016-02-29","1900-03-01"]
        [null,9,41,60,61,60,60]
        */));
    }

}
