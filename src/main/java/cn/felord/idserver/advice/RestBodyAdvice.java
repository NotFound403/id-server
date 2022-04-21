package cn.felord.idserver.advice;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;
import java.util.Collections;

/**
 * 返回体统一封装器
 *
 * @author n1
 */
@Slf4j
@RestControllerAdvice(assignableTypes = BaseController.class)
public class RestBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,@NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(IgnoreRestBody.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        Rest<Object> objectRest;
        if (body == null) {
            objectRest = RestBody.okData(Collections.emptyMap());
        } else if (Rest.class.isAssignableFrom(body.getClass())) {
            objectRest = (Rest<Object>) body;
        }
        else if (checkPrimitive(body)) {
            return RestBody.okData(Collections.singletonMap("result", body));
        }else {
            objectRest = RestBody.okData(body);
        }
        return objectRest;
    }


    private boolean checkPrimitive(Object body) {
        Class<?> clazz = body.getClass();
        return clazz.isPrimitive()
                || clazz.isArray()
                || Collection.class.isAssignableFrom(clazz)
                || body instanceof Number
                || body instanceof Boolean
                || body instanceof Character
                || body instanceof String;
    }
}
