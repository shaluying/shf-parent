package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.UserInfo;
import com.shaluy.result.Result;
import com.shaluy.result.ResultCodeEnum;
import com.shaluy.service.UserInfoService;
import com.shaluy.util.MD5;
import com.shaluy.vo.RegisterVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.shaluy.vo.LoginVo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    //发送手机验证码
    @RequestMapping("/sendCode/{phone}")
    @ResponseBody
    public Result sendCode(@PathVariable("phone") String phone, HttpSession session){
        //设置验证码为8888
        String code = "8888";
        //放入会话域中用以校验
        session.setAttribute("code",code);

        return Result.ok(code);
    }

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session){
        //获取注册信息
        String phone = registerVo.getPhone();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //验空
        if (phone == null || nickName == null || password == null || code == null){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //根据手机号查询注册用户
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);

        if (userInfo != null){
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        if (!code.equals(session.getAttribute("code"))){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhone(phone);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setNickName(nickName);
        userInfo1.setStatus(1);
        //保存注册信息
        userInfoService.insert(userInfo1);

        return Result.ok();
    }

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginVo loginVo, HttpSession session){
        //获取输入的登录信息
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //验空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //根据手机号查询信息
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);

        //判断账号是否存在
        if (userInfo == null){
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //判断账号是否锁定
        if (userInfo.getStatus() == 0){
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //判断密码是否正确
        if (!userInfo.getPassword().equals(MD5.encrypt(password))){
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //登录成功
        //将用户信息放入到session域中
        session.setAttribute("userInfo", userInfo);

        //相应回去的数据中必须有nickName这个键 值是用户昵称
        Map<String, Object> map = new HashMap<>();
        map.put("nickName", userInfo.getNickName());
        map.put("phone", userInfo.getPhone());
        return Result.ok(map);
    }

    //登出
    @RequestMapping("/logout")
    @ResponseBody
    public Result logout(HttpSession session){
        //将session域中用户登录信息移除
        session.removeAttribute("userInfo");

        return Result.ok();
    }
}
