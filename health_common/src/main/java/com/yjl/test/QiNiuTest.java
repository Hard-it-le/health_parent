package com.yjl.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author yjl
 * @create 2020-07-30-14:33
 * 使用七牛云提供的sdk实现将本地图片上环的七牛云服务器
 **/
public class QiNiuTest {
    /**
     * 上传本地图片到七牛云服务器
     */
    @Test
    public void test1() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
            //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
                //...生成上传凭证，然后准备上传
        String accessKey = "NYqrH6zOYGgVkBAk8tm21REFAwULiU3odbV3Wr8v";
        String secretKey = "ZBYpFBApYEKebUAalP4-leRE6yjPyNeavBwX7nHy";
        String bucket = "yjlhealthspace1";
            //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\tmp\\idea快捷键壁纸.png";
            //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "abc.jpg";
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

    /**
     * 删除七牛云服务器的图片
     */
    @Test
    public void test2(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        String accessKey = "NYqrH6zOYGgVkBAk8tm21REFAwULiU3odbV3Wr8v";
        String secretKey = "ZBYpFBApYEKebUAalP4-leRE6yjPyNeavBwX7nHy";
        String bucket = "yjlhealthspace1";
        String key = "abc.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
