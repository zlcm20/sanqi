<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="header.jsp"%>
<style>
	 *{
	            margin:0;
	            padding:0;
	   }
	.search{
	            float: right;
	            margin:5px 235px 5px 200px;
	   }
	
</style>
<script type="text/javascript">
	$(function(){
		goPages(1);
	});
	function goPages(pages){
		$.ajax({
			url:"house_list.action",
			data:"pages="+pages+"&pagesize=5",
			dataType:'HTML',
			success:function(data){
				$("#houseArea").html(data);
			}
		});
	}
</script>

<c:if test="${user!=null }">
	<script>
		function add(){
			document.location="house_toAdd.action";
		}
	</script>
	<div class="search">
		<label class="ui-green" >
			<input onclick="add()" type="button" name="search" value="发布房屋信息" />
		</label> 
		<label class="ui-green">
			<input type="button" name="search" value="退       出" onclick='document.location="user_logout.action"' />
		</label>
	</div>
</c:if>


<div class="main wrap">
	<div id="houseArea"></div>
</div>

<%@ include file="bottom.jsp"%>