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
package org.leo.energy.service.boot.config;


import org.leo.energy.common.utils.SpringUtil;
import org.leo.energy.service.boot.executor.DefaultTaskExecutorCustomizer;
import org.leo.energy.service.boot.listener.ApiLogListener;
import org.leo.energy.service.boot.server.ServerInfo;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 工具配置类
 *
 * @author Chill
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE+10)
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ToolConfiguration implements WebMvcConfigurer {

	/**
	 * Spring上下文缓存
	 *
	 * @return SpringUtil
	 */
	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

	@Bean
	@ConditionalOnClass({DispatcherServlet.class})
	public ServerInfo serverInfo(ServerProperties serverProperties) {
		return new ServerInfo(serverProperties);
	}

	@Bean
	public ApiLogListener apiLogListener(ServerInfo serverInfo) {
		return new ApiLogListener(serverInfo);
	}

	@Bean
	public TaskExecutorCustomizer taskExecutorCustomizer() {
		return new DefaultTaskExecutorCustomizer();
	}

}
