package com.atguigu.gmall0213.logger4.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyso
 * @create 2020-07-16 10:41
 */
@RestController  // =@controller @response
/**
 * Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody来配合
 * 如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式。
 */
public class LogggerController {
    // TODO 负责接收协调web层的代码

    @Autowired
    //用来装配bean，都可以写在字段上，或者方法上
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
    //类定义处: 提供初步的请求映射信息，相对于 WEB 应用的根目录。
    //方法处: 提供进一步的细分映射信息，相对于类定义处的 URL。

    public String applog(@RequestBody String json){

        JSONObject jsonObject = JSON.parseObject(json);
        if(jsonObject.getString("start")!=null && jsonObject.getString("start").length()>0){
            kafkaTemplate.send("GMALL_START0213",json);

        }else{
            kafkaTemplate.send("GMALL_EVENT0213",json);
        }
        System.out.println(json);


        return "success";

    }
}
