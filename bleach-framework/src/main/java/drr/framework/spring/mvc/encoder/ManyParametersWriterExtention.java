
package drr.framework.spring.mvc.encoder;

import static feign.form.util.PojoUtil.toMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import feign.codec.EncodeException;
import feign.form.multipart.ManyParametersWriter;
import feign.form.multipart.Output;
import feign.form.multipart.PojoWriter;
import feign.form.multipart.Writer;

public class ManyParametersWriterExtention extends ManyParametersWriter {

	Iterable<Writer> writers;

	public ManyParametersWriterExtention(Iterable<Writer> writers) {
		this.writers = writers;
	}

	@Override
	public boolean isApplicable(Object value) {
		return value.getClass().isArray() || value instanceof Iterable;
	}

	@Override
	public void write(Output output, String boundary, String key, Object value) throws EncodeException {
		if (value.getClass().isArray()) {
			Object[] objects = (Object[]) value;
			int i = 0;
			for (Object object : objects) {
				Writer writer = findApplicableWriter(object);
				if (Optional.ofNullable(writer).isPresent()) {
					if (writer instanceof PojoWriter) {
						writePojo(output, boundary, key, object, i);
						i++;
					} else {
						writer.write(output, boundary, key, object);
					}
				}
			}
		} else if (value instanceof Iterable) {
			Iterable<?> iterable = (Iterable<?>) value;
			int i = 0;
			for (Object object : iterable) {
				Writer writer = findApplicableWriter(object);
				if (Optional.ofNullable(writer).isPresent()) {
					if (writer instanceof PojoWriter) {
						writePojo(output, boundary, key, object, i);
						i++;
					} else {
						writer.write(output, boundary, key, object);
					}
				}
			}
		}
	}

	private void writePojo(Output output, String boundary, String key, Object object, Integer i) {
		Map<String, Object> map = toMap(object);
		for (Entry<String, Object> entry : map.entrySet()) {
			Writer writer = findApplicableWriter(entry.getValue());
			if (writer == null) {
				continue;
			}

			writer.write(output, boundary, key + "[" + i + "]." + entry.getKey(), entry.getValue());
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
