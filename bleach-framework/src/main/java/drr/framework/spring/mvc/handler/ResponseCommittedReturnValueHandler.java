
package drr.framework.spring.mvc.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import drr.framework.spring.mvc.annotation.ResponseComitted;

public class ResponseCommittedReturnValueHandler extends RequestResponseBodyMethodProcessor {

	public ResponseCommittedReturnValueHandler(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	public ResponseCommittedReturnValueHandler(List<HttpMessageConverter<?>> converters, @Nullable ContentNegotiationManager manager) {

		super(converters, manager);
	}

	public ResponseCommittedReturnValueHandler(List<HttpMessageConverter<?>> converters, @Nullable List<Object> requestResponseBodyAdvice) {

		super(converters, null, requestResponseBodyAdvice);
	}

	public ResponseCommittedReturnValueHandler(List<HttpMessageConverter<?>> converters, @Nullable ContentNegotiationManager manager, @Nullable List<Object> requestResponseBodyAdvice) {

		super(converters, manager, requestResponseBodyAdvice);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		boolean hasResponseComitted = AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseComitted.class) || returnType.hasMethodAnnotation(ResponseComitted.class);
		return hasResponseComitted;
	}

	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
		mavContainer.setRequestHandled(true);
		ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
		ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);

		HttpServletResponse servletResponse = outputMessage.getServletResponse();
		// int status = servletResponse.getStatus();
		// Try even with null return value. ResponseBodyAdvice could get
		// involved.
		if (!servletResponse.isCommitted()) {
			writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
		}
	}
}
