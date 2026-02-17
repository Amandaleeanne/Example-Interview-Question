package com.example;

/**
 * A simple LinkedList implementation of the interface.
 * 
 * Notes: I could have used a hashMap for O(1) search operations, but as this was a linkedList excersize, i opted to not. 
 * It also complicated the removal overhead.
 */
public class LinkedListImpl implements InterfaceLL<DataClass> {
    

    //Constructor for the LinkedList
    LinkedListImpl(DataClass data){
        Node<DataClass> node = new Node<>(data);
        this.head = node;
        this.tail = node;
        size = 1; //added new node, size is now 1
    }
    
    /**
     * Takes an array of type String and adds to the list
     * @param array Array of DataClass to ad to the list
     * @throws IllegalStateException if the array given is empty
     */
    public void addFromArray(String array[])
    {
        if (array.length == 0)
        {
            throw new IllegalStateException("Error: Given array is empty");
        }
        
        for (String string : array)
        {
            this.add(new DataClass(string, this.size+1)); //Zero-ith based numbering
        }
    }

    /**
     * Builds a string of the class and returns a string of all the data in reverse order unless the list is empty.
     * @return String
     */
    public String reversePrint()
    {
        if (this.isEmpty())
        {
            return "List is Empty"; 
        }
        if (this.size == 1)
        {
            head.getData().toString();
        }
        StringBuilder sb = new StringBuilder();
        Node<DataClass> currNode = this.tail;
        int position = size;
        while (currNode != null) {
            updatePosition(currNode, position);
            sb.append(currNode.getData().toString());
            currNode = currNode.previous;
            position--;
        }
        
        return sb.toString();
    }

    /**
     * Given a number, searches the list for the accociated word.
     * @param index
     * @throws IndexOutOfBoundsException if the word to search for given the index does not exist
     * @return
     */

    //@TODO: edit logic so that it starts from the most optimal place in the list for searching. (half of ListSize)
    public String searchForWord(int index)
    {
        //@TODO: check for "off by one" error
        if(index > size)
        {
            throw new IndexOutOfBoundsException("Given index is larger than size of List");
        }

        //set node placement for best search operation
        Node<DataClass> currNode = this.head; //init
        boolean isReversed = false;
        int position = 1;
        if (index >= size / 2)
        {
            currNode = this.tail;
            isReversed = true;
            position = size;
        }


        boolean wordNotFound = true;
        String returnWord = "";
        while (currNode != null)
        {
             updatePosition(currNode, position);
             if (currNode.data.getNumber() == index)
             {
                returnWord = currNode.data.getWord();
                wordNotFound = false;
                break;
             } else { 
                currNode = isReversed ? currNode.previous : currNode.next;
                position = isReversed ? position - 1 : position + 1;
             }
        }
        if (wordNotFound)
        {
            throw new Error("Index given does not exist in list. It may hae been removed or cuorrupted.");
        } else return returnWord;
    }

     /**
     * Searches for a word in the list and returns its position number.
     * @param word the word to search for
     * @throws IndexOutOfBoundsException if the list is empty
     * @return Integer the position number of the word
     */
    public Integer searchForNumber(String word)
    {
        word = word.toLowerCase();
        if(this.isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }
        if (word.equals(this.tail.getData().getWord()))
        {
            return this.tail.getData().getNumber();
        }
        Node<DataClass> currNode = this.head;
        int position = 1;
        while (currNode != null)
        {
            updatePosition(currNode, position);
            if (currNode.data.getWord().toLowerCase().equals(word))
            {
                return currNode.data.getNumber();
            }
            currNode = currNode.next;
            position++;
        }
        
        throw new Error("Word given does not exist in list. It may have been removed or corrupted.");
    }

    //=============================
    // implementation Overrides 
    //==============================

    @Override
    public void add(DataClass element){
        Node<DataClass> node = new Node<>(element);
        if(this.isEmpty())
        {
            size = 1;
            node.getData().setNumber(size);
            this.head = node;
            this.tail = node;
        }else
        {
            size++;
            node.getData().setNumber(size);
            this.tail.next = node;
            node.previous = this.tail;
            this.tail = node;
        }
    }

    @Override
    public DataClass remove(int index) {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot remove from an empty list");
        }
        
        if (index < 1 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. List size: " + size);
        }
        
        Node<DataClass> currNode;
        
        // Start from the optimal end
        if (index <= size / 2) {
            currNode = this.head;
            for (int i = 1; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = this.tail;
            for (int i = size; i > index; i--) {
                currNode = currNode.previous;
            }
        }
        
        DataClass removedData = currNode.getData();
        
        // Handle removal based on position
        if (currNode == this.head && currNode == this.tail) {
            // Only one element
            this.head = null;
            this.tail = null;
        } else if (currNode == this.head) {
            // Remove head
            this.head = currNode.next;
            this.head.previous = null;
        } else if (currNode == this.tail) {
            // Remove tail
            this.tail = currNode.previous;
            this.tail.next = null;
        } else {
            // Remove middle element
            currNode.previous.next = currNode.next;
            currNode.next.previous = currNode.previous;
        }
        
        // Update size
        size--;
        
        // Lazy renumbering: will be updated when nodes are encountered during iteration/search
        
        return removedData;
    }

    @Override
    public DataClass getHead(){
        if (this.isEmpty())
        {
            throw new IllegalStateException();
        }
        this.head.getData().setNumber(1); //lazy update
        return this.head.getData();
    }
    
    @Override
    public DataClass getTail(){
        if (this.isEmpty())
        {
            throw new IllegalStateException();
        }
        this.tail.getData().setNumber(size); //lazy update
        return this.tail.getData();
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0 || head == null;
    }


    @Override
    public String toString() {
        if (this.isEmpty())
        {
            return "List is Empty"; 
        }
        if (this.size == 1)
        {
            updatePosition(this.head, 1);
            return head.getData().toString();
        }
        StringBuilder sb = new StringBuilder();
        Node<DataClass> currNode = this.head;
        int position = 1;
        while (currNode != null) {
            updatePosition(currNode, position);
            sb.append(currNode.getData().toString());
            currNode = currNode.next;
            position++;
        }
        
        return sb.toString();
    }

    //=======================================
    //LinkedList class variables
    //=======================================
    private Node<DataClass> head;
    private Node<DataClass> tail;
    private int size;
    

    //======================================
    //Private Methods
    // =============================

    /**
     * Helper method for lazy renumbering since that cuts down on 
     * the removal method's overhead. Updates a node's number
     * when the node is encountered during iteration or search.
     * @param node the node to update
     * @param position the position in the list
     */
    private void updatePosition(Node<DataClass> node, int position) {
        int oldNumber = node.getData().getNumber();
        if (oldNumber != position) {
            node.getData().setNumber(position);
        }
    }
    //=====================================
    //LinkedList private classes
    //=====================================
    /**
     * Internal Node implementation to represent each element in the linked list.
     */
    public static class Node<DataClass> {
        private final DataClass data;
        private Node<DataClass> next;
        private Node<DataClass> previous;
        
        Node(DataClass data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        Node(DataClass data, Node<DataClass> next)
        {
            this.data = data;
            this.next = next;
            this.previous = null;
        }

        Node(DataClass data, Node<DataClass> next, Node<DataClass> previous)
        {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
        
        public DataClass getData() {
            return data;
        }
        
        public Node<DataClass> getNext() {
            return next;
        }

        public Node<DataClass> getPrevious()
        {
            return previous;
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(this.data.toString());
            sb.append("Next ");
            sb.append(this.next.toString());
            sb.append("Previous: ");
            sb.append(this.previous.toString());
            sb.append("\n");
            return sb.toString();
        }
    }
}