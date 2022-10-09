package com.sanhuo.commom.lock.distribute;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.commom.basic.StringUtil;
import com.sanhuo.commom.lock.DistributeLockKeyStrategy;
import com.sanhuo.commom.lock.distribute.annotation.DistributeLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
//import sun.management.VMManagement;
//
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 锁的工具类
 *
 * @author sanhuo
 * @date 2020/1/20 17:14
 */
public class RedisReentrantLockUtils {
    /**
     * lockValue中的mac地址的键
     */
    public static final String MAC = "macAdress";
    /**
     * lockValue中的当前线程的id的键
     */
    public static final String THREAD = "threadId";
    /**
     * lockValue中的jvm的id的键
     */
    public static final String JVM = "jvmPid";

    /**
     * 根据方法生成锁的唯一key,为当前方法的签名
     */
    public static String getLockKey(ProceedingJoinPoint joinPoint) {
        DistributeLock distributeLock = getDistributedLock(joinPoint);
        String lockKey = null;
        //获取锁名的策略
        DistributeLockKeyStrategy lockKeyStrategy = distributeLock.lockKeyStrategy();
        //method
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //当前方法签名
        String signature = getSignature(method);
        switch (lockKeyStrategy) {
            //方法签名
            case METHOD:
                return signature;
            //token+方法签名
            case USER_METHOD:
                //获取请求头中的token
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String token = request.getHeader(HttpHeaders.AUTHORIZATION);
                return token + "&" + signature;
            //自定义
            case CUSTOM:
                return StringUtil.isNotBlank(distributeLock.lockKey()) ? distributeLock.lockKey() : signature;
            default:
                return signature;
        }
    }


    /**
     * 获取分布式锁注解
     */
    public static DistributeLock getDistributedLock(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DistributeLock distributedLock = targetClass.getAnnotation(DistributeLock.class);
        if (distributedLock != null) {
            return distributedLock;
        } else {
            Method method = signature.getMethod();
            distributedLock = method.getAnnotation(DistributeLock.class);
            return distributedLock;
        }
    }

    /**
     * 生成锁的信息,包括了mac地址,jvm的id和线程id
     */
    public static String getLockValue() {
        Map lockValueMap = new LinkedHashMap();
        lockValueMap.put(MAC, getLocalMac());
        lockValueMap.put(JVM, getJVMId());
        lockValueMap.put(THREAD, getThreadId());
        return JSONObject.toJSONString(lockValueMap);
    }

    /**
     * 获取当前线程id
     */
    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    /**
     * 获取jvm的id
     */
    public static Integer getJVMId() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
//            VMManagement mgmt = (VMManagement) jvm.get(runtime);
//            Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
//            pidMethod.setAccessible(true);
//            int pid = (Integer) pidMethod.invoke(mgmt);
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 获取mac地址
     */
    public static String getLocalMac() {
        String result = "";
        try {

            Process process = Runtime.getRuntime().exec("ipconfig /all");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

            String line;
            int index = -1;
            while ((line = br.readLine()) != null) {
                index = line.toLowerCase().indexOf("物理地址");
                if (index >= 0) {
                    index = line.indexOf(":");
                    if (index >= 0) {
                        result = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            return "";
        }
        return result;

    }


    /**
     * 获取方法签名,格式为[ returnType#methodName:methodParam.1,methodParam.2,...,methodParma.N]
     */
    private static String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }


}
