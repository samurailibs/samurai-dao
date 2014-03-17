package jp.dodododo.dao.util;

import static jp.dodododo.dao.util.DaoUtil.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class ToStringerTest {

	@Test
	public void test() {
		Object value = new int[] { 1, 2, 3, 4 };
		assertEquals("{1,2,3,4}", ToStringer.toString(value));

		value = new Object[] {};
		assertEquals("{}", ToStringer.toString(value));

		value = new Map[] { map("a", "1"), map("2", "b") };
		assertEquals("{{a=1},{2=b}}", ToStringer.toString(value));
	}
}
