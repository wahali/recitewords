package com.eng.recitewords.controller;


import com.eng.recitewords.entity.UploadFile;
import com.eng.recitewords.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    /**
     * 查看所有文件
     * */
//    @RequestMapping("/file/selectAllUploadFiles")
//    @RequestMapping("/admin/Resources")
    public String selectAllUploadFiles(Model model){
        List<UploadFile> fileList = uploadService.selectAllUploadFiles();
        model.addAttribute(fileList);
        System.out.println("file_list is coming!");
        return "user/Resources";
    }


    /**
     * 查看未审核文件
     * */
    @RequestMapping("/admin/Resources")
    public String selectUncheckedFiles(Model model){
        List<UploadFile> fileList = uploadService.selectUncheckedFiles();
        model.addAttribute(fileList);
        System.out.println("UncheckedFiles_list is coming!");
        return "admin/Resources";
    }

    /**
     * 查看已审核文件
     * */
    @RequestMapping("/user/Resources")
    public String selectCheckedFiles(Model model){
        List<UploadFile> fileList = uploadService.selectCheckedFiles();
        model.addAttribute(fileList);
        System.out.println("CheckedFile_list is coming!");
        return "user/Resources";
    }

    /**
     * 更新审核
     * */
    @RequestMapping("/file/check/{fileName}")
    public String updateChecked(@PathVariable String fileName){
        uploadService.updateChecked(fileName);
        return "redirect:/user/Resources";
    }

    /**
     * 删除文件
     * */
    @RequestMapping("/file/deleteFile/{fileName}")
    public String deleteFileByName(@PathVariable String fileName){
        uploadService.deleteFileByName(fileName);
        String path = uploadService.getPath()+"/"+fileName;
        File file = new File(path);
        file.delete();
        /*if (!file.exists()) {// 判断是否待删除目录是否存在
            return false;
        }
        String[] content = file.list();// 取得当前目录下所有文件和文件夹
        for (String name : content) {
            File temp = new File(path, name);
            if (temp.isDirectory()) {// 判断是否是目录
                deleteFile(temp.getAbsolutePath());// 递归调用，删除目录里的内容
                temp.delete();
            } else {
                temp.delete();// 删除文件
            }
        }*/

        return "redirect:/user/Resources";
    }

    /**
     * 增加上传文件
     * */
    @RequestMapping("/file/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        String fileName = uploadService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        if (file.isEmpty()){
            model.addAttribute("message", "The file is empty!");
            return "/uploadStatus";
        }
        UploadFile uploadFile = new UploadFile(1, fileName, fileDownloadUri,
                file.getContentType(), file.getSize(),0);
        uploadService.insertFile(uploadFile);
        return "redirect:/user/Resources";
    }

    /**
    * 返回下载连接
    * */
    @RequestMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // 获取Resource对象
        Resource resource = uploadService.loadFileAsResource(fileName);

        // 取文件类型
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("无法确定文件类型。");
        }

        // 无法确定文件的类型
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
