$(function() {

	// 审批流转
	var approvalTree = [
			{
				"text" : "起草申请",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'  frameborder='0' src='toShowNewApproval.action'/>"
				}
			},
			{
				"text" : "我的申请",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' frameborder='0' src='toShowPlan.action'/>"
				}
			},
			{
				"text" : "待我审批",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' frameborder='0' src='toTalk.action'/>"
				}
			},
			{
				"text" : "经我审批",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' frameborder='0' src='toTalk.action'/>"
				}
			},
			{
				"text" : "我的承办",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' frameborder='0' src='toTalk.action'/>"
				}
			},
			{
				"text" : "审批数据查询",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' frameborder='0' src='toTalk.action'/>"
				}
			}

	];

	// 任务计划
	var PoTreeData = [
			{
				"text" : "查看计划",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'  frameborder='0' src='toShowPlan.action'/>"
				}
			},
			{
				"text" : "工作日志",
				"attributes" : {
					"url" : "<iframe width='100%' height='92%' frameborder='0' src='toShowPlaned.action'/>"
				}
			}

	];

	// 信息系统
	var messageTreeData = [ {
		"text" : "消息管理",
		"state" : "closed",
		"children" : [
		              {
		            	  "text" : "发布公告",
		            	  "attributes" : {
		            		  "url" : "<iframe width='100%' height='100%' src='toAddNotice.action'/>"
		            	  }
		              },
		              {
		            	  "text" : "所有公告",
		            	  "attributes" : {
		            		  "url" : "<iframe width='100%' height='100%' src='toManageNotice.action'/>"
		            	  }
		              }
		              ]
	} ];
	
	//公告系统
		var noticeTreeData = [ {
		"text" : "公告管理",
		"state" : "closed",
		"children" : [
				{
					"text" : "发布消息",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='user/toAddMessage.action'/>"
					}
				},
				{
					"text" : "消息管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='user/toManageMessage.action'/>"
					}
				} ]
	} ];

	
	//用户个人中心管理
	var userTreeData = [ {
		"text" : "个人管理",
		"state" : "closed",
		"children" : [ {
			"text" : "个人资料",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' src='toMyselfMessage.action'/>"
			}
		} ]
	} ];
	
	//文件管理
	var fileTreeData = [ {
		"text" : "文件中心",
		"state" : "closed",
		"children" : [
				{
					"text" : "上传文件",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='user/toUploadFile.action'/>"
					}
				},
				{
					"text" : "查看所有文件",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='user/toShowFile.action'/>"
					}

		              } ,
		              {
		            	  "text" : "查看我的文件",
		            	  "attributes" : {
		            		  "url" : "<iframe width='100%' height='100%' src='user/toShowMeFile.action'/>"
		            	  }

		              
				} ]
	} ];
	
	//权限管理
	var powerTreeData = [ {
		"text" : "权限中心",
		"state" : "closed",
		"children" : [
				{
					"text" : "查看权限",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='toShowPower.action'/>"
					}
				},
				{
					"text" : "修改权限",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' frameborder='0' src='toUpdatePower.action'/>"
					}
				} ]
	} ];
	
	//人员管理
	var userpowerTreeData = [ {
		"text" : "人员管理",
		"state" : "closed",
		"children" : [ {
			"text" : "添加人员",
			"attributes" : {
				"url" :

				"<iframe width='100%' height='100%' frameborder='0' src='toAddUser.action'/>"
			}
		}, {
			"text" : "人员列表",
			"attributes" : {
				"url" :

				"<iframe width='100%' height='100%' frameborder='0'  src='toManUser.action'/>"
			}

		} ]
	} ];
	
	//公文管理
	var documentTreeData = [ {
		"text" : "公文",
		"state" : "closed",
		"children" : [ {
			"text" : "公文编辑",
			"attributes" : {
				"url" :

				"<iframe width='100%' height='100%' frameborder='0' src='user/toEditorDocument.action'/>"
			}
		}, {
			"text" : "查看公文",
			"attributes" : {
				"url" :

				"<iframe width='100%' height='100%' frameborder='0' src='user/toManageDocument.action'/>"
			}
		} ]
	} ];

	//在线会话
	var socketTreeData = [ {
		"text" : "会话",
		"attributes" : {
			"url" : "<iframe width='100%' height='100%' frameborder='0' src='toTalk.action'/>"
		}
	} ];

	showTree("websocketTree", socketTreeData);
	showTree("PoTree", PoTreeData);
	showTree("approvalTree", approvalTree);
	showTree("messageTree", messageData);
	showTree("noticeTree", noticeTreeData);
	showTree("userTree", userTreeData);
	showTree("fileTree", fileTreeData);
	showTree("powerTree", powerTreeData);
	showTree("userpowerTree", userpowerTreeData);
	showTree("documentTree", documentTreeData);
});
function showTree(treeId, data) {
	$("#" + treeId).tree({
		data : data, // 将这里改成：url:"right.action" 这个地址会得到一个上面 treeData这样的字符串
		onClick : function(node) {
			if (node.attributes) {
				openTab(node);
			}
		}
	});
};
function openTab(node) {
	if ($("#mainTt").tabs("exists", node.text)) {
		$("#mainTt").tabs("select", node.text);
	} else {
		$("#mainTt").tabs("add", {
			title : node.text,
			selected : true,
			closable : true,

			/*
			 * url : node.attributes.url, tools : [ { iconCls : 'icon-cancel',
			 * handler : function() { $(this).panel("close"); } } ],
			 */
			// href:node.attributes.url
			content : node.attributes.url
		})
	}
}


