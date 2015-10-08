package jp.dodododo.dao.function;

import static jp.dodododo.dao.columns.ColumnsUtil.*;
import static org.junit.Assert.*;

import java.util.Date;

import jp.dodododo.dao.exception.SQLRuntimeException;
import jp.dodododo.dao.impl.RdbDao;
import jp.dodododo.dao.log.SqlLogRegistry;
import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.Test;

public class NoPersistentColumnInsertTest {

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	private RdbDao dao;

	private SqlLogRegistry logRegistry = new SqlLogRegistry();

	@Test
	public void testInsertAndSelect() {
		dao = new RdbDao(dbTestRule.getDataSource());
		dao.setSqlLogRegistry(logRegistry);

		int count = dao.insert("emp", Emp.EMP, npc("ename", "COMM", "deptNo", "HIREDATE", "MGR", "SAL", "TSTAMP", "JOB"));
		assertEquals(1, count);
		assertEquals("INSERT INTO emp ( EMPNO ) VALUES ( 1 )", logRegistry.getLast().getCompleteSql());

		try {
			count = dao.insert("emp", Emp.EMP);
			fail();
		} catch (SQLRuntimeException success) {
		}
	}

	public static enum Emp {
		EMP;

		public String EMPNO = "1";

		public String NAME;

		public Date TSTAMP;

		public String JOB;

		public String MGR;

		public String HIREDATE;

		public String SAL;

		public String COMM;

		public String DEPTNO;

		public char test;

	}
}
