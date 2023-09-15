package com.kunminx.architecture.data.response;

import com.drake.statelayout.Status;

public class DataResult<T> {
  private final T mEntity;
  private final Status status;
  private final String errorMsg;
  private final ResultSource source;

  public DataResult(T entity) {
    this(entity, Status.CONTENT, "网络异常，请稍后重试", ResultSource.NETWORK);
  }

  public DataResult(Status status){
    this(null, status, "网络异常，请稍后重试", ResultSource.NETWORK);
  }

  public DataResult(T entity, Status status) {
    this(entity, status, "网络异常，请稍后重试", ResultSource.NETWORK);
  }

  public DataResult(T entity, Status status, String errorMsg) {
    this(entity, status, errorMsg, ResultSource.NETWORK);
  }

  public DataResult(T entity, Status status, String errorMsg, ResultSource source) {
    this.mEntity = entity;
    this.status = status;
    this.errorMsg = errorMsg;
    this.source = source;
  }

  public T getResult() {
    return mEntity;
  }

  public Status getStatus() {
    return status;
  }

  public String getErrorMsg(){
    return errorMsg;
  }

  public boolean isSuccess() {
    return status == Status.CONTENT;
  }

  public interface Result<T> {
    void onResult(DataResult<T> dataResult);
  }
}
