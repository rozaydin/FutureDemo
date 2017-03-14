package org.tutorial.future;

import org.tutorial.future.task.FibonacciTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rozaydin on 3/14/17.
 */
public class GetScenario {

    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * Executes the task and call get()
     * blocks till the execution is completed
     * We expect no execeptions and the calculated result
     * to be printed
     */
    public void execute() {

        Future<Long> result = singleThreadExecutor.submit(() -> FibonacciTask.calculate(30)); // 30 - 832040
        singleThreadExecutor.shutdown();

        // 1. get result
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

        GetScenario scenario = new GetScenario();
        scenario.execute();
    }

}
