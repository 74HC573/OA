<%@page import="org.apache.ibatis.session.RowBounds"%>
<%@page import="com.yc.bean.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<script type="text/javascript">
	$(function() {
		var a='${sessionScope.users.perid}';
		var b='${sessionScope.users.uid}';
		$('#showDocumentTable').edatagrid({
			url : 'user/findDocument.action', //查询时加载的URL
			pagination : true,//显示分页
			pageSize : 10, //默认分页的条数
			pageList : [ 5, 10, 15, 20, 25, 30 ],//可选分页条数
			fitColumns : false,//自适应列
			fit : true,//自动补全
			title : "文件管理",
			idField : "doid",//标识，会记录我们选中的一行的id，不一定是id，通常是主键
			rownumbers : "true",//显示行号
			nowrap : "true",//不换行显示
			sortName : "dotime",//排序的列 这个参数会传到7后台的servlet上，所以要有后台对应的接受
			sortOrder : "desc",//排序方式
			singleSelect : true,

			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError : function(a, b) {
				$.messager.alert("错误", "操作失败");
			},
			columns : [ [
					{
						field : 'doid',
						title : '公文编号',
						width : 100,
						align : 'center',
						hidden : 'true'
					},
					{
						field : 'dotitle',
						title : '公文标题',
						width : 100,
						align : 'center'
					},
					{
						field : 'funame',
						title : '发送者',
						width : 50,
						align : 'center'
					},
					{
						field : 'douname',
						title : '实行者',
						width : 50,
						align : 'center'
					},
					{
						field : 'tuname',
						title : '审批者',
						width : 50,
						align : 'center'
					},
					{
						field : 'dotime',
						title : '上传时间',
						width : 150,
						align : 'center'
					},
					{
						field : 'fname',
						title : '公文名',
						width : 100,
						align : 'center'
					},
					{
						field : 'dostatus',
						title : '审核状态',
						width : 100,
						align : 'center'
					},
					{
						field : 'operate',
						title : '下载',
						align : 'center',
						width : 50,
						formatter : function(val, row, index) {
							if(row.dofid!=null){
								var str = '<a href="javascript:void(0)" onclick="filedownload('
										+ index + ')">下载</a>';
								return str;
							}
						}
					},
					{
						field : 'show',
						title : '公文内容',
						align : 'center',
						width : 70,
						formatter : function(val, row, index) {
							if(b==row.dotouid||b==row.dofromuid){
								var str = '<a href="javascript:void(0)" onclick="showDocument('
										+ index + ')">显示详情</a>';
								return str;
							}
						}
					},
					{
					
						field : 'operate2',
						title : '审批',
						align : 'center',
						width : 100,
						formatter : function(val, row, index) {
							if(b==row.dotouid&&a==1){
								var str = '<a href="javascript:void(0)" onclick="goodDocument('
										+ index
										+ ')">合格</a>/<a href="javascript:void(0)" onclick="badDocument('
										+ index + ')">不合格</a>';
								return str;
							}
						}
					
					},
					{
						field : 'operate3',
						title : '终审',
						align : 'center',
						width : 100,
						formatter : function(val, row, index) {
							if(b==row.dotouid&&a==2){
								var str = '<a href="javascript:void(0)" onclick="passDocument('
										+ index
										+ ')">pass</a>/<a href="javascript:void(0)" onclick="badDocument('
										+ index + ')">no pass</a>';
								return str;
							}
						}
					},{
						field : 'operate4',
						title : '归档',
						align : 'center',
						width : 50,
						formatter : function(val, row, index) {
							if(b==row.dofromuid&&row.dostatus=='已完成'){
								var str = '<a href="javascript:void(0)" onclick="gdDocument('
										+ index
										+ ')">归档</a>';
								return str;
							}
						}
					},
					{
					
						field : 'operate5',
						title : '实施情况',
						align : 'center',
						width : 100,
						formatter : function(val, row, index) {
							if(b==row.douid&&row.dostatus=='任务合格'){
								var str = '<a href="javascript:void(0)" onclick="finishDocument('
										+ index
										+ ')">已完成</a>/<a href="javascript:void(0)" onclick="badDocument('
										+ index + ')">未完成</a>';
								return str;
							}
						}
					
					},
					{
						field : 'showComment',
						title : '建议',
						align : 'center',
						width : 80,
						formatter : function(val, row, index) {
							if(row.dostatus=='不合格'&&b==row.dofromuid){
								var str = '<a href="javascript:void(0)" onclick="showComment('
										+ index + ')">显示详情</a>';
								return str;
							}
						}
					} ] ]
		});

	});
	
	function gdDocument(index) {
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/archiveDocument.action?doid=" + row.doid,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "归档成功！");
					$('#showDocumentTable').datagrid("reload");
				} else {
					$.messager.alert("提示！", "归档失败！"+data.msg);
				}
			}
		});
	}
	

	function goodDocument(index) {
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/goodDocument.action?doid=" + row.doid+"&dofromuid="+row.dofromuid,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "审批成功！");
					$('#showDocumentTable').datagrid("reload");
				} else {
					$.messager.alert("提示！", "审批失败！"+data.msg);
				}
			}
		});
	}
	
	function finishDocument(index) {
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/finishDocument.action?doid=" + row.doid,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "审批成功！");
					$('#showDocumentTable').datagrid("reload");
				} else {
					$.messager.alert("提示！", "审批失败！"+data.msg);
				}
			}
		});
	}

	function badDocument(index) {
		
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$('#dlg').show();
		$('#dlg').dialog({
			
		});
		$('#docommentForm').form('load', row);
	}

	function passDocument(index) {
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/passDocument.action?doid=" + row.doid,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "审批成功！");
					$('#showDocumentTable').datagrid("reload");
				} else {
					$.messager.alert("提示！", "审批失败！"+data.msg);
				}
			}
		});
	}


	function filedownload(index) {
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		location.href = "user/fileDownload.action?fid=" + row.dofid;
	}
	
	function submitForm() {
		$.ajax({
			type : "POST",
			data:$("#docommentForm").serialize(),
			url : "user/badDocument.action",
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "发送成功");
                    $('#dlg').dialog('close');
				} else {
					$.messager.alert("提示！", "发送失败！"+data.msg);
				}
			}
		});
	}
	
	function showDocument(index){
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/findContentBydoid.action?doid="+row.doid,
			dataType : "JSON",
			success : function(data) {
				$('#content_dlg').show();
				$('#content_dlg').dialog({
				});
				//具体内容
				$('#content_div').empty();
				var str1=data.rows[0].docontent;
				$('#content_div').append(str1);
			}
		});

	}
	
	function showComment(index){
		$('#showDocumentTable').datagrid('selectRow', index);
		var row = $('#showDocumentTable').datagrid('getSelected');
		$.ajax({
			type : "POST",
			url : "user/findCommentBydoid.action?doid="+row.doid,
			dataType : "JSON",
			success : function(data) {
				$('#comment_dlg').show();
				$('#comment_dlg').dialog({
				});
				//具体内容
				$('#comment_div').empty();
				var str1=data.rows[0].docomment;
				$('#comment_div').append(str1);
			}
		});

	}
</script>
<title></title>
</head>
<body>
	<table id="showDocumentTable"></table>
	<div id="content_dlg" title="公文详情" style="display: none; padding: 10px; width: 500px;">
		<b>公文具体内容：</b>
		<div id="content_div">
		
		</div>

	</div>
	<div id="comment_dlg" title="建议详情" style="display: none; padding: 10px; width: 500px;">
		<b>建议具体内容：</b>
		<div id="comment_div">
		
		</div>

	</div>
	<%@ include file="docomment.jsp"%>
</body>
</html>