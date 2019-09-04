package com.bw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;
import com.bw.service.MyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller

public class MyController {
	@Resource
	private MyService service;
	@RequestMapping("list.do")
	public String findAll(ModelMap modelMap,String dname,@RequestParam(defaultValue="1",required=false)Integer pageNum){
		//添加到map集合
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dname", dname);
		PageHelper pageHelper = new PageHelper();
		pageHelper.startPage(pageNum, 2);
		//分页
		List<Dianying> list = service.findAll(map);
		PageInfo<Dianying> pageInfo = new PageInfo<Dianying>(list);
		
		//添加到作用域中
		
		modelMap.addAttribute("page",pageInfo);
		modelMap.addAttribute("map", map);
		return "list";
		
	}
	
	@RequestMapping("findByDept.do")
	@ResponseBody
	public List<Dept> findByDept(){
		List<Dept> list = service.findByDept();
		return list;//返回一个list集合
	}
	
	@RequestMapping("add.do")
	@ResponseBody//添加电影
	public boolean add(Dianying dianying){
		System.out.println(dianying.toString());
		Integer i = service.add(dianying);
		if(i>0){
			return true;
			
		}else{
			return false;
		}
	}
	
	@RequestMapping("findBydid.do")
	@ResponseBody//修改的回显
	public Map<String, Object> findBydid(Integer did){
		Dianying dianying = service.findBydid(did);
		List<Dept> list = service.findByDept();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dianying", dianying);
		map.put("list", list);
		return map;
		
	}
	
	@RequestMapping("update.do")
	@ResponseBody//修改
	public boolean update(Dianying dianying){
		Integer i = service.update(dianying);
		if(i>0){
			return true;
			
		}else{
			return false;
		}
	}
	@RequestMapping("plsc.do")
	@ResponseBody//批量删除
	public boolean plsc(String did){
		Integer i = service.plsc(did);
		if(i>0){
			return true;
			
		}else{
			return false;
		}
	}
}
