package com.example.demo.controller;
import com.example.demo.util.FileUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class TestController {
//    private final ResourceLoader resourceLoader;
//    @Autowired
//    public TestController(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//    @Value("${web.upload-path}")
//    private String path;
//    /**
//     * 跳轉到檔案上傳頁面
//     * @return
//     */
//    @RequestMapping("test")
//    public String toUpload(){
//        return "test";
//    }
//    /**
//     *
//     * @param file 要上傳的檔案
//     * @return
//     */
//    @RequestMapping("fileUpload")
//    public String upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){
//// 要上傳的目標檔案存放路徑
//        String localPath = "E:/Develop/Files/Photos";
//// 上傳成功或者失敗的提示
//        String msg = "";
//        if (FileUtil.upload(file, localPath, file.getOriginalFilename())){
//// 上傳成功，給出頁面提示
//            msg = "上傳成功！";
//        }else {
//            msg = "上傳失敗！";
//        }
//// 顯示圖片
//        map.put("msg", msg);
//        map.put("fileName", file.getOriginalFilename());
//        return "forward:/test";
//    }
//    /**
//     * 顯示單張圖片
//     * @return
//     */
//    @RequestMapping("show")
//    public ResponseEntity showPhotos(String fileName){
//        try {
//// 由於是讀取本機的檔案，file是一定要加上的， path是在application配置檔案中的路徑
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
