package leetcode;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

        Object[] apply(T solution, String methodName, Object[] params) throws Exception;

    }

    public static long test(String callInputOutputs) {
        return test(callInputOutputs, null, null);
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
        try {
            System.out.println("test cases:\n" + callInputOutputs);

            T solution = null;
            String failedCaseInfo = null;
            for (int i = 0; i < size; i++) {
                Object[] params = ((List) paramsArr.get(i)).toArray();
                if (i == 0) {
                    if (solutionInit != null) {
                        solution = solutionInit.apply((String) calls.get(i), params);
                    }
                    else {
                        solution = initSolution((String) calls.get(i), params);
                    }
                }
                else {
                    String methodName = (String) calls.get(i);

                    Object[] paramConverted = null;
                    Method method = null;
                    if (paramConverter != null) {
                        paramConverted = paramConverter.apply(solution, methodName, params);
                        for (Method tempMethod : solution.getClass().getDeclaredMethods()) {
                            if (Modifier.isPublic(tempMethod.getModifiers())
                                    && tempMethod.getName().equals(methodName)
                                    && tempMethod.getParameterCount() == paramConverted.length) {
                                Object[] convertedArgs = convertArgs(tempMethod.getParameterTypes(), paramConverted);
                                if (convertedArgs != null) {
                                    paramConverted = convertedArgs;
                                    method = tempMethod;
                                    method.setAccessible(true);
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        Object[] methodAndConvertedArgs = findMethodAndConvertArgs(solution, methodName, params);
                        if (methodAndConvertedArgs != null) {
                            method = (Method) methodAndConvertedArgs[0];
                            paramConverted = (Object[]) methodAndConvertedArgs[1];
                        }
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
                        if (!actualResultStr.equals(expectResultStr)) {
                            failedCaseInfo = "case " + i + " check failed: " + objectMapper.writeValueAsString(params) + " expect: " + expectResultStr + " actual: " + actualResultStr;
                            break;
                        }
                    }
                }
            }
            cost /= 1000000;
            if (failedCaseInfo != null) {
                System.out.println("\u001B[38;05;91m" + failedCaseInfo + "\u001B[0m\n");
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

    @SuppressWarnings("unchecked")
    private static <T> T initSolution(String solutionName, Object[] params) throws Exception {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        Class solutionClazz = null;
        for (int i = 2; i < stackTrace.length; i++) {
            try {
                solutionClazz = Class.forName(stackTrace[i].getClassName() + "$" + solutionName);
                break;
            } catch (ClassNotFoundException e) {
            }
        }

        if (solutionClazz == null) {
            throw new RuntimeException("cannot find a class for solution: " + solutionName
                    + ", you can set a lambda function to init the solution at the second parameter of Tester.test");
        }

        Constructor[] constructors = solutionClazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == params.length) {
                Object[] convertedArgs = convertArgs(constructor.getParameterTypes(), params);
                if (convertedArgs != null) {
                    constructor.setAccessible(true);
                    return (T) constructor.newInstance(convertedArgs);
                }
            }
        }

        throw new RuntimeException("cannot match a constuctor for solution: " + solutionName + " in class " + solutionClazz.getName()
                + ", you can set a lambda function to init the solution at the second parameter of Tester.test");
    }

    private static <T> Object[] findMethodAndConvertArgs(T solution, String methodName, Object[] params) {
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
