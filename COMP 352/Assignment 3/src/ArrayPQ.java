import java.util.Arrays;

public class ArrayPQ<K extends Comparable<K>, V extends Comparable<V>>{

    private Entry<K,V>[] heap;
    private boolean isMin;
    private int Qsize;
    private int capacity;

    public ArrayPQ(){
        this(3, false);
    }

    @SuppressWarnings("unchecked")
    public ArrayPQ(int size, boolean min){
        this.heap = (Entry<K,V>[]) new Entry[size];
        this.isMin = true;
        this.capacity = size;
        this.Qsize = 0;
    }

    public Entry<K,V> removeTop(){
        if(isEmpty()){
            return null;
        }
        Entry<K,V> output = this.heap[0];
        this.heap[0] = this.heap[this.Qsize-1];
        this.heap[this.Qsize-1] = null;
        this.Qsize--;

        heapify(0);

        if(this.Qsize == this.capacity/2){
            decrease();
        }

        return output;
    }

    public void insert(K k, V v){

        int location = this.Qsize;
        int parent = parent(location);
        if(this.isMin){
            while(location > 0 && k.compareTo(this.heap[parent].getKey()) < 0){
                this.heap[location] = this.heap[parent];
                location = parent;
                parent = parent(location);
            }
        } else {
            while(location > 0 && k.compareTo(this.heap[parent].getKey()) > 0){
                this.heap[location] = this.heap[parent];
                location = parent;
                parent = parent(location);
            }
        }
        this.heap[location] = new Entry<K,V>(k,v);
        this.Qsize++;

        if(this.Qsize == this.capacity-1){
            increase();
        }

    }

    public V top(){
        if(this.Qsize == 0){
            return null;
        }
        return this.heap[0].getValue();

    }

    public Entry<K,V> remove(K e){
        if(isEmpty()){
            return null;
        }
        if(this.heap[0].getKey().compareTo(e) == 0){
            return removeTop();
        } else {
            int i = 1;
            boolean found = false;
            for(; i < this.Qsize; i++){
                if(heap[i].getKey().compareTo(e) == 0){ 
                    found = true; 
                    break; 
                }
            }
            if (!found){
                return null;
            }
            Entry<K,V> output = this.heap[i];
            this.heap[i] = this.heap[this.Qsize-1];
            this.heap[this.Qsize-1] = null;
            this.Qsize--;

            heapify(i);

            if(this.Qsize == this.capacity/2){
                decrease();
            }

            return output;
        }
    }

    public K replaceKey(K e, K k){
        int i = 0;
        boolean found = false;
        for(; i < this.Qsize; i++){
            if(heap[i].getKey().compareTo(e) == 0){ 
                found = true;
                break; 
            }
        }
        if(!found){
            return null;
        }
        K out = this.heap[i].getKey();
        this.heap[i].setKey(k);
        heapify(i);
        return out;

    }

    public V replaceValue(K e, V v){ 
        int i = 0;
        boolean found = false;
        for(; i < this.Qsize; i++){
            if(heap[i].getKey().compareTo(e) == 0){
                found = true;
                break; 
            }
        }
        if(!found){
            return null;
        }
        V out = this.heap[i].getValue();
        this.heap[i].setValue(v);
        return out;

    }

    public void toggle(){
        this.isMin = !this.isMin;
        rebuild();
    }

    public boolean state(){
        if (this.isMin){
            System.out.printf("This PQ is a Min Heap\n");
        } else {
            System.out.printf("This PQ is a Max Heap\n");
        }
        return this.isMin;
    }

    public boolean isEmpty(){
        if (this.Qsize == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return this.Qsize;
    }



    //Dynamic Array size changers
    @SuppressWarnings("unchecked")
    private void increase(){
        //Holder's size becomes more than 2^(level+1) to keep a buffer
        System.out.printf("OVERFLOW\n");
        int newSize = this.capacity*2 +1;
        Entry<K,V>[] newHeap = (Entry<K,V>[]) new Entry[newSize];
        for(int i = 0; i<this.Qsize; i++){
            newHeap[i] = this.heap[i];
        }
        this.heap = newHeap;
        this.capacity = newSize;
    }

    @SuppressWarnings("unchecked")
    private void decrease(){
        //Holder's size becomes less than 2^(level-1) to keep a buffer
        System.out.printf("UNDERFLOW\n");
        int newSize = this.capacity/2;
        Entry<K,V>[] newHeap = (Entry<K,V>[]) new Entry[newSize];
        for(int i = 0; i<this.Qsize; i++){
            newHeap[i] = this.heap[i];
        }
        this.heap = newHeap;
        this.capacity = newSize;
    }

    //Indices for Heap
    private int leftChild(int index){ return (index*2)+1; }
    private int rightChild(int index){ return (index*2)+2; }
    private int parent(int index){ return index/2; }


    //Heap Helpers
    private void rebuild(){
        for (int i = this.Qsize/2 -1; i >= 0; i--){
            heapify(i);
        }
    }

    private void heapify(int index){
        int current = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if(this.isMin){
            if(left < this.Qsize && this.heap[current].getKey().compareTo(this.heap[left].getKey()) > 0){
                current = left;
            }
            if(right < this.Qsize && this.heap[current].getKey().compareTo(this.heap[right].getKey()) > 0){
                current = right;
            }
        } else {
            if(left < this.Qsize && this.heap[current].getKey().compareTo(this.heap[left].getKey()) < 0){
                current = left;
            }
            if(right < this.Qsize && this.heap[current].getKey().compareTo(this.heap[right].getKey()) < 0){
                current = right;
            }
        }

        if(current != index){
            //Do we need to change the ordering?
            swap(index, current);
            heapify(current);
        }
    }

    private void swap(int a, int b){
        Entry<K,V> temp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = temp;
    }

    public String toString(){
        return Arrays.toString(this.heap);
    }


    public static void main(String[] args){
        // TODO SETUP TEST CASES
        System.out.printf("IM ALIVE\n");
        ArrayPQ<Integer, String> pq = new ArrayPQ<>();
        pq.insert(4, "four");
        pq.insert(1, "one");
        pq.insert(7, "seven");
        System.out.println(pq);
        // pq.removeTop();
        // pq.removeTop();
        pq.toggle();

        System.out.println(pq);
        // pq.removeTop();
        // pq.remove(7);
        pq.replaceKey(7, 0);
        pq.replaceValue(0, "NO");
        // System.out.println(pq.remove(5));
        System.out.println(pq);
        pq.insert(3, "three");
        pq.insert(15, "fifteen");
        pq.insert(69, "sixtynine");
        System.out.println(pq);
        System.out.println(pq.capacity);
    }




}