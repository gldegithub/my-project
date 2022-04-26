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
package org.leo.energy.auth.controller;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.leo.energy.auth.constant.AuthRequestParam;
import org.leo.energy.auth.base.TokenAuthInfo;
import org.leo.energy.auth.cache.CacheNames;
import org.leo.energy.auth.granter.ITokenGranter;
import org.leo.energy.auth.granter.TokenGranterFactory;
import org.leo.energy.auth.utils.TokenGenerator;
import org.leo.energy.common.support.Kv;
import org.leo.energy.common.utils.Func;
import org.leo.energy.common.utils.RedisUtil;
import org.leo.energy.common.utils.WebUtil;
import org.leo.energy.service.boot.api.R;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证模块
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private RedisUtil redisUtil;

	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<TokenAuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
								  @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
								  @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
								  @ApiParam(value = "账号") @RequestParam(required = false) String account,
								  @ApiParam(value = "密码") @RequestParam(required = false) String password) {
		int i = 1 / 0;
		Kv kv = Kv.init();
		kv.set(AuthRequestParam.tenantId, tenantId)
				.set(AuthRequestParam.account, account)
				.set(AuthRequestParam.password, password)
				.set(AuthRequestParam.grantType, grantType)
				.set(AuthRequestParam.refreshToken, refreshToken);
		ITokenGranter granter = TokenGranterFactory.getGranter(grantType);
		//先获取用户信息，然后组装TokenAuthInfo
		UserInfo userInfo = granter.grant(kv);
		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return R.fail(TokenGenerator.USER_NOT_FOUND);
		}
		String userType = Func.toStr(Objects.requireNonNull(
				WebUtil.getRequest()).getHeader(TokenGenerator.USER_TYPE_HEADER_KEY), TokenGenerator.WEB_USER_TYPE);
		userInfo.setUserType(userType);
		return R.data(TokenGenerator.createToken(userInfo));
	}

	@GetMapping("/captcha")
	@ApiOperation(value = "获取验证码")
	public R<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
		// 将key和base64返回给前端
		return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
	}
}
