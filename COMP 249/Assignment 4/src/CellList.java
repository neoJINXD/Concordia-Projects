// ----------------------------------------------------
// Assignment 4
// Question: 2 - Linked Lists - File 1
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.NoSuchElementException;

/**
 * Class to make and handle the methods needed to make a Linked List data structure
 * @author Anik Patel - 40091908
 * @version Part II
 */
public class CellList {
    /**
     * Inner class for a Node object containing the Cellphone and the pointer to the next Node in the Linked List
     */
    private class CellNode {  //Could cause a privacy leak if the inner class is set to public or protected
        private Cellphone cell;
        private CellNode pointer;

        /**
         * Default constructor for the Node, setting everything to null
         */
        public CellNode() {
            this.cell = null;
            this.pointer = null;
        }

        /**
         * Parametrized constructor
         * @param cell Cellphone object to store
         * @param pointer Next node in the Linked List structure
         */
        private CellNode(Cellphone cell, CellNode pointer) {
            this.cell = cell;
            this.pointer = pointer;
        }

        /**
         * Copy constructor that performs a deep copy
         * @param node Node to copy
         */
        private CellNode(CellNode node) {
            CellNode newNode = new CellNode(node.getCell(), node.getPointer());
            this.cell = newNode.getCell();
            this.pointer = newNode.getPointer();
        }

        /**
         * Clone method
         * @return A deep copy of the current object
         */
        public CellNode clone() {
            return new CellNode(this);
        }

        /**
         * Accessor method for the Cellphone
         * @return Cellphone object that is stored
         */
        private Cellphone getCell() {
            return cell;
        }

        /**
         * Mutator method for the Cellphone
         * @param cell New Cellphone object to replace the current one
         */
        private void setCell(Cellphone cell) {
            this.cell = cell;
        }

        /**
         * Accessor method for the Node that is being pointed to
         * @return Node that contains the next iteration in the Linked List
         */
        private CellNode getPointer() {
            return pointer;
        }

        /**
         * Mutator method for the pointer
         * @param pointer New node to set the pointer to
         */
        private void setPointer(CellNode pointer) {
            this.pointer = pointer;
        }
    }

    private CellNode head;
    private int size;

    /**
     * Default constructor for making an empty Linked List
     */
    public CellList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Copy constructor that performs a deep copy
     * @param linkedList Linked List to copy every object from
     */
    public CellList(CellList linkedList) {
        if (linkedList.getHead() == null) {
            this.head = null;
            this.size = 0;
        } else {
            CellList newList = new CellList();
            CellNode current = new CellNode(linkedList.getHead());
            CellNode[] whole = new CellNode[this.size];
            for (int i = 0; i < this.size; i++) {
                whole[i] = new CellNode(current);
                current = current.getPointer();
            }
            newList.setHead(new CellNode(linkedList.getHead()));
            for (int i = whole.length - 1; i >= 0; i--) {
                newList.addToStart(whole[i].getCell());
            }
            this.head = newList.getHead();
            this.size = linkedList.getSize();
        }
    }

    /**
     * Adds a new node containing a new Cellphone as the head
     * @param cell new Cellphone to add to the Linked List
     */
    public void addToStart(Cellphone cell) {
        this.size++;
        if (this.head == null)
            this.head = new CellNode(cell, null);
        else {
            CellNode newHead = new CellNode(cell, this.head);
            this.head = newHead;
        }
    }

    /**
     * Adds a new node containing a new Cellphone at a specific index
     * @param cell new Cellphone to add to the Linked List
     * @param index new index for the new Cellphone
     * @throws NoSuchElementException if the index given is less than 0 or more than the size, or if the Linked List is empty
     */
    public void insertAtIndex(Cellphone cell, int index) throws NoSuchElementException {
        if (this.head == null || index < 0 || index > this.size)
            throw new NoSuchElementException("Index out of bounds.");
        else {
            if (index == 0)
                addToStart(cell);
            else if (index == 1) {
                CellNode toAdd = new CellNode(cell, this.head.getPointer());
                this.head.setPointer(toAdd);
                this.size++;
            } else {
                int location = index;
                CellNode current = new CellNode(this.head);

                while (location != 1) {
                    current = current.getPointer();
                    location--;
                }

                CellNode toAdd = new CellNode(cell, current.getPointer());
                current.setPointer(toAdd);
                this.size++;
            }
        }
    }

    /**
     * Removes the node found at a specific index
     * @param index index to delete the Node from
     * @throws NoSuchElementException if the index given is less than 0 or more than the size, or if the Linked List is empty
     */
    public void deleteFromIndex(int index) throws NoSuchElementException {
        if (this.head == null || index < 0 || index > this.size - 1)
            throw new NoSuchElementException("Index out of bounds.");
        else {
            if (index == 0)
                this.deleteFromStart();
            else if (index == 1) {
                this.head.setPointer(this.head.getPointer().getPointer());
                this.size--;
            } else {
                int location = index;
                CellNode current = new CellNode(this.head);

                while (location != 1) {
                    current = current.getPointer();
                    location--;
                }
                current.setPointer(current.getPointer().getPointer());
                this.size--;
            }
        }
    }

    /**
     * Deletes the Node that is found at the head of the Linked List
     * @throws NoSuchElementException if Linked List is empty
     */
    public void deleteFromStart() throws NoSuchElementException {
        if (this.head == null)
            throw new NoSuchElementException("Linked list is empty!");
        this.head = this.head.getPointer();
        this.size--;
    }

    /**
     * Removes the Node at index and adds a new Node with a new Cellphone at the same index
     * @param cell new Cellphone to add to the Linked List
     * @param index Index to change the Node to the new node
     */
    public void replaceAtIndex(Cellphone cell, int index) {
        if (this.head == null || index < 0 || index > this.size - 1) {
            System.out.println("Failed to replace, Index out of bounds!");
            return;
        }
        if (index == 0) {
            CellNode toAdd = new CellNode(cell, this.head.getPointer());
            this.setHead(toAdd);
        } else if (index == 1) {
            CellNode toAdd = new CellNode(cell, this.head.getPointer().getPointer());
            this.head.setPointer(toAdd);
        } else {
            int location = index;
            CellNode current = new CellNode(this.head);
            while (location != 1) {
                current = current.getPointer();
                location--;
            }
            CellNode toAdd = new CellNode(cell, current.getPointer().getPointer());
            current.setPointer(toAdd);

        }
    }

    /**
     * Method to find the Node containing a serial number we are looking for
     * @param serial serial number to search for in the Linked List
     * @return Node that contains the serial number searching for
     */
    public CellNode find(long serial) {
        if (this.head == null)
            return null;
        CellNode current = new CellNode(this.head);
        int index = 0;
        while (current != null) {
            if (current.getCell().getSerialNum() == serial)
                break;
            else {
                index++;
                current = current.getPointer();
            }
        }
        System.out.println(index + " Iterations were made");
        return current;
    }

    /**
     * Method to check if a serial number is in the Linked List
     * @param serial serial number to look for in the Linked List
     * @return boolean, true if the serial number inputted is found in the Linked List
     */
    public boolean contains(long serial) {
        if (this.head == null)
            return false;

        CellNode current = new CellNode(this.head);
        while (current != null) {
            if (current.getCell().getSerialNum() == serial)
                return true;
            else {
                current = current.getPointer();
            }
        }
        return false;
    }

    /**
     * Method to print out to the console the contents of the Linked List
     */
    public void showContents() {
        int count = 0;
        CellNode current = new CellNode(this.head);
        System.out.println("The current list is of size " + this.size + " and contains:\n=================================================");
        do {
            if (count > 3) {
                count = 0;
                System.out.println();
            }
            String toPrint = current.getCell().getSerialNum() + ": " + current.getCell().getBrand() + " " + current.getCell().getPrice() + "$ " + current.getCell().getYear() + " --->  ";
            System.out.print(toPrint);
            current = current.getPointer();
            count++;

        } while (current != null);
        System.out.println("X");
        System.out.println();
    }


    /**
     * Method to check equality of 2 Linked Lists
     * @param o Linked List to compare to
     * @return boolean, true if all the nodes are the same and if the sizes are the same
     */
    public boolean equals(CellList o) {
        boolean condition = true;
        int location = this.size;
        int oLocation = o.getSize();
        CellNode current = new CellNode(this.head);
        CellNode oCurrent = new CellNode(o.getHead());
        if (this.size != o.getSize()){
            location = -10;
            condition = false;
        }
        do {
            if (current.getCell().getPrice() != oCurrent.getCell().getPrice() && current.getCell().getYear() != oCurrent.getCell().getYear() && (!current.getCell().getBrand().equals(oCurrent.getCell().getBrand()))) {
                condition = false;
                break;
            }
            current = current.getPointer();
            oCurrent = oCurrent.getPointer();
            location--;
            oLocation--;
        }
        while (location > 0 && oLocation > 0 && current.getPointer() != null && oCurrent.getPointer() != null);
        //return (this.size==o.getSize())&&();
        return condition;
    }


    /**
     * Accessor method for the Node at the head of the Linked List
     * @return Node at the head
     */
    public CellNode getHead() {
        return head;
    }

    /**
     * Mutator method for the head
     * @param head New node to set as the head
     */
    public void setHead(CellNode head) {
        this.head = head;
    }

    /**
     * Accessor method for the sze
     * @return Int of the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Mutator method for the size
     * @param size new int to set the size to
     */
    public void setSize(int size) {
        this.size = size;
    }

}
