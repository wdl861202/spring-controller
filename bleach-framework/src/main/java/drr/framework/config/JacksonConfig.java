
package drr.framework.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import drr.framework.jackson.DateJsonSerializer;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		Jackson2ObjectMapperBuilderCustomizer customizer = jacksonObjectMapperBuilder -> {
			jacksonObjectMapperBuilder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
			jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
			jacksonObjectMapperBuilder.serializerByType(Date.class, DateJsonSerializer.instance);
		};
		return customizer;
	}

}
