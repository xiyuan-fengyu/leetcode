package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 11:01.
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
                return time;
            }

        }

        public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
            Recode[] recodes = new Recode[username.length];
            Set<String> pathSet = new TreeSet<>();
            for (int i = 0; i < username.length; i++) {
                recodes[i] = new Recode(username[i], timestamp[i], website[i]);
                pathSet.add(website[i]);
            }
            Arrays.sort(recodes);

            List<String> pathList = new ArrayList<>(pathSet);
            Map<String, Integer> pathToCodes = new HashMap<>();
            int pathSize = pathList.size();
            for (int i = 0; i < pathSize; i++) {
                pathToCodes.put(pathList.get(i), i);
            }

            Map<String, List<Integer>> userRecodes = new HashMap<>();
            for (int i = 0; i < recodes.length; i++) {
                Recode recode = recodes[i];
                List<Integer> pathCodes = userRecodes.computeIfAbsent(recode.user, key -> new ArrayList<>());
                pathCodes.add(pathToCodes.get(recode.path));
            }

            int maxCount = 0;
            int maxCode = 0;
            HashMap<Integer, Integer> recodeCounts = new HashMap<>();
            for (Map.Entry<String, List<Integer>> entry : userRecodes.entrySet()) {
                List<Integer> records = entry.getValue();
                Set<Integer> existed =  new HashSet<>();
                for (int i = 0, size = records.size(); i < size - 2; i++) {
                    int codeI = records.get(i);
                    for (int j = i + 1; j < size - 1; j++) {
                        int codeJ = codeI * pathSize + records.get(j);
                        for (int k = j + 1; k < size; k++) {
                            int codeK = codeJ * pathSize + records.get(k);
                            if (!existed.contains(codeK)) {
                                existed.add(codeK);
                                Integer count = recodeCounts.merge(codeK, 1, (oldV, curV) -> oldV + curV);
                                if (count > maxCount) {
                                    maxCount = count;
                                    maxCode = codeK;
                                }
                            }
                        }
                    }
                }
            }

            String keyK = pathList.get(maxCode % pathSize);
            String keyJ = pathList.get(maxCode / pathSize % pathSize);
            String keyI = pathList.get(maxCode / pathSize / pathSize);
            return Arrays.asList(keyI, keyJ, keyK);
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "mostVisitedPattern"]
                [[], [["joe","joe","joe","james","james","james","james","mary","mary","mary"], [1,2,3,4,5,6,7,8,9,10], ["home","about","career","home","cart","maps","home","home","about","career"]]]
                [null, ["home", "about", "career"]]
                */),
                r(/*
                ["Solution", "mostVisitedPattern"]
                [[], [["u1","u1","u1","u2","u2","u2"],[1,2,3,4,5,6],["a","b","c","a","b","a"]]]
                [null, ["home", "about", "career"]]
                */)
        );
    }

}