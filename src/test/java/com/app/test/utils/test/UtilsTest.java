package com.app.test.utils.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.test.Utils.Utils;

class UtilsTest {

	@Test
	void shoulValidRut() {
		assertTrue(Utils.validRut("257666352"));
	}
	
	@Test
	void shoulNotAValidRut() {
		assertFalse(Utils.validRut("Hello world!"));
	}
}
