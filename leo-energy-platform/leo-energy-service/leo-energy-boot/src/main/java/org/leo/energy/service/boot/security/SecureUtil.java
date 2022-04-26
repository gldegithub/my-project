/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leo.energy.service.boot.security;

import com.google.common.base.Charsets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.leo.energy.service.boot.auth.AuthUser;
import org.leo.energy.service.boot.auth.TokenInfo;
import org.leo.energy.common.utils.*;
import org.leo.energy.common.constant.TokenConstant;
import org.leo.energy.service.boot.security.exception.SecureException;
import org.leo.energy.service.boot.security.provider.IClientDetails;
import org.leo.energy.service.boot.security.provider.IClientDetailsService;
import org.leo.energy.common.utils.WebUtil;
import org.springframework.http.HttpHeaders;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

/**
 * Secure工具类
 *
 * @author Chill
 */
public class SecureUtil {
	private final static String HEADER = TokenConstant.HEADER;
	private final static String BEARER = TokenConstant.BEARER;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
	private final static String LEO_USER_REQUEST_ATTR = TokenConstant.USER_REQUEST_ATTR;
	private final static String LEO_MAP_REQUEST_ATTR = TokenConstant.MAP_REQUEST_ATTR;
	private static final String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

	private static final IClientDetailsService clientDetailsService;
	private static final ClientLoggerBuilder clientLoggerBuilder;

	static {
		clientDetailsService = SpringUtil.getBean(IClientDetailsService.class);
		clientLoggerBuilder = SpringUtil.getBean(ClientLoggerBuilder.class);
	}

	/**
	 * 获取用户信息封装到auth中
	 */
	public static AuthUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object user = request.getAttribute(LEO_USER_REQUEST_ATTR);
		if (user == null) {
			user = getUser(request);
			if (user != null) {
				// 设置到 request 中
				request.setAttribute(LEO_USER_REQUEST_ATTR, user);
			}
		}
		return (AuthUser)user;
	}


	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return map
	 */
	public static AuthUser getUser(HttpServletRequest request) {
		Claims claims = SecureUtil.getClaims(request);
		if (claims != null) {
			AuthUser authUser = JsonUtil.toPojo(claims, AuthUser.class);
			clientLoggerBuilder.setAuthUser(authUser);
			return authUser;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMap() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object map = request.getAttribute(LEO_MAP_REQUEST_ATTR);
		if (map == null) {
			map = getMap(request);
			if (map != null) {
				// 设置到 request 中
				request.setAttribute(LEO_MAP_REQUEST_ATTR, map);
			}
		}
		return  (Map<String,Object>)map;
	}

	private static Map<String,Object> getMap(HttpServletRequest request) {
		Claims claims = SecureUtil.getClaims(request);
		if (claims != null) {
			return new HashMap<>(claims);
		}
		return null;
	}

	/**
	 * 自定义AuthUser的实现类
	 * @param customizerAuthUser
	 * @return AuthUser
	 */
	public static AuthUser getCustomizerAuthUser(Class<? extends AuthUser> customizerAuthUser) {
		if (AuthUser.class.isAssignableFrom(customizerAuthUser)) {
			return Func.copyProperties(getUser(), customizerAuthUser);
		}
		return null;
	}


	/**
	 * 直接转换为任意对象
	 * @param customizerUser
	 * @param <T>
	 * @return
	 */
	public static <T> T getCustomizerUser(Class<T> customizerUser) {
		return JsonUtil.toPojo(getMap(), customizerUser);
	}


	/**
	 * 获取token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			if (headStr.compareTo(BEARER) == 0) {
				auth = auth.substring(7);
			}
			return auth;
		}
		return null;
	}


	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(SecureUtil.HEADER);
		if (StringUtil.isNotBlank(auth) && auth.length() > AUTH_LENGTH) {
			String headStr = auth.substring(0, 6).toLowerCase();
			if (headStr.compareTo(SecureUtil.BEARER) == 0) {
				auth = auth.substring(7);
				return SecureUtil.parseJWT(auth);
			}
		} else {
			String parameter = request.getParameter(SecureUtil.HEADER);
			if (StringUtil.isNotBlank(parameter)) {
				return SecureUtil.parseJWT(parameter);
			}
		}
		return null;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY)).build()
				.parseClaimsJws(jsonWebToken).getBody();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param user      user
	 * @param audience  audience
	 * @param issuer    issuer
	 * @param tokenType tokenType
	 * @return jwt
	 */
	public static TokenInfo createJWT(Map<String, String> user, String audience, String issuer, String tokenType) {

		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];

		// 获取客户端信息
		IClientDetails clientDetails = clientDetails(clientId);

		// 校验客户端信息
		if (!validateClient(clientDetails, clientId, clientSecret)) {
			throw new SecureException("客户端认证失败!");
		}

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥
		byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//添加构成JWT的类
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
			.setIssuer(issuer)
			.setAudience(audience)
			.signWith(signingKey);

		//设置JWT参数
		user.forEach(builder::claim);

		//设置应用id
		builder.claim(CLIENT_ID, clientId);

		//添加Token过期时间
		long expireMillis;
		if (tokenType.equals(TokenConstant.ACCESS_TOKEN)) {
			expireMillis = clientDetails.getAccessTokenValidity() * 1000;
		} else if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
			expireMillis = clientDetails.getRefreshTokenValidity() * 1000;
		} else {
			expireMillis = getExpire();
		}
		long expMillis = nowMillis + expireMillis;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp).setNotBefore(now);

		// 组装Token信息
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setToken(builder.compact());
		tokenInfo.setExpire((int) expireMillis / 1000);

		return tokenInfo;
	}

	/**
	 * 获取过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static long getExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}

	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {

		// 获取请求头客户端信息
		String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(HttpHeaders.AUTHORIZATION);
		header = Func.toStr(header).replace("Basic%20", "Basic ");
		if (!header.startsWith("Basic ")) {
			throw new SecureException("No client information in request header");
		}
		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8.name());

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Charsets.UTF_8.name());
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 获取客户端信息
	 *
	 * @param clientId 客户端id
	 * @return clientDetails
	 */
	private static IClientDetails clientDetails(String clientId) {
		return clientDetailsService.loadClientByClientId(clientId);
	}

	/**
	 * 校验Client
	 *
	 * @param clientId     客户端id
	 * @param clientSecret 客户端密钥
	 * @return boolean
	 */
	private static boolean validateClient(IClientDetails clientDetails, String clientId, String clientSecret) {
		if (clientDetails != null) {
			return StringUtil.equals(clientId, clientDetails.getClientId()) &&
					StringUtil.equals(clientSecret, clientDetails.getClientSecret());
		}
		return false;
	}

}
