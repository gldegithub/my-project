package org.leo.energy.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.leo.energy.service.boot.api.R;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.service.IUserService;
import org.leo.energy.user.wrapper.UserWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:gonglong
 * @Date:2022/4/18 15:08
 */
@RestController
@AllArgsConstructor
@Api(value = "用户", tags = "用户信息相关接口")
public class UserController {

    private IUserService userService;

    @PostMapping("detail")
    @ApiOperation(value = "获取用户详情", notes = "根据用户id等字段查询")
    public R detail(@RequestBody User user) {
        User detail = userService.getOne(Wrappers.<User>query(user));
        return R.data(UserWrapper.build().entityVO(detail));
    }
}
