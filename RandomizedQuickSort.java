
package eventflow.algorithms;

import java.util.Random;

public class RandomizedQuickSort {

    public static void runDemo() {
        System.out.println("\n===== EventFlow: Randomized Event Ranking =====");

        int[] registrations = {45, 12, 78, 30, 56, 21, 67};

        System.out.println("Event registrations before ranking:");
        printArray(registrations);

        quickSort(registrations, 0, registrations.length - 1);

        System.out.println("Events ranked by registration count (ascending):");
        printArray(registrations);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        Random random = new Random();
        int randomIndex = low + random.nextInt(high - low + 1);

        int temp = arr[randomIndex];
        arr[randomIndex] = arr[high];
        arr[high] = temp;

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
