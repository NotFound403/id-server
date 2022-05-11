package cn.felord.idserver.advice;


import cn.felord.idserver.exception.BindingException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


/**
 * 统一异常处理
 *
 * @author n1
 */
@Slf4j
@RestControllerAdvice(assignableTypes = {BaseController.class, AbstractErrorController.class})
public class ApiExceptionHandleAdvice {

    /**
     * Handle rest.
     *
     * @param request the request
     * @param e       the e
     * @return the rest
     */
    @ExceptionHandler(BindException.class)
    public Rest<?> handle(HttpServletRequest request, BindException e) {
        logger(request, e);
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        return RestBody.failure(400, objectError.getDefaultMessage());
    }

    @ExceptionHandler(BindingException.class)
    public Rest<?> binding(HttpServletRequest request, BindingException e) {
        logger(request, e);
        return RestBody.failure(400, e.getMessage());
    }

    /**
     * Handle rest.
     *
     * @param request the request
     * @param e       the e
     * @return the rest
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Rest<?> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        logger(request, e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        return RestBody.failure(400, objectError.getDefaultMessage());
    }

    /**
     * Handle rest.
     *
     * @param request the request
     * @param e       the e
     * @return the rest
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Rest<?> handle(HttpServletRequest request, ConstraintViolationException e) {
        logger(request, e);

        Optional<ConstraintViolation<?>> first = e.getConstraintViolations().stream().findFirst();
        String message = first.isPresent() ? first.get().getMessage() : "";
        return RestBody.failure(400, message);
    }

    /**
     * Handle rest.
     *
     * @param request the request
     * @param e       the e
     * @return the rest
     */
    @ExceptionHandler(Exception.class)
    public Rest<?> handle(HttpServletRequest request, Exception e) {
        logger(request, e);
        return RestBody.failure(700, "未知异常");
    }

    @ExceptionHandler(ServiceException.class)
    public Rest<?> handle(HttpServletRequest request, ServiceException e) {
        logger(request, e);
        String message = e.getMessage();
        return RestBody.failure(701, message);
    }

    private void logger(HttpServletRequest request, Exception e) {
        String contentType = request.getHeader("Content-Type");
        log.error("error uri: {} content-type: {} exception: {}", request.getRequestURI(), contentType, e.toString());
    }
}
