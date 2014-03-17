package jp.dodododo.dao.util;

import static jp.dodododo.dao.util.CollectionOfString.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.dodododo.dao.paging.Paging;
import junit.framework.TestCase;

public class CollectionOfStringTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testList() {
		List<String> list = list("a", "b", "c", "d", "e");
		assertEquals(5, list.size());
		Iterator<String> iterator = list.iterator();
		assertEquals("a", iterator.next());
		assertEquals("b", iterator.next());
		assertEquals("c", iterator.next());
		assertEquals("d", iterator.next());
		assertEquals("e", iterator.next());
	}

	public void testMap() {
		Map<String, Object> map = map(new StringBuilder("a"), "b", "c", "d");
		assertEquals(2, map.size());
		assertEquals("b", map.get("a"));
		assertEquals("d", map.get("c"));

		map = map("a", "b", "c", "d", "e");
		assertEquals("e", map.get(null));
	}

	public void testMap2() {
		Map<String, Object> map = map(new StringBuilder("a"), "b", "c", "d", map("e", "f"), "g");
		assertEquals("b", map.get("a"));
		assertEquals("d", map.get("c"));
		assertEquals("f", map.get("e"));
		assertEquals("g", map.get(null));
		assertNull(map.get("g"));
	}

	public void testMap3() {
		Map<String, Object> map = map(new Paging(1, 1));
		Paging paging = (Paging) map.get(null);
		assertEquals(1, paging.getLimit());
		assertEquals(1, paging.getOffset());
		assertEquals(1, paging.getPageNo());

		map = map("a", "b", new Paging(1, 1));
		assertEquals("b", map.get("a"));
		paging = (Paging) map.get(null);
		assertNotNull(paging);
	}

	public void testMap4() {
		Map<String, Object> map = map("null", null, "a", "b");
		assertNull(map.get("null"));
		assertEquals("b", map.get("a"));
	}

}
