/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leo.energy.auth.granter;

import lombok.AllArgsConstructor;
import org.leo.energy.auth.constant.AuthRequestParam;
import org.leo.energy.auth.cache.CacheNames;
import org.leo.energy.auth.utils.TokenGenerator;
import org.leo.energy.common.support.Kv;
import org.leo.energy.common.utils.*;
import org.leo.energy.service.boot.exception.ServiceException;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private RedisUtil redisUtil;

	@Override
	public UserInfo grant(Kv kv) {
		HttpServletRequest request = Objects.requireNonNull(WebUtil.getRequest());
		//获取captcha头信息
		String key = request.getHeader(TokenGenerator.CAPTCHA_HEADER_KEY);
		String code = request.getHeader(TokenGenerator.CAPTCHA_HEADER_CODE);
		// 获取验证码
		String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
		// 判断验证码
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new ServiceException(TokenGenerator.CAPTCHA_NOT_CORRECT);
		}
		String tenantId = kv.getStr(AuthRequestParam.tenantId);
		String account = kv.getStr(AuthRequestParam.account);
		String password = kv.getStr(AuthRequestParam.password);
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
		}
		return userInfo;
	}

}
