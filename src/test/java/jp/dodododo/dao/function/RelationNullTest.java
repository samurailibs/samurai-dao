package jp.dodododo.dao.function;

import static jp.dodododo.dao.unit.UnitTestUtil.*;
import static jp.dodododo.dao.util.DaoUtil.*;
import static org.junit.Assert.*;

import java.util.Date;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.annotation.Bean;
import jp.dodododo.dao.annotation.Column;
import jp.dodododo.dao.annotation.Id;
import jp.dodododo.dao.annotation.IdDefSet;
import jp.dodododo.dao.dialect.MySQL;
import jp.dodododo.dao.dialect.sqlite.SQLite;
import jp.dodododo.dao.id.Identity;
import jp.dodododo.dao.id.Sequence;
import jp.dodododo.dao.log.SqlLogRegistry;
import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.Test;

public class RelationNullTest {

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	private Dao dao;

	private SqlLogRegistry logRegistry = new SqlLogRegistry();

	@Test
	public void testSelect() {
		dao = newTestDao(dbTestRule.getDataSource());
		dao.setSqlLogRegistry(logRegistry);

		Emp emp = new Emp(new Dept(null));
		dao.insert(emp);

		emp = dao.selectOne("select * from EMP where empno = /*empno*/0", args("empno", emp.EMPNO), Emp.class).get();
		assertNull(emp.getDept());
	}

	public static class Emp {
		@Id(value = { @IdDefSet(type = Sequence.class, name = "sequence"),
				@IdDefSet(type = Identity.class, db = SQLite.class),
				@IdDefSet(type = Identity.class, db = MySQL.class) },
				targetTables = { "emp" })
		public String EMPNO;

		public String ename;

		public Date Tstamp;

		public String JOB;

		public String MGR;

		public String HIREDATE;

		public String SAL;

		public String COMM;

		@Bean(nullable = true)
		public Dept dept;

		public Emp(@Bean(nullable = true) Dept dept) {
			this.dept = dept;
		}

		public Dept getDept() {
			return dept;
		}

		public void setDept(Dept dept) {
			this.dept = dept;
		}

	}

	public static class Dept {
		public String deptno;

		public Date tstamp;

		public Dept(@Column("DEPTNO") String deptno) {
			this.deptno = deptno;
		}
	}
}
