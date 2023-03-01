package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.*;
import com.shaluy.result.Result;
import com.shaluy.service.*;
import com.shaluy.vo.HouseQueryVo;
import com.shaluy.vo.HouseVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;


    //分页及带条件查询
    @RequestMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public Result findPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                           @RequestBody HouseQueryVo houseQueryVo){

        //调用前端分页及带条件查询的方法
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);

        return Result.ok(pageInfo);
    }

    //前台房屋详情
    @RequestMapping("/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id, HttpSession session){
        //根据id获取房源
        House house = houseService.getById(id);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取房源图片信息
        List<HouseImage> houseImageList = houseImageService.getHouseImageListByHouseIdAndType(id, 1);
        //获取经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerListByHouseId(id);

        Map<String,Object> map = new HashMap();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImageList);

        //设置默认没有关注房源
        Boolean isFollowed = false;
        //得到会话域中用户信息
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        //判断用户是否登录
        if (userInfo != null){
            //用户登录
            isFollowed = userFollowService.isUserFollowedHouse(userInfo.getId(), id);
            map.put("isFollow",isFollowed);
        }
        //用户没有登录
        map.put("isFollow",isFollowed);

        return Result.ok(map);
    }

}
