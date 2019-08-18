package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 11:01.
 * https://leetcode-cn.com/contest/biweekly-contest-6/problems/analyze-user-website-visit-pattern/
 */
@EnableTemplateString
public class _1152_M {

    /*
    1152. 用户网站访问行为分析
    为了评估某网站的用户转化率，我们需要对用户的访问行为进行分析，并建立用户行为模型。日志文件中已经记录了用户名、访问时间 以及 页面路径。
    为了方便分析，日志文件中的 N 条记录已经被解析成三个长度相同且长度都为 N 的数组，分别是：用户名 username，访问时间 timestamp 和 页面路径 website。第 i 条记录意味着用户名是 username[i] 的用户在 timestamp[i] 的时候访问了路径为 website[i] 的页面。
    我们需要找到用户访问网站时的 『共性行为路径』，也就是有最多的用户都 至少按某种次序访问过一次 的三个页面路径。需要注意的是，用户 可能不是连续访问 这三个路径的。
    『共性行为路径』是一个 长度为 3 的页面路径列表，列表中的路径 不必不同，并且按照访问时间的先后升序排列。
    如果有多个满足要求的答案，那么就请返回按字典序排列最小的那个。（页面路径列表 X 按字典序小于 Y 的前提条件是：X[0] < Y[0] 或 X[0] == Y[0] 且 (X[1] < Y[1] 或 X[1] == Y[1] 且 X[2] < Y[2])）
    题目保证一个用户会至少访问 3 个路径一致的页面，并且一个用户不会在同一时间访问两个路径不同的页面。

    示例：
    输入：username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home","about","career"]
    输出：["home","about","career"]
    解释：
    由示例输入得到的记录如下：
    ["joe", 1, "home"]
    ["joe", 2, "about"]
    ["joe", 3, "career"]
    ["james", 4, "home"]
    ["james", 5, "cart"]
    ["james", 6, "maps"]
    ["james", 7, "home"]
    ["mary", 8, "home"]
    ["mary", 9, "about"]
    ["mary", 10, "career"]
    有 2 个用户至少访问过一次 ("home", "about", "career")。
    有 1 个用户至少访问过一次 ("home", "cart", "maps")。
    有 1 个用户至少访问过一次 ("home", "cart", "home")。
    有 1 个用户至少访问过一次 ("home", "maps", "home")。
    有 1 个用户至少访问过一次 ("cart", "maps", "home")。

    提示：
    3 <= N = username.length = timestamp.length = website.length <= 50
    1 <= username[i].length <= 10
    0 <= timestamp[i] <= 10^9
    1 <= website[i].length <= 10
    username[i] 和 website[i] 都只含小写字符
     */

    class Solution {

        private final class Recode implements Comparable {
            String user;
            int time;
            String path;
            public Recode(String user, int time, String path) {
                this.user = user;
                this.time = time;
                this.path = path;
            }

            @Override
            public int compareTo(Object o) {
                return time - ((Recode) o).time;
            }

        }

        /**
         * 要点
         * 减少排序次数
         * 由于网站地址有限，所以可以按照字典顺序编号，在计算三个网站的组合key时，通过编号来计算唯一key，避免用字符串拼接作为key，这样会耗费大量时间
         * 另外jdk8中提供的map相关的新的API中，有些参数列表中有Function参数，例如 merge， computeIfAbsent，通过lambda来构建Function参数时，会产生临时类和临时类实例，导致速度大大下降，尽量不要使用这些API，还是乖乖使用老办法
         */
        public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
            Recode[] allRecodes = new Recode[username.length];
            for (int i = 0; i < username.length; i++) {
                allRecodes[i] = new Recode(username[i], timestamp[i], website[i]);
            }
            Arrays.sort(allRecodes);

            Arrays.sort(website);
            List<String> pathList = new ArrayList<>();
            int pathSize = 0;
            Map<String, Integer> pathToCodes = new HashMap<>();
            for (String path : website) {
                if (!pathToCodes.containsKey(path)) {
                    pathToCodes.put(path, pathSize++);
                    pathList.add(path);
                }
            }

            Map<String, List<Integer>> userRecodes = new HashMap<>();
            for (Recode recode : allRecodes) {
                List<Integer> pathCodes = userRecodes.get(recode.user);
                if (pathCodes == null) {
                    pathCodes = new ArrayList<>();
                    userRecodes.put(recode.user, pathCodes);
                }
                pathCodes.add(pathToCodes.get(recode.path));
            }

            int maxCount = 0;
            int minCode = Integer.MAX_VALUE;
            HashMap<Integer, Integer> recodeCounts = new HashMap<>();
            for (List<Integer> records : userRecodes.values()) {
                Set<Integer> existed =  new HashSet<>();
                for (int i = 0, size = records.size(); i < size - 2; i++) {
                    int codeI = records.get(i);
                    for (int j = i + 1; j < size - 1; j++) {
                        int codeJ = codeI * pathSize + records.get(j);
                        for (int k = j + 1; k < size; k++) {
                            int codeK = codeJ * pathSize + records.get(k);
                            if (!existed.contains(codeK)) {
                                existed.add(codeK);
                                Integer count = recodeCounts.getOrDefault(codeK, 0);
                                recodeCounts.put(codeK, ++count);
                                if (count > maxCount) {
                                    maxCount = count;
                                    minCode = codeK;
                                }
                                else if (count == maxCount && codeK < minCode) {
                                    minCode = codeK;
                                }
                            }
                        }
                    }
                }
            }

            String keyK = pathList.get(minCode % pathSize);
            String keyJ = pathList.get(minCode / pathSize % pathSize);
            String keyI = pathList.get(minCode / pathSize / pathSize);
            return Arrays.asList(keyI, keyJ, keyK);
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "mostVisitedPattern"]
                [[], [["dowg","dowg","dowg"],[158931262,562600350,148438945],["y","loedo","y"]]]
                [null, ["y","y","loedo"]]
                */),
                r(/*
                ["Solution", "mostVisitedPattern"]
                [[], [["joe","joe","joe","james","james","james","james","mary","mary","mary"], [1,2,3,4,5,6,7,8,9,10], ["home","about","career","home","cart","maps","home","home","about","career"]]]
                [null, ["home", "about", "career"]]
                */),
                r(/*
                ["Solution", "mostVisitedPattern"]
                [[], [["u1","u1","u1","u2","u2","u2"],[1,2,3,4,5,6],["a","b","c","a","b","a"]]]
                [null, ["a", "b", "a"]]
                */)
        );
    }

}