
package drr.framework.spring.mvc.encoder;

import static feign.form.util.PojoUtil.isUserPojo;
import static feign.form.util.PojoUtil.toMap;

import java.util.Map;
import java.util.Map.Entry;

import feign.codec.EncodeException;
import feign.form.multipart.Output;
import feign.form.multipart.PojoWriter;
import feign.form.multipart.Writer;

public class DRRPojoWriter extends PojoWriter {

	public DRRPojoWriter(Iterable<Writer> writers) {
		super(writers);
	}

	Iterable<Writer> writers;

	@Override
	public boolean isApplicable(Object object) {
		return isUserPojo(object);
	}

	@Override
	public void write(Output output, String boundary, String key, Object object) throws EncodeException {
		Map<String, Object> map = toMap(object);
		for (Entry<String, Object> entry : map.entrySet()) {
			Writer writer = findApplicableWriter(entry.getValue());
			if (writer == null) {
				continue;
			}

			writer.write(output, boundary, key + entry.getKey(), entry.getValue());
		}
	}

	private Writer findApplicableWriter(Object value) {
		for (Writer writer : writers) {
			if (writer.isApplicable(value)) {
				return writer;
			}
		}
		return null;
	}
}