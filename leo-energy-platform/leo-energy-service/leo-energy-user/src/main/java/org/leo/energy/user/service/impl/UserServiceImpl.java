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
package org.leo.energy.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.leo.energy.boot.base.BaseServiceImpl;
import org.leo.energy.common.utils.*;
import org.leo.energy.service.boot.exception.ServiceException;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.mapper.UserMapper;
import org.leo.energy.user.service.IUserService;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private RedisUtil redisUtil;

	private static final String GUEST_NAME = "guest";
	private static final String MINUS_ONE = "-1";


	@Override
	public boolean submit(User user) {
		return false;
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user) {
		return null;
	}

	@Override
	public UserInfo userInfo(Long userId) {
		return null;
	}

	@Override
	public UserInfo userInfo(String tenantId, String account, String password) {
		return null;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		return false;
	}

	@Override
	public boolean resetPassword(String userIds) {
		return false;
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
		return false;
	}

	@Override
	public List<String> getRoleName(String roleIds) {
		return null;
	}

	@Override
	public boolean registerGuest(User user, Long oauthId) {
		return false;
	}

	@Override
	public boolean updateContainPWById(User user) {
		return false;
	}
}
