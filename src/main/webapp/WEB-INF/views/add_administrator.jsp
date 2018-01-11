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
	
	/** 检测房源房号是否存在  **/
	
	/** 检测房源房号是否存在并提交form  **/
	
	
	/** 表单验证  **/
	function validateForm(){
		if($("#xm").val()==""){
			art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'姓名', ok:true,});
			return false;
		}
		if($("#gh").val()==""){
			art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'工号', ok:true,});
			return false;
		}
		
		if ($("#mm").val() == "") {
		    art.dialog({ icon: 'error', title: '提示', drag: false, resize: false, content: '密码', ok: true, });
		    return false;
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
			url:"AddAdminInfo",
			data:{
				
				"str":info,
				
			},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success:function(data){
				// 如果返回数据不为空，加载"业务模块"目录
				if(data != null){					
					// 将返回的数据赋给zTree
					window.parent.$.fancybox.close();
				window.parent.art.dialog({icon:'succeed', title:'友情提示', drag:false, resize:false, content:data, ok:true,});
				window.parent.getTableData(1);
					
					
				}
				
			},
			
			error:function()
			{
				art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请求失败', ok:true,});
			}
		});
	}
</script>
</head>
<body>
<form id="submitForm" name="submitForm" action="/xngzf/archives/initFangyuan.action" method="post">
	<input type="hidden" name="fyID" value="14458625716623" id="fyID"/>
	<div id="container">
		<div id="nav_links">
			当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">管理员信息编辑</span>
			<div id="page_close">
				<a href="javascript:parent.$.fancybox.close();">
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table  cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
			
                <tr>
                    <td class="ui_text_rt" width="80">工号</td>
                    <td class="ui_text_lt">
                        <input type="text" name="ryxx.gh" id="gh" class="input ui_select01" />

                    </td>
                </tr>
                	<tr>
					<td class="ui_text_rt" width="80">姓名</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="xm" class="input ui_select01" />
						
					</td>
				</tr>
                <tr>
                    <td class="ui_text_rt" width="80">密码</td>
                    <td class="ui_text_lt">
                        <input type="text" name="ryxx.mm" id="mm" class="input ui_select01" />

                    </td>
                </tr>
				
                <tr>
                    <td class="ui_text_rt">身份属性</td>
                    <td class="ui_text_lt">
                        <select name="ygxx.sfsx" id="sfsx" class="input ui_select01">
                            
                            <option value="PL管理员" selected="selected">PL管理员</option>
                 
                            <option value="LM管理员">LM管理员</option>

                        </select>
                    </td>
                </tr>

				
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