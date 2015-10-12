package com.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具�?
 * 
 * @author HeHangjie
 * 
 */
public class EntityReflect {
	/**
	 * 转换有�?的属性为HQL查询语句
	 * 
	 * 各属性间的查询语句以AND连接
	 * 
	 * 默认String类型属�?为like语句 , 其它类型�?语句
	 * 
	 * 未�?虑处理日期期间的问题
	 * 
	 * @param entity
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static String reflectForHql(Object entity) throws SecurityException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		StringBuffer hql = new StringBuffer("from "
				+ entity.getClass().getSimpleName() + " where 1=1 ");

		// 获取实体类的�?��属�?
		Field[] field = entity.getClass().getDeclaredFields();

		// 第一个字段是serialVersionUID，第二个是主键，不做
		for (int i = 2; i < field.length; i++) {
			// 获取属�?名字
			String name = field[i].getName();
			String getName = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			Method m;
			Object value;

			try {
				m = entity.getClass().getMethod(getName);
				value = m.invoke(entity);

				if (value == null) {
					continue;
				}

				// 获取属�?类型
				String type = field[i].getGenericType().toString();
				// 这里只支持String与非String，日期类型如何处理需要�?�?
				if (type.equals("class java.lang.String")) {
					hql.append(" and " + name + " like '%" + value + "%' ");
				}
				if (type.equals("double")) {
					hql.append(" and " + name + " >= " + value + " ");
				} else {
					hql.append(" and " + name + " = " + value + " ");
				}
			} catch (NoSuchMethodException e) {
				continue;
			}

		}

		return hql.toString();
	}

}
