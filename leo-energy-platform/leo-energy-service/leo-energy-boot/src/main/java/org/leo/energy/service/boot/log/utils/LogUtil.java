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

package org.leo.energy.service.boot.log.utils;


import org.leo.energy.common.utils.*;
import org.leo.energy.service.boot.log.model.LogAbstract;
import org.leo.energy.service.boot.log.model.LogError;
import org.leo.energy.service.boot.security.SecureUtil;
import org.leo.energy.service.boot.server.ServerInfo;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * Log 相关工具
 *
 * @author Chill
 */
public class LogUtil {

	/**
	 * 向log中添加server等信息
	 * @param serverInfo      服务信息
	 * @param logAbstract     日志基础类
	 */
	public static void populateBaseInfo(ServerInfo serverInfo, LogAbstract logAbstract) {
		if (serverInfo != null) {
			logAbstract.setServerHost(serverInfo.getHostName());
			logAbstract.setServerIp(serverInfo.getIp());
			logAbstract.setServerPort(serverInfo.getPort());
			logAbstract.setApplicationName(serverInfo.getAppName());
			logAbstract.setCreateTime(DateUtil.now());
		}
	}

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void populateRequestInfo(HttpServletRequest request, @NonNull LogAbstract logAbstract) {
		if (request != null) {
			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
			logAbstract.setRemoteIp(WebUtil.getIP(request));
			logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
			logAbstract.setMethod(request.getMethod());
			logAbstract.setParams(WebUtil.getRequestParamString(request) == null
					? StringPool.EMPTY : WebUtil.getRequestParamString(request));
			if (SecureUtil.getUser() != null) {
				logAbstract.setCreateBy(SecureUtil.getUser().getUserId());
			}
		}
	}

	/**
	 *
	 * @param error 错误异常信息
	 * @param logError 错误异常类
	 */
	public static void populateErrorInfo(Throwable error, @NonNull LogError logError) {
		if (error!=null) {
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
		}
	}
}
