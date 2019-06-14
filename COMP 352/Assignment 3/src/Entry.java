public class Entry<K extends Comparable<K>, V extends Comparable<V>>{

    private V value;
    private K key;

    public Entry(K key, V val){
        this.value = val;
        this.key = key;
    }

    public V getValue(){
        return this.value;
    }

    public void setValue(V val){
        this.value = val;
    }

    public K getKey(){
        return this.key;
    }

    public void setKey(K key){
        this.key = key;
    }

    public String toString(){
        return "{Key : " + this.key + ", Val: "+ this.value + "}";
    }
}