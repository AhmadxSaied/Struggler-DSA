

public class countingSort {

    public static  int[] sort(int[] array){
        int max = getMax(array);
        int[] count = new int[max+1];

        for(int i=0;i<array.length;i++){
            count[array[i]]++;
        }
        for(int i=1;i<count.length;i++){
            count[i]+=count[i-1];
        }
        int[] result = new int[array.length];

        for(int i=array.length-1;i>=0;i--){
            result[--count[array[i]]] = array[i];
        }
        return result;
    }

    private static int getMax(int[] array){
        int max = Integer.MIN_VALUE;

        int size = array.length;
        for(int i=0;i<size;i++){
            if(max < array[i]) max = array[i];
        }
        return max;
    }
}
