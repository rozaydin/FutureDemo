package org.tutorial.future;

import org.tutorial.future.task.FibonacciTask;

import java.util.concurrent.*;

/**
 * Created by rozaydin on 3/14/17.
 */
public class GetTimeoutScenario {

    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * Executes the task and calls get(10l, TimeUnit.Seconds)
     * this method will wait for 10 seconds at most for the task
     * to complete, if task completed before 10 seconds gets the result and prints it
     * otherwise throws a TimeoutException - For this scenario we expect no exceptions
     */
    public void execute() {

        Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.calculate(30)); // 30 - 832040
        singleThreadExecutor.shutdown();

        // 1. get result
        try {
            //
            System.out.println("1. calling Future.get() -- this method will block");
            long fiboNumber = result.get(10l, TimeUnit.SECONDS);
            System.out.println("Fibonacci Number is: " + fiboNumber );

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("Task interrupted! " + ie);
        } catch (ExecutionException ee) {
            System.out.println("Execution exception occured! " + ee);
        } catch (TimeoutException te) {
            System.out.println("Timeout Execution exception occured! " + te);
        }
    }

    public static void main(String[] args) {

        GetTimeoutScenario scenario = new GetTimeoutScenario();
        scenario.execute();
    }

}
