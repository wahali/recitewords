package com.eng.recitewords.controller;

import com.alibaba.fastjson.JSON;
import com.eng.recitewords.entity.Admin;
import com.eng.recitewords.entity.Question;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.service.AdminService;
import com.eng.recitewords.service.UserService;
import com.eng.recitewords.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private WordsService wordsService;
    @Autowired
    private UserService userService;

    @RequestMapping("/admin/tables")
    public String tables(Model model) {
        List<User> userlist = adminService.selectAllUser();
        model.addAttribute("userlist",userlist);
        System.out.println("tables are coming!");
        return "admin/tables";
    }

    @RequestMapping("/admin/admin_tables")
    public String admin_tables(Model model) {
        List<Admin> adminlist = adminService.selectAllAdmin();
        model.addAttribute("adminlist",adminlist);
        System.out.println("admin_tables are coming!");
        return "admin/admin_tables";
    }

    @RequestMapping("/admin/index")
    public String index(){
        System.out.println("dashboard is coming!");
        return "admin/index";
    }

    @RequestMapping("/admin/add")
    public String add(){
        System.out.println("add is coming!");
        return "admin/add";
    }

    @RequestMapping("/admin/add_words")
    public String add_words(){
        System.out.println("add_words is coming!");
        return "admin/add_words";
    }

    @RequestMapping("/Add")
    public String Add(
            @RequestParam("adminName") String adminName,
            @RequestParam("adminTel") String adminTel,
            @RequestParam("adminEmail") String adminEmail) {
//            response.setCharacterEncoding("utf-8");
        String adminId = UUID.randomUUID().toString().replace("-","");
        String adminPassword = adminTel;
        System.out.println(adminId + " " + adminName + " " + adminTel + " " + adminEmail + " " + adminPassword + "\n");
        adminService.addInfo(adminId,adminName,adminTel,adminEmail,adminPassword);
        return "redirect:/admin/admin_tables";
    }

    @RequestMapping("/admin/delete/{adminId}")
    public String delete(
            @PathVariable("adminId") String adminId
    ){
        adminService.deleteInfo(adminId);
        System.out.println("delete is coming!");
        return "redirect:/admin/admin_tables";
    }

    @RequestMapping("/admin/deleteUser/{userId}")
    public String deleteUser(
            @PathVariable("userId") String userId
    ){
        adminService.deleteUser(userId);
        System.out.println("deleteUser is coming!");
        return "redirect:/admin/tables";
    }

    @RequestMapping("/admin/update/{adminId}")
    public String update(@PathVariable("adminId") String adminId ,Model model){
        Admin admin = adminService.selectAdminById(adminId);
        model.addAttribute("admin",admin);
        System.out.println("update is coming!" + admin);
        return "admin/update";
    }

    @RequestMapping("/admin/updateUser/{userId}")
    public String updateUser(@PathVariable("userId") String userId ,Model model){
        User user = userService.selectById(userId);
        model.addAttribute("user",user);
        System.out.println("update is coming!" + user);
        return "admin/updateUserInfo";
    }

    @RequestMapping("/admin/userContent")
    public String userContent(
            @RequestParam("userId") String userId,
            @RequestParam("userName") String userName,
            @RequestParam("userTel") String userTel,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userLevel") String userLevel,
            @RequestParam("userTarget") int userTarget, HttpSession session) {
//            response.setCharacterEncoding("utf-8");
        System.out.println(userName + " " + userTel + " " + userEmail + "\n");
        userService.updateUserInfo(userId,userName,userTel,userEmail,userPassword,userLevel,userTarget);
        System.out.println("更新用户信息成功！");
//        User user = (User)session.getAttribute("user");
//        user.setUserTarget(userTarget);
//        user.setUserLevel(userLevel);
//        user.setUserTel(userTel);
//        user.setUserEmail(userEmail);
//        user.setUserPassword(userPassword);
//        user.setUserName(userName);
//        session.setAttribute("userName",userName);
//        session.setAttribute("userTel",userTel);
//        session.setAttribute("userEmail",userEmail);
//        session.setAttribute("userPassword",userPassword);
//        session.setAttribute("userLevel",userLevel);
//        session.setAttribute("userTarget",userTarget);
        return "redirect:/admin/tables";
    }

    @RequestMapping("/layerContent")
    public String layerContent(
            @RequestParam("adminId") String adminId,
            @RequestParam("adminName") String adminName,
            @RequestParam("adminTel") String adminTel,
            @RequestParam("adminEmail") String adminEmail) {
//            response.setCharacterEncoding("utf-8");
            System.out.println(adminName + " " + adminTel + " " + adminEmail + "\n");
            adminService.updateInfo(adminId,adminName,adminTel,adminEmail);
            return "redirect:/admin/admin_tables";
    }

    @RequestMapping("/admin/Words")
    public String wordsSelect(Model model){
        List<Words> wordsList = wordsService.selectAllWords();
        model.addAttribute("wordsList",wordsList);
        return "admin/Words";
    }

    @RequestMapping("/Add_words")
    public String insertWord(
            @RequestParam("englishWord") String englishWord,
            @RequestParam("pa") String pa,
            @RequestParam("chineseWord") String chineseWord) {
        String wordId = UUID.randomUUID().toString().replace("-","");
        //String adminPassword = adminTel;
        //System.out.println(adminId + " " + adminName + " " + adminTel + " " + adminEmail + " " + adminPassword + "\n");
        wordsService.insertWord(wordId,englishWord,pa,chineseWord);
        return "redirect:/admin/Words";
    }

    @RequestMapping("/admin/delete_word/{wordId}")
    public String delete_word(
            @PathVariable("wordId") String wordId
    ){
        wordsService.deleteWord(wordId);
        System.out.println("deleteWord is coming!");
        return "redirect:/admin/Words";
    }

    @RequestMapping("/admin/update_word/{wordId}")
    public String update_word(
            @PathVariable("wordId") String wordId,Model model){
        Words word = wordsService.selectWordById(wordId);
        model.addAttribute("word",word);
        System.out.println("update_word is coming!");
        return "admin/update_word";
    }

    @RequestMapping("/WordsContent")
    public String WordsContent(
            @RequestParam("wordId") String wordId,
            @RequestParam("englishWord") String englishWord,
            @RequestParam("pa") String pa,
            @RequestParam("chineseWord") String chineseWord,
            @RequestParam("englishInstance1") String englishInstance1,
            @RequestParam("chineseInstance1") String chineseInstance1,
            @RequestParam("englishInstance2") String englishInstance2,
            @RequestParam("chineseInstance2") String chineseInstance2,
            @RequestParam("collect") int collect,
            @RequestParam("pron") String pron) {
//            response.setCharacterEncoding("utf-8");
        System.out.println("successfully done!");
        wordsService.updateWord(wordId,englishWord,pa,chineseWord,englishInstance1,chineseInstance1,englishInstance2,chineseInstance2,collect,pron);
        return "redirect:/admin/Words";
    }

    @RequestMapping("/login")
    public String logout(){

        return "login";
    }

    @RequestMapping("admin/questionTable")
    public String questionTable(Model model){
        List<Question> questions = adminService.checkQuestion();
        model.addAttribute("questions",questions);
        return "admin/questionTable";
    }

    @RequestMapping("admin/questionDetail/{questionId}")
    public String questionDetail(@PathVariable("questionId") String questionId, Model model){
        Question question = adminService.selectByQuestionId(questionId);
        model.addAttribute("thisQuestion",question);
        return "admin/questionDetail";
    }

    @RequestMapping("passQuestion")
    public void passQuestion(@RequestParam("questionId")String questionId, HttpServletResponse response) throws IOException {
        boolean flag = false;
        flag = adminService.passQuestion(questionId);
        if (flag){
            response.getWriter().print("success");
        }else {
            response.getWriter().print("fail");
        }
    }

    @RequestMapping("failQuestion")
    public void failQuestion(@RequestParam("questionId")String questionId,
                             @RequestParam("suggestion")String suggestion,
                             HttpServletResponse response) throws IOException {
        boolean flag = false;
        flag = adminService.failQuestion(questionId,suggestion);
        if (flag){
            response.getWriter().print("success");
        }else {
            response.getWriter().print("fail");
        }
    }

}
