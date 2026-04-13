public class insertionSort {
    public static  int[] sort(int[] array){
        int size = array.length;
        
        for(int i=1;i<size;i++){
            int j = i;

            while(j>0 && array[j]< array[j-1]){
                int temp = array[j];
                array[j] = array[j-1];
                array[j-1] = temp;
                j--;
            }
        }
        return array;
    }
}
