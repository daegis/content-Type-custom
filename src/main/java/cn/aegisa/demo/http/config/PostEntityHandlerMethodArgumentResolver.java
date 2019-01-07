package cn.aegisa.demo.http.config;

import cn.aegisa.demo.http.vo.PostEntity;
import cn.aegisa.demo.http.vo.Student;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/1/7 23:57
 */
public class PostEntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return PostEntity.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Student bbb = new Student("bbb", 22);
        return bbb;
    }
}
