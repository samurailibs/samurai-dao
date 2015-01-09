package jp.dodododo.dao.function;

import static jp.dodododo.dao.sql.GenericSql.*;
import static jp.dodododo.dao.unit.UnitTestUtil.*;
import static jp.dodododo.dao.util.DaoUtil.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.annotation.NumKey;
import jp.dodododo.dao.annotation.StringKey;
import jp.dodododo.dao.exception.SQLRuntimeException;
import jp.dodododo.dao.log.SqlLogRegistry;

import org.seasar.extension.unit.S2TestCase;

public class ExecuteXxxTest extends S2TestCase {
	private Dao dao;

	private SqlLogRegistry logRegistry = new SqlLogRegistry();

	@Override
	public void setUp() throws Exception {
		include("jdbc.dicon");
	}

	@Override
	public void tearDown() throws Exception {
	}

	@Override
	protected boolean needTransaction() {
		return true;
	}

	public void testInsertUpdateAndDelete() {
		dao = newTestDao(getDataSource());
		dao.setSqlLogRegistry(logRegistry);

		dao.execute(INSERT, args(TABLE_NAME, "emp", //
				"EMPNO", 100, //
				"ENAME", "name"));

		Map<String, Object> actual = dao.selectOneMap("select * from emp where empno=100").get();
		assertEquals("name", actual.get("ename"));

		dao.execute(UPDATE, args(TABLE_NAME, "emp", //
				"EMPNO", 100, //
				"ENAME", "name2"));
		actual = dao.selectOneMap("select * from emp where empno=100").get();
		assertEquals("name2", actual.get("ename"));

		dao.execute(DELETE, args(TABLE_NAME, "emp", //
				"EMPNO", 100));
		Optional<Map<String, Object>> actual2 = dao.selectOneMap("select * from emp where empno=100");
		assertFalse(actual2.isPresent());

		dao.execute(DELETE_ALL, args(TABLE_NAME, "emp"));
		BigDecimal count = dao.selectOneNumber(COUNT_ALL, args(TABLE_NAME, "emp")).get();
		assertEquals(0, count.intValue());

		dao.execute(INSERT, into("emp"), //
				values("EMPNO", 100, //
						"ENAME", "name"));
		count = dao.selectOneNumber(COUNT_ALL, args(TABLE_NAME, "emp")).get();
		assertEquals(1, count.intValue());

		dao.execute(INSERT, into(Emp.class), //
				values("EMPNO", EmpNo.TEST_NO, //
						"ENAME", Ename.TEST_NAME));
		String sql = logRegistry.getLast().getCompleteSql();
		assertEquals("INSERT INTO EMP (EMPNO ,ENAME) VALUES (101 ,'TEST_NAME')", sql);
		count = dao.selectOneNumber(COUNT_ALL, args(TABLE_NAME, "emp")).get();
		assertEquals(2, count.intValue());

		try {
			dao.executeInsert("INSERT INTO EMP (ENAME ,EMPNO) VALUES ( /*ENAME*/'TEST_NAME' , /*EMPNO*/101 ) ",
					args("ENAME", Ename.TEST_NAME, "EMPNO", EmpNo.TEST_NO));
			fail();
		} catch (SQLRuntimeException uniqueKeyError) {
			assertEquals("INSERT INTO EMP (EMPNO ,ENAME) VALUES (101 ,'TEST_NAME')", sql);
		}
	}

	public static enum EmpNo {
		TEST_NO;

		@NumKey
		public int getNo() {
			return 101;
		}
	}

	public static enum Ename {
		TEST_NAME;

		@StringKey
		public String getName() {
			return toString();
		}
	}

	public static class Emp {
		public String EMPNO;
		public String ENAME;
	}

	public static class Dept {
		public String DEPTNO;
	}

	public static class Misc {
		public String JOB;

		public String MGR;

		public String HIREDATE;

		public String SAL;

		public String COMM;

		public String TSTAMP;
	}
}
