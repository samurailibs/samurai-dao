package jp.dodododo.dao.id;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import jp.dodododo.dao.Dao;
import jp.dodododo.dao.dialect.Dialect;
import jp.dodododo.dao.dialect.DialectManager;
import jp.dodododo.dao.impl.RdbDao;

public enum Sequence implements IdGenerator {
	SEQUENCE;

    @Override
	public Object generate(Connection connection, String sequenceName) {
		Dialect dialect = DialectManager.getDialect(connection);
		return generate(connection, dialect, sequenceName);

	}

    @Override
	public Object generate(Connection connection, Dialect dialect, String sequenceName) {
		String sql = dialect.sequenceNextValSql(sequenceName);
		Dao dao = new RdbDao(connection);
		Optional<Map<String, Object>> result = dao.selectOneMap(sql);
		Collection<Object> values = result.get().values();
		return values.iterator().next();
	}

    @Override
	public boolean isPrepare(Connection connection) {
		return true;
	}

    @Override
	public boolean isPrepare(Dialect dialect) {
		return true;
	}

}
