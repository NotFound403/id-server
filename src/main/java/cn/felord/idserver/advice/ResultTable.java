package cn.felord.idserver.advice;

import lombok.Data;

/**
 * @author rongqiu
 * @description // todo
 * @date 2021/12/2 9:56
 */
@Data
public class ResultTable  {

    private static final long serialVersionUID = -6078342225905202812L;

    /**
     * 状态码
     * */
    private Integer code;

    /**
     * 提示消息
     * */
    private String msg;

    /**
     * 消息总量
     * */
    private Long count;

    /**
     * 数据对象
     * */
    private Object data;

    /**
     * @description //todo 构造pageTable
     * @author rongqiu
     * @date 2021/12/2
     */
    public static ResultTable pageTable(long count, Object data){
        ResultTable resultTable = new ResultTable();
        resultTable.setData(data);
        resultTable.setCode(0);
        resultTable.setCount(count);
        return resultTable;
    }

    /**
     * @description //todo 构造dataTable
     * @author rongqiu
     * @date 2021/12/2
     */
    public static ResultTable dataTable(Object data){
        ResultTable resultTable = new ResultTable();
        resultTable.setData(data);
        resultTable.setCode(0);
        return resultTable;
    }

}
