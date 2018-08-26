package cn.com.todayfarm.service;

import cn.com.todayfarm.dom.User;

/**
 * 服务层接口
 * @author likunshang
 *
 */
public interface UserService {
	
	/**
	 * 用户注册
	 * @param name
	 * @param pwmd5
	 * @param token
	 * @return
	 */
	int register(String name,String pwmd5,String token);

	/**
	 * 用户登陆
	 * @param loginname
	 * @param passwordmd5
	 * @return
	 */
	User login(String loginname,String passwordmd5);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	User getUserByToken(String token);
	
	User getPwByToken(String token);
	
	void updateUserById(String id,String address,String company,String job,String phone,String sex);

	/**
	 * 设置新密码
	 * @param newpw
	 * @param id
	 */
	void setNewPassword(String newpw, Integer id);
}
