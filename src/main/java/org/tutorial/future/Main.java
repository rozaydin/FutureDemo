package org.tutorial.future;

import org.tutorial.future.task.FibonacciTask;

import java.util.concurrent.*;

/**
 * Created by rozaydin on 3/14/17.
 */
public class Main {

    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

        // FutureTask<Long> task = new FutureTask<Long>(() -> FibonacciTask.foreverSleep());


        // long fiboNum = FibonacciTask.calculate(50); // 25172538050
        // long fiboNum = FibonacciTask.fastCalculate(50); // 25172538050

        // Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.calculate(30)); // 50 - 12586269025, 30 - 832040
        // Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.sleep()); // 50 - 12586269025, 30 - 832040
        Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.foreverSleep()); // 50 - 12586269025, 30 - 832040
        singleThreadExecutor.shutdown();

        // wait some time so that task assigned to a thread to run
        try {
            Thread.currentThread().sleep(10);
        } catch (InterruptedException ie) {
            System.out.println("interrupted while sleeping!" + ie);
        }

        boolean isDone = result.isDone();
        System.out.println("Task is completed: " + isDone);
        // boolean isCancelled = result.cancel(false);
        // System.out.println("Task cancellation status: " + isCancelled);

        try {
            //
            System.out.println("1. calling Future.get() -- this method will block");
            // long fiboNumber = result.get();
            long fiboNumber = result.get(1, TimeUnit.SECONDS);
            // long fiboNumber = result.get(1l, TimeUnit.SECONDS);
            System.out.println("Fibonacci Number is: " + fiboNumber );

        } catch (InterruptedException ie) {
            System.out.println("Task interrupted! " + ie);
        } catch (ExecutionException ee) {
            System.out.println("Execution exception occured! " + ee);
        } catch (TimeoutException te) {
            System.out.println("Timeout exception occured! " + te);
        }

    }

}