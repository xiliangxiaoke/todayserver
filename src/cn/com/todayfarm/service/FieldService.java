package cn.com.todayfarm.service;

import java.util.List;

import cn.com.todayfarm.dom.FarmInfo;
import cn.com.todayfarm.dom.FieldInfo;
import cn.com.todayfarm.dom.GrowthDataInfo;
import cn.com.todayfarm.dom.GrowthInfo;
import cn.com.todayfarm.dom.HumidDataInfo;
import cn.com.todayfarm.dom.HumidInfo;

/**
 * 底层服务层 农田
 * @author likunshang
 *
 */
public interface FieldService {

	/**
	 * 
	 * @param farmid
	 * @param name
	 * @param area
	 * @param croptype
	 * @param boundry
	 * @return
	 */
	int addfield(String farmid, String name, String area, String croptype, String boundry);

	/**
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @return
	 */
	int addfarm(Integer id, String name, String address);

	/**
	 * 
	 * @param id
	 * @return
	 */
	List<FarmInfo> getfarms(Integer id);

	/**
	 * 
	 * @param farmid
	 * @return
	 */
	int delfarm(String farmid);

	/**
	 * 
	 * @param id user id
	 * @return
	 */
	List<FieldInfo> getfields(Integer id);
	
	List<GrowthInfo> getGrowthImages(int growthId);
	List<HumidInfo> getHumidImages(int humidId);

	/**
	 * 
	 * @param createtime
	 * @param fieldid
	 * @param savepath
	 * @param extentleft
	 * @param extentbottom
	 * @param extentright
	 * @param extenttop
	 */
	void addFieldGrowthImg(String createtime, String fieldid, String savepath, String extentleft, String extentbottom,
			String extentright, String extenttop);

	/**
	 * 
	 * @param createtime
	 * @param fieldid
	 * @param savepath
	 * @param extentleft
	 * @param extentbottom
	 * @param extentright
	 * @param extenttop
	 */
	void addFieldHumidImg(String createtime, String fieldid, String savepath, String extentleft, String extentbottom,
			String extentright, String extenttop);

	
	String getGrowthRelativePath(int rmGrowthImgId);

	String getHumidRelativePath(int rmHumidImgId);

	void addGrowthData(String fieldid, String datetime, String datavalue);

	void addHumidData(String fieldid, String datetime, String datavalue);

	List<HumidDataInfo> getHumidData(String fieldid);

	List<GrowthDataInfo> getGrowthData(String fieldid);
	
}
