package cn.com.todayfarm.dom;

import java.util.List;

public class FieldInfo {
	
	int fieldid;
	int farmid;
	String name;
	double area;
	String croptype;
	String boundry;
	int rmGrowthImgId;
	int rmHumidImgId;
	
	//实时影像图片地址
	String growthImgPath;
	String humidImgPath;
	
	List<HumidInfo> humidInfo;
	List<GrowthInfo> growthInfo;
	
	
	
	
	public String getGrowthImgPath() {
		return growthImgPath;
	}
	public void setGrowthImgPath(String growthImgPath) {
		this.growthImgPath = growthImgPath;
	}
	public String getHumidImgPath() {
		return humidImgPath;
	}
	public void setHumidImgPath(String humidImgPath) {
		this.humidImgPath = humidImgPath;
	}
	public int getRmHumidImgId() {
		return rmHumidImgId;
	}
	public void setRmHumidImgId(int rmHumidImgId) {
		this.rmHumidImgId = rmHumidImgId;
	}
	public int getFieldid() {
		return fieldid;
	}
	public void setFieldid(int fieldid) {
		this.fieldid = fieldid;
	}
	public int getFarmid() {
		return farmid;
	}
	public void setFarmid(int farmid) {
		this.farmid = farmid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String getCroptype() {
		return croptype;
	}
	public void setCroptype(String croptype) {
		this.croptype = croptype;
	}
	public String getBoundry() {
		return boundry;
	}
	public void setBoundry(String boundry) {
		this.boundry = boundry;
	}
	public int getRmGrowthImgId() {
		return rmGrowthImgId;
	}
	public void setRmGrowthImgId(int rmGrowthImgId) {
		this.rmGrowthImgId = rmGrowthImgId;
	}
	
	public void setHumidInfo(List<HumidInfo> info) {
		humidInfo = info;
	}
	public List<HumidInfo> getHumidInfo() {
		return humidInfo;
	}
	
	public void setGrowthInfo(List<GrowthInfo> info) {
		growthInfo = info;
	}
	
	public List<GrowthInfo> getGrowthInfo() {
		return growthInfo;
	}
	
	
	
}
