package com.wnliam.uqdate.controller;

import com.wnliam.uqdate.util.GetPathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/delete")
public class DeleteFileController {
    private String folder = "";
    //获取文件所在路径
    public DeleteFileController() {
        try {
            folder = GetPathUtil.getJarRootPath() + "/static";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @PostMapping
    public String deleteFile(@RequestParam(value = "filename")String fileName,@RequestParam(value = "openid") String openID){
        String result = "删除失败";
        String filepath = folder + "/" + openID;
        System.out.println("delete-openid:" + openID);
        File file = new File(filepath+"/"+fileName);
        if (file.exists()) {
            file.delete();
            result = "删除成功";
            System.out.println("文件已经被成功删除");
        }else
            result = "远程文件不存在";
        return result;
    }

}
