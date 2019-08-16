package leetcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        Object[] apply(T solution, String methodName, Object[] params) throws Exception;

    }

    public static long test(String callInputOutputs) {
        return test(callInputOutputs, null, null);
    }

    public static <T> long test(String callInputOutputs, SolutionInit<T> solutionInit) {
        return test(callInputOutputs, solutionInit, null);
    }

    public static <T> long test(String callInputOutputs, SolutionInit<T> solutionInit, SolutionParamsConverter<T> paramConverter) {
        String[] split = callInputOutputs.split("\n");
        List calls = null;
        List paramsArr = null;
        List outputs = null;
        try {
            calls = objectMapper.readValue(split[0], List.class);
            paramsArr = objectMapper.readValue(split[1], List.class);
            if (split.length >= 3) {
                outputs = objectMapper.readValue(split[2], List.class);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("bad test cases");
        }

        int size = calls.size();
        if (paramsArr.size() != size || (outputs != null && outputs.size() != size)) {
            throw new IllegalArgumentException("different size between calls, params and outputs");
        }

        long cost = 0;
        String failedCaseInfo = null;
        boolean allExecuted = false;
        List<Object> actualOutputs = new ArrayList<>();
        try {
            System.out.println("test cases:\n" + callInputOutputs);

            T solution = null;
            for (int i = 0; i < size; i++) {
                Object objI = paramsArr.get(i);
                Object[] params = objI instanceof List ? ((List) objI).toArray() : new Object[] {objI};
                if (i == 0) {
                    if (solutionInit != null) {
                        solution = solutionInit.apply((String) calls.get(i), params);
                    }
                    else {
                        solution = initSolution((String) calls.get(i), params);
                    }
                    actualOutputs.add(null);
                }
                else {
                    String methodName = (String) calls.get(i);

                    Object[] paramConverted = null;
                    Method method = null;
                    if (paramConverter != null) {
                        paramConverted = paramConverter.apply(solution, methodName, params);
                    }
                    Object[] methodAndConvertedArgs = findMethodAndConvertArgs(solution, methodName, paramConverted == null ? params : paramConverted);
                    if (methodAndConvertedArgs != null) {
                        method = (Method) methodAndConvertedArgs[0];
                        paramConverted = (Object[]) methodAndConvertedArgs[1];
                    }

                    if (method == null) {
                        throw new RuntimeException("cannot find a method named " + methodName + " with params " + objectMapper.writeValueAsString(params) + " in class " + solution.getClass().getName());
                    }

                    long now = System.nanoTime();
                    Object actualResult = method.invoke(solution, paramConverted);
                    cost += System.nanoTime() - now;
                    if (outputs != null) {
                        String actualResultStr = objectMapper.writeValueAsString(actualResult);
                        String expectResultStr = objectMapper.writeValueAsString(outputs.get(i));
                        StringBuilder paramsStr = new StringBuilder();
                        for (int j = 0; j < params.length; j++) {
                            paramsStr.append(j == 0 ? "" : ", ").append(objectMapper.writeValueAsString(params[j]));
                        }
                        if (!actualResultStr.equals(expectResultStr)) {
                            failedCaseInfo = "case " + i + " check failed: " + methodName + "(" + paramsStr + ") expect: " + expectResultStr + " actual: " + actualResultStr;
                            break;
                        }
                    }
                    else {
                        actualOutputs.add(actualResult);
                    }
                }
            }
            allExecuted = true;
        }
        catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        cost /= 1000000;
        if (failedCaseInfo != null) {
            System.out.println("\u001B[38;05;91m" + failedCaseInfo + "\u001B[0m\n");
        }
        else if (allExecuted) {
            if (outputs != null) {
                System.out.println("all cases passed, cost: " + cost + "ms\n");
            }
            else {
                String actualOutputsStr = "";
                try {
                    actualOutputsStr = objectMapper.writeValueAsString(actualOutputs) + "\n";
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println(actualOutputsStr +  "all cases executed, cost: " + cost + "ms\n");
            }
        }
        return cost;
    }

    @SuppressWarnings("unchecked")
    private static <T> T initSolution(String solutionName, Object[] params) throws Exception {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        Class wrapperClass = null;
        Class solutionClass = null;
        for (int i = 2; i < stackTrace.length; i++) {
            try {
                solutionClass = Class.forName(stackTrace[i].getClassName() + "$" + solutionName);
                wrapperClass = Class.forName(stackTrace[i].getClassName());
                break;
            } catch (ClassNotFoundException e) {
            }
        }

        if (wrapperClass == null || solutionClass == null) {
            throw new RuntimeException("cannot find a class for solution: " + solutionName
                    + ", you can set a lambda function to init the solution at the second parameter of Tester.test");
        }

        T solution = tryInitSolution(wrapperClass, solutionClass, params);
        if (solution == null) {
            // 尝试将 params 当成一个参数来初始化
            solution = tryInitSolution(wrapperClass, solutionClass, new Object[] {params});
        }
        if (solution != null) {
            return solution;
        }

        throw new RuntimeException("cannot match a constuctor for solution: " + solutionName + " in class " + solutionClass.getName()
                + ", you can set a lambda function to init the solution at the second parameter of Tester.test");
    }

    @SuppressWarnings("unchecked")
    private static <T> T tryInitSolution(Class<?> wrapperClass, Class<?> solutionClass, Object[] params) {
        try {
            boolean isSolutionClassStatic = Modifier.isStatic(solutionClass.getModifiers());
            Constructor[] constructors = solutionClass.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                if (isSolutionClassStatic) {
                    if (constructor.getParameterCount() == params.length) {
                        Object[] convertedArgs = convertArgs(constructor.getParameterTypes(), params);
                        if (convertedArgs != null) {
                            constructor.setAccessible(true);
                            return (T) constructor.newInstance(convertedArgs);
                        }
                    }
                }
                else if (constructor.getParameterCount() == params.length + 1
                        && constructor.getParameterTypes()[0] == wrapperClass) {
                    constructor.setAccessible(true);
                    Object[] newParams = new Object[params.length + 1];
                    newParams[0] = wrapperClass.newInstance();
                    System.arraycopy(params, 0, newParams, 1, params.length);
                    return (T) constructor.newInstance(newParams);
                }
            }
        }
        catch (Exception e) {
        }
        return null;
    }

    private static <T> Object[] findMethodAndConvertArgs(T solution, String methodName, Object[] params) {
        Object[] methodAndConvertArgs = tryFindMethodAndConvertArgs(solution, methodName, params);
        if (methodAndConvertArgs != null) {
            return methodAndConvertArgs;
        }
        // 将 params 当成一个参数来尝试
        return tryFindMethodAndConvertArgs(solution, methodName, new Object[]{params});
    }

    private static <T> Object[] tryFindMethodAndConvertArgs(T solution, String methodName, Object[] params) {
        for (Method tempMethod : solution.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(tempMethod.getModifiers())
                    && tempMethod.getName().equals(methodName)
                    && tempMethod.getParameterCount() == params.length) {
                Object[] convertedArgs = convertArgs(tempMethod.getParameterTypes(), params);
                if (convertedArgs != null) {
                     return new Object[] {tempMethod, convertedArgs};
                }
            }
        }
        return null;
    }

    private static Object[] convertArgs(Class[] classes, Object[] args) {
        Object[] convertedArgs = new Object[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                convertedArgs[i] = convertArg(classes[i], args[i]);
            }
        }
        catch (Exception e) {
            return null;
        }
        return convertedArgs;
    }

    private static Object convertArg(Class<?> clazz, Object arg) throws Exception {
        if (arg == null) {
            if (clazz.isPrimitive()) {
                throw new Exception();
            }
            return null;
        }
        String argStr = objectMapper.writeValueAsString(arg);
        return objectMapper.readValue(argStr, clazz);
    }

}
