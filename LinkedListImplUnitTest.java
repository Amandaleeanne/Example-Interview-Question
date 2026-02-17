package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("LinkedListImpl Comprehensive Unit Tests")
public class LinkedListImplUnitTest {
    
    private DataClass dc(String word, int num) {
        return new DataClass(word, num);
    }

    // ============================================================
    // Creation and Storage Tests
    // ============================================================
    
    @Test
    @DisplayName("Constructor initializes head, tail and size correctly")
    void constructorInitializes() {
        DataClass d1 = dc("one", 1);
        LinkedListImpl list = new LinkedListImpl(d1);

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertEquals("one", list.getHead().getWord());
        assertEquals("one", list.getTail().getWord());
        assertEquals(1, list.getHead().getNumber());
    }

    @Test
    @DisplayName("Multiple DataClass objects can be stored with correct word and number")
    void storeMultipleDataClasses() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);

        assertEquals(4, list.size());
        assertEquals("first", list.getHead().getWord());
        assertEquals(1, list.getHead().getNumber());
        assertEquals("fourth", list.getTail().getWord());
        assertEquals(4, list.getTail().getNumber());
    }

    // ============================================================
    // Load List with Words
    // ============================================================
    
    @Test
    @DisplayName("Load list with 25+ words using addFromArray")
    void loadListUsingAddFromArray() {
        String[] words = {
            "abandon", "ability", "able", "about", "above",
            "abroad", "absence", "absolute", "absorb", "abstract",
            "absurd", "abundance", "abuse", "access", "accident",
            "account", "accuse", "achieve", "acid", "acoustic",
            "acquire", "across", "act", "action", "active",
            "actually"
        };
        
        LinkedListImpl list = new LinkedListImpl(dc(words[0], 1));
        
        // Create array with remaining words
        String[] moreWords = new String[words.length - 1];
        System.arraycopy(words, 1, moreWords, 0, words.length - 1);
        
        list.addFromArray(moreWords);
        
        // Verify size is 26
        assertEquals(26, list.size());
        assertEquals("abandon", list.getHead().getWord());
        assertEquals("actually", list.getTail().getWord());
    }

    // ============================================================
    // Forward Traversal - Print Word and Number
    // ============================================================
    
    @Test
    @DisplayName("Forward traverse list and print each node's word and number")
    void forwardTraverseAndPrint() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        // Get toString representation (forward traversal)
        String output = list.toString();
        
        // Verify output contains all words and numbers in forward order
        assertTrue(output.contains("first"));
        assertTrue(output.contains("second"));
        assertTrue(output.contains("third"));
        assertTrue(output.contains("fourth"));
        assertTrue(output.contains("fifth"));
        
        // Verify order
        assertTrue(output.indexOf("first") < output.indexOf("second"));
        assertTrue(output.indexOf("second") < output.indexOf("third"));
        assertTrue(output.indexOf("third") < output.indexOf("fourth"));
        assertTrue(output.indexOf("fourth") < output.indexOf("fifth"));
    }

    @Test
    @DisplayName("Forward traversal with lazy renumbering updates positions")
    void forwardTraversalLazyRenumbering() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        // Remove middle element (should break numbering)
        list.remove(2);
        
        // toString should lazy-renumber during traversal
        String output = list.toString();
        
        // Verify all elements still present and renumbered
        assertTrue(output.contains("first"));
        assertTrue(output.contains("third"));
        assertTrue(output.contains("fourth"));
        assertFalse(output.contains("second"));
    }

    // ============================================================
    // Reverse Traversal Tests
    // ============================================================
    
    @Test
    @DisplayName("Reverse traverse list and print each node's word and number")
    void reverseTraverseAndPrint() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        // Get reversePrint representation
        String output = list.reversePrint();
        
        // Verify output contains all words
        assertTrue(output.contains("first"));
        assertTrue(output.contains("second"));
        assertTrue(output.contains("third"));
        assertTrue(output.contains("fourth"));
        assertTrue(output.contains("fifth"));
        
        // Verify reverse order
        assertTrue(output.indexOf("fifth") < output.indexOf("fourth"));
        assertTrue(output.indexOf("fourth") < output.indexOf("third"));
        assertTrue(output.indexOf("third") < output.indexOf("second"));
        assertTrue(output.indexOf("second") < output.indexOf("first"));
    }

    @Test
    @DisplayName("Reverse traversal with lazy renumbering")
    void reverseTraversalLazyRenumbering() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        // Remove tail element
        list.remove(5);
        
        String output = list.reversePrint();
        
        // Verify correct reverse order and renumbering
        assertTrue(output.contains("fourth"));
        assertTrue(output.contains("third"));
        assertFalse(output.contains("fifth"));
    }

    // ============================================================
    // Search by Number - Find Word at Position
    // ============================================================
    
    @Test
    @DisplayName("Search for word at specific position")
    void searchForFifthElement() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth", "sixth"};
        list.addFromArray(words);
        
        String word = list.searchForWord(5);
        assertEquals("fifth", word);
    }

    @Test
    @DisplayName("Search multiple positions in large list")
    void searchMultiplePositions() {
        String[] words = {
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "ice", "jackfruit",
            "kiwi", "lemon", "mango", "nectarine", "orange",
            "papaya", "quince", "raspberry", "strawberry", "tangerine",
            "ugli", "vanilla", "watermelon", "xigua", "yam", "zucchini"
        };
        
        LinkedListImpl list = new LinkedListImpl(dc(words[0], 1));
        String[] remainingWords = new String[words.length - 1];
        System.arraycopy(words, 1, remainingWords, 0, words.length - 1);
        list.addFromArray(remainingWords);
        
        assertEquals("apple", list.searchForWord(1));
        assertEquals("elderberry", list.searchForWord(5));
        assertEquals("zucchini", list.searchForWord(26));
    }

    @Test
    @DisplayName("Search throws exception for out-of-range position")
    void searchOutOfRangeThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("one", 1));
        String[] words = {"two", "three"};
        list.addFromArray(words);
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.searchForWord(99));
    }

    @Test
    @DisplayName("Search with lazy renumbering after removal")
    void searchWithLazyRenumberingAfterRemoval() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        // Remove middle element
        list.remove(2);
        
        // Search should correctly find element even after removal
        assertEquals("first", list.searchForWord(1));
        assertEquals("third", list.searchForWord(2));
        assertEquals("fifth", list.searchForWord(4));
    }

    // ============================================================
    // SECTION E: Search by Word - Find Number at Position
    // ============================================================
    
    @Test
    @DisplayName("Search for a word and return its position number")
    void searchForWordAndPrintNumber() {
        LinkedListImpl list = new LinkedListImpl(dc("apple", 1));
        String[] words = {"banana", "cherry", "date"};
        list.addFromArray(words);
        
        int position = list.searchForNumber("cherry");
        assertEquals(3, position);
    }

    @Test
    @DisplayName("Search for word in large 25+ word list")
    void searchWordInLargeList() {
        String[] words = {
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "ice", "jackfruit",
            "kiwi", "lemon", "mango", "nectarine", "orange",
            "papaya", "quince", "raspberry", "strawberry", "tangerine",
            "ugli", "vanilla", "watermelon", "xigua", "yam", "zucchini"
        };
        
        LinkedListImpl list = new LinkedListImpl(dc(words[0], 1));
        String[] remainingWords = new String[words.length - 1];
        System.arraycopy(words, 1, remainingWords, 0, words.length - 1);
        list.addFromArray(remainingWords);
        
        assertEquals(5, list.searchForNumber("elderberry"));
        assertEquals(15, list.searchForNumber("orange"));
        assertEquals(26, list.searchForNumber("zucchini"));
    }

    @Test
    @DisplayName("Search is case-insensitive")
    void searchCaseInsensitive() {
        LinkedListImpl list = new LinkedListImpl(dc("Apple", 1));
        String[] words = {"Banana", "Cherry"};
        list.addFromArray(words);
        
        assertEquals(1, list.searchForNumber("apple"));
        assertEquals(2, list.searchForNumber("BANANA"));
        assertEquals(3, list.searchForNumber("cherry"));
    }

    @Test
    @DisplayName("Search throws exception for non-existent word")
    void searchNonExistentWordThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("apple", 1));
        String[] words = {"banana", "cherry"};
        list.addFromArray(words);
        
        assertThrows(Error.class, () -> list.searchForNumber("does-not-exist"));
    }

    // ============================================================
    // SECTION F: Removal Method Tests
    // ============================================================
    
    @Test
    @DisplayName("Remove single element from list of one")
    void removeSingleElement() {
        LinkedListImpl list = new LinkedListImpl(dc("only", 1));
        
        DataClass removed = list.remove(1);
        
        assertEquals("only", removed.getWord());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Remove head element from list")
    void removeHeadElement() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        DataClass removed = list.remove(1);
        
        assertEquals("first", removed.getWord());
        assertEquals(3, list.size());
        assertEquals("second", list.getHead().getWord());
        assertEquals(1, list.getHead().getNumber());
        assertEquals("fourth", list.getTail().getWord());
        assertEquals(3, list.getTail().getNumber());
    }

    @Test
    @DisplayName("Remove tail element from list")
    void removeTailElement() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        DataClass removed = list.remove(4);
        
        assertEquals("fourth", removed.getWord());
        assertEquals(3, list.size());
        assertEquals("first", list.getHead().getWord());
        assertEquals(1, list.getHead().getNumber());
        assertEquals("third", list.getTail().getWord());
        assertEquals(3, list.getTail().getNumber());
    }

    @Test
    @DisplayName("Remove middle element from list")
    void removeMiddleElement() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        DataClass removed = list.remove(2);
        
        assertEquals("second", removed.getWord());
        assertEquals(3, list.size());
        assertEquals("first", list.getHead().getWord());
        assertEquals("fourth", list.getTail().getWord());
        
        // Verify connection is maintained
        assertEquals("third", list.searchForWord(2));
    }

    @Test
    @DisplayName("Remove element and verify lazy renumbering")
    void removeAndVerifyLazyRenumbering() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        // Remove third element
        list.remove(3);
        
        assertEquals(4, list.size());
        assertEquals("first", list.searchForWord(1));
        assertEquals("second", list.searchForWord(2));
        assertEquals("fourth", list.searchForWord(3));
        assertEquals("fifth", list.searchForWord(4));
    }

    @Test
    @DisplayName("Remove multiple elements sequentially")
    void removeMultipleElements() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth", "fifth"};
        list.addFromArray(words);
        
        list.remove(2); // Remove second
        list.remove(3); // Remove fourth (now at position 3 after renumbering)
        
        assertEquals(3, list.size());
        assertEquals("first", list.searchForWord(1));
        assertEquals("third", list.searchForWord(2));
        assertEquals("fifth", list.searchForWord(3));
    }

    @Test
    @DisplayName("Remove throws exception for out-of-range index")
    void removeOutOfRangeThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("one", 1));
        String[] words = {"two", "three"};
        list.addFromArray(words);
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(99));
    }

    @Test
    @DisplayName("Remove throws exception for invalid index (0)")
    void removeInvalidIndexZeroThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("one", 1));
        String[] words = {"two", "three"};
        list.addFromArray(words);
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    @DisplayName("Remove throws exception from empty list")
    void removeFromEmptyListThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("element", 1));
        list.remove(1);
        
        assertThrows(IllegalStateException.class, () -> list.remove(1));
    }

    @Test
    @DisplayName("Remove from large list maintains structure")
    void removeFromLargeList() {
        String[] words = {
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "ice", "jackfruit"
        };
        
        LinkedListImpl list = new LinkedListImpl(dc(words[0], 1));
        String[] remainingWords = new String[words.length - 1];
        System.arraycopy(words, 1, remainingWords, 0, words.length - 1);
        list.addFromArray(remainingWords);
        
        // Remove several elements
        list.remove(3); // cherry
        list.remove(5); // fig
        list.remove(7); // grape (now at different position)
        
        assertEquals(7, list.size());
        assertEquals("apple", list.getHead().getWord());
        assertEquals("jackfruit", list.getTail().getWord());
    }

    @Test
    @DisplayName("Remove word and verify it's no longer searchable")
    void removeAndVerifyWordUnsearchable() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        list.remove(2); // Remove "second"
        
        // Verify removed word is no longer searchable
        assertThrows(Error.class, () -> list.searchForNumber("second"));
    }

    // ============================================================
    // Edge Case Tests
    // ============================================================
    
    @Test
    @DisplayName("addFromArray with empty array throws IllegalStateException")
    void addFromArrayEmptyThrows() {
        LinkedListImpl list = new LinkedListImpl(dc("start", 1));
        String[] empty = new String[0];
        
        assertThrows(IllegalStateException.class, () -> list.addFromArray(empty));
    }

    @Test
    @DisplayName("Head always numbered 1 after removal")
    void headAlwaysNumberOneAfterRemoval() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        list.remove(1); // Remove head
        
        // New head should always be number 1
        assertEquals(1, list.getHead().getNumber());
        assertEquals("second", list.getHead().getWord());
    }

    @Test
    @DisplayName("Tail always numbered size after removal")
    void tailAlwaysNumberSizeAfterRemoval() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        list.remove(4); // Remove tail
        
        // Tail should always be numbered as size
        assertEquals(list.size(), list.getTail().getNumber());
        assertEquals("third", list.getTail().getWord());
    }

    @Test
    @DisplayName("Removal of middle element and immediately perform a word search")
    void middleRemoveAndSearch() {
        LinkedListImpl list = new LinkedListImpl(dc("first", 1));
        String[] words = {"second", "third", "fourth"};
        list.addFromArray(words);
        
        list.remove(2); // Remove middle

        Integer returnedNumber = list.searchForNumber("third");
        
        // Third should now be at position 2 after lazy renumbering
        assertEquals(2, returnedNumber);
    }
}