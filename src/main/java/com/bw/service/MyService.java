package com.bw.service;

import java.util.List;
import java.util.Map;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;

public interface MyService {
	//��ѯ����
	public List<Dianying> findAll(Map<String, Object> map);
	//��ѯ��������
	public List<Dept> findByDept();
	//���
	public Integer add(Dianying dianying);

	public Dianying findBydid(Integer did);
	//�޸�
	public Integer update(Dianying dianying);
	//����ɾ����Ӱ��
	public Integer plsc(String did);
}
