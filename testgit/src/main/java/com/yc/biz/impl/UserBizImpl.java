package com.yc.biz.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.yc.bean.User;
import com.yc.biz.UserBiz;

@Service
public class UserBizImpl implements UserBiz {

	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public User login(User user) {
		User u = sqlSession.selectOne("com.yc.dao.UserDaoMapper.login",user);
		return u;
	}

}
