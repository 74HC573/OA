package com.yc.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yc.bean.Users;
import com.yc.biz.FileuploadBiz;
import com.yc.biz.UsersBiz;
import com.yc.utils.Encrypt;
import com.yc.utils.FileuploadReady;
import com.yc.web.model.JsonModel;


@RestController
public class UsersController {

	@Resource(name="usersBizImpl")
	private UsersBiz usersBiz;

	@Resource(name = "fileuploadBizImpl")
	private FileuploadBiz fileuploadBiz;



	@RequestMapping("/users_login.action")
	public JsonModel login(Users user, HttpServletRequest request, HttpSession session) {
		JsonModel jsonModel = new JsonModel();
		String zccode = request.getParameter("zccode");
		String rand = session.getAttribute("rand").toString();
		if (!rand.equals(zccode)) {
			jsonModel.setCode(0);
			jsonModel.setMsg("验证码错误");
		} else {
			try{
				user = usersBiz.login(user);
				if (user != null) {
					jsonModel.setCode(1);
					user.setUpwd(null);   // 设为空后,密码就不会传到界面
					jsonModel.setObj(user);
				} else {
					jsonModel.setCode(0);
					jsonModel.setMsg("用户名或密码错误");
				}

			}catch(Exception e){
				e.printStackTrace();
				jsonModel.setCode(0);
				jsonModel.setMsg(e.getMessage());
			}
		}
		session.setAttribute("users", user);
		return jsonModel;
	}

	//	@RequestMapping("/users_login.action")
	//	public JsonModel login(Users user, HttpServletRequest request, HttpSession session) {
	//		JsonModel jsonModel = new JsonModel();
	//		String zccode = request.getParameter("zccode");
	//		String rand = session.getAttribute("rand").toString();
	//		if (!rand.equals(zccode)) {
	//			jsonModel.setCode(0);
	//			jsonModel.setMsg("验证码错误");
	//		} else {
	//			try{
	//				user = usersBiz.login(user);
	//				if (user != null) {
	//					jsonModel.setCode(1);
	//					user.setUpwd(null);   // 设为空后,密码就不会传到界面
	//					jsonModel.setObj(user);
	//				} else {
	//					jsonModel.setCode(0);
	//					jsonModel.setMsg("用户名或密码错误");
	//				}
	//			}catch(Exception e){
	//				e.printStackTrace();
	//				jsonModel.setCode(0);
	//				jsonModel.setMsg(e.getMessage());
	//			}
	//		}
	//		session.setAttribute("users", user);
	//		return jsonModel;
	//	}

	//添加员工
	@RequestMapping("/user/users_add.action")
	public JsonModel addUsers(Users users, HttpSession session,
			@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
		FileuploadReady fileuploadReady = new FileuploadReady();
		JsonModel jsonModel = new JsonModel();
		String fileName = "";
		Map<String, String> map = fileuploadReady.upload(fileName, file, request);
		String destFilePathName = map.get("destFilePathName");
		String weburl=map.get("weburl");
		users.setPhoto(weburl);
		File newFile = new File(destFilePathName);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(newFile);
		boolean r=usersBiz.add(users);
		if (r) {
			jsonModel.setCode(1);
		} else {
			jsonModel.setCode(0);
		}
		return jsonModel;
	}

	//修改员工资料
	@RequestMapping("/updateUser.action")
	public JsonModel updateUser(Users users) {
		JsonModel jm=new JsonModel();
		if (users.getDid()!=null&&!users.getDid().equals("")&&
				users.getGid()!=null&&!users.getGid().equals("")&&
				users.getUstatus()!=null&&!users.getUstatus().equals("")) {
			boolean result=usersBiz.updateUsers(users);
			if(result){
				jm.setCode(1);
			} else{
				jm.setCode(0);
			}
		}else{
			jm.setCode(0);
			jm.setMsg("数据不能为空！");
		}
		return jm;
	}

	//修改个人信息
	@RequestMapping("/updatePwd.action")
	public JsonModel updatePwd(Users users,HttpServletRequest request) {
		JsonModel jm=new JsonModel();
		users.setUpwd(request.getParameter("pwd"));
		if(!users.getUpwd().equals(users.getRepwd())){
			jm.setCode(0);
			jm.setMsg("两次密码不相等！");
			return jm;
		}
		if (users.getUpwd()!=null&&!users.getUpwd().equals("")) {
			users.setUpwd(Encrypt.md5AndSha(users.getUpwd()));
			boolean result=usersBiz.updatePwd(users);
			if(result){
				jm.setCode(1);
			} else{
				jm.setCode(0);
			}
		}else{
			jm.setCode(0);
			jm.setMsg("新密码不能为空！");
		}
		request.setAttribute("jm", jm);
		return jm;
	}

	// 删除人员(员工离职)
	@RequestMapping("/delUsers.action")
	public JsonModel delUsers(Integer uid) throws Exception {
		boolean flag = this.usersBiz.delUsers(uid);
		JsonModel jm = new JsonModel();
		if (flag) {
			jm.setCode(1);
		} else {
			jm.setCode(0);
		}
		return jm;
	}

	//查询所有员工,加载到页面分页等
	@RequestMapping("/user/manUser.action")
	public JsonModel manUser(Users users,HttpServletRequest request) throws Exception {
		JsonModel jModel=new JsonModel();
		int pages=Integer.parseInt(request.getParameter("page").toString());
		int pagesize=Integer.parseInt(request.getParameter("rows").toString());
		int start=(pages-1)*pagesize;
		users.setStart(start);
		users.setPagesize(pagesize);
		List<Users> list=usersBiz.getAllUsers(users);
		Integer count =usersBiz.getAllUsersCount(users);
		jModel.setRows(list);
		jModel.setTotal(count);
		return jModel;
		//easyUI要求的格式


	}

	//查询所有员工,加载到页面分页等
	@RequestMapping("/user/myselfMessage.action")
	public JsonModel myselfMessage(Users users,HttpSession session) throws Exception {
		JsonModel jModel=new JsonModel();
		users=(Users) session.getAttribute("users");
		List<Users> list= usersBiz.getUsersByUid(users.getUid());
		jModel.setRows(list);
		return jModel;
		//easyUI要求的格式

	}


	@RequestMapping("/user/uname_list.action")
	public JsonModel uname_list(HttpServletRequest request){
		int did = Integer.parseInt( request.getParameter("did") );
		int gid = Integer.parseInt( request.getParameter("gid") );
		List<Users> list = usersBiz.getUserBygidanddid(did,gid);
		JsonModel jm =new JsonModel();
		jm.setRows(list);     //jm.setObj(list);
		return jm;
	}


	@RequestMapping(value="/user/findPermissionforuser.action")
	private JsonModel findPermissionforuser(Users users,HttpServletRequest request) throws Exception {
		JsonModel jsonModel = new JsonModel();
		int pages=0;
		int pagesize =0;
		if(request.getParameter("page").toString()!=null){
			pages = Integer.parseInt(request.getParameter("page").toString());
		}
		if(request.getParameter("rows").toString()!=null){
			pagesize = Integer.parseInt(request.getParameter("rows").toString());
		}
		int start = (pages-1)*pagesize;
		users.setStart(start);
		users.setPagesize(pagesize);
		List<Users> list = usersBiz.findPermissionforUser(users);
		Integer count = usersBiz.findPermissionCount(users);
		jsonModel.setPageSize(pagesize);
		jsonModel.setRows(list);
		jsonModel.setTotal(count);
		return jsonModel;
	}
}

