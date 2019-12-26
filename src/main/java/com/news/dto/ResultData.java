package com.news.dto;

import lombok.Data;

@Data
public class ResultData<T> {
	private long code;
	private String message;
	private T data;

	protected ResultData() {
	}

	protected ResultData(long code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * �ɹ����ؽ��
	 *
	 * @param data
	 *            ��ȡ������
	 */
	public static <T> ResultData<T> success(T data) {
		return new ResultData<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
	}

	/**
	 * �ɹ����ؽ��
	 *
	 * @param data
	 *            ��ȡ������
	 * @param message
	 *            ��ʾ��Ϣ
	 */
	public static <T> ResultData<T> success(T data, String message) {
		return new ResultData<T>(ResultCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * ʧ�ܷ��ؽ��
	 * 
	 * @param failed
	 *            ������
	 */
	public static <T> ResultData<T> failed(ResultCode failed) {
		return new ResultData<T>(failed.getCode(), failed.getMessage(), null);
	}

	/**
	 * ʧ�ܷ��ؽ��
	 * 
	 * @param message
	 *            ��ʾ��Ϣ
	 */
	public static <T> ResultData<T> failed(String message) {
		return new ResultData<T>(ResultCode.FAILED.getCode(), message, null);
	}

	/**
	 * ʧ�ܷ��ؽ��
	 */
	public static <T> ResultData<T> failed() {
		return failed(ResultCode.FAILED);
	}

	/**
	 * ������֤ʧ�ܷ��ؽ��
	 */
	public static <T> ResultData<T> validateFailed() {
		return failed(ResultCode.VALIDATE_FAILED);
	}

	/**
	 * ������֤ʧ�ܷ��ؽ��
	 * 
	 * @param message
	 *            ��ʾ��Ϣ
	 */
	public static <T> ResultData<T> validateFailed(String message) {
		return new ResultData<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
	}

	/**
	 * δ��¼���ؽ��
	 */
	public static <T> ResultData<T> unauthorized(T data) {
		return new ResultData<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * δ��Ȩ���ؽ��
	 */
	public static <T> ResultData<T> forbidden(T data) {
		return new ResultData<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
