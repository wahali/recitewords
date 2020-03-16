package com.eng.recitewords.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.service.UserService;
import com.eng.recitewords.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WordsService wordsService;

//    @GetMapping("/user/{userId}")
//    public String selectById(@PathVariable("userId") String userId, Model model) {
//        User user = userService.selectById(userId);
//        model.addAttribute("user",user);
//        return "user/userShow";
//    }

    @RequestMapping("/user/index")
    public String index(){
        return "user/index";
    }

    @RequestMapping("/user/register")
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("userTel") String userTel,
                           @RequestParam("userEmail") String userEmail,
                           @RequestParam("userPassword") String userPassword,
//                           @RequestParam("userPasswordAgain") String userPasswordAgain,
//                           @RequestParam("md5password") String md5password,
//                           @RequestParam("md5passwordAgain") String md5passwordAgain,
                           Model model) {
//        System.out.println(userName + " " + userTel + " " + userEmail + " " + userPassword + " " + userPasswordAgain + " " + md5password + " " + md5passwordAgain);
//        if(!userPassword.equals(userPasswordAgain)) {
//            model.addAttribute("msg", "两次输入密码不一致");
//            return "user/register";
//        } else {
//            String msg = userService.register(userName, userTel, userEmail, userPassword);
        userService.register(userName, userTel, userEmail, userPassword);
//            if(! "success".equals(msg)) {
//                System.out.println(msg);
//                model.addAttribute("msg",msg);
//                return "user/register";
//            } else {
        return "redirect:/";
//            }
//        }
    }

    @RequestMapping("/user/forgot_password")
    public String forget_password() {
        return "user/forgot-password";
    }

    @RequestMapping("/user/resetPassword")
    public String resetPassword(@RequestParam("userEmail") String userEmail, Model model) {
        System.out.println(userEmail);
        String msg = userService.resetPassword(userEmail);
        if(msg.equals("success")) {
            //直接返回新的密码，通过邮箱返回给用户
            return "redirect:/";
        } else {
            model.addAttribute("msg", msg);
            model.addAttribute("email", userEmail);
            return "user/forgot-password";
        }
    }

    @RequestMapping("/resetPasswordCheck")
    public void resetPasswordCheck(@RequestParam("userEmail") String userEmail,HttpServletResponse response) {
//        System.out.println(userEmail);
        User user = userService.selectByEmail(userEmail);
        if (user == null) {
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().print("<font>该用户不存在</font>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/user/logOut")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/registCheck")
    public void registCheck(@RequestParam("userEmail") String userEmail, HttpServletResponse response) {
//        System.out.println(userEmail);
        User user = userService.selectByEmail(userEmail);
        if(user != null) {
            try {
                response.setCharacterEncoding("utf-8");
                response.getWriter().print("<font>该用户已注册，请登录</font>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/user/recitation")
    public String recitation(Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<Words> wordsList = userService.selectPartWord(user.getUserLast(),user.getUserLast() + user.getUserTarget());
//        System.out.println(wordsList.size());
        List<String> wordsIdList = wordsService.selectWordsByUId(user.getUserId());
        List<Words> wordsErrorList = wordsService.selectByWId(wordsIdList);
        String index = "0";
        model.addAttribute("word",wordsList.get(Integer.parseInt(index)));
        model.addAttribute("index",index);
        model.addAttribute("wordErrors", wordsErrorList);
        request.getSession().setAttribute("words",wordsList);
//        System.out.println(wordsList.get(0));
        return "user/recitation";
    }

    @RequestMapping("/user/errorWords")
    public String errorWords(Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<String> wordsIdList = wordsService.selectWordsByUId(user.getUserId());
        List<Words> wordsErrorList = wordsService.selectByWId(wordsIdList);
        List<String> indexList = new ArrayList<>();
        for(int i = 0; i < wordsErrorList.size(); i++) {
            indexList.add(String.valueOf(i));
        }
        model.addAttribute("wordErrors", wordsErrorList);
        model.addAttribute("indexs",indexList);
        return "user/errorWords";
    }
    @RequestMapping("/chooseC/nextWord")
    public void nextWord(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        User user = (User)request.getSession().getAttribute("user");
        if(user.getUserTarget() <= Integer.parseInt(index)) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) + 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();
            json.put("word",word);
            json.put("index",index);

            long t = System.currentTimeMillis();
            Random random = new Random(t);
            HashMap<String, Object> map = new HashMap<>();
            int num = random.nextInt(4)+1;
//        System.out.println(num);
            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
            for(int i = 1 ; i <= 4; i++) {
                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
                    map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
                } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
                    i--;
                }
            }

            json.put("map",map);
            System.out.println(map);

            Words word1 = wordsList.get(Integer.parseInt(index));

//            String wd = word1.getEnglishWord();
//            String sentence1 = word1.getEnglishInstance1();
//            String sentence2 = word1.getEnglishInstance2();
//            String wd1 = wd.substring(0,wd.length()-1);
//            wd1 = "(\\w)*" + wd1 + "\\w";
//            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
//            Matcher matcher1 = pattern.matcher(sentence1);
//            Matcher matcher2 = pattern.matcher(sentence2);
//            sentence1 = matcher1.replaceAll(" ______ ");
//            sentence2 = matcher2.replaceAll(" ______ ");
////            model.addAttribute("sentence1",sentence1);
////            model.addAttribute("sentence2",sentence2);
//            json.put("sentence1",sentence1);
//            json.put("sentence2",sentence2);


            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/fillBlanks/nextWord")
    public void nextWordInFillBlanks(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        User user = (User)request.getSession().getAttribute("user");
        if(user.getUserTarget() <= Integer.parseInt(index)) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) + 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();
            json.put("word",word);
            json.put("index",index);

//            long t = System.currentTimeMillis();
////            Random random = new Random(t);
////            HashMap<String, Object> map = new HashMap<>();
////            int num = random.nextInt(4)+1;
//////        System.out.println(num);
////            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
//////        System.out.println(map.get(String.valueOf(num)) == null);
////            for(int i = 1 ; i <= 4; i++) {
////                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
////                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
////                    map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
////                } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
////                    i--;
////                }
////            }
////
////            json.put("map",map);
////            System.out.println(map);

            Words word1 = wordsList.get(Integer.parseInt(index));

            String wd = word1.getEnglishWord();
            String sentence1 = word1.getEnglishInstance1();
            String sentence2 = word1.getEnglishInstance2();
            String wd1 = wd.substring(0,wd.length()-1);
            wd1 = "(\\w)*" + wd1 + "\\w";
            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
            Matcher matcher1 = pattern.matcher(sentence1);
            Matcher matcher2 = pattern.matcher(sentence2);
            sentence1 = matcher1.replaceAll(" ______ ");
            sentence2 = matcher2.replaceAll(" ______ ");
//            model.addAttribute("sentence1",sentence1);
//            model.addAttribute("sentence2",sentence2);
            json.put("sentence1",sentence1);
            json.put("sentence2",sentence2);


            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/chooseE/nextWord")
    public void nextWordInChooseE(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        User user = (User)request.getSession().getAttribute("user");
        if(user.getUserTarget() <= Integer.parseInt(index)) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) + 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();
            json.put("word",word);
            json.put("index",index);

            long t = System.currentTimeMillis();
            Random random = new Random(t);
            HashMap<String, Object> map = new HashMap<>();
            int num = random.nextInt(4)+1;
//        System.out.println(num);
            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getEnglishWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
            for(int i = 1 ; i <= 4; i++) {
                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getEnglishWord())) {
                    map.put("map"+String.valueOf(i), wordsList.get(count).getEnglishWord());
                } else if(map.containsValue(wordsList.get(count).getEnglishWord())) {
                    i--;
                }
            }

            json.put("map",map);
            System.out.println(map);

//            Words word1 = wordsList.get(Integer.parseInt(index));

//            String wd = word1.getEnglishWord();
//            String sentence1 = word1.getEnglishInstance1();
//            String sentence2 = word1.getEnglishInstance2();
//            String wd1 = wd.substring(0,wd.length()-1);
//            wd1 = "(\\w)*" + wd1 + "\\w";
//            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
//            Matcher matcher1 = pattern.matcher(sentence1);
//            Matcher matcher2 = pattern.matcher(sentence2);
//            sentence1 = matcher1.replaceAll(" ______ ");
//            sentence2 = matcher2.replaceAll(" ______ ");
////            model.addAttribute("sentence1",sentence1);
////            model.addAttribute("sentence2",sentence2);
//            json.put("sentence1",sentence1);
//            json.put("sentence2",sentence2);


            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/chooseC/preWord")
    public void preWord(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        response.setCharacterEncoding("utf-8");
        User user = (User)request.getSession().getAttribute("user");
//        System.out.println(user);
        if(Integer.parseInt(index) <= 0) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) - 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();

            json.put("word",word);
            json.put("index",index);

            long t = System.currentTimeMillis();
            Random random = new Random(t);
            HashMap<String, Object> map = new HashMap<>();
            int num = random.nextInt(4)+1;
//        System.out.println(num);
            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
            for(int i = 1 ; i <= 4; i++) {
                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
                    map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
                } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
                    i--;
                }
            }
            json.put("map",map);

            Words word1 = wordsList.get(Integer.parseInt(index));

//            String wd = word1.getEnglishWord();
//            String sentence1 = word1.getEnglishInstance1();
//            String sentence2 = word1.getEnglishInstance2();
//            String wd1 = wd.substring(0,wd.length()-1);
//            wd1 = "(\\w)*" + wd1 + "\\w";
//            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
//            Matcher matcher1 = pattern.matcher(sentence1);
//            Matcher matcher2 = pattern.matcher(sentence2);
//            sentence1 = matcher1.replaceAll(" ______ ");
//            sentence2 = matcher2.replaceAll(" ______ ");
////            model.addAttribute("sentence1",sentence1);
////            model.addAttribute("sentence2",sentence2);
//            json.put("sentence1",sentence1);
//            json.put("sentence2",sentence2);

            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/fillBlanks/preWord")
    public void preWordInFillBlanks(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        User user = (User)request.getSession().getAttribute("user");
        if(user.getUserTarget() <= Integer.parseInt(index)) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) - 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();
            json.put("word",word);
            json.put("index",index);

//            long t = System.currentTimeMillis();
////            Random random = new Random(t);
////            HashMap<String, Object> map = new HashMap<>();
////            int num = random.nextInt(4)+1;
//////        System.out.println(num);
////            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
//////        System.out.println(map.get(String.valueOf(num)) == null);
////            for(int i = 1 ; i <= 4; i++) {
////                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
////                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
////                    map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
////                } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
////                    i--;
////                }
////            }
////
////            json.put("map",map);
////            System.out.println(map);

            Words word1 = wordsList.get(Integer.parseInt(index));

            String wd = word1.getEnglishWord();
            String sentence1 = word1.getEnglishInstance1();
            String sentence2 = word1.getEnglishInstance2();
            String wd1 = wd.substring(0,wd.length()-1);
            wd1 = "(\\w)*" + wd1 + "\\w";
            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
            Matcher matcher1 = pattern.matcher(sentence1);
            Matcher matcher2 = pattern.matcher(sentence2);
            sentence1 = matcher1.replaceAll(" ______ ");
            sentence2 = matcher2.replaceAll(" ______ ");
//            model.addAttribute("sentence1",sentence1);
//            model.addAttribute("sentence2",sentence2);
            json.put("sentence1",sentence1);
            json.put("sentence2",sentence2);


            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/chooseE/preWord")
    public void preWordInChooseE(@RequestParam("index") String index, Model model, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        List<Words> wordsList = (List<Words>) request.getSession().getAttribute("words");
        User user = (User)request.getSession().getAttribute("user");
        if(user.getUserTarget() <= Integer.parseInt(index)) {
            try {
                response.getWriter().print("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index = String.valueOf(Integer.parseInt(index) - 1);
            Words word = wordsList.get(Integer.parseInt(index));
            JSONObject json = new JSONObject();
            json.put("word",word);
            json.put("index",index);

            long t = System.currentTimeMillis();
            Random random = new Random(t);
            HashMap<String, Object> map = new HashMap<>();
            int num = random.nextInt(4)+1;
//        System.out.println(num);
            map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getEnglishWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
            for(int i = 1 ; i <= 4; i++) {
                int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
                if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getEnglishWord())) {
                    map.put("map"+String.valueOf(i), wordsList.get(count).getEnglishWord());
                } else if(map.containsValue(wordsList.get(count).getEnglishWord())) {
                    i--;
                }
            }

            json.put("map",map);
            System.out.println(map);

//            Words word1 = wordsList.get(Integer.parseInt(index));

//            String wd = word1.getEnglishWord();
//            String sentence1 = word1.getEnglishInstance1();
//            String sentence2 = word1.getEnglishInstance2();
//            String wd1 = wd.substring(0,wd.length()-1);
//            wd1 = "(\\w)*" + wd1 + "\\w";
//            Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
//            Matcher matcher1 = pattern.matcher(sentence1);
//            Matcher matcher2 = pattern.matcher(sentence2);
//            sentence1 = matcher1.replaceAll(" ______ ");
//            sentence2 = matcher2.replaceAll(" ______ ");
////            model.addAttribute("sentence1",sentence1);
////            model.addAttribute("sentence2",sentence2);
//            json.put("sentence1",sentence1);
//            json.put("sentence2",sentence2);


            try {
                response.getWriter().print(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/addError")
    public void addError(String wordId, HttpServletRequest request,HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        response.setCharacterEncoding("utf-8");
//        System.out.println(user);
        int count = 0;
        count = wordsService.selectErrorWordByWId(wordId);
//        System.out.println(count);
        if(count == 0) {
            wordsService.insertValue(wordId, user.getUserId());
//        Words word = wordsService.selectOneByWId(wordId);
////        System.out.println(word);
//        JSONObject json = new JSONObject();
//        json.put("word",word);

            try {
                response.getWriter().print("添加成功!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().print("已添加!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping("/user/removeError")
    public void removeError(@RequestParam("wordId") String wordId,HttpServletResponse response) {
//        System.out.println(wordId);
        wordsService.deleteByWId(wordId);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print("移除成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/fillBlanks")
    public String FillBlanks(Model model,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Words> wordsList = userService.selectPartWord(user.getUserLast(),user.getUserLast() + user.getUserTarget());
//        System.out.println(wordsList.size());
        List<String> wordsIdList = wordsService.selectWordsByUId(user.getUserId());
        String index = "0";
        long t = System.currentTimeMillis();
        Random random = new Random(t);
        HashMap<String, Object> map = new HashMap<>();
        int num = random.nextInt(4)+1;
//        System.out.println(num);
        map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
        System.out.println(map.get(String.valueOf(num)) == null);
        for(int i = 1 ; i <= 4; i++) {
            int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
            if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
                map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
            } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
                i--;
            }
        }
//        List<Words> wordsErrorList = wordsService.selectByWId(wordsIdList);

        Words word = wordsList.get(Integer.parseInt(index));

        String wd = word.getEnglishWord();
        String sentence1 = word.getEnglishInstance1();
        String sentence2 = word.getEnglishInstance2();
        String wd1 = wd.substring(0,wd.length()-1);
        wd1 = "(\\w)*" + wd1 + "\\w";
        Pattern pattern = Pattern.compile(wd1,Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern.matcher(sentence1);
        Matcher matcher2 = pattern.matcher(sentence2);
        sentence1 = matcher1.replaceAll(" ______ ");
        sentence2 = matcher2.replaceAll(" ______ ");
        model.addAttribute("sentence1",sentence1);
        model.addAttribute("sentence2",sentence2);
        model.addAttribute("word",word);
        model.addAttribute("index",index);
        model.addAttribute("chineseMap",map);
//        model.addAttribute("wordErrors", wordsErrorList);
        request.getSession().setAttribute("words",wordsList);
        return "user/fillBlanks";
    }

    @RequestMapping("/user/personalInfo")
    public String personalInfo(){
        System.out.println(" Personal Info is coming!");
        return "user/personalInfo";
    }

    @RequestMapping("/userContent")
    public String userContent(
            @RequestParam("userId") String userId,
            @RequestParam("userName") String userName,
            @RequestParam("userTel") String userTel,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userLevel") String userLevel,
            @RequestParam("userTarget") int userTarget,HttpSession session) {
//            response.setCharacterEncoding("utf-8");
        System.out.println(userName + " " + userTel + " " + userEmail + "\n");
        userService.updateUserInfo(userId,userName,userTel,userEmail,userPassword,userLevel,userTarget);
        User user = (User)session.getAttribute("user");
        user.setUserTarget(userTarget);
        user.setUserLevel(userLevel);
        user.setUserTel(userTel);
        user.setUserEmail(userEmail);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
//        session.setAttribute("userName",userName);
//        session.setAttribute("userTel",userTel);
//        session.setAttribute("userEmail",userEmail);
//        session.setAttribute("userPassword",userPassword);
//        session.setAttribute("userLevel",userLevel);
//        session.setAttribute("userTarget",userTarget);
        return "redirect:/user/personalInfo";
    }

















    @RequestMapping("/user/chooseChineseWord")
    public String chooseChinese(Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<Words> wordsList = userService.selectPartWord(user.getUserLast(),user.getUserLast() + user.getUserTarget());
//        System.out.println(wordsList.size());
        List<String> wordsIdList = wordsService.selectWordsByUId(user.getUserId());
        String index = "0";
        long t = System.currentTimeMillis();
        Random random = new Random(t);
        HashMap<String, Object> map = new HashMap<>();
        int num = random.nextInt(4)+1;
//        System.out.println(num);
        map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getChineseWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
        for(int i = 1 ; i <= 4; i++) {
            int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
            if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getChineseWord())) {
                map.put("map"+String.valueOf(i), wordsList.get(count).getChineseWord());
            } else if(map.containsValue(wordsList.get(count).getChineseWord())) {
                i--;
            }
        }
//        List<Words> wordsErrorList = wordsService.selectByWId(wordsIdList);

        model.addAttribute("word",wordsList.get(Integer.parseInt(index)));
        model.addAttribute("index",index);
        model.addAttribute("chineseMap",map);
//        model.addAttribute("wordErrors", wordsErrorList);
        request.getSession().setAttribute("words",wordsList);
        return "user/chooseC";
    }

    @RequestMapping("/user/chooseEnglishWord")
    public String chooseEnglish(Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<Words> wordsList = userService.selectPartWord(user.getUserLast(),user.getUserLast() + user.getUserTarget());
//        System.out.println(wordsList.size());
        List<String> wordsIdList = wordsService.selectWordsByUId(user.getUserId());
        String index = "0";
        long t = System.currentTimeMillis();
        Random random = new Random(t);
        HashMap<String, Object> map = new HashMap<>();
        int num = random.nextInt(4)+1;
//        System.out.println(num);
        map.put("map"+String.valueOf(num),wordsList.get(Integer.parseInt(index)).getEnglishWord());
//        System.out.println(map.get(String.valueOf(num)) == null);
        for(int i = 1 ; i <= 4; i++) {
            int count = random.nextInt(user.getUserTarget()) + user.getUserLast();
            if(map.get("map"+String.valueOf(i)) == null && !map.containsValue(wordsList.get(count).getEnglishWord())) {
                map.put("map"+String.valueOf(i), wordsList.get(count).getEnglishWord());
            } else if(map.containsValue(wordsList.get(count).getEnglishWord())) {
                i--;
            }
        }
//        List<Words> wordsErrorList = wordsService.selectByWId(wordsIdList);

        model.addAttribute("word",wordsList.get(Integer.parseInt(index)));
        model.addAttribute("index",index);
        model.addAttribute("englishMap",map);
//        model.addAttribute("wordErrors", wordsErrorList);
        request.getSession().setAttribute("words",wordsList);
        return "user/chooseE";
    }


    @RequestMapping("/user/StudyPlan")
    public String StudyPlan(){
        System.out.println("study plan is coming!");
        return "user/StudyPlan";
    }

    @RequestMapping("/user/PhoneticSymbol")
    public String PhoneticSymbol(){
        System.out.println("PhoneticSymbol is coming!");
        return "user/PhoneticSymbol";
    }

    @RequestMapping("/user/Alphabet")
    public String Alphabet(){
        System.out.println("Alphabet is coming!");
        return "user/Alphabet";
    }

    @RequestMapping("/user/CharacteristicOfWord")
    public String CharacteristicOfWord(){
        System.out.println("CharacteristicOfWord is coming!");
        return "user/CharacteristicOfWord";
    }

    /**
     * 目前通过url加入参数来测试派生，后台显示结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/user/DeriveOfWord")
    public String DeriveOfWord(Model model, HttpServletRequest request){
        String englishWord = request.getParameter("englishWord");
        Words words = wordsService.selectByEnglishWord(englishWord);
        JSONArray json = wordsService.getDeriveByWordId(words.getWordId());
        System.out.println("DeriveOfWord is coming!");
        System.out.println(json);
        return "user/DeriveOfWord";
    }

    @RequestMapping("/user/Grammar")
    public String Grammar(){
        System.out.println("Grammar is coming!");
        return "user/Grammar";
    }

    @RequestMapping("/user/Sentence")
    public String Sentence(){
        System.out.println("Sentence is coming!");
        return "user/Sentence";
    }

    @RequestMapping("/user/DOW")
    public String DOW(){
        System.out.println("DOW is coming!");
        return "user/DOW";
    }
}


