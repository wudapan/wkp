package com.bw.mapper;

import java.util.List;
import java.util.Map;

import com.bw.entity.Dept;
import com.bw.entity.Dianying;

public interface MyDao {
	//��ѯ����
	public List<Dianying> findAll(Map<String, Object> map);
//��ѯ��������
	public List<Dept> findByDept();
//���
	public Integer add(Dianying dianying);

//�м�����
	public void adddianying_dept(Map<String, Object> map);
//����did��ѯ���ض���

	public Dianying findBydid(Integer did);
//�޸�
	public Integer update(Dianying dianying);
//����ɾ����Ӱ��
	public Integer plsc(String did);
//����ɾ���м��
	public void del(String did);
}
