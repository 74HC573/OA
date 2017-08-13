package com.yc.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.Users;
import com.yc.biz.UsersBiz;
import com.yc.web.model.JsonModel;


@RestController
public class UsersController {

	@Resource(name="usersBizImpl")
	private UsersBiz usersBiz;
	
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
					session.setAttribute("user", user);
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
		return jsonModel;

	}
	
	@RequestMapping("/user/users_add.action")
	public JsonModel addUser(Users user) {
		JsonModel jm=new JsonModel();
		boolean result=usersBiz.add(user);
		if(result){
			jm.setCode(1);
		} else{
			jm.setCode(0);
		}
		return jm;
		
		

	}
	
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
	
	
	@RequestMapping("/user/uname_list.action")
	public JsonModel uname_list(HttpServletRequest request){
		int did = Integer.parseInt( request.getParameter("did") );
		int gid = Integer.parseInt( request.getParameter("gid") );
		List<Users> list = usersBiz.getUserBygidanddid(did,gid);
		JsonModel jm =new JsonModel();
		jm.setRows(list);     //jm.setObj(list);
		return jm;
	}
}

