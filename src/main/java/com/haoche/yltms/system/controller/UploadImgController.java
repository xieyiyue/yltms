package com.haoche.yltms.system.controller;

import com.haoche.yltms.system.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/upload")
public class UploadImgController {
    @RequestMapping("/uploadImg") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(HttpServletRequest req, @RequestParam("file") MultipartFile file) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
        Result result = new Result();
        try {
            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名

            String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("")).getPath();
            String destFileName = path + "static/uploaded" + File.separator + fileName;
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
     result.setMsg(fileName);
            file.transferTo(destFile);
            //6.把文件名放在model里，以便后续显示用
            result.setSuccess(true);
        } catch (IOException e) {
            e.printStackTrace();
            result.setMsg( "上传失败," + e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
}
