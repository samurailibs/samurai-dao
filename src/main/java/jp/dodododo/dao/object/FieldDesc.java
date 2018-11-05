package jp.dodododo.dao.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import jp.dodododo.dao.annotation.Property;
import jp.dodododo.dao.exception.IllegalFieldRuntimeException;
import jp.dodododo.dao.util.FieldUtil;

public class FieldDesc {

	protected Field field;

	protected String fieldName;

	protected Class<?> fieldType;

	protected Type genericFieldType;

	protected ObjectDesc<?> objectDesc;

	private boolean readable = false;

	private boolean writable = false;

	protected FieldDesc() {
	}

	private FieldDesc(String fieldName, ObjectDesc<?> objectDesc) {
		if (fieldName == null) {
			throw new NullPointerException("propertyName");
		}
		this.fieldName = fieldName;
		this.objectDesc = objectDesc;
	}

	protected FieldDesc(String propertyName, Field f, ObjectDesc<?> objectDesc) {
		this(propertyName, objectDesc);
		this.field = f;
		this.fieldType = f.getType();
		this.genericFieldType = f.getGenericType();

		setupReadableWritable(f);
	}

	private void setupReadableWritable(Field field) {
		Property p = field.getAnnotation(Property.class);
		if (p != null) {
			this.readable = p.readable().toBoolean(false);
			this.writable = p.writable().toBoolean(false);
			if (this.writable || this.readable) {
				field.setAccessible(true);
			}
		} else if (Modifier.isPublic(field.getModifiers()) == true
				&& Modifier.isPublic(field.getDeclaringClass().getModifiers()) == true) {
			this.readable = true;
			this.writable = true;
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	@SuppressWarnings("unchecked")
	public <VALUE> VALUE getValue(Object target) {
		return (VALUE) getValue(target, false);
	}

	public <VALUE> VALUE getValue(Object target, boolean force) {
		try {
			if (force == true && field.isAccessible() == false) {
				field.setAccessible(true);
			}
			@SuppressWarnings("unchecked")
			VALUE ret = (VALUE) FieldUtil.get(field, target);
			return ret;
		} catch (Throwable t) {
			throw new IllegalFieldRuntimeException(objectDesc.getTargetClass(), fieldName, t);
		}
	}

	public void setValue(Object target, Object value) {
		setValue(target, value, false);
	}

	public void setValue(Object target, Object value, boolean force) {
		try {
			if (force == true && field.isAccessible() == false) {
				field.setAccessible(true);
			}
			FieldUtil.set(field, target, value);
		} catch (Throwable t) {
			throw new IllegalFieldRuntimeException(objectDesc.getTargetClass(), fieldName, t);
		}
	}

}
