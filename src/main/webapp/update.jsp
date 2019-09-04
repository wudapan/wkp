
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
		<input type="hidden" value="${param.did }" name="did">
		电影名称：<input type="text" name="dname" id="dname"><br><br>
		剧情介绍：<input type="text" name="desc" id="desc"><br><br>
		导演：<input type="text" name="daoyan" id="daoyan"><br><br>
		发行日期：<input type="date" name="startdate" id="startdate"><br><br>
		电影类型：<span id="tname"></span><br><br>
		<input type="button" value="修改" onclick="update()"> 
	</form>
	<script type="text/javascript">
		function update(){
			$.post("update.do",$("form").serialize(),function(data){
				if(data){
					alert("修改成功")
					location.href="list.do"
				}else{
					alert("修改失败")
				}
				
			},"json");
		}
	$(function(){
		$.post("findBydid.do",{"did":"${param.did}"},function(data){
			var dianying = data.dianying;
			var list = data.list;
			$("#dname").val(dianying.dname);
			$("#desc").val(dianying.desc);
			$("#daoyan").val(dianying.daoyan);
			$("#startdate").val(dianying.startdate);
			var str ="";
			for ( var i in list) {
				str+="<input type='checkbox' value='"+list[i].tid+"' name='tid'>"+list[i].tname;
			}
			$("#tname").append(str);

			$("[name='tid']value='"+dianying.tid+"'").prop("checked",true);
			
		},"json");
		
	})
	
	</script>
</body>
</html>