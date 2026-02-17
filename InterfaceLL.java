package com.example;

public interface InterfaceLL<T> {

    /**
     * Adds an element to the list.
     * @param element the element to add
     */
    public void add(T element);

    /**
     * Removes an element at the specified index from the list.
     * @param index the index of the element to remove
     * @throws IndexOutOfBoundsException if the index is invalid or out of bounds
     * @throws IllegalStateException if the list is empty
     */
    public T remove(int index);

    /**
     * Returns head node data in the list without removing it.
     * @return the first element
     * @throws IllegalStateException if the list is empty
     */
    public T getHead();
    
    /**
     * Returns the tail node data in the list without removing it.
     * @return the last element
     * @throws IllegalStateException if the list is empty
     */
    public T getTail();

    /**
     * Returns the number of elements in the list.
     * @return the size of the list
     */
    public int size();

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty();
} 
