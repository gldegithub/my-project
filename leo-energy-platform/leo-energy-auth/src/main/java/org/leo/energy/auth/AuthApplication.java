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
package org.leo.energy.auth;


import org.leo.energy.common.constant.AppConstant;
import org.leo.energy.launch.LaunchApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 用户认证服务器
 *
 * @author Chill
 */
@EnableWebMvc
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = LaunchApplication.create(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args).run();
/*		ConfigurableEnvironment environment = run.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		propertySources.forEach(propertySource -> {
			String s = propertySource.toString();
			System.out.println(s);
		});
		PropertiesPropertySource systemProperties = (PropertiesPropertySource) propertySources.get("systemProperties");
		String[] propertyNames = systemProperties.getPropertyNames();
		for (String propertyName : propertyNames) {
			System.out.println(propertyName);
		}
		System.out.println(environment.getProperty("leo.energy.version"));
		System.out.println(environment.getProperty("spring.application.name"));
		//System.out.println(System.getProperty("leo.energy.version"));*/
	}

}
