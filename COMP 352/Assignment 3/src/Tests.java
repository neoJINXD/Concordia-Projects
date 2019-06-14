

public class Tests{

    public static void main(String[] args){
        System.out.printf("Currently testing the AFPQ Implementation\n\n");

        //Test constructors
        System.out.printf("Constructing 2 different PQs...\n");

        ArrayPQ<Integer, String> pq = new ArrayPQ<>();
        ArrayPQ<Integer, String> pq2 = new ArrayPQ<>(3, false);

        System.out.printf("Successfully created PQ\n\n");


        //Test removeTop, remove, top, replaceKey and replaceValue when empty
        System.out.printf("Testing removeTop, remove, top, replaceKey and replaceValue when PQ is empty\n");

        //removeTop
        if(pq.removeTop() != null){
            System.out.printf("Failed to stop removeTop() for empty PQ\n");
            System.exit(-1);
        }

        //top
        if(pq.top() != null){
            System.out.printf("Failed to stop top() for empty PQ\n");
            System.exit(-1);
        }

        //remove
        if(pq.remove(5) != null){
            System.out.printf("Failed to stop remove() for empty PQ\n");
            System.exit(-1);
        }

        //replaceKey
        if(pq.replaceKey(5, 6) != null){
            System.out.printf("Failed to stop replaceKey() for empty PQ\n");
            System.exit(-1);
        }

        //replaceValue
        if(pq.replaceValue(5, "ting") != null){
            System.out.printf("Failed to stop replaceValue() for empty PQ\n");
            System.exit(-1);
        }

        System.out.printf("Successfully avoided errors when removing of modifying from empty PQ\n\n");


        //Test isEmpty on empty PQ
        System.out.printf("Testing isEmpty on Empty PQ\n");

        if(!(pq.isEmpty() && pq2.isEmpty())){
            System.out.printf("Failed to detect empty PQ\n");
            System.exit(-1);
        }

        System.out.printf("Successfully detected empty PQs\n\n");


        //Test state
        System.out.printf("Testing state for getting isMin boolean value\n");

        System.out.printf("The first PQ: \n");
        if(!pq.state()){
            System.out.printf("Failed to detect Min Heap PQ\n");
            System.exit(-1);
        }
        System.out.printf("The second PQ: \n");

        if(pq2.state()){
            System.out.printf("Failed to detect Max Heap PQ\n");
            System.exit(-1);
        }

        System.out.printf("Successfully detected how the PQs are organized\n\n");


        //Test inserting with dynamic increasing
        System.out.printf("Testing insert with heap array dynamic sizing\n");

        System.out.printf("Initial PQ: \n%s\n\n", pq);
        pq.insert(7, "seven");
        pq.insert(10, "ten");
        pq.insert(15, "fifteen");
        pq.insert(1, "one");
        if(pq.getCap() <= 3){
            System.out.printf("Failed to increase size\n");
        }
        if(pq.size() != 4){
            System.out.printf("Failed to add all elements\n");
        }
        System.out.printf("After adding 4 Entries: \n%s\n\n", pq);

        pq2.insert(7, "seven");
        pq2.insert(10, "ten");
        pq2.insert(15, "fifteen");
        pq2.insert(75, "seventyfive");
        pq2.insert(2, "two");
        pq2.insert(97, "ninetyseven");
        pq2.insert(45, "fourtyfive");
        pq2.insert(63, "sixtythree");
        pq2.insert(4, "four");
        pq2.insert(0, "zero");

        System.out.printf("The same values in a Max Heap: \n%s\n", pq2);

        System.out.printf("Successfully inserted new Entries to the PQ\n\n");


        //Test toggle both ways
        System.out.printf("Testing toggle for Min -> Max and Max -> Min\n");

        //Min -> Max
        System.out.printf("Initial PQ: \n%s\n\n", pq);
        System.out.printf("Flipping first PQ\n");
        pq.toggle();
        System.out.printf("Flipped PQ: \n%s\n\n", pq);
        if(pq.state()){
            System.out.printf("Failed to flip PQ\n");
            System.exit(-1);
        }
        
        //Max -> Min
        System.out.printf("\nInitial PQ2: \n%s\n\n", pq2);
        System.out.printf("Flipping second PQ\n");
        pq2.toggle();
        System.out.printf("Flipped PQ2: \n%s\n\n", pq2);
        System.out.printf("The second PQ: \n");
        if(!pq2.state()){
            System.out.printf("Failed to flip PQ2\n");
            System.exit(-1);
        }

        System.out.printf("Successfully flipped both kinds of PQ\n\n");


        //Test isEmpty on non empty PQ
        System.out.printf("Testing isEmpty for non Empty PQ\n");

        if(pq.isEmpty() || pq2.isEmpty()){
            System.out.printf("Failed to detect non empty PQ\n");
            System.exit(-1);
        }

        System.out.printf("Successfully detected non empty PQs\n\n");


        //Test remove, replaceKey and replaceValue when Key is not found
        System.out.printf("Testing remove, replaceKey and replaceValue when key is not found\n");

        //remove
        if(pq.remove(100) != null){
            System.out.printf("Failed to stop remove() for non existing key\n");
            System.exit(-1);
        }

        //replaceKey
        if(pq.replaceKey(100, 6) != null){
            System.out.printf("Failed to stop replaceKey() for non existing key\n");
            System.exit(-1);
        }

        //replaceValue
        if(pq.replaceValue(100, "ting") != null){
            System.out.printf("Failed to stop replaceValue() for non existing key\n");
            System.exit(-1);
        }

        System.out.printf("Successfully detected non existing key when removing or modifying PQ\n\n");


        //Test working removeTop, remove, top, replaceKey and replaceValue with dynamic decreasing
        System.out.printf("Testing removeTop, remove, top, replaceKey and replaceValue when key is found with dynamic sizing\n");

        //removeTop and top
        System.out.printf("Current PQ: \n%s\n\n", pq);
        String oldValue = pq.top();
        pq.removeTop();
        if(oldValue.equals(pq.top())){
            System.out.printf("Failed to remove top\n");
            System.exit(-1);
        }
        System.out.printf("After removing top PQ: \n%s\n\n", pq);

        //remove
        System.out.printf("Current PQ: \n%s\n\n", pq);
        pq.remove(7);
        System.out.printf("After removing Key 7: \n%s\n\n", pq);

        //replaceKey
        System.out.printf("Current PQ: \n%s\n\n", pq2);
        pq2.replaceKey(7, 115);
        System.out.printf("After replacing Key 7 with 115: \n%s\n\n", pq2);

        //replaceValue
        System.out.printf("Current PQ: \n%s\n\n", pq2);
        pq2.replaceValue(10, "NO GOD PLEASE NO");
        System.out.printf("After replacing Key 10's value with a new String: \n%s\n\n", pq2);

        System.out.printf("Successfully modified PQ\n");
        


        System.out.printf("\n\nAll tests were successful, Good job! You could've used JUnit for Unit Testing this...\n");
    }
}