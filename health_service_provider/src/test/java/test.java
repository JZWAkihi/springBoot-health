import com.google.gson.Gson;
import com.jiang.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;


public class test {

//    public void set(String key, User user) {
//        redisTemplate.opsForValue().set(key, user);
//    }
//    public User get(String key) {
//        return (User) redisTemplate.boundValueOps(key).get();
//    }
//    public void setCode(String key, String code) {
//        stringRedisTemplate.opsForValue().set(key, code, 60, TimeUnit.SECONDS);
//    }
//    public String getCode(String key) {
//        return stringRedisTemplate.boundValueOps(key).get();
//    }

    @Test
    public void test(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "bEsX76jRPWzWQK6GqADcuaZomEFWeqhdWS9ok4tL";
        String secretKey = "h6lDfr6Ms6fG4nQ8cqLypXFPD0TLYZDUMflFg5pQ";
        String bucket = "jiang-health-pic";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\JiangZW\\Desktop\\新建文件夹\\aop.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "aop.jpg";
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
                //ignor
            }
        }
    }


    @Test
    public void Test(){
//        QiniuUtils.upload2Qiniu("C:\\Users\\JiangZW\\Desktop\\新建文件夹\\aop.jpg","apo.jpg");

        QiniuUtils.deleteFileFromQiniu("aop.jpg");

    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void Test1(){
        System.out.println(stringRedisTemplate);
    }
//
//

//    @Test
//    public void Test2() {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.select(0);
//        Set<String> keys = jedis.keys("*");
//        keys.forEach(key -> System.out.println("keys" + key));
//        System.out.println(jedis.get("1"));
//        jedis.close();
//    }

}
