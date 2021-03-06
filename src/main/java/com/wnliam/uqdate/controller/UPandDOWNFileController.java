package com.wnliam.uqdate.controller;


import com.wnliam.uqdate.info.FileInfo;
import com.wnliam.uqdate.util.GetPathUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
* @Description:    控制上传下载的Controller
* @Author:         Wnliam
* @CreateDate:     2019/1/16 14:28
* @UpdateUser:     Wnliam
* @UpdateDate:     2019/1/16 14:28
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("/file")
public class UPandDOWNFileController {
    //存放文件的路径 ，这里直接放到controller文件夹下
    private  String folder = "";

    public UPandDOWNFileController() throws FileNotFoundException {
        folder = GetPathUtil.getJarRootPath() + "/static";
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public void update(@RequestParam(value="file",required=false)MultipartFile file,@RequestParam(value = "openid")
            String openID) throws IOException {
        /**
         * 注意：file名字要和传入的name一致
         */
        System.out.println("openid:"+openID);
        System.out.println("file name=" + file.getName());
        System.out.println("origin file name=" + file.getOriginalFilename());
        System.out.println("file size=" + file.getSize());
        System.out.println("file folder=" + folder);

        String filepath = folder + "/" + openID;
        /**
         * 这里是写到本地
         * 还可以用file.getInputStrem()
         */
        File localFile = new File(filepath, file.getOriginalFilename());
        File fileParent = localFile.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录
            fileParent.mkdirs();
        }
        localFile.createNewFile();
        //把传入的文件写到本地文件
        file.transferTo(localFile);

//        return new FileInfo(localFile.getAbsolutePath()); //getAbsolutePath为绝对路径

    }

    /**
     * 文件的下载
     */
    @RequestMapping(value = "/download")
    public void download(@RequestParam("filename")String fileName, @RequestParam("openid") String openID, HttpServletResponse response) {
        String filepath = folder + "/" + openID;
        File dfile = new File(filepath,fileName);
        try (
                //jdk7新特性，可以直接写到try()括号里面，java会自动关闭
                InputStream inputStream = new FileInputStream(dfile);
                OutputStream outputStream = response.getOutputStream()
        ) {
            //指明为下载
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);   // 设置文件名


            //把输入流copy到输出流
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
