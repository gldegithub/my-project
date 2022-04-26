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

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.leo.energy.common.constant.TokenConstant;
import org.leo.energy.common.support.Kv;
import org.leo.energy.common.utils.Func;
import org.leo.energy.service.boot.security.SecureUtil;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	@Override
	public UserInfo grant(Kv kv) {
		String grantType = kv.getStr("grantType");
		String refreshToken = kv.getStr("refreshToken");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken)
				&& GRANT_TYPE.equals(grantType)) {
			Claims claims = SecureUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get("token_type"));
			if (tokenType.equals(GRANT_TYPE)) {

			}
		}
		return userInfo;
	}
}
