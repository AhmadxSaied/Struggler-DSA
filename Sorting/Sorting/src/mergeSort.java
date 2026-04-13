public class mergeSort {
    public static int[] sort(int[] array){
        split(array, 0, array.length-1);
        return array;
    }

    public static void split(int[] array,int l,int r){

        if(l>=r) return;
        int mid = l + (r-l)/2;
        split(array, l, mid);
        split(array, mid+1 , r);

        sort(array , l , mid , mid+1,  r);
    }

    public static void sort(int[] array , int start1 , int end1 ,int start2, int end2){
        

        int movingpointer = start1;

        int firstelements = end1 - start1 + 1;
        int[] firsthalf = new int[firstelements];

        for(int i=0;i<firstelements;i++){
            firsthalf[i] = array[start1++];
        }


        int secondelements = end2 - start2 + 1;

        int[] secondhalf = new int[secondelements];

        for(int i=0;i<secondelements;i++){
            secondhalf[i] = array[start2++];
        }

        int firstpointer = 0;
        int secondpointer = 0;
        

        while(movingpointer <= end2){

            if(firstpointer == firstelements){
                array[movingpointer++] = secondhalf[secondpointer++];
                continue;

            }
            if(secondpointer == secondelements){
                array[movingpointer++] = firsthalf[firstpointer++];
                continue;
            }

            if(firsthalf[firstpointer] <= secondhalf[secondpointer]){
                array[movingpointer++] = firsthalf[firstpointer++];
            }
            else{
                array[movingpointer++] = secondhalf[secondpointer++];
            }
            

        }
    }   
}
