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

package org.leo.energy.service.boot.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leo.energy.common.constant.RoleConstant;
import org.leo.energy.common.utils.JsonUtil;
import org.leo.energy.service.boot.log.constant.EventConstant;
import org.leo.energy.service.boot.log.event.ErrorLogEvent;
import org.leo.energy.service.boot.log.model.LogError;
import org.leo.energy.service.boot.log.utils.LogUtil;
import org.leo.energy.service.boot.security.annotation.PreAuth;
import org.leo.energy.service.boot.server.ServerInfo;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;


/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

	private final ServerInfo serverInfo;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public void saveApiLog(ErrorLogEvent event) {
		LogError logError = (LogError) event.getSource();
		LogUtil.populateBaseInfo(serverInfo, logError);
		log.info(JsonUtil.toJson(logError));
	}
}
