package jp.dodododo.dao.issue;

import static jp.dodododo.dao.util.DaoUtil.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import jp.dodododo.dao.config.DaoConfig;
import jp.dodododo.dao.row.Row;

import org.junit.Before;
import org.junit.Test;

public class Issue8Test {

	@Before
	public void setUp() throws Exception {
		DaoConfig.getDefaultConfig().setFormats("yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss");
	}

	@Test
	public void test() throws Exception {
		Row row = new Row(map("bool", true, "num", "12,345,678.90", "date1", "2000/01/23 12:23:34", "date2", "2000/01/23"));
		assertTrue(row.getBoolean("bool"));
		assertEquals(12345678.90D, row.getDouble("num").doubleValue(), 2);
		assertEquals(12345678.90F, row.getFloat("num").doubleValue(), 2);
		assertEquals(new BigDecimal("12345678.90"), row.getBigDecimal("num"));
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2000/01/23 12:23:34"), row.getDate("date1"));
		assertEquals(new SimpleDateFormat("yyyy/MM/dd").parse("2000/01/23"), row.getDate("date2"));
	}

}
