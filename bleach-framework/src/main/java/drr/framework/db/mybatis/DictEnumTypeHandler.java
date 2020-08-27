
package drr.framework.db.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import drr.constant.common.IDictParamTypeEnum;

/**
 * 实现IDictParamTypeEnum接口枚举类型处理方法
 *
 * @author wangdl
 */
public class DictEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

	private final Class<E> type;

	public DictEnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (parameter instanceof IDictParamTypeEnum) {
			if (jdbcType == null) {
				ps.setString(i, ((IDictParamTypeEnum<String>) parameter).getValue());
			} else {
				ps.setObject(i, ((IDictParamTypeEnum) parameter).getValue(), jdbcType.TYPE_CODE); // see
			}
		} else {
			throw new SQLException("非法枚举值：【" + parameter.name() + "】");
		}
	}

	@SuppressWarnings("rawtypes")
	private E getEnum(Object s) throws SQLException {
		E[] enumConstants = type.getEnumConstants();
		for (E e : enumConstants) {
			if (e instanceof IDictParamTypeEnum) {
				IDictParamTypeEnum dpye = (IDictParamTypeEnum) e;

				// 数值类型处理
				Object value = dpye.getValue();
				if (Optional.ofNullable(s).isPresent()) {
					if (value instanceof Number && s instanceof Number) {
						if (((Number) value).intValue() == ((Number) s).intValue()) {
							return e;
						}
					}

					// 其他类型处理
					if (dpye.getValue().equals(s)) {
						return e;
					}
				} else {
					if (!Optional.ofNullable(value).isPresent()) {
						return e;
					}
				}

			}
		}
		throw new SQLException("非法枚举值：【" + s + "】");
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Object o = rs.getObject(columnName);
		return o == null ? null : getEnum(o);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Object o = rs.getObject(columnIndex);
		return o == null ? null : getEnum(o);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Object o = cs.getObject(columnIndex);
		return o == null ? null : getEnum(o);
	}
}