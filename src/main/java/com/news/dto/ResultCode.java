package com.news.dto;

public enum ResultCode {
	SUCCESS(200, "�����ɹ�"), FAILED(500, "����ʧ��"), VALIDATE_FAILED(404, "��������ʧ��"), UNAUTHORIZED(401,
			"��δ��¼��token�Ѿ�����"), FORBIDDEN(403, "û�����Ȩ��");
	private long code;
	private String message;

	private ResultCode(long code, String message) {
		this.code = code;
		this.message = message;
	}

	public long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
