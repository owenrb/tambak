package io.owenrbee.tambak.aspects;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.owenrbee.tambak.aspect.ReverseListAspect;
import io.owenrbee.tambak.aspect.UniqueListAspect;
import io.owenrbee.tambak.config.EnableTambakAnnotations;
import io.owenrbee.tambak.service.DataService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@SpringBootTest
@Slf4j
class AspectsApplicationTests {

	// Inner Configuration class to set up a minimal Spring context for the test.
	// This now uses @EnableReverseLibrary for activating the aspect.
	@Configuration
	@EnableTambakAnnotations // Use the new convenience annotation to enable both aspects
	@ComponentScan(basePackages = { "io.owenrbee.tambak.service" }) // Still need to scan for DataService in the test
																	// module
	static class TestConfig {
		// Explicitly defining aspect beans here to ensure they are picked up.
		// While @ComponentScan in @EnableReverseLibrary should handle this,
		// this explicit definition can help in specific test environments where
		// component scanning might be subtly misconfigured or for debugging.
		@Bean
		public ReverseListAspect reverseListAspect() {
			return new ReverseListAspect();
		}

		@Bean
		public UniqueListAspect uniqueListAspect() {
			return new UniqueListAspect();
		}
	}

	@Autowired // Automatically injects the DataService bean from the test context
	private DataService dataService;

	/**
	 * Test case to verify that a List of Strings returned by an @Reverse annotated
	 * method is correctly reversed by the aspect.
	 */
	@Test
	void testGetItemsInOrderIsReversed() {
		log.debug("\n--- Test: Calling getItemsInOrder() ---");
		List<String> expectedReversedOrder = Arrays.asList("Date", "Cherry", "Banana", "Apple");

		List<String> actualResult = dataService.getItemsInOrder();
		log.debug("Test Result (after aspect): {}", actualResult);

		// Assert that the actual result matches the expected reversed order
		Assertions.assertEquals(expectedReversedOrder, actualResult, "List of strings should be reversed.");
	}

	/**
	 * Test case to verify that a List of Integers returned by an @Reverse annotated
	 * method is correctly reversed.
	 */
	@Test
	void testGetNumbersIsReversed() {
		log.debug("\n--- Test: Calling getNumbers() ---");
		List<Integer> expectedReversedOrder = Arrays.asList(5, 4, 3, 2, 1);

		List<Integer> actualResult = dataService.getNumbers();
		log.debug("Test Result (after aspect): {}", actualResult);

		// Assert that the actual result matches the expected reversed order
		Assertions.assertEquals(expectedReversedOrder, actualResult, "List of integers should be reversed.");
	}

	/**
	 * Test case to ensure that methods WITHOUT the @Reverse annotation are NOT
	 * affected
	 * by the aspect, meaning their returned lists remain in the original order.
	 */
	@Test
	void testGetCharactersIsNotReversed() {
		log.debug("\n--- Test: Calling getCharacters() (no @Reverse) ---");
		List<Character> expectedOriginalOrder = Arrays.asList('a', 'b', 'c'); // Should remain unchanged

		List<Character> actualResult = dataService.getCharacters();
		log.debug("Test Result (no aspect): {}", actualResult);

		// Assert that the actual result is still in the original order
		Assertions.assertEquals(expectedOriginalOrder, actualResult, "List of characters should NOT be reversed.");
	}

	/**
	 * Test case to verify that a List of Strings returned by an @UniqueList
	 * annotated
	 * method has its duplicates removed while preserving order.
	 */
	@Test
	void testGetItemsWithDuplicatesIsMadeUnique() {
		log.debug("\n--- Test: Calling getItemsWithDuplicates() ---");
		List<String> expectedUniqueOrder = Arrays.asList("Red", "Blue", "Green", "Yellow");

		List<String> actualResult = dataService.getItemsWithDuplicates();
		log.debug("Test Result (after unique aspect): {}", actualResult);

		Assertions.assertEquals(expectedUniqueOrder, actualResult,
				"List of strings should have duplicates removed and order preserved.");
	}

	/**
	 * Test case to verify that a List of Integers returned by an @UniqueList
	 * annotated
	 * method has its duplicates removed while preserving order.
	 */
	@Test
	void testGetNumbersWithDuplicatesIsMadeUnique() {
		log.debug("\n--- Test: Calling getNumbersWithDuplicates() ---");
		List<Integer> expectedUniqueOrder = Arrays.asList(10, 20, 30, 40);

		List<Integer> actualResult = dataService.getNumbersWithDuplicates();
		log.debug("Test Result (after unique aspect): {}", actualResult);

		Assertions.assertEquals(expectedUniqueOrder, actualResult,
				"List of integers should have duplicates removed and order preserved.");
	}
}
