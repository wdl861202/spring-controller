
package drr.framework.spring.mvc.encoder;

import drr.constant.common.IDictParamTypeEnum;
import feign.codec.EncodeException;
import feign.form.multipart.Output;
import feign.form.multipart.SingleParameterWriter;

public class IDictParamTypeEnumWriter extends SingleParameterWriter {

	@Override
	public boolean isApplicable(Object value) {
		return value instanceof IDictParamTypeEnum;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void write(Output output, String key, Object value) throws EncodeException {
		IDictParamTypeEnum d = (IDictParamTypeEnum) value;
		super.write(output, key, d.getValue());
	}
}
