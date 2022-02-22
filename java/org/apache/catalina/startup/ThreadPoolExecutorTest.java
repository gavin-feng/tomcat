package org.apache.catalina.startup;

import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

//import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorTest {
    private static BlockingQueue blockingQueue;
    private static ExecutorService executorService;
    private static final int corePoolSize = 2;
    private static final int maxPoolSize = 5;
    private static final int queueCapacity = 1;
    private static final int tasks = 3;         // 修改任务数3、6

    private static void prepareThreadPoolExecutor() {
        // 1. tomcat 定制 ThreadPoolExecutor 以及 TaskQueue
        blockingQueue = new TaskQueue(queueCapacity);
        executorService
            = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, blockingQueue);
        ((TaskQueue)blockingQueue).setParent((ThreadPoolExecutor)executorService);
        // 2. jdk
//        blockingQueue = new LinkedBlockingQueue(queueCapacity);
//        executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, blockingQueue);
    }

    public static void main(String[] args) {
        prepareThreadPoolExecutor();

        // 提交N个任务
        for (int i = 0; i < tasks; i++) {
            executorService.submit(ThreadPoolExecutorTest::submitATask);
        }
        getActiveThreadCount();
        executorService.shutdown();
    }

    // 一个执行5秒的任务，每秒输出信息观察下
    public static void submitATask() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " num: " + i + " " );
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "over");
    }

    // 观察线程池内的活跃线程数
    // jdk 线程池，只有队列满了，才会新开辅助线程
    // tomcat 线程池，任务超过核心线程数之后，优先新开辅助线程
    public static void getActiveThreadCount() {
        for (int i = 0; i < 9; i++) {
            int activeCount = ((ThreadPoolExecutor) executorService).getActiveCount();
            System.err.println("active: " + activeCount + " queue: " + blockingQueue.size() + " 执行时间: " + i + "s");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
