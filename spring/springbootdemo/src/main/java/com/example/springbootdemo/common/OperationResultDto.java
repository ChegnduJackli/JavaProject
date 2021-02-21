package com.example.springbootdemo.common;

import java.util.Map;

public class OperationResultDto<T> extends OperationResultBase {

    public static final OperationResultDto<Boolean> SUCCESS_EMPTY = new OperationResultDto<>();

    static {
        SUCCESS_EMPTY.setResult(true);
        SUCCESS_EMPTY.setData(true);
        SUCCESS_EMPTY.setResultMsg("");
    }

    private Map<String, String> errors;

    private T data;


    public OperationResultDto() {
    }

    public OperationResultDto(Boolean result) {
        super();
        this.setResult(result);
    }

    public OperationResultDto(Boolean result, String resultMsg) {
        super();
        this.setResult(result);
        this.setResultMsg(resultMsg);
    }

    public OperationResultDto(Boolean result, String resultMsg, T data) {
        super();
        this.setResult(result);
        this.setResultMsg(resultMsg);
        this.setData(data);
    }


    public static OperationResultDto<?> success() {
        return new OperationResultDto<>(true);
    }


    public static OperationResultDto<?> success(Object data) {
        OperationResultDto dto = new OperationResultDto();
        dto.setData(data);
        dto.setResult(true);
        return dto;
    }

    public static OperationResultDto<?> error() {
        return OperationResultDto.error(null);
    }

    public static OperationResultDto<?> error(String msg) {
        return new OperationResultDto<>(false, msg);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static OperationResultDto<Boolean> errorFor(String reason) {
        OperationResultDto<Boolean> resultDto = new OperationResultDto<>();
        resultDto.setResult(false);
        resultDto.setData(false);
        resultDto.setResultMsg(reason);
        return resultDto;
    }

}
