package com.bw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;
import com.bw.mapper.MyDao;
@Service
public class MyServiceImp implements MyService{
	@Resource 
	private MyDao dao;
	public List<Dianying> findAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.findAll(map);
	}
	public List<Dept> findByDept() {
		// TODO Auto-generated method stub
		return dao.findByDept();
	}
	public Integer add(Dianying dianying) {
		Integer add = dao.add(dianying);
		Integer did = dianying.getDid();
		System.out.println(did);
		
		String[] split = dianying.getTid().split(",");
		for (String string : split) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("did", did);
			map.put("tid", string);
			dao.adddianying_dept(map);
			map.clear();
		}
		
		
		
		return 1;
	}
	public Dianying findBydid(Integer did) {
		// TODO Auto-generated method stub
		return dao.findBydid(did);
	}
	public Integer update(Dianying dianying) {
		// TODO Auto-generated method stub
		return dao.update(dianying);
	}
	public Integer plsc(String did) {
		dao.plsc(did);
		dao.del(did);
		return 1;
	}

}
