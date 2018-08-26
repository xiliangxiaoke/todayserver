package cn.com.todayfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.todayfarm.dom.User;

public interface UserMapper {
	
	
	@Insert("insert into TF_USERS(NAME,PASSWORD_MD5,TOKEN) VALUES(#{name},#{passwordmd5},#{token})")
	int registerNewUser(
			@Param("name") String name,
			@Param("passwordmd5") String passwordmd5,
			@Param("token") String token
			);
	
	
	
	
	

	@Select("select * from TF_USERS where NAME = #{loginname} and PASSWORD_MD5 = #{password}")
	@Results({
		@Result(property="address",column="ADDRESS"),
		@Result(property="company",column="COMPANY"),
		@Result(property="job",column="JOB"),
		@Result(property="phone",column="PHONE"),
		@Result(property="sex",column="SEX"),
		@Result(property="token",column="TOKEN"),
		@Result(property="username",column="NAME")
	})
	List<User> findWithLoginnameAndPassword(@Param("loginname")String loginname,
			@Param("password")String password);
	
	
	
	
	@Select("select * from TF_USERS where TOKEN = #{token} ")
	@Results({
		@Result(property="address",column="ADDRESS"),
		@Result(property="company",column="COMPANY"),
		@Result(property="job",column="JOB"),
		@Result(property="phone",column="PHONE"),
		@Result(property="sex",column="SEX"),
		@Result(property="token",column="TOKEN"),
		@Result(property="username",column="NAME"),
		@Result(property="id",column="USER_ID")
	})
	List<User> selectUserByToken(@Param("token")String token);
	
	
	@Select("select USER_ID,PASSWORD_MD5 from TF_USERS where TOKEN = #{token} ")
	@Results({
		@Result(property="password",column="PASSWORD_MD5"),
		@Result(property="id",column="USER_ID")
	})
	List<User>  getPwByToken(@Param("token")String token);
	
	
	@Update("update TF_USERS SET SEX=#{sex} ,COMPANY=#{company},JOB=#{job},ADDRESS=#{address},PHONE=#{phone} WHERE USER_ID = #{id}")
	void updateUserInfo(
			@Param("sex") String sex,
			@Param("company") String company,
			@Param("job") String job,
			@Param("address") String address,
			@Param("phone") String phone,
			@Param("id") String id
			);






	@Update("update TF_USERS SET PASSWORD_MD5=#{newpw} WHERE USER_ID = #{id}")
	void setNewPassword(
			@Param("newpw")String newpw, 
			@Param("id")Integer id
			);
	
}
