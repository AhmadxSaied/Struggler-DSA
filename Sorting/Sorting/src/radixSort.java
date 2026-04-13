public class radixSort { //base10

    public static  int[] sort(int[] arrayx){

        int[] array = arrayx.clone();

        int max = getMax(array);
        int exp=1;

        

        for(; max/exp > 0; exp*=10){
            int[] count = new int[10];

            for (int i = 0; i < array.length; i++) {
                count[(array[i]/exp)%10] ++;
            }
            for(int i=1;i<count.length;i++){
                count[i]+=count[i-1];
            }
            int [] result = new int[array.length];
            
            for(int i=array.length-1;i>=0;i--){
                result[--count[(array[i]/exp)%10]] = array[i];
            }
            array = result;
        }
        return array;
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
