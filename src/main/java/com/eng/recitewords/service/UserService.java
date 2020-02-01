package com.eng.recitewords.service;

import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public String userLogin(String userEmail, String password) {
        User user = selectByEmail(userEmail);
        if(user == null) {
            return "用户不存在";
        } else if(!user.getUserPassword().equals(password)) {
            return "用户名或密码错误";
        } else {
            return "success";
        }
    }

    public User selectByEmail(String userEmail) {
        User user = userMapper.selectByEmail(userEmail);
        return user;
    }

    public void register(String userName, String userTel, String userEmail, String userPassword) {
//        System.out.println(userPassword);
//        User ruser = selectByEmail(userEmail);
//        if(ruser == null) {
        User user = new User();
        String userId = UUID.randomUUID().toString().replace("-", "");
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserTel(userTel);
        user.setUserEmail(userEmail);
        user.setUserPassword(userPassword);
        userMapper.register(user);
//        return "success";
//        } else {
//            return "该用户已注册,请登录";
//        }
    }

    public String resetPassword(String userEmail) {
        User user = selectByEmail(userEmail);
        if(user == null) {
            return "用户不存在";
        } else {
            String password = UUID.randomUUID().toString().replace("-","").substring(1,16);
            user.setUserPassword(password);
            userMapper.updatePassword(user);
            send(user, password);
            return "success";
        }
    }

    /**
     * 给用户发邮件
     * @param user
     */
    public void send(User user, String password) {
//        System.out.println(user.toString());
//        System.out.println(from);
//        System.out.println(password);
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(user.getUserEmail());
            helper.setSubject("密码找回");
            String content = "<html><body><p>尊敬的" + user.getUserName() + "用户，您的账号密码将被重置为" + password +"。请及时进行登陆并更改</p></body></html>";
            helper.setText(content, true);
            mailSender.send(message);
        } catch( Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Words> selectPartWord(int start, int end) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("end",end);
        List<Words> wordsList =userMapper.selectPartWord(map);
        return wordsList;
    }

//    public User selectUserById (String userId){
//        User user = userMapper.selectUserById(userId);
//        return user;
//    }

    public void updateUserInfo(String userId,String userName,String userTel,String userEmail,String userPassword,String userLevel,int userTarget){
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserEmail(userEmail);
        user.setUserTel(userTel);
        user.setUserLevel(userLevel);
        user.setUserTarget(userTarget);
        userMapper.updateUserInfo(user);
    }

}
