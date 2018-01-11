<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
<title>信息管理系统</title>
<script type="text/javascript">

	

	
	    function test(){
	    var person_num={},hello='person_num';
	    person_num[hello]=${user};
	     $.ajax({    
                   type:"post",  
                    url:"GetPersonInfo",     
                    data:person_num,     
                    dataType:"json", //很重要!!!.预期服务器返回的数据类型 ,    
                    success:function(data){ 
                    	
                    	
                   	
                   
                    	
                    	// var objs=JSON.parse(data); //解析json对象
                    	 var table = $("#table");  
                    	 
                    	 $.each(data,function(n,v) {    
                           	var str = '';  
                           	str += '<tr><td><input type="checkbox" name="IDCheck" value="'+v.person_num+'" class="acb" /></td>'
                              	
                              	+'<td>'+v.name 

                  +'</td>'
                              	+'<td>'+v.partment+'</td>'
                              	+'<td>'+v.education+'</td>'
                              	+'<td>'+v.id_num+'</td>'
                              	+'<td>'+v.birthday+'</td>'
                              	+'<td>'+v.political_status+'</td>'
                              	+'<td>'+v.address+'</td>'
                              	+'<td>'+v.school+'</td>'
                              	
                  				+'<td><a href="worker_info_edit.jsp?personID='+v.person_num+'"  class="edit">编辑</a></td>';  
                  				
                  				
                  			
                              table.append(str); 
                              
                         	 /**编辑   **/
                         	    $("a.edit").fancybox({
                         	    	'width' : 733,
                         	        'height' : 530,
                         	        'type' : 'iframe',
                         	        'hideOnOverlayClick' : false,
                         	        'showCloseButton' : false,
                         	        'onClosed' : function() { 
                         	        	window.location.href = 'personal_info.jsp';
                         	        }
                         	    });
                    	 });
                    


                    },  
                    error:function(){   
                    
                    	alert(XMLResponse.responseText);
                        alert("error occured!!!");   
                        
                    }     
                         
                });     
	    
	    
	    }
	   window.onload = test;

</script>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	<form id="submitForm" name="submitForm" action="" method="post">
		<input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
		<input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						
						<div id="box_top"></div>
						<div id="box_center">
							用户个人信息
						</div>
						
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table  id="table" class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
							</th>
							<th>姓名</th>
							<th>部门</th>
							<th>学历</th>
							<th>证件号码</th>
							<th>出生年月</th>
							<th>政治面貌</th>
							<th>籍贯</th>
							<th>毕业院校</th>
							<th>操作</th>
						</tr>
					
					</table>
				</div>
			</div>
		</div>
	</form>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
