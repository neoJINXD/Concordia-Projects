public class Stack<T>{

    private T[] holder;
    private int counter;

    public Stack(){
        this.holder = new T[10];
        this.counter = 0;
    }

    public Stack(int size){
        this.holder = new T[size];
        this.counter = 0;
    }

    public int size(){
        return counter;
    }

    public boolean isEmpty(){
        return this.counter == 0;
    }

    //POSSIBLE PRIVACY LEAKS?
    public T top(){
        if (this.isEmpty())
            return null;
        return holder[counter];
    }

    public void push(E input){
        holder[counter] = input;
        this.counter++;
    }

    //POSSIBLE PRIVACY LEAKS?
    public T pop(){
        if (this.isEmpty())
            return null;
        
        T temp = holder[counter];
        holder[counter] = null;
        this.counter--;
        return temp;
    }


    private static void increase(){

    }

    private static void reduce(){

    }


}