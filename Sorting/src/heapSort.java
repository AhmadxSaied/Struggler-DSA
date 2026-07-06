public class heapSort {
    int[] array;
    int last_index;

    public heapSort(int[] array){
        this.array = array;
        last_index= array.length-1;
    }
    public int[] sort(){

        for(int i=array.length/2;i>=0;i--){
            heapdown(i);
        }


        for(int i=0;i<array.length;i++){
            remove();
        }
        return array;
    }
    
    public void remove(){
        int min = array[0];
        array[0] = array[last_index];
        array[last_index --] = min;
        heapdown(0);
    }

    private void heapdown(int index){

        int size = last_index;

        if(index > size) return;
        
        int leftchild = leftchild(index);
        int rightchild = rightchild(index);

        int min = index;

        if(leftchild <= size && array[leftchild] < array[min]) min = leftchild;
        if(rightchild <= size && array[rightchild] < array[min]) min = rightchild; 

        if(min != index){
            int temp = array[min];
            array[min] = array[index];
            array[index] = temp;
            heapdown(min);
        }

    }

    private void heapup(int index){
        if(index == 0) return;

        int parent = parent(index);

        if(array[parent] > array[index]){
            int temp = array[parent];
            array[parent] = array[index];
            array[index] = temp;
            heapup(parent);
        }

    }

    private int parent(int index){
        return (int)((index-1)/2);
    }


    private  int leftchild(int index){
        return 2*index + 1;
    }
    private  int rightchild(int index){
        return 2*index + 2;
    }
}
