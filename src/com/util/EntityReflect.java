package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author HeHangjie
 * 
 */
public class EntityReflect {
	/**
	 * 转换有值的属性为HQL查询语句
	 * 
	 * 各属性间的查询语句以AND连接
	 * 
	 * 默认String类型属性为like语句 , 其它类型为=语句
	 * 
	 * 未考虑处理日期期间的问题
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

		// 获取实体类的所有属性
		Field[] field = entity.getClass().getDeclaredFields();

		// 第一个字段是serialVersionUID，第二个是主键，不做
		for (int i = 2; i < field.length; i++) {
			// 获取属性名字
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

				// 获取属性类型
				String type = field[i].getGenericType().toString();
				// 这里只支持String与非String，日期类型如何处理需要考虑
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
