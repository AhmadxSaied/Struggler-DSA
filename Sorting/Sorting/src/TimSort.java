import java.util.Arrays;

public class TimSort<T extends Comparable<T>> {
    private final static int MIN_RUN_SIZE = 64;
    private final static int MIN_GALLOP = 16;

    public TimSort() {
    }

    public void sort(T[] array) {
        int minRun = FindRunLength(array.length);

        for (int i = 0; i < array.length; i += minRun) {
            int end = Math.min((i + minRun - 1), (array.length - 1));
            if (!reverseRuns(array, i, end))
                InsertionSort(array, i, end);
        }
        for (int currentRunSize = minRun; currentRunSize < array.length; currentRunSize *= 2) {
            for (int start = 0; start < array.length; start += currentRunSize * 2) {
                int mid = start + currentRunSize - 1;
                int right = Math.min(currentRunSize * 2 + start - 1, array.length - 1);
                if (mid < right)
                    merge(array, start, mid, right);

            }
        }

    }

    private int FindRunLength(int arrayLength) {
        int remainder = 0;
        while (arrayLength >= MIN_RUN_SIZE) {
            if ((arrayLength & 1) == 1)
                remainder++;
            arrayLength >>= 1;
        }
        return (arrayLength + remainder);
    }

    private void InsertionSort(T[] array, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int j = i;
            while (j > start && array[j - 1].compareTo(array[j]) > 0) {
                T temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
                j--;
            }
        }
    }

    private void merge(T[] array, int start, int middle, int end) {
        T[] left_array = (T[]) new Comparable[middle - start + 1];
        T[] right_array = (T[]) new Comparable[end - middle];

        for (int i = start; i <= middle; i++) {
            left_array[i - start] = array[i];
        }
        for (int i = middle + 1; i <= end; i++) {
            right_array[i - (middle + 1)] = array[i];
        }

        int leftArrayPointer = 0;
        int rightArrayPointer = 0;
        int startOrigin = start;

        int leftWins = 0;
        int rightWins = 0;
        while (leftArrayPointer < left_array.length && rightArrayPointer < right_array.length) {
            if (left_array[leftArrayPointer].compareTo(right_array[rightArrayPointer]) > 0) {
                array[startOrigin++] = right_array[rightArrayPointer++];
                leftWins++;
                rightWins = 0;

                if (leftWins >= MIN_GALLOP && rightArrayPointer < right_array.length) {
                    int jump_index = Arrays.binarySearch(left_array, leftArrayPointer, left_array.length,
                            right_array[rightArrayPointer],
                            (a, b) -> a.compareTo(b));
                    if (jump_index < 0)
                        jump_index = -(jump_index) - 1;
                    while (leftArrayPointer < jump_index) {
                        array[startOrigin++] = left_array[leftArrayPointer++];
                    }
                    leftWins = 0;
                }

            } else {
                array[startOrigin++] = left_array[leftArrayPointer++];
                rightWins++;
                leftWins = 0;

                if (rightWins >= MIN_GALLOP && leftArrayPointer < left_array.length) {
                    int jump_index = Arrays.binarySearch(right_array, rightArrayPointer, right_array.length,
                            left_array[leftArrayPointer],
                            (a, b) -> a.compareTo(b));
                    if (jump_index < 0)
                        jump_index = -(jump_index) - 1;
                    while (rightArrayPointer < jump_index) {
                        array[startOrigin++] = right_array[rightArrayPointer++];
                    }
                    rightWins = 0;
                }

            }

        }
        while (leftArrayPointer < left_array.length)
            array[startOrigin++] = left_array[leftArrayPointer++];
        while (rightArrayPointer < right_array.length)
            array[startOrigin++] = right_array[rightArrayPointer++];
    }

    private boolean reverseRuns(T[] array, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            if (array[i - 1].compareTo(array[i]) < 0)
                return false;
        }
        int replace_idx = end;
        for (int i = start; i <= end; i++) {
            T temp = array[start];
            array[start] = array[replace_idx];
            array[replace_idx--] = temp;
        }
        return true;
    }

}
