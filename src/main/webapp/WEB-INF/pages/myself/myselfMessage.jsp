<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<title>人员列表</title>
</head>
<body>
<script type="text/javascript">
	$(function(params){
		$('#manTypeTable').edatagrid({
			url:'user/myselfMessage.action',//查询时加载的URL
			//pagination:true,  //显示分页
			//pageSize:5, //默认的分页条数
			//pageList :[5,10,15,20,25,30,35,40,45,50,100,200,500],//可选分页条数
			fitColumns:true,//自适应列
			fit:true,//自动补全
			title:"人员管理",
			idField:"uid",//标识,会记录我们选中的一行的id,不一定是id 通常都是主键
			rownumbers:"true",//显示行号
			nowrap:"true",//不换行显示
			sortName:"uid",//排序的列,这个参数会传到后台的servlet上,所有要有后台对应的接收
			sortOrder:"desc",//排序方式
			singleSelect:true,
			toolbar:['#tb',{//工具条
		    	//这里是修改按钮的具体操作
		        text:'修改密码',
		        iconCls:'icon-edit',
		        handler:function(){
      			 var rows = $('#manTypeTable').datagrid('getSelected');
		            if (null == rows || rows.length == 0) {  
		               noSelect();
		               return;  
		            } 
		        	$('#update_dlg').show();
		        	$('#update_dlg').dialog({
		        		 iconCls: 'icon-edit',
		        	});
		        	$('#updateUserForm').form('load', rows);
		        }
		    } ],
		
			//以上四种增删改查操作,只要失败,都会回调这个onError
			onError : function(a,b){
				$.messager.alert("错误","操作失败");
			},
			columns:[[{
				field:'uid',
				title:'人员编号',
				width:100,
				align:'center',
				hidden:'true'
			},{
				field:'uname',
				title:'人员名字',
				width:100,
				align:'center'
			},{
				field:'sex',
				title:'性别',
				width:100,
				align:'center'
			},{
				field:'entrytime',
				title:'入职时间',
				width:100,  
				align:'center'
			},{
				field:'tel',
				title:'联系电话',
				width:100,
				align:'center'
			},{
				field:'address',
				title:'家庭地址',
				width:100,
				align:'center'
			},{
				field:'department',
				title:'所属部门',
				width:100,
				align:'center'
			},{
				field:'gname',
				title:'所属小组',
				width:100,
				align:'center'
			},{
				field:'ustatus',
				title:'显示状态',
				width:100,
				align:'center'
			}]]
			
		})
	
	});
	function clearForm() {
		$('#updateUserForm').form('clear');
	}
	
	function updateForm() {
		$.ajax({
			type : "POST",
			data:$("#updateUserForm").serialize(),
			url : "updatePwd.action",
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$.messager.alert("提示！", "信息修改成功！");
					clearForm();
                    $('#update_dlg').dialog('close');
                    $('#manTypeTable').datagrid("reload");
                    $.messager.confirm('提示', '您的身份信息已过期，请重新登录', function(){
        				top.location.href="index.action";
        			});
                   
				} else {
					$.messager.alert("提示！", "信息修改失败！"+data.msg);
				}
			}
		});
	}
	
	//如果没有选择数据
	function noSelect() {
		$.messager.show({
			title : '请注意',
			msg : '请选择数据记录后继续该操作！',
			timeout : 5000,
			showType : 'slide'
		});
	}
	
	
	 
</script>
</head>
<body>

<table id="manTypeTable"></table>

<%@ include file="myselfUpdate.jsp" %>	
</body>
</html>