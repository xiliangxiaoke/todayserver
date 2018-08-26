package cn.com.todayfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cn.com.todayfarm.dom.FarmInfo;
import cn.com.todayfarm.dom.FieldInfo;
import cn.com.todayfarm.dom.GrowthDataInfo;
import cn.com.todayfarm.dom.GrowthInfo;
import cn.com.todayfarm.dom.HumidDataInfo;
import cn.com.todayfarm.dom.HumidInfo;





public interface FieldMapper {

	@Insert("insert into TF_FIELD(FARM_ID,NAME,AREA,CROPTYPE,BOUNDRY) values(#{farmid},#{name},#{area},#{croptype},#{boundry})")
	int addfield(
			@Param("farmid")String farmid, 
			@Param("name")String name, 
			@Param("area")String area, 
			@Param("croptype")String croptype, 
			@Param("boundry")String boundry);

	@Insert("insert into TF_FARM (USER_ID,NAME,ADDRESS) VALUES (#{id},#{name},#{address})")
	int addfarm(
			@Param("id")Integer id, 
			@Param("name")String name, 
			@Param("address")String address);

	@Select("SELECT * FROM DB_TODAY_FARM.TF_FARM where USER_ID=#{id};")
	@Results({
		@Result(property="farmid",column="FARM_ID"),
		@Result(property="userid",column="USER_ID"),
		@Result(property="name",column="NAME"),
		@Result(property="address",column="ADDRESS")
	})
	List<FarmInfo> getfarms(@Param("id")Integer id);

	@Delete("delete from TF_FARM WHERE FARM_ID=#{farmid};")
	int delfarm(@Param("farmid")String farmid);

	@Select("SELECT a.* FROM TF_FIELD a,TF_FARM b where b.USER_ID=#{userid} AND a.FARM_ID=b.FARM_ID")
	@Results({
		@Result(property="fieldid",column="FIELD_ID"),
		@Result(property="farmid",column="FARM_ID"),
		@Result(property="name",column="NAME"),
		@Result(property="area",column="AREA"),
		@Result(property="croptype",column="CROPTYPE"),
		@Result(property="boundry",column="BOUNDRY"),
		@Result(property="rmGrowthImgId",column="RM_GROWTH_IMG_ID"),
		@Result(property="rmHumidImgId",column="RM_HUMID_IMG_ID")
	})
	List<FieldInfo> getfields(@Param("userid")Integer id);
	
	@Select("select * from TF_GROWTH_IMG where GROWTH_ID = #{growthId};")
	@Results({
	    @Result(property="growthid", column="GROWTH_ID"),
	    @Result(property="growthtime", column="GROWTH_TIME"),
	    @Result(property="fieldid", column="FIELD_ID"),
	    @Result(property="path", column="PATH"),
	    @Result(property="eleft", column="extentleft"),
	    @Result(property="ebottom", column="extentbottom"),
	    @Result(property="eright", column="extentright"),
	    @Result(property="etop", column="extenttop"),
	})
	List<GrowthInfo> getGrowthImages(@Param("growthId") int growthId);

	@Select("select * from TF_HUMID_IMG where HUMID_ID = #{humidId}")
	@Results({
	    @Result(property="himidid", column="HUMID_ID"),
	    @Result(property="humidtime", column="HUMID_TIME"),
	    @Result(property="fieldid", column="FIELD_ID"),
	    @Result(property="path", column="PATH"),
	    @Result(property="eleft", column="extentleft"),
	    @Result(property="ebottom", column="extentbottom"),
	    @Result(property="eright", column="extentright"),
	    @Result(property="etop", column="extenttop"),
	})
	List<HumidInfo> getHumidImages(@Param("humidId") int humidId);

	@Insert("insert into TF_GROWTH_IMG(GROWTH_TIME,FIELD_ID,PATH,extentleft,extentbottom,extentright,extenttop)\n" + 
			"values(#{growthtime},#{fieldid},#{path},#{eleft},#{ebottom},#{eright},#{etop})")
	@Options(useGeneratedKeys = true, keyProperty = "growthid")
	@Results({
		@Result(property="growthid",column="GROWTH_ID", id=true),
		@Result(property="growthtime",column="GROWTH_TIME"),
		@Result(property="path",column="PATH"),
		@Result(property="fieldid",column="FIELD_ID"),
		@Result(property="eleft",column="extentleft"),
		@Result(property="ebottom",column="extentbottom"),
		@Result(property="eright",column="extentright"),
		@Result(property="etop",column="extenttop")
	})
	int addGrowthImg(GrowthInfo info);

	@Update("update TF_FIELD SET RM_GROWTH_IMG_ID = #{growthid} WHERE FIELD_ID=#{fieldid}")
	void updateFieldRmGrowthImgId(
			@Param("fieldid")String fieldid, 
			@Param("growthid")int growthid);

	@Insert("insert TF_HUMID_IMG(HUMID_TIME,FIELD_ID,PATH,extentleft,extentbottom,extentright,extenttop)\n" + 
			"value(#{humidtime},#{fieldid},#{path},#{eleft},#{ebottom},#{eright},#{etop})")
	@Options(useGeneratedKeys = true, keyProperty = "himidid")
	@Results({
		@Result(property="himidid",column="GROWTH_ID", id=true),
		@Result(property="humidtime",column="GROWTH_TIME"),
		@Result(property="path",column="PATH"),
		@Result(property="fieldid",column="FIELD_ID"),
		@Result(property="eleft",column="extentleft"),
		@Result(property="ebottom",column="extentbottom"),
		@Result(property="eright",column="extentright"),
		@Result(property="etop",column="extenttop")
	})
	void addHumidImg(HumidInfo humidInfo);

	@Update("update TF_FIELD SET RM_HUMID_IMG_ID = #{himidid} WHERE FIELD_ID=#{fieldid}")
	void updateFieldRmHumidImgId(
			@Param("fieldid")String fieldid, 
			@Param("himidid")int himidid);

	@Select("SELECT a.PATH FROM DB_TODAY_FARM.TF_GROWTH_IMG a WHERE GROWTH_ID=#{id};")
	String getGrowthRelPath(@Param("id") int rmGrowthImgId);

	@Select("SELECT a.PATH FROM DB_TODAY_FARM.TF_HUMID_IMG a WHERE HUMID_ID=#{id};")
	String getHumidRelPath(@Param("id")int rmHumidImgId);

	@Insert("insert into TF_GROWTHDATA (FIELD_ID,DATADATE,DATAVALUE)VALUES(#{id},#{time},#{value})")
	void addGrowthData(
			@Param("id")String fieldid, 
			@Param("time")String datetime, 
			@Param("value")String datavalue);

	@Insert("insert into TF_HUMIDDATA (FIELD_ID,DATADATE,DATAVALUE)VALUES(#{id},#{time},#{value})")
	void addHumidData(
			@Param("id")String fieldid, 
			@Param("time")String datetime, 
			@Param("value")String datavalue);

	@Select("select * from TF_HUMIDDATA WHERE FIELD_ID=#{id} ORDER BY DATADATE ASC LIMIT 365")
	List<HumidDataInfo> getHumidData(@Param("id")String fieldid);

	@Select("select * from TF_GROWTHDATA WHERE FIELD_ID=#{id} ORDER BY DATADATE ASC LIMIT 365")
	List<GrowthDataInfo> getGrowthData(@Param("id")String fieldid);

	
//	int fieldid;
//	int farmid;
//	String name;
//	double area;
//	String croptype;
//	String boundry;
//	int rmGrowthImgId;
	
	

}
