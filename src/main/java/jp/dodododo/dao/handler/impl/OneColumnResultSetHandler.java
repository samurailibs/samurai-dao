package jp.dodododo.dao.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.dodododo.dao.IterationCallback;
import jp.dodododo.dao.columns.ResultSetColumn;
import jp.dodododo.dao.types.JavaType;

/**
 *
 * @author Satoshi Kimura
 */
public class OneColumnResultSetHandler<T> extends AbstractResultSetHandler<T> {

	protected JavaType<?> javaType;

	public OneColumnResultSetHandler(IterationCallback<T> callback, Class<T> clazz, JavaType<?> javaType) {
		super(callback);
		this.javaType = javaType;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T createRow(ResultSet rs, List<ResultSetColumn> resultSetColumnList) throws SQLException {
		return (T) javaType.convert(javaType.getValue(rs, 1));
	}
}
