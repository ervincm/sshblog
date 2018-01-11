<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/jquery/jquery-1.4.4.min.js"></script>
<script src="scripts/My97DatePicker/WdatePicker.js" type="text/javascript" defer></script>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/*
		 * 提交
		 */
		 
		 getTableData(1);
		 
		 $("#submitbutton").click(function() {
				if(validateForm()){
					sendInfo();
				}
			});
		
		/*
		 * 取消
		 */
		$("#cancelbutton").click(function() {
			/**  关闭弹出iframe  **/
			window.parent.$.fancybox.close();
		});
		
		var result = 'null';
		if(result =='success'){
			/**  关闭弹出iframe  **/
			window.parent.$.fancybox.close();
		}
	});
	

	/** 检测信息是否填写正确并提交form  **/
	function checkFyFhSubmit(){
		// 获取id_num
			
		var id_num = $("#id_num").val();
		
		
			
					if(id_num.length!=18){
						 art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'身份证号码错误', ok:true,});
						 $("#fyFh").css("background", "#EEE");
						 $("#fyFh").focus();
						 return false;
					}else{
						
						$("#submitForm").attr("action", "SaveEditInfo").submit();
					}
				
			
		
	}
	
	
	/** 表单验证  **/
	function validateForm(){
		var length = $('#info').find("tr").length;
		for(var i = 1; i <=length; i++){
			var key = "p"+i;
			if($("#"+key).val() == ""){
				$("#"+key).focus();
				art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:$("#"+key).parent().prev().text()+' 不能为空', ok:true,});
				return false;
			}
		}

		
		return true;
	}
	function sendInfo(){
		
		//构成插入用的sql插入字符串
		var info = "";
		$(".input").each(function(index, domEle){
			
				info += "'"+$(domEle).val()+"'" + ",";
			
		});
		info=info.substring(0, info.length-1);
		
		
		$.ajax({
			type:"POST",
			url:"AddWorkerInfo",
			data:{
				"type":"${param.type}",
				"str":info
				
			},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success:function(data){
				// 如果返回数据不为空，加载"业务模块"目录
				if(data != null){					
					// 将返回的数据赋给zTree
					window.parent.art.dialog({icon:'succeed', title:'友情提示', drag:false, resize:false, content:"修改成功", ok:true,});
					window.parent.$.fancybox.close();
					window.parent.getTableData($("#currentPage",window.parent.document).text());
					
				}
				
			},
			
			error:function()
			{
				art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请求失败', ok:true,});
			}
		});
	}
	
	function getTableData(page){
		$.ajax({
			type:"POST",
			url:"GetData",
			data:{
				"page":page,
				"type":"${param.type}",
				"name":"",
				"id":"${param.personID}", 
				
			},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success:function(data){
				// 如果返回数据不为空，加载"业务模块"目录
				if(data != null){					
					var json = JSON.parse(data);
					var jslength=0;	
					$.each(JSON.parse(json[0].mainData), function (index, el){
						if(index == 0){//表头
							for(var js2 in el){
								jslength++;
							}
							for(var i=jslength;i>=1;i--){
								var key = "p"+i;
								var inputStr = '<tr><td class="ui_text_rt">'+el[key]+'</td><td class="ui_text_lt"><input id="'+key+'" type="text" name="ygxx.mz"  class="input ui_input_txt02" /></td></tr>'
								$("#info").prepend(inputStr);
							}
						}else if (index == 1) {
							
						
							for(var i=1;i<=jslength;i++){
								var key = "p"+i;
								$("#"+key).val(el[key]);
							}
						}
						
						
					});					
				}				
			},
			
			error:function()
			{
				art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请求失败', ok:true,});
			}
		});
		}
	/* function test(){
	    var person_num={},hello='person_num';
	    person_num[hello]=${param.personID};
	     $.ajax({    
                        type:"post",  
                    url:"GetEditInfo",     
                    data:person_num,     
                    dataType:"json", //很重要!!!.预期服务器返回的数据类型 ,    
                    success:function(data){ 
                    	
                    },  
                    error:function(){   
                    
                    	alert(XMLResponse.responseText);
                        alert("error occured!!!");   
                        
                    }     
                         
                });     
	    
	    
	    } */
	  // window.onload = test;
</script>
</head>
<body>
<form id="submitForm" name="submitForm" action="SaveEditInfo" method="post">
	
	<input type="hidden" name="person_num" value="${param.personID}" id="person_num"/>
	<div id="container">
		<div id="nav_links">
			当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">员工信息编辑</span>
			<div id="page_close">
				<a href="javascript:parent.$.fancybox.close();">
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table  id="info" cellspacing="0" cellpadding="0"   border="0">
				
				<!-- <tr>
					<td class="ui_text_rt">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
					<td class="ui_text_lt">
                        <input id="p1" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="80">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="p2" class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="80">五级部门</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="p3" class="input ui_input_txt02" />
						
					</td>
				</tr>
                <tr>
					<td class="ui_text_rt" width="80">六级部门</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="p4" class="input ui_input_txt02" />
						
					</td>
				</tr>
				
				<tr>
					<td class="ui_text_rt">&nbsp;&nbsp;项&nbsp;&nbsp;目&nbsp;&nbsp;组</td>
					<td class="ui_text_lt">
                        <input id="p5" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
                
                <tr>
                    <td class="ui_text_rt">&nbsp;上&nbsp;岗&nbsp;/&nbsp;入&nbsp;池&nbsp;时&nbsp;间</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p6" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
                <tr>
                    <td class="ui_text_rt">&nbsp;出&nbsp;生&nbsp;年&nbsp;月</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p7" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
					<td class="ui_text_rt" width="80">&nbsp;直&nbsp;接&nbsp;主&nbsp;管&nbsp;姓&nbsp;名&nbsp;</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="p8" class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">&nbsp;直&nbsp;接&nbsp;主&nbsp;管&nbsp;工&nbsp;号</td>
					<td class="ui_text_lt">
                        <input id="p9" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
                <tr>
                    <td class="ui_text_rt">&nbsp;资&nbsp;源&nbsp;池&nbsp;属&nbsp;性&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p10" class="input ui_select01" >
                            <option value="">--请选择--</option>
                            
                            <option value="在岗">在岗</option>
                            <option value="后备">后备</option>
                            
                        </select>
                    </td>
                    </tr>
				<tr>
                    <td class="ui_text_rt">&nbsp;任&nbsp;命&nbsp;时&nbsp;间</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p11" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;关&nbsp;键&nbsp;正&nbsp;向&nbsp;事&nbsp;件</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p12" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;管&nbsp;理&nbsp;幅&nbsp;度</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p13" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;转&nbsp;正&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p14" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;担&nbsp;任&nbsp;QCC&nbsp;圈&nbsp;长&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p15" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;担&nbsp;任&nbsp;思&nbsp;想&nbsp;导&nbsp;师&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p16" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;AAR&nbsp;认&nbsp;证&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p17" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;PLDP&nbsp; MOOC&nbsp; 是&nbsp;否&nbsp;通&nbsp;过</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p18" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;PLDP&nbsp;是&nbsp;否&nbsp;通&nbsp;过</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p19" class="input ui_select01">
                            <option value="">--请选择--</option>
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr> -->
				
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input id="submitbutton" type="button" value="提交" class="ui_input_btn01"/>
						&nbsp;<input id="cancelbutton" type="button" value="取消" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>