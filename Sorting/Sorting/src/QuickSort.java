

public class QuickSort {
    public static int[] ascquicksort(int[] array){
        int L=0;
        int R = array.length-1;
        int [] copy = array.clone();
        ascquicksort(copy,L,R);
        return copy;
    }

    private static void ascquicksort(int[] array, int l, int r) {
        int last_swap_index =l;
        int current = l;

        if(l>=r) {return;}

        while(current <=r) {
            if(array[current] < array [l]){
                int x = array [current];
                array[current] = array[last_swap_index+1];
                array[++last_swap_index] = x;
            }
            current++;
        }
        int x = array[l];
        array[l] = array[last_swap_index];
        array[last_swap_index] = x;

        int current_pivot_position = last_swap_index;
        ascquicksort(array,l,current_pivot_position-1);
        ascquicksort(array,current_pivot_position+1,r);

    }
}