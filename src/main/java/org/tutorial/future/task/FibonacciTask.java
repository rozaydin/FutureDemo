package org.tutorial.future.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rozaydin on 3/14/17.
 */
public class FibonacciTask {

    static boolean cf;
    static int counter = 0;

    public static long fastCalculate(int fiboNumber) {

        List<Long> numbers = new ArrayList<Long>();
        numbers.add(0l);
        numbers.add(1l);

        for (int i = 2; i <= fiboNumber; i++) {
            numbers.add(numbers.get(i - 1) + numbers.get(i - 2));
        }

        return numbers.get(fiboNumber);
    }

    public static long calculate(int fiboNumber) {

        if (Thread.currentThread().isInterrupted()) {
            if (!cf) {
                System.out.println("-- Thread is interrupted!");
            }
            //
            cf = true;
        }

        if (fiboNumber < 2) { // f0 - 0, f1 - 1
            return fiboNumber;
        } else {
            return calculate(fiboNumber - 1) + calculate(fiboNumber - 2);
        }
    }

    public static long foreverSleep() {

        boolean runState = true;
        while (runState) {
            sleep();
            System.out.println(Thread.currentThread().getName() + " " + " computation continues in the limbo!");
        }

        return 0l;
    }

    public static long sleep() {

        try {
            //
            Thread.currentThread().sleep(1000);
            return 1000l;
        } catch (Exception exc) {
            System.out.println("Interrupted while calculating! " + exc);
            return -1l;
        }
    }

    public static long calculateSlowly(int fiboNumber) {

        // before calculation wait for some time
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }

        if (fiboNumber < 2) { // f0 - 0, f1 - 1
            return fiboNumber;
        } else {
            return calculate(fiboNumber - 1) + calculate(fiboNumber - 2);
        }
    }

}