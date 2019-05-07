package com.wnliam.uqdate.controller;

import com.wnliam.uqdate.info.FileInfo;
import com.wnliam.uqdate.util.GetPathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/searchfile")
public class SearchFileController {
    private String folder = "";
    File crrentParent = null;
    File[] crrentParentFiles = null;

    public SearchFileController() {
        try {
            folder = GetPathUtil.getJarRootPath() + "/static";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @PostMapping
    public List searchByName(@RequestParam(value = "filename")String filename,
                             @RequestParam(value = "openid")String openID){
        String filepath = folder + "/" + openID;
        System.out.println("openid:" + openID);
        File root = new File(filepath);
        crrentParent = root;
        System.out.println("root:" + root.getAbsoluteFile());
        crrentParentFiles = root.listFiles();
        List<FileInfo> list = new ArrayList<>();
        for (int i = 0; i < crrentParentFiles.length; i++) {
            if (crrentParentFiles[i].getName().indexOf(filename)>-1) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(crrentParentFiles[i].getName());
                fileInfo.setTime(crrentParentFiles[i].lastModified() + "");
                fileInfo.setSize(crrentParentFiles[i].length() / 1024.0);
                list.add(fileInfo);
            }
        }
        return list;
    }
}
