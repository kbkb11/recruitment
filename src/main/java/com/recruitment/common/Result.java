package com.recruitment.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code; // 状态码，例如：200 表示成功，500 表示失败
    private Object message; // 返回信息，可以是字符串，也可以是 JSON 对象

    // 可以添加一些静态方法方便创建 Result 对象
    public static Result success(String message) {
        return new Result(200, message);
    }

    public static Result success(Object message) {
        return new Result(200, message);
    }

    public static Result failure(Integer code, String message) {
        return new Result(code, message);
    }

    public static Result failure(Integer code, Object message) {
        return new Result(code, message);
    }

    public static Result failure(String message) {
        return new Result(500, message);
    }

    public static Result failure(Object message) {
        return new Result(500, message);
    }
}