package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.House;
import com.shaluy.entity.UserFollow;
import com.shaluy.entity.UserInfo;
import com.shaluy.result.Result;
import com.shaluy.service.UserFollowService;
import com.shaluy.vo.UserFollowVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    @ResponseBody
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session){
        UserFollow userFollow = new UserFollow();

        //在会话域中获取userInfo
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        userFollow.setHouseId(houseId);
        userFollow.setUserId(userInfo.getId());

        //保存关注信息
        userFollowService.insert(userFollow);

        return Result.ok();
    }

    //我的关注分页显示
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    @ResponseBody
    public Result myFollow(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, HttpSession session){
        //获得登录信息
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        //获取该等登录用户关注的房源分页信息
        PageInfo<UserFollowVo> pageInfo = userFollowService.getFollowedHouseByUserId(userInfo.getId(), pageNum, pageSize);

        return Result.ok(pageInfo);
    }

    //取消关注
    @RequestMapping("//auth/cancelFollow/{id}")
    @ResponseBody
    public Result cancelFollow(@PathVariable("id") Long id){
        //根据id取消用户关注的房源
        userFollowService.cancelFollow(id);

        return Result.ok();
    }

}
