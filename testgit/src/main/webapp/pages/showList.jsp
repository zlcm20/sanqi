<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.yc.bean.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function del(id,pages){
		var r=window.confirm("您确定要删除编号为："+id+"的记录嘛？");
		if(r==true){
			$.ajax({
				url:"house_del.action",
				type:'POST',
				data:"id="+id,
				dataType:'JSON',
				success:function(data){
					alert('删除成功');
					goPages(pages);
				}
			});
		}else{
			alert('删除失败！！');
		}
	}
	
	function update(id,pages){
		location.href="house_toUpdate.action?id="+id;
	}
</script>

		<table class="house-list">
		
			<c:forEach items="${jsonModel.rows }" var="v">
				<tr>
					<td class="house-thumb">
						<span>
							<a href="page/show.jsp?id=10001">
							<img src="images/thumb_house.gif" /></a>
						</span>
					</td>
					<td>
						<dl>
							<dt>
								<a href="house_detail.action/${v.id}">${v.title }</a>
							</dt>
							<dd>
								${v.description}
							</dd>
						</dl>
					</td>
					
					<c:if test="${user.id==v.user_id }">
						<td class="house-type">
							<label class="ui-green">
								<input type="button" onclick='update(${v.id},${jsonModel.pages })' value="修    改" />
							</label>
						</td>
						<td class="house-price">
							<label class="ui-green">
								<input type="button" onclick='del(${v.id},${jsonModel.pages })' value="删    除" />
							</label>
						</td>
					</c:if>
					
					<c:if test="${user.id!=v.user_id }">
						<td class="house-type" colspan="2"></td>
					</c:if>
					
				</tr>
			</c:forEach>
			
		</table>
		
		
		
		
		
		
		<div class="pager">
			<ul>
				<li class="current">
					<a href="javascript:goPages(1)">首页</a> 
				</li>
				<%
					JsonModel jsonModel=(JsonModel)request.getAttribute("jsonModel");
					int pages=jsonModel.getPages();
					int total=jsonModel.getTotal();
					int pagesize=jsonModel.getPageSize();
					int totalPages=total%pagesize==0?total/pagesize:total/pagesize+1;
					int prepage=pages;
					if(pages>1){
						prepage=pages-1;
					}
					int nextpage=totalPages;
					if(pages<totalPages){
						nextpage=pages+1;
					}
				%>
				<li class="current">
					<a href="javascript:goPages(<%=prepage%>)">上一页</a> 
				</li>
				
				<li class="current">
					<a href="javascript:goPages(<%=nextpage%>)">下一页</a> 
				</li>
				
				<li class="current">
					<a href="javascript:goPages(<%=totalPages%>)">最后一页</a> 
				</li>
				
			</ul>
			<span class="total">第<%=pages %>页/共<%=totalPages %>页  记录总数：<%=total %>条  每页显示<%=pagesize %>条</span>
		</div>