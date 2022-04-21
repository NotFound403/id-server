package cn.felord.idserver.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author n1
 * @since 1.0.0
 */
@Data
public class RestBody<T> implements Rest<T>, Serializable {

    private static final long serialVersionUID = -7616216747521482608L;
    private int code = 200;
    private T data;
    private String msg = "";
    private boolean identifier = true;


    public static Rest<?> ok() {
        return new RestBody<>();
    }

    public static Rest<?> ok(String msg) {
        Rest<?> restBody = new RestBody<>();
        restBody.setMsg(msg);
        return restBody;
    }

    public static <T> Rest<T> okData(T data) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        return restBody;
    }

    public static <T> Rest<T> okData(T data, String msg) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }


    public static <T> Rest<T> build(int httpStatus, T data, String msg, boolean identifier) {
        Rest<T> restBody = new RestBody<>();
        restBody.setCode(httpStatus);
        restBody.setData(data);
        restBody.setMsg(msg);
        restBody.setIdentifier(identifier);
        return restBody;
    }

    public static Rest<?> failure(String msg, boolean identifier) {
        Rest<?> restBody = new RestBody<>();
        restBody.setMsg(msg);
        restBody.setIdentifier(identifier);
        return restBody;
    }

    public static Rest<?> failure(int httpStatus, String msg) {
        Rest<?> restBody = new RestBody<>();
        restBody.setCode(httpStatus);
        restBody.setMsg(msg);
        restBody.setIdentifier(false);
        return restBody;
    }

    public static <T> Rest<T> failureData(T data, String msg, boolean identifier) {
        Rest<T> restBody = new RestBody<>();
        restBody.setIdentifier(identifier);
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }

    public static <T> RestBody<T> fallback() {
        RestBody<T> restBody = new RestBody<>();
        restBody.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        restBody.setMsg("服务不可用");
        restBody.setIdentifier(false);
        return restBody;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", data:" + data +
                ", msg:" + msg +
                ", identifier:" + identifier +
                '}';
    }
}
