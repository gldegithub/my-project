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
package org.leo.energy.service.boot.security.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leo.energy.service.boot.auth.AuthUser;
import org.leo.energy.common.utils.JsonUtil;
import org.leo.energy.common.utils.StringUtil;
import org.leo.energy.service.boot.api.R;
import org.leo.energy.service.boot.api.ResultCode;
import org.leo.energy.service.boot.security.SecureUtil;
import org.leo.energy.common.utils.WebUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 客户端校验
 *
 * @author Chill
 */
@Slf4j
public class ClientInterceptor implements AsyncHandlerInterceptor {

	private final String clientId;

	public ClientInterceptor(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		AuthUser user = SecureUtil.getUser();
		if (user != null && StringUtil.equals(clientId, SecureUtil.getClientIdFromHeader())
				&& StringUtil.equals(clientId, user.getClientId())) {
			return true;
		} else {
			log.warn("客户端认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(),
					WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			R result = R.fail(ResultCode.UN_AUTHORIZED);
			response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
			return false;
		}
	}

}
