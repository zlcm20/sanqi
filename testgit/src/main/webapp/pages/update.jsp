<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>

<div id="regLogin" class="wrap">
		<div class="dialog">
			<dl class="clearfix">
				<dt>新房屋信息发布</dt>
				<dd class="past">填写房屋信息</dd>

			</dl>
			<div class="box">
				<form id="House_add_action" name="House_add_action" action="house_doUpdate.action" method="post">
					<input type="hidden" name="id" value="${house.id }"/>
					<div class="infos">
						<table class="field">
							<tr>
								<td class="field">标 题：</td>
								<td>
									<input type="text" name="title" value="${house.title }" id="House_add_action_title" class="text"/>
								</td>
							</tr>
							<tr>
								<td class="field">户 型：</td>
								<td>
									<select class="text" name="type_id" id="houseType">
										<c:forEach items="${houseTypeList }" var="houseType">
											<c:if test="${house.type_id==houseType.id }">
												<option value="${houseType.id }" selected>${houseType.name }</option>
											</c:if>
											
											<c:if test="${house.type_id!=houseType.id }">
												<option value="${houseType.id }">${houseType.name }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="field">面 积：</td>
								<td><input type="text" name="floorage" value="${house.floorage }" id="House_add_action_floorage" class="text"/></td>
							</tr>
							<tr>
								<td class="field">价 格：</td>
								<td><input type="text" name="price" value="${house.price }" id="House_add_action_price" class="text"/></td>
							</tr>

							<tr>
								<td class="field">位 置：</td>
								<td>
									区：
									<select class="text" name="district.id" id="houseDistrict" onchange="onchangeDistrict(this.value)">
										<c:forEach items="${districtList }" var="district">
											<option value="${district.id }">${district.name }</option>
										</c:forEach>
									</select> 
									街：
									<select class="text" name="street.id" id="houseStreet">
										<option value="${house.street_id }">${house.street_id}</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="field">联系方式：</td>
								<td><input type="text" name="contact" value="${house.contact }" id="House_add_action_contact" class="text"/></td>
							</tr>
							<tr>
								<td class="field">详细信息：</td>
								<td>
									<textarea class="ckeditor" name="description">${house.description}</textarea>
								</td>
							</tr>
						</table>
						<div class="buttons">
							<input type="submit" id="House_add_action_0" value="修改完成"/>

						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


<<script type="text/javascript">

	function onchangeDistrict(obj){
		var districtid=obj;
		$.ajax({
			url:"street_list.action",
			data:"id="+districtid,
			dataType:"JSON",
			success:function(data){
				var str="";
				var length=data.rows.length;
				for(var i=0;i<length;i++){
					var street=data.rows[i];
					str+='<option value="'+street.id+'">'+street.name+'</option>';
					
				}
				$("#houseStreet").html(str);
				
			}
		});
    }

</script>
	
	
<%@ include file="bottom.jsp"%>