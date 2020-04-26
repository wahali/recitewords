package com.eng.recitewords.controller;

import com.alibaba.fastjson.JSONObject;
import com.eng.recitewords.entity.Admin;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.service.AdminService;
import com.eng.recitewords.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class CurrencyController {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @RequestMapping({"/","/index.html", "index"})
    public String index() {
        return "login";
    }

    @RequestMapping("/currency/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("userName") String userName,
                        @RequestParam("password") String password,
//                        @RequestParam("md5password") String md5password,
                        @RequestParam("kind") String kind,
                        Model model, HttpSession session) {
//        System.out.println(email + " " + password + " "+ kind);

        if(kind.equals("adminUser")) {
//            System.out.println("我是管理员");
            Admin admin = adminService.selectByEmail(email);
//            System.out.println(admin.getAdminPassword());
            if ((admin == null) || (!admin.getAdminPassword().equals(password))) {
                model.addAttribute("msg","用户未注册或账号密码错误");
                return "login";
            } else {
                session.setAttribute("admin", admin);
                return "admin/index";
            }

//            System.out.println(admin.getAdminPassword());
//            session.setAttribute("admin", admin);
//            return "admin/index";
//            String msg = adminService.adminLogin(email, password);
//            if(msg.equals("success")) {

//                model.addAttribute("admin", admin);


//            } else {
//                model.addAttribute("msg", msg);
//                model.addAttribute("email", email);
////                model.addAttribute("password", password);
//                return "login";
//            }
        } else {
//            System.out.println("我是普通用户");
//            String msg = userService.userLogin(email, password);
//            if(msg.equals("success")) {
            User user = userService.selectByEmail(email);
            if(user == null){
                user  = userService.selectByUserName(userName);
            }
            if((user == null) || (!user.getUserPassword().equals(password))) {
                model.addAttribute("msg","用户未注册或账号密码错误");
                return "login";
            } else {
                session.setAttribute("user", user);
                return "user/index";
            }
//                System.out.println(user.getUserPassword());
//                model.addAttribute("user", user);

//            } else {
//                model.addAttribute("msg",msg);
//                model.addAttribute("email", email);
////                model.addAttribute("password", password);
//                return "login";
//            }

        }
    }

    @RequestMapping("/register")
    public String register() {
        return "user/register";
    }

    @RequestMapping("/loginCheck")
    public void loginCheck(@RequestParam("email") String email,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("kind") String kind,
                           HttpServletResponse response) {
//        System.out.println(email + " " + password + " " + kind);
//        User user = userService.selectByEmail(email);
//        if(user == null) {
//            try {
//                response.setCharacterEncoding("utf-8");
//                response.getWriter().print("<font>该用户未注册，请注册</font>");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        response.setCharacterEncoding("utf-8");
        if(kind.equals("adminUser")) {
            Admin admin = adminService.selectByEmail(email);
            if(admin == null) {
                try {
                    JSONObject json = new JSONObject();
                    String content = "该用户未注册，请注册";
                    json.put("hjk",content);

                    response.getWriter().print(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(!admin.getAdminPassword().equals(password)) {
                try {
                    JSONObject json = new JSONObject();
                    String content = "该用户未注册，请注册";
                    json.put("hjk",content);

                    response.getWriter().print(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            User user = userService.selectByEmail(email);
            if(user == null){
                user  = userService.selectByUserName(userName);
            }
            if(user == null) {
                try {
                    JSONObject json = new JSONObject();
                    String content = "该用户未注册，请注册";
                    json.put("hjk",content);

                    response.getWriter().print(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(!user.getUserPassword().equals(password)) {
                try {
                    JSONObject json = new JSONObject();
                    String content = "该用户未注册，请注册";
                    json.put("hjk",content);

                    response.getWriter().print(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
