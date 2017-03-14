package org.tutorial.future;

import org.tutorial.future.task.FibonacciTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rozaydin on 3/14/17.
 */
public class CancelScenario1 {

    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * We cancel the task after it's completed
     * We expect cancel() method to return false
     * and get() method to return the result
     */
    public void execute() {

        Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.calculate(5)); // 30 - 832040
        singleThreadExecutor.shutdown();

        // 1. wait some time so that task assigned to a worker thread
        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException ie) {
            System.out.println("interrupted while sleeping!" + ie);
        }

        // 2. ensure task is not yet completed
        boolean isDone = result.isDone();
        System.out.println("Task is completed: " + isDone);
        /**
         * Here we call with mayInterrupt param true
         * Since in this scenario we expect the task
         * to complete prior cancellation, it does not
         * matter the value of the flag the outcome will
         * be the same (cancellation will fail the get() will return the result)
         */
        boolean isCancelled = result.cancel(true);
        System.out.println("Task cancellation status: " + isCancelled);

        // 3. get the result
        try {
            //
            System.out.println("1. calling Future.get() -- this method will block");
            long fiboNumber = result.get();
            System.out.println("Fibonacci Number is: " + fiboNumber );

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("Task interrupted! " + ie);
        } catch (ExecutionException ee) {
            System.out.println("Execution exception occured! " + ee);
        }

    }

    public static void main(String[] args) {

        CancelScenario1 scenario = new CancelScenario1();
        scenario.execute();

    }

}
