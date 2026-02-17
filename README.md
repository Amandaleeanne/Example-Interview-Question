# Example-Interview-Question
I was tasked with an Example interview question and the code inside this repo was written by me.

## The task:
1 Write the C#/Java code that implements a linked list
2.Using an interface to define the LinkList definition, subclass the interface, and implement the
    following methods:
    A. Create the linked list and store class X (work, number) in each node in the list
    B. Load the link list with at least 25 words from a English dictionary using a class X. Each class x must have a work and number.
      The number should indicate the location in the list.
    C. Forward Traverse the list from the root and print the Class X's word and number
    D. Search for the 5th (number = 5) and print the word.
    E. Search for a word and print its number.
3. Provide Unit Tests for each methods (A-E) to prove it works.

Bonus points (optional)
  F. Reverse traversal the list and print out the work and number in reverse order.
       Also, provide unit tests.

## My Thinking:

Since this was a fairly simple task, I opted to use Java for easy use of generics in order to implement the interface, as I have not worked within C# yet, and thought the use of pointers that C# allows would be overkill. Taking into account the entire problem, including the bonus part, I decided to implement a doubly linked list from the interface for easy searching and reverse traversal. Additionally, this helped to optimize the searching for a number (problem D), as I could take into account the position in the list as stored within the dataClass to start from either the tail or the head for less node traversals. This did, however, lead to some issues with the remove() function, but I took a page out of Java's Iterator book and implemented "lazy" updates. Since the entire point of the list was traversal, and you must always traverse a LinkedList to access the data within, it makes sense to traverse and check for updates rather than update the entire list all at once every single `remove()` call.
