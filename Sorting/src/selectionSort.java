


public class selectionSort {
    public static  int[] sort(int[] array){
        int lastelement = array.length-1;
        int size = array.length;

        for(int i=0;i<size;i++){
            int runmax = Integer.MIN_VALUE;
            int runmax_index=-1;
            for(int j=0;j<=lastelement;j++){
                if(runmax <= array[j]){
                    runmax = array[j];
                    runmax_index = j;
                }
            }

            int temp = array[runmax_index];
            array[runmax_index] = array[lastelement];
            array[lastelement--] = temp;
        }
        return array;
    }
}
