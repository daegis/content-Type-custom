package cn.aegisa.demo.http.config;

import cn.aegisa.demo.http.vo.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/1/7 23:57
 */
@Slf4j
@Component
public class PostEntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    @Autowired
    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return PostEntity.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        String contentType = request.getContentType();
        log.info("Content-Type:{}", contentType);
        if ("application/json".equals(contentType.toLowerCase())) {
            return requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        } else {
            return servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }
    }
}
