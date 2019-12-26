package com.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
	private String token;

	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;

	@TableField("login_id")
	private String loginId;

	@TableField("password")
	private String passWord;

	@TableField("state")
	private String state;

	@TableField
	private String sex;

	@TableField
	private String name;

	@TableField
	private String qq;

	@TableField
	private String email;

	@TableField
	private String mobile;

	@TableField
	private String images;

	@TableField
	private String address;

	@TableField("last_time")
	private LocalDateTime lastTime;

	@TableField("create_by")
	private String createBy;

	@TableField("create_time")
	private LocalDateTime createTime;

	@TableField("update_by")
	private String updateBy;

	@TableField("update_time")
	private LocalDateTime updateTime;
}
