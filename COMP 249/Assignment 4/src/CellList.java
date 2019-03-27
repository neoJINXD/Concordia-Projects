// ----------------------------------------------------
// Assignment 4
// Question: 2 - Linked Lists - File 1
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.NoSuchElementException;

public class CellList {
    private class CellNode {
        private Cellphone cell;
        private CellNode pointer;

        public CellNode() {
            this.cell = null;
            this.pointer = null;
        }

        public CellNode(Cellphone cell, CellNode pointer) {
            this.cell = cell;
            this.pointer = pointer;
        }

        public CellNode(CellNode node) {
            CellNode newNode = new CellNode(node.getCell(), node.getPointer());
            this.cell = newNode.getCell();
            this.pointer = newNode.getPointer();
        }

        public CellNode clone() {
            return new CellNode(this);
        }

        public Cellphone getCell() {
            return cell;
        }

        public void setCell(Cellphone cell) {
            this.cell = cell;
        }

        public CellNode getPointer() {
            return pointer;
        }

        public void setPointer(CellNode pointer) {
            this.pointer = pointer;
        }
    }

    private CellNode head;
    private int size;

    public CellList() {
        this.head = null;
        this.size = 0;
    }

    public CellList(CellList linkedList) {
        CellList newList = new CellList();
        this.head = linkedList.getHead();
        this.size = linkedList.getSize();
    }

    public void addToStart(Cellphone cell) {
        this.size++;
        if (this.head == null)
            this.head = new CellNode(cell, null);
        else {
            CellNode newHead = new CellNode(cell, this.head);
            this.head = newHead;
        }
    }

    public void insertAtIndex(Cellphone cell, int index) throws NoSuchElementException {
        if (this.head == null || index < 0 || index > this.size - 1)
            throw new NoSuchElementException("Index out of bounds.");
        else {
            CellNode current = new CellNode(this.head);

            while (index != 0) {
                current = current.getPointer();
                index--;
            }

            CellNode toAdd = new CellNode(cell, current.getPointer());
            current.setPointer(toAdd);
            this.size++;
        }
    }

    public void deleteFromIndex(int index) {
        if (this.head == null || index < 0 || index > this.size - 1)
            throw new NoSuchElementException("Index out of bounds.");
        else {
            CellNode current = new CellNode(this.head);

            while (index > 1) {
                current = current.getPointer();
                index--;
            }
            current.setPointer(current.getPointer().getPointer());
            this.size--;
        }
    }

    public void deleteFromStart() {
        if (this.head == null)
            throw new NoSuchElementException();
        this.head = this.head.getPointer();
        this.size--;
    }

    public void replaceAtIndex(Cellphone cell, int index) {
        if (this.head == null || index < 0 || index > this.size - 1)
            return;

        CellNode current = new CellNode(this.head);
        while (index > 1) {
            current = current.getPointer();
            index--;
        }
        CellNode toAdd = new CellNode(cell, current.getPointer().getPointer());
        current.setPointer(toAdd);

    }

    public CellNode find(long serial) {
        if (this.head == null)
            return null;
        CellNode current = new CellNode(this.head);
        int index = 0;
        while (current.getPointer() != null) {
            if (current.getCell().getSerialNum() == serial)
                return current;
            else {
                index++;
                current = current.getPointer();
            }
        }
        return null;
    }

    public boolean contains(long serial) {
        if (this.head == null)
            return false;

        CellNode current = new CellNode(this.head);
        while (current.getPointer() != null) {
            if (current.getCell().getSerialNum() == serial)
                return true;
            else {
                current = current.getPointer();
            }
        }
        return false;
    }

    public void showContents() {
        CellNode current = new CellNode(this.head);
        System.out.println("The current list is of size " + this.size + " and contains:\n=================================================\n");
        do {
            String toPrint = current.getCell().getSerialNum() + ": " + current.getCell().getBrand() + " " + current.getCell().getPrice() + "$ " + current.getCell().getYear() + "--->";
            System.out.println(toPrint);
            current = current.getPointer();

        } while (current != null);
        System.out.println("X");
    }


    public boolean equals(CellList o) {
        boolean condition = true;
        CellNode current = new CellNode(this.head);
        CellNode oCurrent = new CellNode(o.getHead());
        do {
            if (current.getCell().getPrice() != oCurrent.getCell().getPrice() && current.getCell().getYear() != oCurrent.getCell().getYear() && (!current.getCell().getBrand().equals(oCurrent.getCell().getBrand()))) {
                condition = false;
                break;
            }
        }
        while (this.size == o.getSize() && current.getPointer() != null && oCurrent.getPointer() != null);
        //return (this.size==o.getSize())&&();
        return condition;
    }


    public CellNode getHead() {
        return head;
    }

    public void setHead(CellNode head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
