package com.news.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTUtil {
	/**
	 * { </br>
	 * "iss":"Issuer ���� ����˵����JWT����˭ǩ����", </br>
	 * "sub":"Subject ���� ����˵����JWT����Ķ���", </br>
	 * "aud":"Audience ���� ����˵����JWT���͸����û�", </br>
	 * "exp":"Expiration Time ���� �������ͣ�˵����JWT���ڵ�ʱ��", </br>
	 * "nbf":"Not Before ���� �������ͣ�˵���ڸ�ʱ��֮ǰJWT���ܱ������봦��", </br>
	 * "iat":"Issued At ���� �������ͣ�˵����JWT��ʱ��ǩ��", </br>
	 * "jti":"JWT ID ���� ˵������JWT��ΨһID", </br>
	 * "user-definde1":"�Զ������Ծ���", </br>
	 * "user-definde2":"�Զ������Ծ���" </br>
	 * }
	 */
	private static final String KEYID = "hajhfdhfehf56REWwEIRUirepIEW89787wrWVert34i59e';t'ewte[wl;2[l=[55";

	// ���ܣ������˺ź���Ч��
	public static String sign(String login_id, long maxAge) {
		return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + maxAge)).withClaim("login_id", login_id)
				.sign(Algorithm.HMAC256(KEYID));
	}

	// ���ܣ������˺źʹ���
	public static String sign(String login_id, int count) {
		return JWT.create().withClaim("count", count).withClaim("login_id", login_id).sign(Algorithm.HMAC256(KEYID));
	}

	public static String sign(String login_id) {
		return JWT.create().withClaim("login_id", login_id).sign(Algorithm.HMAC256(KEYID));
	}

	// ���ܣ�����һ�����ܺ��token�ַ����ͽ��ܺ������
	public static String unsign(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(KEYID)).build().verify(token).getClaim("login_id").asString(); // Reusable
		} catch (Exception e) {
			log.error("����ʧ��", e);
		}
		return null;
	}

	// public static void main(String[] args) throws InterruptedException {
	// String sign = sign("admin");
	// System.out.println(sign);
	// String unsign = unsign(sign);
	// System.out.println("admin".equals(unsign));
	//
	// }
}
