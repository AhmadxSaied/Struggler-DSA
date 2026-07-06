public class IntroSort {
    private int maxdepth;
    private final static int INSERTION_CRITERTION = 16;
    private final static int HEAP_CRITERTION = 16;

    public IntroSort() {

    }

    public void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        maxdepth = (int) Math.floor(Math.log(array.length) / Math.log(2)) * 2;
        introSort(array, 0, array.length - 1, this.maxdepth);
    }

    private void introSort(int[] array, int start, int end, int depthlength) {
        int size = end - start + 1;

        if (size <= INSERTION_CRITERTION) {
            insertionSort(array, start, end);
            return;
        }
        if (depthlength == HEAP_CRITERTION) {
            heapSort(array, start, end);
            return;
        }
        int pivot = partition(array, start, end);
        introSort(array, start, pivot - 1, depthlength - 1);
        introSort(array, pivot + 1, end, depthlength - 1);
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[low];

        int next_to_insert = low + 1;

        for (int i = low + 1; i <= high; i++) {
            if (array[i] < pivot) {
                int temp = array[next_to_insert];
                array[next_to_insert] = array[i];
                array[i] = temp;
                next_to_insert++;
            }
        }
        next_to_insert--;
        int temp = array[next_to_insert];
        array[next_to_insert] = array[low];
        array[low] = temp;
        return next_to_insert;

    }

    private void insertionSort(int[] array, int begin, int high) {
        for (int i = begin + 1; i <= high; i++) {
            int j = i - 1;
            while (j >= begin && array[j] > array[i]) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                j--;
            }
        }
    }

    private void heapSort(int[] array, int begin, int end) {
        int size = end - begin + 1;
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(array, i, begin, size);

        for (int i = size - 1; i > 0; i--) {
            int temp = array[begin];
            array[begin] = array[begin + i];
            array[begin + i] = temp;
            heapify(array, 0, begin, i);
        }
    }

    private int leftchild(int index) {
        return 2 * index + 1;
    }

    private int rightchild(int index) {
        return 2 * index + 2;
    }

    private void heapify(int[] array, int index, int begin, int end) {
        int leftchild = leftchild(index);
        int rightchild = rightchild(index);

        int maxidx = index;

        if (leftchild < end && array[begin + index] < array[begin + leftchild])
            maxidx = leftchild;
        if (rightchild < end && array[begin + index] < array[begin + rightchild])
            maxidx = rightchild;

        if (maxidx != index) {
            int temp = array[begin + index];
            array[begin + index] = array[begin + maxidx];
            array[begin + maxidx] = temp;
            heapify(array, maxidx, begin, end);
        }

    }
}
