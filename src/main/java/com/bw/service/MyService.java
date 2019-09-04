package com.bw.service;

import java.util.List;
import java.util.Map;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;

public interface MyService {
	//查询所有
	public List<Dianying> findAll(Map<String, Object> map);
	//查询类别表所有
	public List<Dept> findByDept();
	//添加
	public Integer add(Dianying dianying);

	public Dianying findBydid(Integer did);
	//修改
	public Integer update(Dianying dianying);
	//批量删除电影表
	public Integer plsc(String did);
}
