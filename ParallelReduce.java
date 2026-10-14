
package eventflow.algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelReduce {

    public static void runDemo() {
        System.out.println("\n===== EventFlow: Parallel Event Statistics =====");

        int[] registrations = {45, 12, 78, 30, 56, 21, 67, 40};

        System.out.println("Registration counts: "
                + Arrays.toString(registrations));

        ForkJoinPool pool = new ForkJoinPool();

        int total = pool.invoke(
                new SumTask(registrations, 0, registrations.length));

        pool.shutdown();

        double average = (double) total / registrations.length;

        System.out.println("Total registrations across events: " + total);
        System.out.printf("Average registrations per event: %.2f%n", average);
        System.out.println("Statistics calculated using parallel reduction.");
    }

    private static class SumTask extends RecursiveTask<Integer> {
        private final int[] data;
        private final int start;
        private final int end;

        SumTask(int[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 2) {
                int sum = 0;

                for (int i = start; i < end; i++) {
                    sum += data[i];
                }

                return sum;
            }

            int mid = (start + end) / 2;

            SumTask left = new SumTask(data, start, mid);
            SumTask right = new SumTask(data, mid, end);

            left.fork();

            int rightSum = right.compute();
            int leftSum = left.join();

            return leftSum + rightSum;
        }
    }
}
