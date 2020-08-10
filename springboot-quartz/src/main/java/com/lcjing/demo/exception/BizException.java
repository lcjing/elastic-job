package com.lcjing.demo.exception;

/**
 * 自定义异常
 *
 * @author lcjing
 * @date 2020/08/10
 */
public class BizException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BizException(String msg) {
        super(msg);
    }
}
