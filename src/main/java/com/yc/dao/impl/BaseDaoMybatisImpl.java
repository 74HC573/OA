package com.yc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.yc.dao.BaseDao;

/**
 * sqlId:全是调用的各个mapper文件中的 方法名
 * @param <T>
 */
@Repository(value = "baseDao")
public class BaseDaoMybatisImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {

	private final String MAPPERPATH = "com.yc.bean.";

	// 重写了父类 SqlSessionDaoSupport 方法实现注入 sqlSessionTemplate
	// 为什么要重写? 如果不重写的话，则需要 xml配置spring
	@Resource(name = "sqlSession")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public void save(T t, String sqlId) {
		super.getSqlSession().insert(MAPPERPATH + t.getClass().getSimpleName() + "Mapper." + sqlId, t);
	}

	@Override
	public void save(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		super.getSqlSession().insert(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, parameterMap);
	}
	
	@Override
	public void save(Class<T> clazz, String sqlId, List<T> list) {
		super.getSqlSession().insert(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, list);
	}

	@Override
	public void update(Class<T> clazz, String sqlId) {
		super.getSqlSession().update(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId);
	}
	
	@Override
	public void update(T t, String sqlId) {
		super.getSqlSession().update(MAPPERPATH + t.getClass().getSimpleName() + "Mapper." + sqlId, t);
	}

	@Override
	public void update(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		super.getSqlSession().update(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, parameterMap);
	}
	
	@Override
	public void update(Class<T> clazz, String sqlId, List<T> list) {
		super.getSqlSession().update(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, list);
	}

	@Override
	public void del(Class<T> clazz, String sqlId, String id) {
		super.getSqlSession().delete(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId,id);
	}
	@Override
	public void del(T t, String sqlId) {
		super.getSqlSession().delete(MAPPERPATH + t.getClass().getSimpleName() + "Mapper." + sqlId,t);
	}

	@Override
	public void del(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		super.getSqlSession().delete(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, parameterMap);
	}

	@Override
	public void del(Class<T> clazz, String sqlId, List<Object> ids) {
		super.getSqlSession().delete(MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId, ids);
	}

	@Override
	public List<T> findAll(Class<T> clazz, String sqlId) {
		return super.getSqlSession().selectList(  MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId );
	}

	@Override
	public List<T> findAll(T t, String sqlId) {
		return super.getSqlSession().selectList(  MAPPERPATH + t.getClass().getSimpleName() + "Mapper." + sqlId ,t);
	}

	@Override
	public List<T> findAll(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		                                     // com.yc.bean.     HouseMapper.findHouseCondition
		return super.getSqlSession().selectList(  MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId ,parameterMap);
	}
	
	@Override
	public T findOne(Class<T> clazz, String sqlId) {
		List<T> list= super.getSqlSession().selectList(  MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId );
		if( list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public T findOne(T t, String sqlId) {
		List<T> list=super.getSqlSession().selectList(  MAPPERPATH + t.getClass().getSimpleName() + "Mapper." + sqlId ,t);
		if( list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public T findOne(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		List<T> list=super.getSqlSession().selectList(  MAPPERPATH + clazz.getSimpleName() + "Mapper." + sqlId ,parameterMap);
		if( list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	

	@Override
	public double getFunc(Class<T> clazz, String sqlId) {
		return super.getSqlSession().selectOne(MAPPERPATH+clazz.getSimpleName()+ "Mapper." + sqlId );
	}

	@Override
	public double getFunc(T t, String sqlId) {
		return super.getSqlSession().selectOne(MAPPERPATH+t.getClass().getSimpleName()+ "Mapper." + sqlId ,t);
	}

	@Override
	public double getFunc(Class<T> clazz, String sqlId, Map<String, Object> parameterMap) {
		return new Double( super.getSqlSession().selectOne(MAPPERPATH+clazz.getSimpleName()+ "Mapper." + sqlId ,parameterMap).toString());
	}

	
	


}
