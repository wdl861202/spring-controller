
package drr.framework.jackson;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

/**
 * 默认Date类型全部设置为yyyy-MM-dd，如果想设置为其他类型，需要单独设置
 *
 * @author wangdl
 *
 */
public class DateJsonSerializer extends DateSerializer {

	private static final long serialVersionUID = 1L;

	public static final DateJsonSerializer instance = new DateJsonSerializer();

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(DateFormatUtils.ISO_DATE_FORMAT.format(value));
	}

	public DateJsonSerializer() {
		this(null, null);
	}

	public DateJsonSerializer(Boolean useTimestamp, DateFormat customFormat) {
		super(useTimestamp, customFormat);
	}

	@Override
	public DateJsonSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
		return new DateJsonSerializer(timestamp, customFormat);
	}

	@Override
	protected long _timestamp(Date value) {
		return (value == null) ? 0L : value.getTime();
	}

}