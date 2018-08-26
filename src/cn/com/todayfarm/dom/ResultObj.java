package cn.com.todayfarm.dom;

import java.util.List;

/**
 * 请求数据返回的基本结构 code==200 正常返回
 * @author likunshang
 *
 * @param <T>
 */
public class ResultObj<T> {
	
	public int code = -1;
	public String msg;
	public T data;
	public List<T> list;
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	

}
