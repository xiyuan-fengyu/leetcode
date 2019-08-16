package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 11:01.
 * https://leetcode-cn.com/contest/biweekly-contest-6/problems/analyze-user-website-visit-pattern/
 */
@EnableTemplateString
public class _1152_M {

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