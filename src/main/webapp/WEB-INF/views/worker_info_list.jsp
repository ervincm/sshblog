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
     var edit=null;
     var add=null;
	$(document).ready(function(){
		//判断是哪个请求界面
		
		if("${param.type}"=="lm"){
			add="add_LMworker_info.jsp?type="+"${param.type}";
			edit="edit_LMworker_info.jsp?type="+"${param.type}";
		}else 
			if("${param.type}"=="pl"){
				add="add_PLworker_info.jsp?type="+"${param.type}";
				edit="edit_PLworker_info.jsp?type="+"${param.type}";
			}
		getTableData(1);
		
		
		
		/** 新增   **/
	    $("#addBtn").fancybox({
	    	'href'  : add,
	    	'width' : 733,
	        'height' : 530,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	/* window.location.href = 'worker_info_list.jsp'; */
	        }
	    });
		
	    /** 导入  **/
	    $("#importBtn").fancybox({
	    	'href'  : 'import_info.jsp',
	    	'width' : 633,
	        'height' : 260,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	/* window.location.href = 'worker_info_list.jsp'; */
	        }
	    });
		
	    /**编辑   **/
	    

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
	});
	/** 用户角色   **/
	var userRole = '';

	/** 模糊查询来电用户  **/
	function search(){
		/* $("#submitForm").attr("action", "worker_info_list.html?page=" + 1).submit(); */
		getTableData(1);
	}


	 
	/** Excel导出  **/
	function exportExcel(){
		if( confirm('您确定要导出吗？') ){
			
//	 		
			$("#submitForm").attr("action", "DataExcelExportServlet").submit();	
		}
	}
	
	/** 删除 **/
	function del(workId){
		// 非空判断
		if(workId == '' || workId == null) return;
		if(confirm("您确定要删除吗？")){
			$.ajax({
				type:"GET",
				url:"DeletData",
				data:{
					"workId":workId,
					"type":"${param.type}"
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

	/** 普通跳转 **/
	function jumpNormalPage(page){
		
	}
	
	/** 输入页跳转 **/
	function jumpInputPage(totalPage){
		// 如果“跳转页数”不为空
		if($("#jumpNumTxt").val() != ''){
			var pageNum = parseInt($("#jumpNumTxt").val());
			// 如果跳转页数在不合理范围内，则置为1
			if(pageNum<1 | pageNum>totalPage){
				art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
				pageNum = 1;
			}
			$("#submitForm").attr("action", "house_list.html?page=" + pageNum).submit();
		}else{
			// “跳转页数”为空
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
			$("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
		}
	}
	function getTableData(page){
		$.ajax({
			type:"POST",
			url:"GetData",
			data:{
				"page":page,
				"type":"${param.type}",
				"name":$("#fyXq").val(),
				"id":$("#id_person").val(),
				"department":$("#department").val(),
				
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
							for(var i=1;i<=jslength;i++){
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
							tr = tr+'<td><a href="'+edit+'&personID='+el.p1+'" class="edit">编辑	</a><a href="javascript:del('+el.p1+');">删除</a></td></tr>';
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
		<input type="hidden" name="type" value="${param.type}" id="allIDCheck"/>
		<input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							姓名
                            <input id="fyXq" type="text" name="name"  class="ui_select01"  />
                            	工号
                            <input id="id_person" type="text" name="id"  class="ui_select01"  />
                            
							部门
							 <input id="department" type="text" name="department"  class="ui_select01"  />
							<!-- <select name="fangyuanEntity.fyDhCode" id="fyDh" class="ui_select01">
                                <option value="">--请选择--</option>
                                
                                <option value="76">研发部</option>
                                <option value="10">销售部</option>
                                <option value="14">运营部</option>
                           
                            </select> -->
						
					
						</div>
						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01" onclick="search();" /> 
							<input type="button" value="新增" class="ui_input_btn01" id="addBtn" /> 
							<input type="button" value="删除" class="ui_input_btn01" onclick="batchDel();" /> 
							<input type="button" value="导入" class="ui_input_btn01" id="importBtn" />
							<input type="button" value="导出" class="ui_input_btn01" onclick="exportExcel();" />
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" id="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						
					
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
