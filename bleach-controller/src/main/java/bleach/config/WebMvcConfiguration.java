
package drr.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import drr.framework.spring.mvc.handler.ResponseCommittedReturnValueHandler;
import drr.web.BaseDTOArgumentResolver;

@Configuration
@ConditionalOnClass({ RequestMappingHandlerAdapter.class })
public class WebMvcConfiguration implements InitializingBean {

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Autowired
	@Qualifier("mvcContentNegotiationManager")
	private ContentNegotiationManager contentNegotiationManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		addArgumentResolvers();
		addReturnValueHandlers();
	}

	private void addArgumentResolvers() {
		int size = requestMappingHandlerAdapter.getArgumentResolvers().size();
		List<HandlerMethodArgumentResolver> argumentResolversNew = new ArrayList<>(size + 1);
		argumentResolversNew.add(new BaseDTOArgumentResolver(requestMappingHandlerAdapter.getMessageConverters()));
		argumentResolversNew.addAll(requestMappingHandlerAdapter.getArgumentResolvers());
		requestMappingHandlerAdapter.setArgumentResolvers(argumentResolversNew);
	}

	private void addReturnValueHandlers() {
		ClassLoader classLoader = WebMvcConfiguration.class.getClassLoader();
		boolean jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
		List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> returnValueHandlersNew = new ArrayList<>();
		returnValueHandlersNew.addAll(returnValueHandlers);
		if (jackson2Present) {
			List<Object> advices = new ArrayList<>();
			advices.add(new JsonViewRequestBodyAdvice());
			advices.add(new JsonViewResponseBodyAdvice());
			returnValueHandlersNew.add(new ResponseCommittedReturnValueHandler(requestMappingHandlerAdapter.getMessageConverters(), contentNegotiationManager, advices));
			requestMappingHandlerAdapter.setReturnValueHandlers(returnValueHandlersNew);
		} else {
			returnValueHandlersNew.add(new ResponseCommittedReturnValueHandler(requestMappingHandlerAdapter.getMessageConverters(), contentNegotiationManager));
			requestMappingHandlerAdapter.setReturnValueHandlers(returnValueHandlersNew);
		}
	}

}
