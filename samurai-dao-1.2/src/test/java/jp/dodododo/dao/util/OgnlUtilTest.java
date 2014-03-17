package jp.dodododo.dao.util;

import java.util.HashMap;
import java.util.Map;

import jp.dodododo.dao.util.OgnlUtil.OgnlRuntimeException;
import junit.framework.TestCase;

public class OgnlUtilTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetValueStringObject() {
		String exp = "a.b.c";
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("a", new A());
		Object value = OgnlUtil.getValue(exp, root);
		assertNotNull(value);
		assertTrue(value instanceof C);

		exp = "a.b.c.a";
		root = new HashMap<String, Object>();
		root.put("a", new A());
		value = OgnlUtil.getValue(exp, root);
		assertNull(value);

		exp = "a.b.c.a.b";
		root = new HashMap<String, Object>();
		root.put("a", new A());
		try {
			value = OgnlUtil.getValue(exp, root);
			fail();
		} catch (OgnlRuntimeException success) {
		}
	}

	public static class A {
		public B getB() {
			return new B();
		}
	}

	public static class B {
		public C getC() {
			return new C();
		}
	}

	public static class C {
		public A getA() {
			return null;
		}
	}
}
