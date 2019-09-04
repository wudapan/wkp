
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/index.css">
<link rel="stylesheet" href="css/index1.css">
<link rel="stylesheet" href="css/index3.css">
<link rel="stylesheet" href="css/index2.css">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
</head>
<body>
	<form action="">
		电影名称：<input type="text" name="dname"><br><br>
		剧情介绍：<input type="text" name="desc"><br><br>
		导演：<input type="text" name="daoyan"><br><br>
		发行日期：<input type="date" name="startdate"><br><br>
		电影类型：<span id="tname"></span><br><br>
		<input type="button" value="保存" onclick="add()"> 
	</form>
	<script type="text/javascript">
		function add(){
			$.post("add.do",$("form").serialize(),function(data){
				if(data){
					alert("添加成功")
					location.href="list.do"
				}else{
					alert("添加失败")
				}
				
			},"json");
		}
	
	
		$(function(){
			$.post("findByDept.do",function(data){
				var str ="";
				for ( var i in data) {
					str+="<input type='checkbox' value='"+data[i].tid+"' name='tid'>"+data[i].tname;
				}
				$("#tname").append(str);
			},"json");
			
		})
	
	</script>
</body>
</html>