package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.util.DateUtil;
import com.ai.dataplatform.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: FileController
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-08 16:37
 */
@Slf4j
@Controller
@Api(value = "文件相关接口")
@RequestMapping(value = "/file")
public class FileController {

    @Value("${file.uploadPath}")
    private String PATH;
    @Value("${file.uploadPath2}")
    private String PATH2;


    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            log.info("当前系统为Linux【{}】" , os);
            return true;
        } else {
            log.info("当前系统为非Linux【{}】" , os);
            return false;
        }
    }


    @ResponseBody
    @RequestMapping(value = "upload", method = {RequestMethod.POST})
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public MyResp upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileNamesuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileNameReal = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileNameReal + "_" + RandomUtil.getRandom(true, 6) + "." + fileNamesuffix;
        String completePath = "";
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            log.info("开始上传文件【{}】...", fileName);
            if (isOSLinux()){
                completePath = PATH2 + "/" + DateUtil.getYMD() + "/" + fileName;
            }else {
                completePath = PATH + "/" + DateUtil.getYMD() + "/" + fileName;
            }
            File dest = new File(completePath);

            // 判断路径在不在
            if (!dest.exists()) {
                dest.mkdirs();
            }

            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("文件【{}】上传成功...", fileName);
        return new MyResp().SuccessResp("", completePath);
    }

    @ApiOperation(value = "文件下载测试", notes = "文件下载测试")
    @RequestMapping(value = "download", method = {RequestMethod.GET})
    public void download1(@RequestParam(value = "path", required = true) String path, HttpServletResponse response) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        log.info("开始下载文件【{}】...", fileName);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(path)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            log.info("下载文件成功【{}】...", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @ApiOperation(value = "文件下载2", notes = "强制下载")
//    @RequestMapping(value = "download2" , method = {RequestMethod.GET})
//    public void download2(HttpServletResponse response){
//        String fileName = "1559808308117_7_112351.jpg";
//        // 设置强制下载不打开
//        response.setContentType("application/force-download");
//        try {
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        byte[] buff = new byte[1024];
//        BufferedInputStream bis = null;
//        try {
//            ServletOutputStream outputStream = response.getOutputStream();
//            byte[] buff = FileUtils.readFileToByteArray(new File("C:\\test001\\20191008\\" + fileName));
//            outputStream.write(buff);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @ApiOperation(value = "文件批量上传", notes = "文件批量上传")
//    @RequestMapping(value = "batch", method = {RequestMethod.POST})
//    public String batchFileUpload(HttpServletRequest request){
//        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
//        for (int i = 0; i < fileList.size(); i++) {
//            MultipartFile file = fileList.get(i);
//            byte[] bytes = new byte[0];
//            try {
//                bytes = file.getBytes();
//                log.info("开始上传文件【{}】...", i);
//                FileUtils.writeByteArrayToFile(new File("c:/test/" + file.getOriginalFilename()), bytes);
//                log.info("文件【{}】上传成功...", i);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return "success";
//    }

//    @ResponseBody
//    @PostMapping("/uploadN")
//    @ApiOperation(value = "文件上传N", notes = "文件上传N")
//    public String uploadN(MultipartFile uploadFile, HttpServletRequest request){
//        if(uploadFile.isEmpty()){
//            //返回选择文件提示
//            return "请选择上传文件";
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
//        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
//        String realPath = new String("src/main/resources/static/uploadFile/");
//        log.info("-----------上传文件保存的路径【"+ realPath +"】-----------");
//        String format = sdf.format(new Date());
//        //存放上传文件的文件夹
//        File file = new File(realPath + format);
//        log.info("-----------存放上传文件的文件夹【"+ file +"】-----------");
//        log.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【"+ file.getAbsolutePath() +"】-----------");
//        if(!file.isDirectory()){
//            //递归生成文件夹
//            file.mkdirs();
//        }
//        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
//        String oldName = uploadFile.getOriginalFilename();
//        log.info("-----------文件原始的名字【"+ oldName +"】-----------");
//        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
//        log.info("-----------文件要保存后的新名字【"+ newName +"】-----------");
//        try {
//            //构建真实的文件路径
//            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
//            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
//            uploadFile.transferTo(newFile);
//            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
//            log.info("-----------【"+ filePath +"】-----------");
//            return filePath;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "上传失败!";
//    }

}
