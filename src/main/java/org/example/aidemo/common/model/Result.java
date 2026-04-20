package org.example.aidemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;


    public static <E> Result<E> success(E data){
        return new Result<E>(200, "success", data);
    }
    public static Result success(){
        return new Result(200, "success", null);
    }
    public static Result error(String message){
        return new Result(1, message, null);
    }

}
