package com.wnliam.uqdate.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
/**
* @Description:    这个类用来获取项目的绝对路径
* @Author:         Wnliam
* @CreateDate:     2019/1/16 14:15
* @UpdateUser:     Wnliam
* @UpdateDate:     2019/1/16 14:15
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class GetPathUtil {
    public static Logger log = LogManager.getLogger(GetPathUtil.class);

    /**
     *
     * @author      Wnliam
     * @return      java.lang.String
     * @exception   FileNotFoundException
     * @date        2019/1/16 14:31
     */
    public static String getJarRootPath() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();//可能会在Linux中失效
        //=> file:/root/tmp/demo-springboot-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
        log.debug("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
        //创建File时会自动处理前缀和jar包路径问题  => /root/tmp
        File rootFile = new File(path);
        if(!rootFile.exists()) {
            log.info("根目录不存在, 重新创建");
            rootFile = new File("");
            log.info("重新创建的根目录: "+rootFile.getAbsolutePath());
        }
        log.debug("项目根目录: "+rootFile.getAbsolutePath());        //获取的字符串末尾没有分隔符 /
        return rootFile.getAbsolutePath();
    }

}
