package cn.howso.deeplan.framework.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.util.LogUtil;

@Controller
@RequestMapping("files")
public class FileUploadController {

    private final Logger logger = LogUtil.getLogger();

    /**
     * 表单方式文件上传
     */
    @RequestMapping(value = "upload-form", method = RequestMethod.POST)
    @ResponseBody
    public R upload(MultipartFile file, HttpServletRequest request) throws IOException {
        if(file==null){
            return R.error("file should not be null");
        }
        String originalfilename = file.getOriginalFilename();
        try (InputStream in = file.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                FileOutputStream fos = new FileOutputStream("/upload" + File.separator + originalfilename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            StreamUtils.copy(bis, bos);
        } catch (Exception e) {
            logger.error("文件上传失败", e);
        }
        return R.ok();
    }

    /**
     * 流方式文件上传
     */
    @RequestMapping(value = "upload-stream", method = RequestMethod.PUT)
    @ResponseBody
    public R uploadByStream(HttpServletRequest request) throws IOException {
        String filename = UUID.randomUUID().toString();
        try (InputStream in = request.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                FileOutputStream fos = new FileOutputStream("/upload" + File.separator + filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            StreamUtils.copy(bis, bos);
        } catch (Exception e) {
            logger.error("文件上传失败", e);
        }
        return R.ok();
    }
}
