package jp.dodododo.dao.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.dodododo.dao.config.DaoConfig;
import jp.dodododo.dao.exception.SQLRuntimeException;
import jp.dodododo.dao.log.SlowQuery;
import jp.dodododo.dao.log.SqlLogRegistry;

/**
 *
 * @author Satoshi Kimura
 */
public abstract class PreparedStatementUtil extends StatementUtil {

	public static int executeUpdate(PreparedStatement ps) {
		return executeUpdate(ps, null);
	}

	public static int executeUpdate(PreparedStatement ps, SqlLogRegistry sqlLogRegistry) {
		try {
			int ret = ps.executeUpdate();
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(sqlLogRegistry.getLast().getCompleteSql(), e);
		}
	}

	public static void addBatch(PreparedStatement ps) {
		addBatch(ps, null);
	}

	public static void addBatch(PreparedStatement ps, SqlLogRegistry sqlLogRegistry) {
		try {
			ps.addBatch();
		} catch (SQLException e) {
			throw new SQLRuntimeException(sqlLogRegistry.getLast().getCompleteSql(), e);
		}
	}

	public static int[] executeBatch(PreparedStatement ps) {
		return executeBatch(ps, null);
	}

	public static int[] executeBatch(PreparedStatement ps, SqlLogRegistry sqlLogRegistry) {
		try {
			int[] ret = ps.executeBatch();
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(sqlLogRegistry.getLast().getCompleteSql(), e);
		}
	}

	public static ResultSet executeQuery(PreparedStatement ps) {
		return executeQuery(ps, null);
	}

	public static ResultSet executeQuery(PreparedStatement ps, SqlLogRegistry sqlLogRegistry) {
		try {
			double start = System.currentTimeMillis();
			ResultSet resultSet = ps.executeQuery();
			double end = System.currentTimeMillis();
			double time = (end - start) / 1000D;
			if (DaoConfig.getDefaultConfig().getLongQuerySeconds() < time) {
				SlowQuery.warn(time, sqlLogRegistry.getLast().getCompleteSql());
			}
			return resultSet;
		} catch (SQLException e) {
			throw new SQLRuntimeException(sqlLogRegistry.getLast().getCompleteSql(), e);
		}
	}

}
