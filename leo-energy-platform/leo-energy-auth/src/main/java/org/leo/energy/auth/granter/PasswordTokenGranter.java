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
import org.leo.energy.common.support.Kv;
import org.leo.energy.common.utils.DigestUtil;
import org.leo.energy.common.utils.Func;
import org.leo.energy.service.boot.security.provider.ClientDetails;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	private JdbcTemplate jdbcTemplate;

	public static final String GRANT_TYPE = "password";


	@Override
	public UserInfo grant(Kv kv) {
		String tenantId = kv.getStr(AuthRequestParam.tenantId);
		String account = kv.getStr(AuthRequestParam.account);
		String password = kv.getStr(AuthRequestParam.password);
		UserInfo userInfo = null;
		if (Func.isNoneBlank(tenantId,account, password)) {
			password = DigestUtil.encrypt(password);
			String sql = " select * from leo_energy_user where account = ? and password = ? and tenant_id = ? and is_deleted = 0 ";
			User user = jdbcTemplate.queryForObject(
					sql, new BeanPropertyRowMapper<>(User.class),account,password,tenantId);
			userInfo = new UserInfo();
			userInfo.setUser(user);
		}
		return userInfo;
	}

}
