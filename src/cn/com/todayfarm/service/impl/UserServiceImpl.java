package cn.com.todayfarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.todayfarm.dom.User;
import cn.com.todayfarm.mapper.UserMapper;
import cn.com.todayfarm.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User login(String loginname, String password) {
		List<User> list =  userMapper.findWithLoginnameAndPassword(loginname, password);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public int register(String name, String pwmd5, String token) {
		// TODO Auto-generated method stub
		return userMapper.registerNewUser(name, pwmd5, token);
	}

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		
		List<User> list =  userMapper.selectUserByToken(token);
		
		if(list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void updateUserById(String id, String address, String company, String job, String phone, String sex) {
		// TODO Auto-generated method stub
		userMapper.updateUserInfo(sex, company, job, address, phone, id);
	}

	@Override
	public void setNewPassword(String newpw, Integer id) {
		// TODO Auto-generated method stub
		userMapper.setNewPassword(newpw,id);
	}

	@Override
	public User getPwByToken(String token) {
		// TODO Auto-generated method stub
		List<User> list =   userMapper.getPwByToken(token);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
}
