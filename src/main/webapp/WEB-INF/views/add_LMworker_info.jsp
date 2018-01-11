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
	
	<div id="container">
		<div id="nav_links">
			当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">增加LM员工信息</span>
			<div id="page_close">
				<a href="javascript:parent.$.fancybox.close();">
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table id="info"  cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
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
					<td class="ui_text_rt">五级部门</td>
					<td class="ui_text_lt">
                        <input id="p3" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
                <tr>
					<td class="ui_text_rt">六级部门</td>
					<td class="ui_text_lt">
                        <input id="p4" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
						
					</td>
				</tr>
					<tr>
					<td class="ui_text_rt" width="80">&nbsp;直&nbsp;接&nbsp;主&nbsp;管&nbsp;</td>
					<td class="ui_text_lt">
                        <input  type="text"  name="ryxx.xm" id="p5" class="input ui_input_txt02" />
						
					</td>
				</tr>
				
                <tr>
                    <td class="ui_text_rt">&nbsp;学&nbsp;习&nbsp;能&nbsp;力</td>
                    <td class="ui_text_lt">
                       <input id="p6" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;影&nbsp;响&nbsp;力&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p7" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                            
                        </select>
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;成&nbsp;就&nbsp;导&nbsp;向&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p8" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;沟&nbsp;通&nbsp;能&nbsp;力&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p9" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;及&nbsp;时&nbsp;高&nbsp;质&nbsp;量&nbsp;交&nbsp;付&nbsp;责&nbsp;任&nbsp;者&nbsp;</td>
                    <td class="ui_text_lt">
                       <input id="p10" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;持&nbsp;续&nbsp;竞&nbsp;争&nbsp;力&nbsp;构&nbsp;建&nbsp;者&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p11" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
				 <tr>
                    <td class="ui_text_rt">&nbsp;团&nbsp;队&nbsp;持&nbsp;续&nbsp;战&nbsp;斗&nbsp;力&nbsp;构&nbsp;建&nbsp;者&nbsp;</td>
                    <td class="ui_text_lt">
                       <input id="p12" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>
					 <tr>
                    <td class="ui_text_rt">&nbsp;组&nbsp;织&nbsp;能&nbsp;力&nbsp;建&nbsp;设&nbsp;和&nbsp;效&nbsp;率&nbsp;提&nbsp;升&nbsp;的&nbsp;责&nbsp;任&nbsp;者&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p13" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>

					 <tr>
                    <td class="ui_text_rt">&nbsp;核&nbsp;心&nbsp;价&nbsp;值&nbsp;观&nbsp;传&nbsp;承&nbsp;者&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p14" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>

					<tr>
                    <td class="ui_text_rt">&nbsp;PDU&nbsp;是&nbsp;否&nbsp;曝&nbsp;光&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p15" class="input ui_select01">
                            <option value="">--请选择--</option>
                       
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>
                    </tr>

					<tr>
                    <td class="ui_text_rt">&nbsp;DU&nbsp;是&nbsp;否&nbsp;曝&nbsp;光&nbsp;</td>
                    <td class="ui_text_lt">
                        <select name="ryxx.zzmm" id="p16" class="input ui_select01">
                            <option value="">--请选择--</option>
                         
                            <option value="是">是</option>
                            <option value="否">否</option>
                            
                        </select>
                    </td>

                    </tr>

					<tr>
                    <td class="ui_text_rt">&nbsp;PMDP&nbsp;培&nbsp;训&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p17" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                        </select>
                    </td>
                    </tr>
				<tr>
                    <td class="ui_text_rt">&nbsp;与&nbsp;LM&nbsp;匹&nbsp;配&nbsp;的&nbsp;能&nbsp;力&nbsp;GAP&nbsp;</td>
                    <td class="ui_text_lt">
                        <input id="p18" type="text" name="ygxx.mz"   class="input ui_input_txt02" />
                    </td>
                    </tr>

				<tr>
                    <td class="ui_text_rt">&nbsp;重&nbsp;点&nbsp;工&nbsp;作&nbsp;描&nbsp;述&nbsp;</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p19" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>
					
				<tr>
                    <td class="ui_text_rt">&nbsp;导&nbsp;师&nbsp;姓&nbsp;名&nbsp;</td>
                    <td class="ui_text_lt">
                        <input type="text" id="p20" name="ryxx.csrq"  class="input ui_input_txt02" />
                    </td>
                    </tr>	-->
				
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
