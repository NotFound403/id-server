package cn.felord.idserver.advice;

import lombok.Data;

/**
 * @author rongqiu
 * @description // todo 前端tree结果集的封装
 * @date 2021/12/7 10:07
 */
@Data
public class ResultTree {


    /**
     * 状态信息
     */
    private Status status = new Status();


    /**
     * 返回数据
     */
    private Object data;

    /**
     * 所需内部类
     */
    @Data
    public class Status{
        private Integer code = 200;
        private String message = "默认";
    }

}
