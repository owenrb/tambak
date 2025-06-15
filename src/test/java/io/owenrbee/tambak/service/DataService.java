package io.owenrbee.tambak.service;

import org.springframework.stereotype.Service;

import io.owenrbee.tambak.annotation.MustNotNullList;
import io.owenrbee.tambak.annotation.ReverseList;
import io.owenrbee.tambak.annotation.UniqueList;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Sample service to demonstrate annotations for
 * testing purposes.
 * This class is part of the test module and is not shipped with the library.
 */
@Service // Declares this class as a Spring service for test context
@Slf4j
public class DataService {

    /**
     * Returns a list of strings. This method is annotated with @Reverse,
     * so its returned list will be reversed by the ReverseListAspect.
     * 
     * @return A list of strings.
     */
    @ReverseList // Apply our custom annotation here for testing reversal
    public List<String> getItemsInOrder() {
        log.debug("--- DataService: Inside getItemsInOrder() method.");
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
        log.debug("--- DataService: Inside getNumbers() method.");
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
        log.debug("--- DataService: Inside getCharacters() method (not reversed).");
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
        log.debug("--- DataService: Inside getItemsWithDuplicates() method.");
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
        log.debug("--- DataService: Inside getNumbersWithDuplicates() method.");
        return Arrays.asList(10, 20, 10, 30, 20, 40, 10);
    }

    // --- Methods directly annotated with @MustNotNullList ---

    /**
     * Returns null, but @MustNotNullList (default modifiable=true) should ensure
     * a new modifiable empty list is returned.
     * 
     * @return A null list.
     */
    @MustNotNullList
    public List<String> getNullableStringsModifiable() {
        log.debug("--- DataService: Inside getNullableStringsModifiable() method (returning null).");
        return null;
    }

    /**
     * Returns null, but @MustNotNullList (modifiable=false) should ensure
     * an unmodifiable empty list is returned.
     * 
     * @return A null list.
     */
    @MustNotNullList(modifiable = false)
    public List<Integer> getNullableIntegersUnmodifiable() {
        log.debug("--- DataService: Inside getNullableIntegersUnmodifiable() method (returning null).");
        return null;
    }

    /**
     * Returns an empty list explicitly. @MustNotNullList should not interfere.
     * 
     * @return An empty list.
     */
    @MustNotNullList
    public List<Double> getNonNullEmptyList() {
        log.debug("--- DataService: Inside getNonNullEmptyList() method (returning empty list).");
        return Collections.emptyList();
    }

    /**
     * Returns a non-empty list. @MustNotNullList should not interfere.
     * 
     * @return A non-empty list.
     */
    @MustNotNullList
    public List<Boolean> getNonNullNonEmptyList() {
        log.debug("--- DataService: Inside getNonNullNonEmptyList() method (returning non-empty list).");
        return Arrays.asList(true, false);
    }
}