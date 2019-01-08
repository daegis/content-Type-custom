package cn.aegisa.demo.http.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/1/7 23:07
 */
@SpringBootConfiguration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Autowired
    private PostEntityHandlerMethodArgumentResolver postEntityHandlerMethodArgumentResolver;

    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;

    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    @PostConstruct
    private void init() {
        List<HandlerMethodArgumentResolver> current = adapter.getArgumentResolvers();
        for (HandlerMethodArgumentResolver resolver : current) {
            if (resolver instanceof ServletModelAttributeMethodProcessor) {
                this.servletModelAttributeMethodProcessor = (ServletModelAttributeMethodProcessor) resolver;
            }
            if (resolver instanceof RequestResponseBodyMethodProcessor) {
                this.requestResponseBodyMethodProcessor = (RequestResponseBodyMethodProcessor) resolver;
            }
            if (Objects.nonNull(servletModelAttributeMethodProcessor) && Objects.nonNull(requestResponseBodyMethodProcessor)) {
                break;
            }
        }
        List<HandlerMethodArgumentResolver> newList = new ArrayList<>(current.size() + 1);
        newList.add(postEntityHandlerMethodArgumentResolver);
        newList.addAll(current);
        List<HandlerMethodArgumentResolver> unmodifiableList = Collections.unmodifiableList(newList);
        adapter.setArgumentResolvers(unmodifiableList);
    }

    @Bean
    public ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor() {
        return this.servletModelAttributeMethodProcessor;
    }

    @Bean
    public RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor() {
        return this.requestResponseBodyMethodProcessor;
    }


    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters((HttpMessageConverter<?>) fastJsonHttpMessageConverter);
    }

}
