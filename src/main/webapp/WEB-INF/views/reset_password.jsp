<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style/authority/login_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#login_reset").click(function(){
			$("#submitForm").attr("action", "ResetPwd").submit();
		});

		$("#name").focus(function(){
			$("#name_error").hide(500);
		});
		$("#num").focus(function(){
			$("#num_error").hide(500);
		});
		$("#card").focus(function(){
			$("#card_error").hide(500);
		});
		$("#login_back").click(function(){
			window.location.href="login.jsp";
		});
		if(getCookie('role') && getCookie('name')){
		      $("#role").val(getCookie('role'));
		      $("#num").val(getCookie('name'));
		    }

	});


	
	/*回车事件*/
	function EnterPress(e){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			$("#submitForm").attr("action", "ResetPwd").submit();
		} 
	}

	function checkUser(){
	    var result = $("#name").val();
	    var num = $("#num").val();
		var card = $("#card").val();
	    if(result == ""  ){
		    $("#name_error").show(500);
		    return false;
	    }
	    if(num == ""  ){
		    $("#num_error").show(500);;
		    return false;
	    }
		if(card == ""  ){
		    $("#card_error").show(500);;
		    return false;
	    }
		else{
	    	return true;
	    }
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
<title>重置密码</title>
</head>

<body>
<div id="login_center">
		<div id="login_area">
			<div id="login_box">
				<div id="login_form">
					<form id="submitForm" action="ResetPwd" method="post" onsubmit="return checkUser();">
						<div id="login_tip">
							<span id="login_err" class="sty_txt2"></span>
						</div>
                        <div>
							 角&nbsp;&nbsp;&nbsp;色：
							 <select name="userEntity.userRole" id="role" class="userrole">
                            <option value="1">管理员</option>
                            <option value="0">员工</option>

                        </select>
						</div>
						<div>
							 姓&nbsp;&nbsp;&nbsp;名：<input type="text" name="userEntity.name" class="username" id="name" />
							 <span class="error_info" id="name_error">*姓名为空</span>
						</div>
						<div>
							工&nbsp;&nbsp;&nbsp;号：<input type="text" name="userEntity.personNumber" class="num" id="num" onkeypress="EnterPress(event)" onkeydown="EnterPress()" />
							<span class="error_info" id="num_error">*工号为空</span>
						</div>
					
                        
						<div id="btn_area">
							<input type="button" class="login_btn" id="login_reset"  value="重置密码" />
							<input type="button" class="login_btn" id="login_back" value="返  回" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
