package com.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.news.dto.ResultData;
import com.news.entity.User;
import com.news.service.UserService;
import com.news.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
@Api(tags = "UserInterfaceApi", description = "�û������ӿ�")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * �û�Token��¼
	 * 
	 * @param login_id
	 * @return
	 */
	@ApiOperation(value = "�û���¼", notes = "���ݱ����û��������¼")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "login_id", value = "�û��˺�", required = true, dataType = "String", paramType = "query", example = "admin"),
			@ApiImplicitParam(name = "token", value = "�û�token", required = true, dataType = "String", paramType = "query", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbl9pZCI6ImFkbWluIn0.x6TX6mpL1Xs0cPoHfIagN9S6r63AeBFq9yJJhUdbdnw") })
	@RequestMapping(value = "/loginToken", method = RequestMethod.POST)
	public Object loginByToken(@RequestParam(value = "login_id") String login_id) {
		Assert.notNull(login_id, "��¼�˺Ų���Ϊ��");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(User::getLoginId, login_id);
		User user = userService.getOne(queryWrapper);
		if (user == null) {
			return ResultData.failed("��¼ʧ��");
		}
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda().eq(User::getLoginId, login_id).set(User::getLastTime, LocalDateTime.now());
		userService.update(updateWrapper);
		user.setToken(JWTUtil.sign(user.getLoginId()));
		return ResultData.success(user, "��½�ɹ�");
	}

	/**
	 * �û���¼
	 * 
	 * @param login_id
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "�û���¼", notes = "���ݱ����û��������¼")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "login_id", value = "�û��˺�", required = true, dataType = "String", paramType = "query", example = "admin"),
			@ApiImplicitParam(name = "password", value = "�û�����", required = true, dataType = "String", paramType = "query", example = "123456") })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(@RequestParam(value = "login_id") String login_id,
			@RequestParam(value = "password") String password) {
		Assert.notNull(login_id, "��¼�˺Ų���Ϊ��");
		Assert.notNull(password, "��¼���벻��Ϊ��");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(User::getLoginId, login_id).eq(User::getPassWord, password);
		User user = userService.getOne(queryWrapper);
		if (user == null) {
			return ResultData.failed("��¼ʧ��");
		}
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda().eq(User::getLoginId, login_id).set(User::getLastTime, LocalDateTime.now());
		userService.update(updateWrapper);
		user.setToken(JWTUtil.sign(user.getLoginId()));
		return ResultData.success(user, "��½�ɹ�");
	}

	/**
	 * ����ID��ѯ�û�
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "��ȡ�û���ϸ��Ϣ", notes = "����url��user_id����ȡ�û���ϸ��Ϣ")
	@ApiImplicitParam(name = "user_id", value = "�û�ID", required = true, dataType = "Integer", paramType = "path", example = "1")
	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public Object getUserById(@PathVariable(value = "user_id") Integer user_id) {
		Assert.notNull(user_id, "�û�user_id����Ϊ��");
		return ResultData.success(userService.getById(user_id), "��ѯ�ɹ�");
	}

	/**
	 * ��ѯ�û��б�
	 * 
	 * @return
	 */
	@ApiOperation(value = "��ȡ�û��б�", notes = "��ȡ�û��б�")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Object getUserList() {
		return ResultData.success(userService.getBaseMapper().selectList(null), "��ѯ�ɹ�");
	}

	/**
	 * ����idɾ���û�
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "ɾ���û�", notes = "����url��id��ָ��ɾ���û�")
	@ApiImplicitParam(name = "user_id", value = "�û�ID", required = true, dataType = "Long", paramType = "path", example = "1")
	@RequestMapping(value = "/{user_id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable(value = "user_id") Integer user_id) {
		Assert.notNull(user_id, "�û�user_id����Ϊ��");
		return ResultData.success(userService.getBaseMapper().deleteById(user_id), "ɾ���ɹ�");
	}
}