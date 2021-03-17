package cn.xcm.controller;

import cn.xcm.model.Balance;
import cn.xcm.model.User;
import cn.xcm.service.UserService;
import cn.xcm.util.GlobalResult;
import cn.xcm.util.RespBean;
import cn.xcm.util.WechatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @program: demo
 * @description
 * @author: chenshuofang
 * @create: 2020-08-07 09:27
 **/
@RestController
@RequestMapping("/wx")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getTotal")
    public RespBean getTotal(){
        JSONObject jsonObject=new JSONObject();
        BigDecimal income = userService.getIncome();
        BigDecimal outcome = userService.getOutcome();
        if(income == null){
            income = new BigDecimal("0");
        }
        if(outcome == null){
            outcome = new BigDecimal("0");
        }
        BigDecimal total = income.subtract(outcome);
        jsonObject.put("income",income);
        jsonObject.put("outcome",outcome);
        jsonObject.put("total",total);
        return RespBean.ok("success",jsonObject);
    }
    @PostMapping("/addBalance")
    public RespBean addBalance(@RequestBody Balance balance){
        System.out.println(balance);
        if(userService.addBalance(balance) == 1){
            return RespBean.ok("success");
        }
        return RespBean.error("error");
    }
    @PostMapping("/login")
    @ResponseBody
    public GlobalResult user_login(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "rawData", required = false) String rawData,
                                   @RequestParam(value = "signature", required = false) String signature,
                                   @RequestParam(value = "encrypteData", required = false) String encrypteData,
                                   @RequestParam(value = "iv", required = false) String iv) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return GlobalResult.build(500, "签名校验失败", null);
        }
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
//        User user = userRepository.findByOpenId(openid);
        User user = userService.findByOpenId(openid);

        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (user == null) {
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");

            user = new User();
            user.setOpenId(openid);
            user.setSkey(skey);
            user.setCreateTime(new Date());
            user.setLastVisitTime(new Date());
            user.setSessionKey(sessionKey);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            user.setGender((byte) Integer.parseInt(gender));
            user.setNickName(nickName);

            userService.save(user);
        } else {
            // 已存在，更新用户登录时间
            user.setLastVisitTime(new Date());
            // 重新设置会话skey
            user.setSkey(skey);
            userService.update(user);
        }
        //encrypteData比rowData多了appid和openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);
        //6. 把新的skey返回给小程序
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("skey", skey);
        jsonObject.put("id", user.getId());
        GlobalResult result = GlobalResult.build(200, null, jsonObject);
        return result;
    }

    @GetMapping({"/loginout"})
    public GlobalResult logout() {
        return GlobalResult.build(200, (String)null, (Object)null);
    }
}
