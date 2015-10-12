package com.common.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.common.util.EntityReflect;

/**
 * 请求对象 通过实例化时产生该HQL语句
 * 
 * 实例化时传入search对象、当前页与页面最大数据条数�?
 * 
 * search对象是一个查询实体，通过EntityReflect工具类转换为HQL查询语句
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public class PageRequest<T extends Serializable> {

	private int offset;// 起始索引
	private int max;// �?��数据�?
	private String hql;// 查询语句

	public PageRequest(T search, int currentPage, int max) {

		try {
			hql = EntityReflect.reflectForHql(search);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		this.max = max;
		this.offset = (currentPage - 1) * max;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

}
