package com.wnliam.uqdate.controller;

import com.wnliam.uqdate.info.FileInfo;
import com.wnliam.uqdate.util.GetPathUtil;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class FileCategoryController {
    private String folder = "";
    File crrentParent = null;
    File[] crrentParentFiles = null;

    public FileCategoryController() {
        try {
            folder = GetPathUtil.getJarRootPath() + "/static";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/all")
    public List<FileInfo> getAllFile(@RequestParam(value = "openid") String openID) {
        String filepath = folder + "/" + openID;
        System.out.println("openid:" + openID);
        File root = new File(filepath);
        crrentParent = root;
        System.out.println("root:" + root.getAbsoluteFile());
        crrentParentFiles = root.listFiles();


        return buildList();
    }

    @PostMapping
    public List<FileInfo> getFile(@RequestParam(value = "openid") String openID,
                                     @RequestParam(value = "flag") String filetype) {
        String filepath = folder + "/" + openID;
        System.out.println("openid:" + openID);
        File root = new File(filepath);
        crrentParent = root;
        System.out.println("root:" + root.getAbsoluteFile());
        crrentParentFiles = root.listFiles();

        return getFilesByType(filetype);
    }

    private List buildList(){
        List<FileInfo> list = new ArrayList<>();
        for (int i = 0; i < crrentParentFiles.length; i++) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(crrentParentFiles[i].getName());
            fileInfo.setTime(crrentParentFiles[i].lastModified() + "");
            fileInfo.setSize(crrentParentFiles[i].length() / 1024.0);
            list.add(fileInfo);
        }
        return list;
    }

    private List getFilesByType(String filetype){
        List<FileInfo> list = new ArrayList<>();
        String [] types= null;
        switch (filetype){
            case  "video":
                types = new String[]{"rm", "rmvn", "rmv", "mp4", "avi"};
                break;
            case "text":
                types = new String[]{"pdf", "xls" , "xlsx", "doc", "ppt","docx","text"};
                break;
            case "image":
                types = new String[]{"bmp", "jpg" , "jpeg", "png", "swf"};
                break;
            case "music":
                types = new String[]{"mp3", "wma" , "wav", "mid"};
                break;
            case "apk":
                types = new String[]{"apk"};
                break;
            case "z_r":
                types = new String[]{"rar","zip","iso"};
                break;
            default:
                types = new String[]{};
        }
        for (File f: crrentParentFiles){
            String localtype = getFileType(f.getName());
            boolean isContains = Arrays.asList(types).contains(localtype);
            if(isContains){
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(f.getName());
                fileInfo.setTime(f.lastModified() + "");
                fileInfo.setSize(f.length() / 1024.0);
                list.add(fileInfo);
            }
        }
        return list;
    }


    private String getFileType(String fileName) {
        if ("".equals(fileName)) try {
            throw new Exception("传入的文件名为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileType=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        return fileType;
    }
}