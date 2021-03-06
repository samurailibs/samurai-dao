package jp.dodododo.dao.function;

import static jp.dodododo.dao.unit.UnitTestUtil.*;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.annotation.Bean;
import jp.dodododo.dao.annotation.Column;
import jp.dodododo.dao.annotation.Id;
import jp.dodododo.dao.annotation.IdDefSet;
import jp.dodododo.dao.columns.ResultSetColumn;
import jp.dodododo.dao.dialect.MySQL;
import jp.dodododo.dao.dialect.sqlite.SQLite;
import jp.dodododo.dao.id.Identity;
import jp.dodododo.dao.id.Sequence;
import jp.dodododo.dao.impl.Dept;
import jp.dodododo.dao.log.SqlLogRegistry;
import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.Test;

public class CreateMethodConstructorTest {

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	private Dao dao;

	@Test
	public void testInsertAndSelect() {
		dao = newTestDao(dbTestRule.getDataSource());

		Emp emp = new Emp();
		emp.dept = new Dept();
		emp.dept.setDEPTNO("10");
		emp.dept.setDNAME("dept__name");
		emp.COMM = "2";
		// emp.EMPNO = "1";
		emp.TSTAMP = null;
		emp.TSTAMP = new Date();
		emp.NAME = "ename";
		dao.insert("EMP", emp);
		// dao.insert(emp.dept);
		String empNo = emp.EMPNO;

		List<Emp> select = dao.select("select DEPT.deptno as DEPTNO from EMP, DEPT where EMP.deptno = DEPT.deptno and empno = " + empNo, Emp.class);

		assertTrue(select.get(0).dept instanceof Dept2);
		assertEquals("10", select.get(0).dept.getDEPTNO());
	}

	public static class Emp {
		@Id(value = { @IdDefSet(type = Sequence.class, name = "sequence"), @IdDefSet(type = Identity.class, db = SQLite.class), @IdDefSet(type = Identity.class, db = MySQL.class) }, targetTables = { "emp" })
		public String EMPNO;

		@Column("ename")
		public String NAME;

		@Column(table = "emp", value = "Tstamp")
		public Date TSTAMP;

		public String JOB;

		public String MGR;

		public String HIREDATE;

		public String SAL;

		public String COMM;

		private Dept dept;

		public Emp() {
		}

		public Emp(
				@Bean(createMethod = "@jp.dodododo.dao.function.CreateMethodConstructorTest@createDept(resultSet, columnList)") Dept dept) {
			this.dept = dept;
		}

		public Dept getDept() {
			return dept;
		}
	}

	public static Dept createDept(ResultSet rs, List<ResultSetColumn> columnList) throws SQLException {
		String deptno = rs.getString("DEPTNO");
		// System.out.println("DEPTNO = " + eptno);
		// for (ResultSetColumn resultSetColumn : columnList) {
		// System.out.println(resultSetColumn.getName());
		// }
		return new Dept2(deptno);
	}

	public static class Dept2 extends Dept {

		public String DEPTNO;

		public Dept2(String DEPTNO) {
			this.DEPTNO = DEPTNO;
		}

		@Override
		public String getDEPTNO() {
			return super.getDEPTNO();
		}

	}
}
