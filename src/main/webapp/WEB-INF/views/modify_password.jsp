<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 

 if(session.getAttribute("user") == "" || session.getAttribute("user")==null){
	session.invalidate();
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SCT-后台系统</title>
<link href="style/authority/modify_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		 $("#login_sub").click(function(){
			$("#submitForm").attr("action", "ModifyPassword").submit();
		});

		$("#name").focus(function(){
			$("#name_error").hide(1000);
		});
		$("#oldpwd").focus(function(){
			$("#oldpwd_error").hide(500);
		});
		$("#newpwd").focus(function(){
			$("#newpwd_error").hide(500);
		});
		$("#affirmpwd").focus(function(){
			$("#affirmpwd_error").hide(500);
		});

	});
	


	
	/*回车事件*/
	function EnterPress(e){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			$("#submitForm").attr("action", "ModifyPassword").submit();
		} 
	}
	function rec(){
		var newpwd=$("#newpwd").val();
		var affirm=$("#affirmpwd").val();
		if(newpwd!=affirm&&newpwd!="")
		alert("新密码与确认密码不同！");
		else
		{
		window.location.href="modify_password.jsp";
		}
	}
	
	//判断输入是否为空密码
 

	function checkUser(){
	    var result = $("#name").val();
	    var oldpassword = $("#oldpwd").val();
		var newpassword = $("#newpwd").val();
		var affirmpassword = $("#affirmpwd").val();
	    if(result == ""  ){
		    $("#name_error").show(700);
		    return false;
	    }
	    if(oldpassword == ""  ){
		    $("#oldpwd_error").show(500);;
		    return false;
	    }
		if(newpassword == ""  ){
		    $("#newpwd_error").show(500);;
		    return false;
	    }
		if(affirmpassword == ""  ){
		    $("#affirmpwd_error").show(500);;
		    return false;
	    }
		if(newpassword!=affirmpassword)
			{
				alert("新密码与确认密码不同！");
			    return false;
			}
		else
		{
			
			return true;
		
		}
		
    }
	
</script>
</head>
<body>
	<div id="login_center">
		<div id="login_area">
			<div id="login_box">
				<div id="login_form">
					<form id="submitForm" action="" method="post" onsubmit="return checkUser();">
						<div id="login_tip">
							<span id="login_err" class="sty_txt2"></span>
						</div>
                       
						<div>
						&nbsp;用户名：&nbsp;&nbsp;<input type="text" name="userEntity.personNum" class="username1" id="name"  value="${user}">
							 <span class="error_info" id="name_error">*用户名为空</span>
						</div>
						<div>
						&nbsp;原密码：&nbsp;&nbsp;<input type="password" name="userEntity.oldpassword" class="oldpwd" id="oldpwd" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
							<span class="error_info" id="oldpwd_error">*原密码为空</span>
						</div>
                        <div>
						&nbsp;新密码：&nbsp;&nbsp;<input type="password" name="userEntity.newpassword" class="newpwd" id="newpwd" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
							<span class="error_info" id="newpwd_error">*新密码为空</span>
						</div>
                        <div>
						确认密码：&nbsp;<input type="password" name="userEntity.affirmpassword" class="affirmpwd" id="affirmpwd" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
							<span class="error_info" id="affirmpwd_error">*确认密码为空</span>
						</div>
						<div id="btn_area">
							<input type="button" name="button" class="login_btn" id="login_sub"   value="提交修改" >
							
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>