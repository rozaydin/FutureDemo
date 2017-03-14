package org.tutorial.future;

import org.tutorial.future.task.FibonacciTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rozaydin on 3/14/17.
 */
public class CancelScenario2 {

    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * We cancel the task before it's completed with mayInterruptIfRunning flag is false
     * We expect a java.util.concurrent.CancellationException to occur when we call get()
     * and we expect the executing threads interrupted status to remain false
     */
    public void execute() {

        Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.calculate(40)); // 30 - 832040
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
        boolean isCancelled = result.cancel(false);
        System.out.println("Task cancellation status: " + isCancelled);

        // 3. get the result
        try {
            //
            System.out.println("1. calling Future.get() -- this method will block");
            long fiboNumber = result.get();
            System.out.println("Fibonacci Number is: " + fiboNumber);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("Task interrupted! " + ie);
        } catch (ExecutionException ee) {
            System.out.println("Execution exception occured! " + ee);
        }
    }

    public static void main(String[] args) {

        CancelScenario2 scenario = new CancelScenario2();
        scenario.execute();
    }

}
