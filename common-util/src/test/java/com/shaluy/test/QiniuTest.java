package com.shaluy.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.shaluy.util.QiniuUtil;
import org.junit.Test;

public class QiniuTest {

    /*
    测试向七牛云上传文件
        Zone.zone0() 华东
        Zone.zone1() 华北
        Zone.zone2() 华南
     */
    @Test
    public void testUpload(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        //个人中心，密钥管理中奖AK和SK拷贝过来
        String accessKey = "w5k5vYOUw-RDlXgKQpKzGccfViHlqGgajAC2PlrN";
        String secretKey = "VJ3XJfjQUFhGcjZkxBHspmzTdArOdgCPe8cdfjGH";
        //设置你创建的空间名字
        String bucket = "shaluy-shf";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //需上传文件的本地路径
        String localFilePath = "D:\\Users\\刺客—秦名谦\\Pictures\\联想锁屏壁纸\\8469969.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }

    /*
    测试工具类QiniuUtil
     */
    @Test
    public void testQiniuUtil(){
        QiniuUtil.upload2Qiniu("D:\\Users\\刺客—秦名谦\\Pictures\\联想锁屏壁纸\\8469973.jpg",null);

    }
}
