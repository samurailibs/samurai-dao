package jp.dodododo.dao.util;

import java.lang.reflect.Field;

import jp.dodododo.dao.message.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldUtil {

	private static final Log logger = LogFactory.getLog(FieldUtil.class);

	public static Object get(Field field, Object target) {

		try {
			if (logger.isTraceEnabled()) {
				logger.trace(Message.getMessage("00030", field.getDeclaringClass().getName(), field.getName()));
			}
			return field.get(target);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			if (logger.isTraceEnabled()) {
				logger.trace(Message.getMessage("00031", field.getDeclaringClass().getName(), field.getName()));
			}
		}
	}

	public static void set(Field field, Object target, Object value) {

		try {
			if (logger.isTraceEnabled()) {
				logger.trace(Message.getMessage("00032", field.getDeclaringClass().getName(), field.getName()));
			}
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			if (logger.isTraceEnabled()) {
				logger.trace(Message.getMessage("00033", field.getDeclaringClass().getName(), field.getName()));
			}
		}
	}

}
