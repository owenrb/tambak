package io.owenrbee.tambak.service;

import org.springframework.stereotype.Service;

import io.owenrbee.tambak.annotation.ReverseList;
import io.owenrbee.tambak.annotation.UniqueList;

import java.util.Arrays;
import java.util.List;

/**
 * Sample service to demonstrate the @Reverse and @UniqueList annotations for
 * testing purposes.
 * This class is part of the test module and is not shipped with the library.
 */
@Service // Declares this class as a Spring service for test context
public class DataService {

    /**
     * Returns a list of strings. This method is annotated with @Reverse,
     * so its returned list will be reversed by the ReverseListAspect.
     * 
     * @return A list of strings.
     */
    @ReverseList // Apply our custom annotation here for testing reversal
    public List<String> getItemsInOrder() {
        System.out.println("--- DataService: Inside getItemsInOrder() method.");
        return Arrays.asList("Apple", "Banana", "Cherry", "Date");
    }

    /**
     * Returns another list of integers. This method is also annotated
     * with @Reverse,
     * confirming the aspect works with different List types.
     * 
     * @return A list of integers.
     */
    @ReverseList
    public List<Integer> getNumbers() {
        System.out.println("--- DataService: Inside getNumbers() method.");
        return Arrays.asList(1, 2, 3, 4, 5);
    }

    /**
     * Returns a list of characters. This method does NOT have the @Reverse
     * annotation,
     * serving as a control to ensure the aspect only affects annotated methods.
     * 
     * @return A list of characters.
     */
    public List<Character> getCharacters() {
        System.out.println("--- DataService: Inside getCharacters() method (not reversed).");
        return Arrays.asList('a', 'b', 'c');
    }

    /**
     * Returns a list with duplicates. This method is annotated with @UniqueList,
     * so its returned list will have duplicates removed while preserving order.
     * 
     * @return A list of strings with duplicates.
     */
    @UniqueList // Apply the new unique list annotation
    public List<String> getItemsWithDuplicates() {
        System.out.println("--- DataService: Inside getItemsWithDuplicates() method.");
        return Arrays.asList("Red", "Blue", "Green", "Red", "Blue", "Yellow", "Green");
    }

    /**
     * Returns a list of integers with duplicates. This method is annotated
     * with @UniqueList,
     * so its returned list will have duplicates removed while preserving order.
     * 
     * @return A list of integers with duplicates.
     */
    @UniqueList
    public List<Integer> getNumbersWithDuplicates() {
        System.out.println("--- DataService: Inside getNumbersWithDuplicates() method.");
        return Arrays.asList(10, 20, 10, 30, 20, 40, 10);
    }
}