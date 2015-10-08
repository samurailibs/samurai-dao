package jp.dodododo.dao.issue;

import static jp.dodododo.dao.sql.GenericSql.*;
import static jp.dodododo.dao.sql.Operator.*;
import static jp.dodododo.dao.unit.UnitTestUtil.*;
import static jp.dodododo.dao.util.DaoUtil.*;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.script.Each;
import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.Test;

public class Issue46Test {

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	private Dao dao;

	@Test
	public void test1() throws Exception {
		dao = newTestDao(getDataSource());

		final AtomicBoolean invoked = new AtomicBoolean(false);
		dao.selectMap("select * from emp", by("empno", 1), new Each() {
			@SuppressWarnings("unused")
			public void each(Map<String, Object> row) {
				invoked.getAndSet(true);
			}
		});
		assertTrue(invoked.get());
	}

	@Test
	public void test2() throws Exception {
		dao = newTestDao(getDataSource());

		final AtomicBoolean invoked = new AtomicBoolean(false);
		dao.selectMap(ALL, from("emp"), new Each() {
			@SuppressWarnings("unused")
			public void each(Map<String, Object> row) {
				invoked.getAndSet(true);
			}
		});
		assertTrue(invoked.get());
	}

	@Test
	public void test3() throws Exception {
		dao = newTestDao(getDataSource());

		final AtomicBoolean invoked = new AtomicBoolean(false);
		dao.selectMap("select * from emp", new Each() {
			@SuppressWarnings("unused")
			public void each(Map<String, Object> row) {
				invoked.getAndSet(true);
			}
		});
		assertTrue(invoked.get());
	}

	@Test
	public void test4() throws Exception {
		dao = newTestDao(getDataSource());

		final AtomicBoolean invoked = new AtomicBoolean(false);
		dao.selectMap(from("emp"), where("empno", ge("100")), new Each() {
			@SuppressWarnings("unused")
			public void each(Map<String, Object> row) {
				invoked.getAndSet(true);
			}
		});
		assertTrue(invoked.get());
	}

	private DataSource getDataSource() {
		return dbTestRule.getDataSource();
	}
}
