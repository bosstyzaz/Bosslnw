package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.Movie;

class MovieTestCase {
	
	@Test
	void testGetName() {
		Movie movie = new Movie("Alien", 2);
		assertEquals("Alien", movie.getName());
		movie = new Movie("", 2);
		assertEquals("No Name", movie.getName());
	}

	@Test
	void testGetLength() {
		Movie movie = new Movie("Alien", 2);
		assertEquals(2, movie.getLength());
		movie = new Movie("Alien", 0);
		assertEquals(1, movie.getLength());
	}

}
