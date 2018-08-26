package cn.com.todayfarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.todayfarm.dom.FarmInfo;
import cn.com.todayfarm.dom.FieldInfo;
import cn.com.todayfarm.dom.GrowthDataInfo;
import cn.com.todayfarm.dom.GrowthInfo;
import cn.com.todayfarm.dom.HumidDataInfo;
import cn.com.todayfarm.dom.HumidInfo;
import cn.com.todayfarm.mapper.FieldMapper;
import cn.com.todayfarm.service.FieldService;

@Service("fieldService")
public class FieldServiceImpl implements FieldService {
	
	@Autowired
	private FieldMapper fieldMapper;

	@Override
	public int addfield(String farmid, String name, String area, String croptype, String boundry) {
		// TODO Auto-generated method stub
		
		
		return fieldMapper.addfield(farmid,name,area,croptype,boundry);
	}

	@Override
	public int addfarm(Integer id, String name, String address) {
		// TODO Auto-generated method stub
		return fieldMapper.addfarm(id,name,address);
	}

	@Override
	public List<FarmInfo> getfarms(Integer id) {
		// TODO Auto-generated method stub
		return fieldMapper.getfarms(id);
	}

	@Override
	public int delfarm(String farmid) {
		// TODO Auto-generated method stub
		return fieldMapper.delfarm(farmid);
	}

	@Override
	public List<FieldInfo> getfields(Integer id) {
		// TODO Auto-generated method stub
		return fieldMapper.getfields(id);
	}

	@Override
	public void addFieldGrowthImg(String createtime, String fieldid, String savepath, String extentleft,
			String extentbottom, String extentright, String extenttop) {
		// TODO Auto-generated method stub
		
		GrowthInfo grouthinfo = 
				new GrowthInfo(0, Integer.parseInt(createtime) , Integer.parseInt(fieldid) , savepath, extentleft, extentbottom, extentright, extenttop);
		
		//tf_growth_img表中添加记录
		fieldMapper.addGrowthImg(grouthinfo);
		System.out.println("return id: "+ grouthinfo.getGrowthid());
		//tf_field表中更新rm_growth_img_id
		fieldMapper.updateFieldRmGrowthImgId(fieldid,grouthinfo.getGrowthid());
		
	}

	@Override
	public void addFieldHumidImg(String createtime, String fieldid, String savepath, String extentleft,
			String extentbottom, String extentright, String extenttop) {
		// TODO Auto-generated method stub
		HumidInfo humidInfo = 
				new HumidInfo(0, Integer.parseInt(createtime) , Integer.parseInt(fieldid), 
						savepath, extentleft, extentbottom, extentright, extenttop);
		
		fieldMapper.addHumidImg(humidInfo);
		fieldMapper.updateFieldRmHumidImgId(fieldid,humidInfo.getHimidid());
	}

	@Override
	public String getGrowthRelativePath(int rmGrowthImgId) {
		// TODO Auto-generated method stub
		return fieldMapper.getGrowthRelPath(rmGrowthImgId);
	}

	@Override
	public String getHumidRelativePath(int rmHumidImgId) {
		// TODO Auto-generated method stub
		return fieldMapper.getHumidRelPath(rmHumidImgId);
	}

	@Override
	public void addGrowthData(String fieldid, String datetime, String datavalue) {
		// TODO Auto-generated method stub
		fieldMapper.addGrowthData(fieldid,datetime,datavalue);
	}

	@Override
	public void addHumidData(String fieldid, String datetime, String datavalue) {
		// TODO Auto-generated method stub
		fieldMapper.addHumidData(fieldid,datetime,datavalue);
	}

	@Override
	public List<HumidDataInfo> getHumidData(String fieldid) {
		// TODO Auto-generated method stub
		return fieldMapper.getHumidData(fieldid);
	}

	@Override
	public List<GrowthDataInfo> getGrowthData(String fieldid) {
		// TODO Auto-generated method stub
		return fieldMapper.getGrowthData(fieldid);
	}

	@Override
	public List<GrowthInfo> getGrowthImages(int growthId) {
		// TODO Auto-generated method stub
		return fieldMapper.getGrowthImages(growthId);
	}

	@Override
	public List<HumidInfo> getHumidImages(int humidId) {
		// TODO Auto-generated method stub
		return fieldMapper.getHumidImages(humidId);
	}
	
	

}
