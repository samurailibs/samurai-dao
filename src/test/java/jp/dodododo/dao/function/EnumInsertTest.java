package jp.dodododo.dao.function;

import static jp.dodododo.dao.unit.UnitTestUtil.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.annotation.NumKey;
import jp.dodododo.dao.exception.SQLRuntimeException;
import jp.dodododo.dao.log.SqlLogRegistry;
import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.Test;

public class EnumInsertTest {

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	private Dao dao;

	private SqlLogRegistry logRegistry = new SqlLogRegistry();


	@Test
	public void testInsertAndSelect() {
		dao = newTestDao(getDataSource());
		dao.setSqlLogRegistry(logRegistry);

		int count = dao.insert("emp", Emp.EMP);
		assertEquals(1, count);

		try {
			count = dao.insert("emp", Emp.EMP);
			fail();
		} catch (SQLRuntimeException success) {
		}

		List<Emp> result = dao.select("SELECT empno FROM emp where empno=1", Emp.class);
		assertEquals(result.get(0).getClass(), Emp.class);
		assertEquals(result.get(0).getId(), Emp.EMP.getId());
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

		@NumKey
		public int getId() {
			return Integer.parseInt(EMPNO);
		}
	}

	private DataSource getDataSource() {
		return dbTestRule.getDataSource();
	}
}
