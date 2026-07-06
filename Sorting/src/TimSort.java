import java.util.Arrays;

public class TimSort<T extends Comparable<T>> {
    // this parameter is used in order to break up a big array into chunks of
    // smaller ones of nearly equal sized
    private final static int MIN_RUN_SIZE = 64;
    // this parameter is used to deside when are we able to make large jumps when
    // merging two sub arrays
    private final static int MIN_GALLOP = 16;

    public TimSort() {
    }

    public void sort(T[] array) {
        // here we find the length of the run we are going to take
        int minRun = FindRunLength(array.length);

        // here we insure to sort individual small sized runs seperately in order to
        // suit them up for merging
        for (int i = 0; i < array.length; i += minRun) {
            int end = Math.min((i + minRun - 1), (array.length - 1));
            // notice that the reverse part is used in order to get away from insertion sort
            // worst case of O(n^2)
            if (!reverseRuns(array, i, end))
                InsertionSort(array, i, end);
        }
        // here we loop on two runs and merge them together and keep increasing the run
        // size in order to make sure
        // that the larger runs we merged from smaller runs are to be merged aswell
        // every time we multiple the size of the section to be merged by the double
        for (int currentRunSize = minRun; currentRunSize < array.length; currentRunSize *= 2) {
            // here we loop from the start to the end of each merged section thus for
            // example
            // 0 -> 2 * S -> 4 * S as so on
            for (int start = 0; start < array.length; start += currentRunSize * 2) {
                int mid = start + currentRunSize - 1;
                int right = Math.min(currentRunSize * 2 + start - 1, array.length - 1);
                if (mid < right)
                    merge(array, start, mid, right);

            }
        }

    }

    // here is the function that calculates the minimum run length
    // as the run length will not always be a perfect power of 2
    // we need to take into consideration the remainder of the length
    // thus we keep dividing the length by 2 and keeping count of the remainder each
    // time
    private int FindRunLength(int arrayLength) {
        int remainder = 0;
        while (arrayLength >= MIN_RUN_SIZE) {
            if ((arrayLength & 1) == 1)
                remainder++;
            arrayLength >>= 1;
        }
        return (arrayLength + remainder);
    }

    // this is the normal insertion sort nothing new
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

    // Here we will only use the merging part in merge sort
    private void merge(T[] array, int start, int middle, int end) {
        T[] left_array = (T[]) new Comparable[middle - start + 1];
        T[] right_array = (T[]) new Comparable[end - middle];

        for (int i = start; i <= middle; i++) {
            left_array[i - start] = array[i];
        }
        for (int i = middle + 1; i <= end; i++) {
            right_array[i - (middle + 1)] = array[i];
        }

        // those are pointer to elements in the left and right subsections as well as
        // the next insertion place in the original array
        int leftArrayPointer = 0;
        int rightArrayPointer = 0;
        int startOrigin = start;

        // those variables are used to decide when we will gallop and make large steps
        int leftWins = 0;
        int rightWins = 0;

        // This is the normal merging function except for the galloping
        while (leftArrayPointer < left_array.length && rightArrayPointer < right_array.length) {

            if (left_array[leftArrayPointer].compareTo(right_array[rightArrayPointer]) > 0) {
                array[startOrigin++] = right_array[rightArrayPointer++];
                leftWins++;
                rightWins = 0;

                // --------------------------------------------------------------------------------------
                // Here we check if the leftsubsection keeps wining thus means that the entire
                // left array can be smaller that the currect right subarray
                // thus we binary search on the index of the element larger than or equal to the
                // current element in the right subarray (lower bound)
                // if jump idx returns negative thus the whole array is smaller so we adjust the
                // jump idx
                // and then fill the original array till we reach the final idx
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
                // --------------------------------------------------------------------------------------

            } else {
                array[startOrigin++] = left_array[leftArrayPointer++];
                rightWins++;
                leftWins = 0;
                // ---------------------------------------------------------------------------------------
                // The same is applied here as for the gallop of the left part
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
                // ---------------------------------------------------------------------------------------
            }

        }
        // here we continue filling the original array
        while (leftArrayPointer < left_array.length)
            array[startOrigin++] = left_array[leftArrayPointer++];
        while (rightArrayPointer < right_array.length)
            array[startOrigin++] = right_array[rightArrayPointer++];
    }

    // here we check for reversed parts of the array that can hurdle the insertion
    // sort and make this part sorted
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
