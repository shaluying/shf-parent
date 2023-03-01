package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.HouseImage;
import com.shaluy.result.Result;
import com.shaluy.service.HouseImageService;
import com.shaluy.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    //去到房源或房产图片上传页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String toUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Model model){
        model.addAttribute("houseId", houseId);
        model.addAttribute("type",type);

        return "house/upload";
    }

    //房源或房产图片上传
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, @RequestParam("file") MultipartFile[] files){
        try {
            //判断文件数组
            if (files != null && files.length > 0){
                //遍历文件数组
                for (MultipartFile file : files) {
                    //获取文件真实姓名
                    String originalFilename = file.getOriginalFilename();
                    //利用UUID生成随机字符串作为文件上传到七牛云的名字
                    String qiniuFileName = UUID.randomUUID().toString();
                    //获取文件字节数组
                    byte[] bytes = file.getBytes();

                    //使用工具类将图片上传到七牛云
                    QiniuUtil.upload2Qiniu(bytes,qiniuFileName);

                    //创建图片实体类
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    houseImage.setImageUrl("http://rq08efj8c.hn-bkt.clouddn.com/"+qiniuFileName);
                    //将图片在七牛云域名信息保存到数据库
                    houseImageService.insert(houseImage);
                }
                return Result.ok();
            }

            return Result.fail();

        }catch (Exception e){
            e.printStackTrace();
            return Result.fail();
        }
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id){
        //根据id删除图片
        houseImageService.delete(id);

        return "redirect:/house/"+houseId;
    }

}
