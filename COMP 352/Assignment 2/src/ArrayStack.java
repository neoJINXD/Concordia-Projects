/*
Name:   Anik Patel
ID:     40091908
COMP 352 - Assignment 2
Due Date: May 29th, 2019
*/

//STILL HAVE UNCHECKED TYPE CAST, DOES NOT RESPECT TYPE SAFETY
public class ArrayStack<T> implements Stack<T>{

    private final int INCREMENT = 5;
    private T[] stack;
    private int topPointer;
    private int size;
    

    public ArrayStack(){
        this(5);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int size){
        this.stack = (T[]) new Object[size];
        this.topPointer = -1;
        this.size = size;
    }

    @Override
    public int size(){
        return topPointer+1;
    }

    public int maxSize(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return this.topPointer == -1;
    }

    @Override
    public T top(){
        if (this.isEmpty()){
            return null;
        }

        T temp = (T) this.stack[topPointer];
        return temp;
    }

    @Override
    public void push(T input){
        if (topPointer == this.stack.length-1){
            this.increase();
        }
        this.topPointer++;
        this.stack[topPointer] = input;
    }

    @Override
    public T pop(){
        if (topPointer == this.stack.length-6){
            this.reduce();
        }

        if (this.isEmpty()){
            return null;
        }
        T temp = (T)this.stack[topPointer];
        this.stack[topPointer] = null;
        this.topPointer--;
        return temp;
    }

    @SuppressWarnings("unchecked")
    private void increase(){
        System.out.println("STACK OVERFLOW");
        Object[] t = new Object[this.stack.length+INCREMENT];
        for (int i = 0; i<this.stack.length; i++){
            t[i] = this.stack[i];
        }
        this.stack = (T[]) t;
    }

    @SuppressWarnings("unchecked")
    private void reduce(){
        System.out.println("UNDEFLOWING");
        Object[] t = new Object[this.stack.length-INCREMENT];
        for (int i = 0; i <= this.topPointer; i++){
            t[i] = this.stack[i];
        }
        this.stack = (T[]) t;
    }

    public static void main(String[] args){
        ArrayStack<Integer> st = new ArrayStack<Integer>();
        st.push(29);
        st.push(69);
        st.push(115);
        st.push(115);
        st.push(115);

        st.push(115);
        st.push(115);
        st.push(115);
        st.push(115);
        st.push(115);

        st.push(15);
        st.pop();

        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.pop();

        st.pop();
        st.pop();
        st.pop();
        st.pop();

        st.push(41);

        System.out.printf("My guy, it is %d at most\n", st.maxSize());
        System.out.println(st.size());
        System.out.println(st.isEmpty());
        System.out.println(st.top());

        

    }


}