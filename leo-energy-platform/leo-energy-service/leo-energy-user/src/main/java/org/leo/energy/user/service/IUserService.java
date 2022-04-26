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
package org.leo.energy.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.leo.energy.boot.base.BaseService;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.vo.UserInfo;
import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);


	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, String password);


	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);


	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 更新包括密码
	 * @param user
	 * @return
	 */
	boolean updateContainPWById(User user);


}
