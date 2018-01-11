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
		getTableData(1);
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
					window.parent.art.dialog({icon:'succeed', title:'友情提示', drag:false, resize:false, content:data, ok:true,});
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
	/** 检测房源房号是否存在  **/
	
	/** 检测房源房号是否存在并提交form  **/
	function getTableData(page){
		$.ajax({
			type:"POST",
			url:"GetData",
			data:{
				"page":page,
				"type":"${param.type}",
				"name":"",
				"id":"0", 
				
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
</script>
</head>
<body>
<form id="submitForm" name="submitForm" action="/xngzf/archives/initFangyuan.action" method="post">
	<input type="hidden" name="fyID" value="14458625716623" id="fyID"/>
	<div id="container">
		<div id="nav_links">
			当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">增加PL员工信息</span>
			<div id="page_close">
				<a href="javascript:parent.$.fancybox.close();">
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table id="info" cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				
				<!-- <tr>
					<td class="ui_text_rt">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
					<td class="ui_text_lt">
                        <input id="gh" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="80">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="xm" class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="80">五级部门</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="xm" class="input ui_input_txt02" />
						
					</td>
				</tr>
                <tr>
					<td class="ui_text_rt" width="80">六级部门</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.ljbm" id="xm" class="input ui_input_txt02" />
						
					</td>
				</tr>
				
				<tr>
					<td class="ui_text_rt">&nbsp;&nbsp;项&nbsp;&nbsp;目&nbsp;&nbsp;组</td>
					<td class="ui_text_lt">
                        <input id="xmz" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
                
                <tr>
                    <td class="ui_text_rt">&nbsp;上&nbsp;岗&nbsp;/&nbsp;入&nbsp;池&nbsp;时&nbsp;间</td>
                    <td class="ui_text_lt">
                        <input type="text" id="sgsj" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
                <tr>
                    <td class="ui_text_rt">&nbsp;出&nbsp;生&nbsp;年&nbsp;月</td>
                    <td class="ui_text_lt">
                        <input type="text" id="csrq" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
					<td class="ui_text_rt" width="80">&nbsp;直&nbsp;接&nbsp;主&nbsp;管&nbsp;姓&nbsp;名&nbsp;</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="zjzgxm" class="input ui_input_txt02" />
						
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">&nbsp;直&nbsp;接&nbsp;主&nbsp;管&nbsp;工&nbsp;号</td>
					<td class="ui_text_lt">
                        <input id="zjzggh" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
                <tr>
                    <td class="ui_text_rt">&nbsp;资&nbsp;源&nbsp;池&nbsp;属&nbsp;性&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_zyc" class="input ui_select01">
                            <option value="">--请选择--</option>                           
                            <option value="在岗">在岗</option>
                            <option value="后备">后备</option>
                            
                        </select>
                    </td>
                    </tr>
				<tr>
                    <td class="ui_text_rt">&nbsp;任&nbsp;命&nbsp;时&nbsp;间</td>
                    <td class="ui_text_lt">
                        <input type="text" id="rmsj" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;关&nbsp;键&nbsp;正&nbsp;向&nbsp;事&nbsp;件</td>
                    <td class="ui_text_lt">
                        <input type="text" id="gjzxsj" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;管&nbsp;理&nbsp;幅&nbsp;度</td>
                    <td class="ui_text_lt">
                        <input type="text" id="glfd" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;转&nbsp;正&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_zz" class="input ui_select01">
                           <option value="">--请选择--</option>                           
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;担&nbsp;任&nbsp;QCC&nbsp;圈&nbsp;长&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_qcc" class="input ui_select01">
                            <option value="">--请选择--</option>                           
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;担&nbsp;任&nbsp;思&nbsp;想&nbsp;导&nbsp;师&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_sxds" class="input ui_select01">
                           <option value="">--请选择--</option>                           
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;是&nbsp;否&nbsp;AAR&nbsp;认&nbsp;证&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_aar" class="input ui_select01">
                            <option value="">--请选择--</option>                           
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;PLDP&nbsp; MOOC&nbsp; 是&nbsp;否&nbsp;通&nbsp;过</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_pldpmooc" class="input ui_select01">
                            <option value="">--请选择--</option>                           
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>
					<tr>
                    <td class="ui_text_rt">&nbsp;PLDP&nbsp;是&nbsp;否&nbsp;通&nbsp;过</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="submitForm_ryxx_pldp" class="input ui_select01">
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