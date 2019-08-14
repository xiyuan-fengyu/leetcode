package leetcode;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2019/8/14 12:56.
 */
public class Tester {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionalInterface
    public interface SolutionInit<T> {

        T apply(String solutionName, Object[] params);

    }

    @FunctionalInterface
    public interface SolutionParamsConverter<T> {

        Object[] apply(T solution, Method method, Object[] params) throws Exception;

    }

    public static <T> long test(String callInputOutputs, SolutionInit<T> solutionInit, SolutionParamsConverter<T> converter) {
        String[] split = callInputOutputs.split("\n");
        List calls = null;
        List params = null;
        List outputs = null;
        try {
            calls = objectMapper.readValue(split[0], List.class);
            params = objectMapper.readValue(split[1], List.class);
            if (split.length >= 3) {
                outputs = objectMapper.readValue(split[2], List.class);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("bad test cases");
        }

        int size = calls.size();
        if (params.size() != size || (outputs != null && outputs.size() != size)) {
            throw new IllegalArgumentException("different size between calls, params and outputs");
        }

        long cost = 0;
        try {
            System.out.println("test cases:\n" + callInputOutputs);

            T solution = null;
            Method[] methods = null;
            String failedCaseInfo = null;
            for (int i = 0; i < size; i++) {
                Object[] param = ((List) params.get(i)).toArray();
                if (i == 0) {
                    solution = solutionInit.apply((String) calls.get(i), param);
                    methods = solution.getClass().getDeclaredMethods();
                }
                else {
                    String methodName = (String) calls.get(i);
                    Method method = null;
                    for (int j = 0; j < methods.length; j++) {
                        if (methods[j].getName().equals(methodName)) {
                            method = methods[j];
                            method.setAccessible(true);
                            break;
                        }
                    }
                    Object[] paramConverted = converter.apply(solution, method, param);
                    long now = System.nanoTime();
                    Object actualResult = method.invoke(solution, paramConverted);
                    cost += System.nanoTime() - now;
                    if (outputs != null) {
                        String actualResultStr = objectMapper.writeValueAsString(actualResult);
                        String expectResultStr = objectMapper.writeValueAsString(outputs.get(i));
                        if (!actualResultStr.equals(expectResultStr)) {
                            failedCaseInfo = "case " + i + " check failed: " + objectMapper.writeValueAsString(param) + " expect: " + expectResultStr + " actual: " + actualResultStr;
                            break;
                        }
                    }
                }
            }
            cost /= 1000000;
            if (failedCaseInfo != null) {
                System.err.println(failedCaseInfo + "\n");
            }
            else if (outputs != null) {
                System.out.println("all cases passed, cost: " + cost + "ms\n");
            }
            else {
                System.out.println("all cases executed, cost: " + cost + "ms\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cost;
    }

}
