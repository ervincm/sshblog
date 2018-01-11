<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	$(document).ready(function(){
		
		getTableData(1);
		
		
		//页面跳转控制
		 $("#firstPageBtn").click(function(){//首页按钮
		    	var page = parseInt($("#currentPage").text());
		    	if(page>1){
		    		getTableData(1);
		    	}else{
		    		art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'已经在首页', ok:true,});
		    	};
		    });
		    $("#previousPageBtn").click(function(){//上一页按钮
		    	var page = parseInt($("#currentPage").text())-1;
		    	if(page>=1){
		    		getTableData(page);
		    	}else{
		    		art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'已经在首页', ok:true,});
		    	}
		    });
		    $("#nextPageBtn").click(function(){//下一页按钮
		    	var page = parseInt($("#currentPage").text())+1;
		    	if(page<=parseInt($("#pageCount").text())){
		    		getTableData(page);
		    	}else{
		    		art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'已经在尾页', ok:true,});
		    	}
		    });
		    $("#lastPageBtn").click(function(){//尾页按钮
			      getTableData(parseInt($("#pageCount").text()));
		    });
		    $("#jumpPageBtn").click(function(){//跳转页按钮
			    
		    	if($("#jumpNumTxt").val()){
		    		 var page = parseInt($("#jumpNumTxt").val());
		    		if(page>=1 && page<=parseInt($("#pageCount").text())){
		    			getTableData(page);
		    		}else{
		    			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入正确页码', ok:true,});
		    		} 
		    	}
			      
		    });
		
	
		/** 新增   **/
	    $("#addBtn").fancybox({
	    	'href'  : 'add_administrator.jsp',
	    	'width' : 733,
	        'height' : 530,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	//window.location.href = 'administrator.jsp';
	        }
	    });
		
	
		
	    /**编辑   **/
	    $("a.edit").fancybox({
	    	'width' : 733,
	        'height' : 530,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	            //window.location.href = 'administrator.jsp';
	        }
	    });
	});

	/** 新增   **/
	function add(){
		$("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();	
	}
	

	


	/** 删除 **/
	function del(adminId){
		// 非空判断
		if(adminId == '' || adminId == null) return;
		if(confirm("您确定要删除吗？")){
			$.ajax({
				type:"GET",
				url:"DeletAdminData",
				data:{
					"adminId":adminId,
				},
				success:function(data){
					if(data=="1"){
						getTableData(parseInt($("#currentPage").text()));
					}
				},
				
				error:function()
				{
					art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请求失败', ok:true,});
					
				}
			});			
		}
	}
	
	/** 批量删除 **/
	function batchDel(){
		if($("input[name='IDCheck']:checked").size()<=0){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'至少选择一条', ok:true,});
			return;
		}
		// 1）取出用户选中的checkbox放入字符串传给后台,form提交
		var allIDCheck = "";
		$("input[name='IDCheck']:checked").each(function(index, domEle){
			
				allIDCheck += $(domEle).val() + ",";
			
		});
		// 截掉最后一个","
		if(allIDCheck.length>0) {
			allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
			del(allIDCheck);
		}
	}

	function getTableData(page){
		$.ajax({
			type:"POST",
			url:"GetAdminData",
			data:{
				"page":page,
				
				
			},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success:function(data){
				// 如果返回数据不为空，加载"业务模块"目录
				if(data != null){					
					// 将返回的数据赋给zTree
				
					
					
					var table = $("#table");
					table.empty();
					var json = JSON.parse(data);
					var jslength=0;	
					$("#pageCount").text(json[0].pageCount);
					$("#count").text(json[0].count);
					$("#currentPage").text(json[0].currentPage);
					$.each(JSON.parse(json[0].mainData), function (index, el){
						if(index == 0){//表头
							for(var js2 in el){
								jslength++;
							}
							var tr = '<tr><th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" /></th>';
							for(i=1;i<=jslength;i++){
								var key = "p"+i;
								tr=tr+'<th>'+el[key]+'</th>';
							}
							tr=tr+'<th>操作</th></tr>';
							table.append(tr);
						}else{
							var tr = '<tr><td><input type="checkbox" name="IDCheck" value="'+el.p1+'" class="acb" /></td>';
							for(i=1;i<=jslength;i++){
								var key = "p"+i;
								tr=tr+'<td>'+el[key]+'</td>';
							}
							tr = tr+'<td><a href="javascript:del('+el.p1+');">删除</a></td></tr>';
							table.append(tr);
						}
						
						
					});
					$("a.edit").fancybox({
             	    	'width' : 733,
             	        'height' : 530,
             	        'type' : 'iframe',
             	        'hideOnOverlayClick' : false,
             	        'showCloseButton' : false,
             	        'onClosed' : function() { 
             	        	
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
						
						<div id="box_center">
							管理员列表
                            </div>
                            <div id="box_bottom">
                                <!--<input type="button" value="增加管理员" class="ui_input_btn01" onclick="search();" />-->
                                <input type="button" value="删除" class="ui_input_btn01" onclick="batchDel();" /> 
                                <input type="button" value="新增" class="ui_input_btn01" id="addBtn" /> 
                               
                            </div>


</div>
				</div>
			</div>
			
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" id="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
							</th>
							<th>账号</th>
							<th>密码</th>
							<th>删除管理员</th>
						</tr>
                        <tr>
                            <td><input type="checkbox" name="IDCheck" value="14458579642011" class="acb" /></td>
                            <td>tfyy</td>
                            <td>1234566</td>
                            <td>
                               
                                <a href="javascript:del('14458579642011');">删除管理员</a>
                            </td>
                            </tr>
                           




</table>
				</div>
				<div class="ui_tb_h30">
					<div class="ui_flt" style="height: 30px; line-height: 30px;">
						共有
						<span class="ui_txt_bold04" id="count"></span>
						条记录，当前第
						<span class="ui_txt_bold04"><span id="currentPage"></span>
						/
						<span id="pageCount"></span></span>
						页
					
					</div>
					<div class="ui_frt">
						<!--    如果是第一页，则只显示下一页、尾页 -->
						
							<input type="button" value="首页" class="ui_input_btn01" id="firstPageBtn"/>
							<input type="button" value="上一页" class="ui_input_btn01" id="previousPageBtn"/>
							<input type="button" value="下一页" class="ui_input_btn01" id="nextPageBtn"/>
							<input type="button" value="尾页" class="ui_input_btn01" id="lastPageBtn"/>
				
						
						<!--     如果是最后一页，则只显示首页、上一页 -->
						
						转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页
							 <input type="button" class="ui_input_btn01" value="跳转" id="jumpPageBtn" />
					</div>
				</div>
			</div>
		</div>
	</form>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
