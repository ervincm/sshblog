<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>导入表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">  
<script src="scripts/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
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
				if("lm"=="${param.type}"){
					$("#fileform").attr("action", "ExcelLMModelUpdateServlet").submit();
				}else if("pl"=="${param.type}"){
					$("#fileform").attr("action", "ExcelPLModelUpdateServlet").submit();
				}
				
				 
				
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
		if("${param.success}"=="true"){
			/**  关闭弹出iframe  **/
			window.parent.$.fancybox.close();
			window.parent.art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'导入成功', ok:true,});
		}else if("${param.success}"=="false") {
			window.parent.$.fancybox.close();
			window.parent.art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'导入成功', ok:true,});
			// art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'导入失败,请按模板导入', ok:true,});
		}
	});
	
	/** 检测房源房号是否存在  **/
	
	/** 检测房源房号是否存在并提交form  **/
	
	
	/** 表单验证  **/
	function validateForm(){
		if($("#inputfile").val()==""){
			art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'选择文件', ok:true,});
			return false;
		}
		if($("#inputfile").val().indexOf(".xls")==-1){
			art.dialog({icon:'error', title:'提示', drag:false, resize:false, content:'请选择xls,xlsx文件', ok:true,});
			return false;
		}
		
		return true;
	}
</script>
</head>
<body>
<form method="post" action="" enctype="multipart/form-data" id="fileform" >
	<div id="container">
		<div id="nav_links">
			当前位置：基础数据&nbsp;>&nbsp;<span style="color: #1A5CC6;">模板导入</span>
			<div id="page_close">
				<a href="javascript:parent.$.fancybox.close();">
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
        
		<div class="ui_content">
		  <!-- <form method="post" action="DataExcelImport" enctype="multipart/form-data" id="fileform" > -->
	          <div class="form-group">
                  <label for="inputfile">请选择导入的文件</label>
                  <input type="file" id="inputfile" name="file">
                  <p class="help-block">仅限xls,xlsx格式的文件。</p>
              </div>
              <input type="hidden" name="type" value="${param.type}" id="allIDCheck"/>
              <input id="submitbutton" type="button" value="确认" class="ui_input_btn01"/>
             
         
		</div>
	</div>
</form>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>