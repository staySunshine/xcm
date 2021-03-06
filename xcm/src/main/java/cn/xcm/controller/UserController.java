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
        // ????????????????????????rawData
        // ?????????signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.????????????????????????code
        // 2.?????????????????? ???????????????????????? appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.???????????????????????? ?????????????????????
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        // 4.???????????? ????????????????????????signature??????????????????????????????signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return GlobalResult.build(500, "??????????????????", null);
        }
        // 5.???????????????User?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//        User user = userRepository.findByOpenId(openid);
        User user = userService.findByOpenId(openid);

        // uuid????????????key?????????????????????????????????????????????????????????
        String skey = UUID.randomUUID().toString();
        if (user == null) {
            // ??????????????????
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
            // ????????????????????????????????????
            user.setLastVisitTime(new Date());
            // ??????????????????skey
            user.setSkey(skey);
            userService.update(user);
        }
        //encrypteData???rowData??????appid???openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);
        //6. ?????????skey??????????????????
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
