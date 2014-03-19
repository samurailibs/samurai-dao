package jp.dodododo.dao.object;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.dodododo.dao.impl.Emp;
import jp.dodododo.dao.types.JavaTypes;

import org.junit.Test;

public class MapObjectDescTest {

	@Test
	public void testMapObjectDesc() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 1);
		map.put("b", "str");
		map.put("c", new Emp());

		MapObjectDesc objectDesc = new MapObjectDesc(map, true);

		assertEquals(3, objectDesc.getPropertyDescSize());

		PropertyDesc propertyDesc = objectDesc.getPropertyDesc(Deprecated.class);
		assertNull(propertyDesc);
		List<PropertyDesc> propertyDescs = objectDesc.getPropertyDescs(Deprecated.class);
		assertEquals(0, propertyDescs.size());

		assertEquals("a", objectDesc.getPropertyDescs(JavaTypes.INTEGER).get(0).getPropertyName());
		assertEquals("b", objectDesc.getPropertyDescs(JavaTypes.STRING).get(0).getPropertyName());
		assertEquals("c", objectDesc.getPropertyDescs(JavaTypes.OBJECT).get(0).getPropertyName());

		assertEquals(3, objectDesc.getPropertyDescs().size());
		assertEquals(3, objectDesc.getReadablePropertyDescs().size());
		assertEquals(3, objectDesc.getWritablePropertyDescs().size());

		assertEquals(new Integer(1), objectDesc.getPropertyDesc("a").getValue(map));
		assertEquals("str", objectDesc.getPropertyDesc("b").getValue(map));
		assertEquals(new Emp().toString(), objectDesc.getPropertyDesc("c").getValue(map).toString());

		assertTrue(objectDesc.hasPropertyDesc("a"));
		assertTrue(objectDesc.hasPropertyDesc("b"));
		assertTrue(objectDesc.hasPropertyDesc("c"));
		assertFalse(objectDesc.hasPropertyDesc("d"));

		@SuppressWarnings("rawtypes")
		ClassDesc<Map> classDesc = objectDesc.getClassDesc();
		assertEquals(Map.class, classDesc.getTargetClass());

	}
}
