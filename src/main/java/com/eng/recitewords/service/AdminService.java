package com.eng.recitewords.service;

import com.eng.recitewords.entity.Admin;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.mapper.AdminMapper;
import com.eng.recitewords.mapper.WordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
    private WordsMapper wordsMapper;

    public String adminLogin(String adminEmail, String password) {
        Admin admin = selectByEmail(adminEmail);
        if(admin == null) {
            return "用户不存在";
        } else if(!admin.getAdminPassword().equals(password)){
            return "用户名或密码错误";
        } else {
            return "success";
        }
    }

    public Admin selectByEmail(String adminEmail) {
        Admin admin = adminMapper.selectByEmail(adminEmail);
        return admin;
    }

    public List<User> selectAllUser(){
        List<User> userlist = adminMapper.selectAllUser();
        return userlist;
    }

    public List<Admin> selectAllAdmin(){
        List<Admin> adminlist = adminMapper.selectAllAdmin();
        return adminlist;
    }

    public Admin selectAdminById(String adminId) {
        Admin admin = adminMapper.selectAdminById(adminId);
        return admin;
    }

    public void updateInfo(String adminId,String adminName,String adminTel,String adminEmail){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setAdminName(adminName);
        admin.setAdminTel(adminTel);
        admin.setAdminEmail(adminEmail);
        adminMapper.updateInfo(admin);
   }

    public void addInfo(String adminId,String adminName,String adminTel,String adminEmail,String adminPassword){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setAdminName(adminName);
        admin.setAdminTel(adminTel);
        admin.setAdminEmail(adminEmail);
        admin.setAdminPassword(adminPassword);
        adminMapper.addInfo(admin);
    }

    public void deleteInfo(String adminId){
        adminMapper.deleteInfo(adminId);
    }



}
