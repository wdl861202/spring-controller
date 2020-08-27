
package drr.framework.spring.mvc.editor;

import java.beans.PropertyEditorSupport;

import drr.constant.common.IDictParamTypeEnum;

public class IDictParamTypeEnumPropertyEditor extends PropertyEditorSupport {

	private Class enumClass;

	public IDictParamTypeEnumPropertyEditor(Class enumClass) {
		this.enumClass = enumClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
		Object[] enums = enumClass.getEnumConstants();
		for (Object e : enums) {
			if (e instanceof IDictParamTypeEnum) {
				IDictParamTypeEnum d = (IDictParamTypeEnum) e;
				if (d.getValue().equals(text)) {
					setValue(e);
				}
			}
		}

	}

	@Override
	public Object getValue() {
		// 当变量为字符串"null"时 转换为null
		if ("null".equals(super.getValue())) {
			return null;
		}
		return super.getValue();
	}

}
