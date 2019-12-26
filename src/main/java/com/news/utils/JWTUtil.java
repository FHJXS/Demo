package com.news.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTUtil {
	/**
	 * { </br>
	 * "iss":"Issuer —— 用于说明该JWT是由谁签发的", </br>
	 * "sub":"Subject —— 用于说明该JWT面向的对象", </br>
	 * "aud":"Audience —— 用于说明该JWT发送给的用户", </br>
	 * "exp":"Expiration Time —— 数字类型，说明该JWT过期的时间", </br>
	 * "nbf":"Not Before —— 数字类型，说明在该时间之前JWT不能被接受与处理", </br>
	 * "iat":"Issued At —— 数字类型，说明该JWT何时被签发", </br>
	 * "jti":"JWT ID —— 说明标明JWT的唯一ID", </br>
	 * "user-definde1":"自定义属性举例", </br>
	 * "user-definde2":"自定义属性举例" </br>
	 * }
	 */
	private static final String KEYID = "hajhfdhfehf56REWwEIRUirepIEW89787wrWVert34i59e';t'ewte[wl;2[l=[55";

	// 加密，传入账号和有效期
	public static String sign(String login_id, long maxAge) {
		return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + maxAge)).withClaim("login_id", login_id)
				.sign(Algorithm.HMAC256(KEYID));
	}

	// 加密，传入账号和次数
	public static String sign(String login_id, int count) {
		return JWT.create().withClaim("count", count).withClaim("login_id", login_id).sign(Algorithm.HMAC256(KEYID));
	}

	public static String sign(String login_id) {
		return JWT.create().withClaim("login_id", login_id).sign(Algorithm.HMAC256(KEYID));
	}

	// 解密，传入一个加密后的token字符串和解密后的类型
	public static String unsign(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(KEYID)).build().verify(token).getClaim("login_id").asString(); // Reusable
		} catch (Exception e) {
			log.error("解密失败", e);
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
