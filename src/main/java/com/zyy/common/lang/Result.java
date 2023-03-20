package com.zyy.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;   //200表示正常，非200表示异常结果
    private String msg;
    private Object data;

    //操作成功时，提示信息不重要，可以使用该方法，侧重于获取数据
    public static Result success(Object data){
        Result result = new Result();
        result.setMsg("操作成功");
        result.setCode(200);
        result.setData(data);
        return result;
    }
    public static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    //操作失败时，没有数据，侧重于提示信息
    public static Result fail(String msg){
        Result result = new Result();
        result.setData(null);
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }
    public static Result fail(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
