<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%session.invalidate(); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
<link href="style/authority/login_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#login_sub").click(function(){
			$("#submitForm").attr("action", "Login").submit();
		});

		$("#role").focus(function(){
			$("#role_error").hide(500);
		});
		$("#name").focus(function(){
			$("#name_error").hide(500);
		});
		$("#pwd").focus(function(){
			$("#pwd_error").hide(500);
			$("#login_error").hide(500);
		});
		$("#login_ret").click(function(){
			window.location.href="reset_password.jsp";
		});
		
		if(getCookie('role') && getCookie('name')){
		      $("#role").val(getCookie('role'));
		      $("#name").val(getCookie('name'));
		    }
		if("${param.error}"){
			$("#login_error").show(1000);
		}
	});


	
	/*回车事件*/
	function EnterPress(e){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			$("#submitForm").attr("action", "Login").submit();
		} 
	}

	function checkUser(){
		var role = $("#role").val();
	    var name = $("#name").val();
	    var password = $("#pwd").val();
	    if(role == ""){
	    	$("#role_error").show(500);
		    return false;
	    }
	    if(name == ""  ){
		    $("#name_error").show(500);
		    return false;
	    }
	    if(password == ""  ){
		    $("#pwd_error").show(500);;
		    return false;
	    }else{
	    	setCookie("role",role,1);
	    	setCookie("name",name,1);
	    	return true;
	    }
    }
	
	//设置cookie
	function setCookie(cname,cvalue,exdays)
	{
	  var d = new Date();
	  d.setTime(d.getTime()+(exdays*24*60*60*1000));
	  var expires = "expires="+d.toGMTString();
	  document.cookie = cname + "=" + cvalue + "; " + expires;
	}
	  
	//获取cookie
	  function getCookie(cname)
		{
		  var name = cname + "=";
		  var ca = document.cookie.split(';');
		  for(var i=0; i<ca.length; i++) 
		  {
		    var c = ca[i].trim();
		    if (c.indexOf(name)==0) return c.substring(name.length,c.length);
		  }
		  return "";
		}
 
</script>
</head>
<body>
	<div id="login_center">
		<div id="login_area">
			<div id="login_box">
				<div id="login_form">
					<form id="submitForm" action="Login" method="post" onsubmit="return checkUser();">
						<div id="login_tip">
							<span id="login_err" class="sty_txt2"></span>
						</div>
                        <div>
							 角&nbsp;&nbsp;&nbsp;色：
							 <select name="userEntity.userRole" id="role" class="userrole">
							  
                            <option value="1">管理员</option>                  
                            <option value="2">超级管理员</option>
                          <!--   <option value="0">员工</option> -->

	                        </select>
	                        <span class="error_info" id="role_error">*选择用户类型</span>
						</div>
						<div >
							 用户名：<input type="text" name="userEntity.userName" class="username" id="name" >
							 <span class="error_info" id="name_error">*用户名为空</span>
						</div>
						<div>
							密&nbsp;&nbsp;&nbsp;码：<input type="password" name="userEntity.password" class="pwd" id="pwd" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
							<span class="error_info" id="pwd_error">*密码为空</span>
						</div>
						<div class="error_info" id="login_error">*登录失败，请核对登录信息</div>
						<div id="btn_area">
							<input type="button" class="login_btn" id="login_sub"  value="登  录">
							<input type="button" class="login_btn" id="login_ret" value="忘记密码">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
