package com.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

/**
 * @ClassName: BaseDao
 * @Description: TODO(DAO基类，其它DAO可以直接继承这个DAO，不但可以复用共用的方法，还可以获得泛型的好处。)
 * @author Victor.Chen chenld_fzu@163.com
 * @date Mar 24, 2012 4:08:57 PM
 * 
 * @param <T>
 * @param <PK>
 */
public class BaseDao<T, PK extends Serializable> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected Class<T> entityClass;
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;
    @Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 通过反射获取子类确定的泛型类
	 */
	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用的BaseDao构造函数. 在构造函数中定义对象类型Class. eg.
	 * BaseDao<User, Long> userDao = new BaseDao<User, Long>(User.class);
	 */
	public BaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 根据ID加载PO实例, 如果二级缓存中存在, 则读取二级缓存的数据
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T load(PK id) {
		Assert.notNull(id);

		if (logger.isDebugEnabled()) {
			logger.debug("load entity id[" + id + "], entityClass["
					+ entityClass + "]");
		}
		return (T) hibernateTemplate.load(entityClass, id);
	}

	/**
	 * 根据ID获取PO实例, 如果一级缓存不存在, 则从数据库中读取
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T get(PK id) {
		Assert.notNull(id);

		if (logger.isDebugEnabled()) {
			logger.debug("get entity id[" + id + "], entityClass["
					+ entityClass + "]");
		}
		T t = (T) hibernateTemplate.get(entityClass, id);

		return t;
	}

	/**
	 * 获取PO的所有对象
	 * 
	 * @return
	 */
	public List<T> loadAll() {

		List<T> lst = hibernateTemplate.loadAll(entityClass);

		if (logger.isDebugEnabled()) {
			logger.debug("load all entities[" + entityClass + "]...");
		}
		return lst;
	}

	/**
	 * 保存PO
	 * 
	 * @param entity
	 */
	public Serializable save(T entity) {
		Assert.notNull(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("save entity[" + entity.toString() + "], entityClass["
					+ entityClass + "]," + " information["
					+ ToStringBuilder.reflectionToString(entity) + "]");
		}
		return hibernateTemplate.save(entity);
	}

	/**
	 * 删除PO
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		Assert.notNull(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("delete entity[" + entity.toString()
					+ "], entityClass[" + entityClass + "], " + "information["
					+ ToStringBuilder.reflectionToString(entity) + "]");
		}
		hibernateTemplate.delete(entity);
	}

	/**
	 * 更新PO
	 * 
	 * @param entity
	 */
	public void update(T entity) {
		Assert.notNull(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("update entity[" + entity.toString()
					+ "], entityClass[" + entityClass + "]," + " information["
					+ ToStringBuilder.reflectionToString(entity) + "]");
		}
		hibernateTemplate.update(entity);
	}

	/**
	 * 保存或更新PO
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("saveOrUpdate entity[" + entity.toString()
					+ "], entityClass[" + entityClass + "], " + "information["
					+ ToStringBuilder.reflectionToString(entity) + "]");
		}
		hibernateTemplate.saveOrUpdate(entity);
	}
	/**
	 * 执行带参的HQL查询
	 * 
	 * @param sql
	 * @param params
	 * @return 查询结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... params) {
		Assert.hasText(hql);

		if (logger.isDebugEnabled()) {
			logger.debug("find entities[" + hql + "], entityClass["
					+ entityClass + "], " + "params["
					+ ToStringBuilder.reflectionToString(params) + "]");
		}

		List<T> lst = hibernateTemplate.find(hql, params);

		return lst;
	}

	 
	/**
	 * 取总记录数
	 * 
	 * @param hql
	 * @return
	 */
	public int getCount(String hql) {
		Integer count = (Integer) hibernateTemplate.find(hql).size();
		return count.intValue();
	}
	protected HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * 强烈建议JdbcTemplate 实例用于查询操作
	 * 
	 * @return
	 */

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	
	public List<Map<String,Object>> getTListWithLimit(String sql,int from,int amount){
		 List<Map<String,Object>> maps=(List<Map<String,Object>>)getJdbcTemplate().queryForList(sql,new Object[]{from,from+amount});
		 
		 return maps;
		
	}
	
	public int getTCount(String sql){
		return  jdbcTemplate.queryForInt(sql);
		
	}
	
}