
package drr.framework.spring.mvc.mapping;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import feign.Feign;

@Configuration
@ConditionalOnClass({ Feign.class })
public class FeignClientMappingIgnore implements WebMvcRegistrations {

	private RequestMappingHandlerMapping requestMappingHandlerMapping = new FeignRequestMappingHandlerMapping();

	@Override
	@Nullable
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return requestMappingHandlerMapping;
	}

	private static class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

		@Override
		protected boolean isHandler(Class<?> beanType) {
			return super.isHandler(beanType) && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
		}
	}
}