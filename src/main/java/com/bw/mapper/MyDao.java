package com.bw.mapper;

import java.util.List;
import java.util.Map;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;

public interface MyDao {
	//查询所有
	public List<Dianying> findAll(Map<String, Object> map);
//查询类别表所有
	public List<Dept> findByDept();
//添加
	public Integer add(Dianying dianying);

//中间表添加
	public void adddianying_dept(Map<String, Object> map);
//根据did查询返回对象

	public Dianying findBydid(Integer did);
//修改
	public Integer update(Dianying dianying);
//批量删除电影表
	public Integer plsc(String did);
//批量删除中间表
	public void del(String did);
}
