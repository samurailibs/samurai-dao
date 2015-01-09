package jp.dodododo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import jp.dodododo.dao.script.Each;
import jp.dodododo.dao.sql.Sql;

public interface SelectDao {
	<ROW> List<ROW> select(Class<ROW> returnType, Object... args);

	<ROW> Optional<ROW> selectOne(Class<ROW> returnType, Object... args);

	<ROW> List<ROW> select(String sql, Class<ROW> as);

	<ROW> List<ROW> select(String sql, Class<ROW> as, Each callback);

	<ROW> List<ROW> select(String sql, Class<ROW> as, Consumer<ROW> callback);

	<ROW> List<ROW> select(String sql, Map<String, Object> arg, Class<ROW> as, Each callback);

	<ROW> List<ROW> select(String sql, Map<String, Object> arg, Class<ROW> as, Consumer<ROW> callback);

	<ROW> List<ROW> select(String sql, Map<String, Object> arg, Class<ROW> as);

	<ROW> List<ROW> select(String sql, Map<String, Object> arg, Class<ROW> as, IterationCallback<ROW> callback);

	<ROW> List<ROW> select(String sql, Class<ROW> as, IterationCallback<ROW> callback);

	<ROW> Optional<ROW> selectOne(String sql, Map<String, Object> arg, Class<ROW> as);

	<ROW> Optional<ROW> selectOne(String sql, Class<ROW> as);

	List<Map<String, Object>> selectMap(String sql, Map<String, Object> arg);

	List<Map<String, Object>> selectMap(String sql, Map<String, Object> arg, IterationCallback<Map<String, Object>> callback);

	List<Map<String, Object>> selectMap(String sql, Map<String, Object> arg, Each callback);

	List<Map<String, Object>> selectMap(String sql, Map<String, Object> arg, Consumer<Map<String, Object>> callback);

	List<Map<String, Object>> selectMap(String sql);

	List<Map<String, Object>> selectMap(String sql, IterationCallback<Map<String, Object>> callback);

	List<Map<String, Object>> selectMap(String sql, Each callback);

	List<Map<String, Object>> selectMap(String sql, Consumer<Map<String, Object>> callback);

	Optional<Map<String, Object>> selectOneMap(String sql, Map<String, Object> arg);

	Optional<Map<String, Object>> selectOneMap(String sql);

	Optional<BigDecimal> selectOneNumber(String sql, Map<String, Object> arg);

	Optional<BigDecimal> selectOneNumber(String sql);

	<ROW> List<ROW> select(Sql sql, Class<ROW> as);

	<ROW> List<ROW> select(Sql sql, ROW query);

	<ROW> List<ROW> select(Sql sql, ROW query, Each callback);

	<ROW> List<ROW> select(Sql sql, ROW query, IterationCallback<ROW> callback);

	<ROW> List<ROW> select(Sql sql, ROW query, Consumer<ROW> callback);

	<ROW> List<ROW> select(Sql sql, Map<String, Object> arg, Class<ROW> as);

	<ROW> Optional<ROW> selectOne(Sql sql, ROW query);

	List<Map<String, Object>> selectMap(Sql sql, Map<String, Object> query);

	List<Map<String, Object>> selectMap(Sql sql, Map<String, Object> query, IterationCallback<Map<String, Object>> callback);

	List<Map<String, Object>> selectMap(Sql sql, Map<String, Object> query, Each callback);

	List<Map<String, Object>> selectMap(Object... query);

	List<Map<String, Object>> selectMap(Sql sql, Map<String, Object> query, Consumer<Map<String, Object>> callback);

	Optional<Map<String, Object>> selectOneMap(Sql sql, Map<String, Object> query);

	Optional<Map<String, Object>> selectOneMap(Object... query);

	Optional<BigDecimal> selectOneNumber(Sql sql, Map<String, Object> query);

	<QUERY> Optional<BigDecimal> selectOneNumber(Sql sql, QUERY query);

	boolean existsRecord(String sql);

	boolean existsRecord(String sql, Map<String, Object> query);

	boolean existsRecord(Sql sql);

	<QUERY> boolean existsRecord(Sql sql, QUERY query);

	boolean existsRecord(Object... query);

	boolean exists(String tableName, Object... entities);

	<ENTITY> boolean exists(ENTITY entity);
}
