
package drr.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.PageableSpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import drr.framework.spring.mvc.encoder.DRRSpringEncoder;
import feign.codec.Encoder;

@Configuration
public class FeignClientConfig {

	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;

	@Autowired(required = false)
	private SpringDataWebProperties springDataWebProperties;

	@Bean
	public Encoder feignEncoder() {
		return new DRRSpringEncoder(messageConverters);
	}

	@Primary
	@Bean
	public Encoder feignEncoderPageable() {
		PageableSpringEncoder encoder = new PageableSpringEncoder(new DRRSpringEncoder(messageConverters));
		if (springDataWebProperties != null) {
			encoder.setPageParameter(springDataWebProperties.getPageable().getPageParameter());
			encoder.setSizeParameter(springDataWebProperties.getPageable().getSizeParameter());
			encoder.setSortParameter(springDataWebProperties.getSort().getSortParameter());
		}
		return encoder;
	}
}
