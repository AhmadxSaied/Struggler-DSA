public class QuickSelect {
    public static Integer quickSelect(int [] array,int order){
        if(order == 0 || order >array.length) return null;
        int l=0;
        int r = array.length-1;
        int[] copy = array.clone();
        return quickSelect(copy,order,l,r);
    }

    private static int quickSelect(int[] array,int element_order,int l,int r){
        if(l==r) return array[l];

        int last_swap_index=l;
        int current = l;
        
        while(current <= r){
            if(array[current] < array[l]){
                int x = array[current];
                array[current] = array[last_swap_index+1];
                array[++last_swap_index] = x;
            }
            current++;
        }
        int x = array[l];
        array[l] = array[last_swap_index];
        array[last_swap_index] = x ;


        if(last_swap_index == element_order-1) return array[last_swap_index];

        if(last_swap_index < element_order-1) return quickSelect(array, element_order,last_swap_index+1,r);
        else return quickSelect(array, element_order,l,last_swap_index-1);


    }
}
