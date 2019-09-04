
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
<form action="list.do">
	电影名称：<input type="text" name="dname">
	<input type="submit" value="查询">
</form>
	<table>
		<tr>
			<td colspan="10">
				<a href="add.jsp"><input type="button" value="添加"></a>
				<input type="button" value="批量删除" onclick="plsc()">
			</td>
			
		</tr>
		
		<tr>
			<td>
				<input type="checkbox"  id="qx">全选/全不选
				<input type="checkbox"  id="fx">反选
			</td>
			<td>编号</td>
			<td>电影名称</td>
			<td>剧情介绍</td>
			<td>导演</td>
			<td>发行日期</td>
			<td>电影类型</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${page.list }" var="h">
			<tr>
				<td>
					<input type="checkbox" value="${h.did }" class="did">
				</td>
				<td>${h.did }</td>
				<td>${h.dname }</td>
				<td>${h.desc }</td>
				<td>${h.daoyan }</td>
				<td>${h.startdate }</td>
				<td>${h.tname }</td>
				<td>
					<a href="update.jsp?did=${h.did }"><input type="button" value="修改"></a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10">
				共：${page.pages}页
				<a href="?pageNum=1">首页</a>
				<a href="?pageNum=${page.pageNum-1 <1 ?page.pageNum:page.pageNum-1 }&dname=${map.dname}">上一页</a>
				<a href="?pageNum=${page.pageNum+1 >page.pages ?page.pageNum:page.pageNum+1 }&dname=${map.dname}">下一页</a>
				<a href="?pageNum=${page.pages }">尾页</a>
				
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		function plsc(){
			alert("确认删除")
			var ids = "";
			$(":checkbox:checked").each(function(){
				ids+=","+this.value;
			})
			var did = ids.substring(1);
			alert(did)
			$.post("plsc.do",{"did":did},function(data){
				if(data){
					alert("删除成功")
					location.href="list.do"
				}else{
					alert("删除失败")
				}
				
			},"json");
		}
	var ids = "";
		$("#qx").click(function(){
			$(".did").prop("checked",$(this).prop("checked",true));
			
		})
		$("#fx").click(function(){
			$(".did").each(function(){
				this.checked = !this.checked;
			})
		})
	</script>
</body>
</html>