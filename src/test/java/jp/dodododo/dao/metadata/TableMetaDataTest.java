package jp.dodododo.dao.metadata;

import static jp.dodododo.dao.unit.Assert.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import jp.dodododo.dao.unit.DbTestRule;

import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TableMetaDataTest {

	@DataPoints({"tableNames"})
	public static String[] tableNames = {"emp", "EMP", "Emp", "EmP", "EMp"};

	@Rule
	public DbTestRule dbTestRule = new DbTestRule();

	@Theory
	public void tableMetaDataConnectionString(@FromDataPoints("tableNames") String tableName) {
		TableMetaData data = new TableMetaData(getConnection(), tableName);

		ColumnMetaData columnMetaData = data.getColumnMetaData("ename");
		assertNotNull(columnMetaData);
		ColumnMetaData pk = data.getColumnMetaData("EMPNO");
		assertTrue(pk.isPrimaryKey());
	}

	private DataSource getDataSource() {
		return dbTestRule.getDataSource();
	}

	private Connection getConnection() {
		return dbTestRule.getConnection();
	}

}
