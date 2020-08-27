
package drr.web;

import java.util.List;
import java.util.Map;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import drr.api.common.dto.BaseDTO;
import drr.constant.sm.AuthenticationDetailsConst;

/**
 * 设置基础属性
 *
 * @author wangdl
 *
 */
public class BaseDTOArgumentResolver extends AbstractMessageConverterMethodArgumentResolver {

	public BaseDTOArgumentResolver(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return BaseDTO.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		parameter = parameter.nestedIfOptional();
		Object arg = readWithMessageConverters(webRequest, parameter, parameter.getNestedGenericParameterType());
		String name = Conventions.getVariableNameForParameter(parameter);

		if (binderFactory != null) {
			WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
			if (arg != null) {
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
					throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
				}
			}
			if (mavContainer != null) {
				mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
			}
		}

		updateBaseDTOValue(arg);

		return adaptArgumentIfNecessary(arg, parameter);
	}

	@SuppressWarnings("unchecked")
	private void updateBaseDTOValue(Object arg) {
		if (arg instanceof BaseDTO) {
			BaseDTO dto = (BaseDTO) arg;
			Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
			if (details instanceof Map) {
				Map<Object, Object> m = (Map<Object, Object>) details;
				dto.setStaffId(Integer.valueOf(String.valueOf(m.get(AuthenticationDetailsConst.STAFF_ID))));
				dto.setCompany(String.valueOf(m.get(AuthenticationDetailsConst.COMPANY)));
			}
		}
	}
}
