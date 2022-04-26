package org.leo.energy.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.leo.energy.boot.base.TenantEntity;

import java.util.Date;

/**
 * @Author:gonglong
 * @Date:2022/4/13 16:16
 */
@Data
@TableName("leo_energy_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户基本信息")
public class User extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    /**
     * 编号
     */
    @ApiModelProperty(value = "用户的编号信息")
    private String code;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户的昵称")
    private String name;
    /**
     * 真名
     */
    @ApiModelProperty(value = "用户的真实名称")
    private String realName;
    /**
     * 头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "用户email")
    private String email;
    /**
     * 手机
     */
    @ApiModelProperty(value = "用户手机号")
    private String phone;
    /**
     * 生日
     */
    @ApiModelProperty(value = "用户生日")
    private Date birthday;
    /**
     * 性别
     */
    @ApiModelProperty(value = "用户性别")
    private Integer sex;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "用户角色")
    private String roleId;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private String accountStatus;

}
