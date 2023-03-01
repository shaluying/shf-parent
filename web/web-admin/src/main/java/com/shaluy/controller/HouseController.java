package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.*;
import com.shaluy.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    //分页及带条件查询的方法
    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将请求参数放入请求域中
        model.addAttribute("filters", filters);
        //获得分页数据
        PageInfo<House> pageInfo = houseService.findPage(filters);
        //将分页数据放入请求域中
        model.addAttribute("page", pageInfo);

        //获取数据并将之放入到请求域中
        setRequestAttribute(model);

        return "house/index";
    }

    //去到新增页面
    @RequestMapping("/create")
    public String toCreatePage(Model model){
        setRequestAttribute(model);

        return "house/create";
    }



    //保存一个房源
    @RequestMapping("/save")
    public String save(House house){
        //保存一个房源
        houseService.insert(house);

        return "common/successPage";

    }

    //去到更新编辑页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable(value = "id") Long id, Model model){
        //根据id获取房源
        House house = houseService.getById(id);
        //将房源放入到共享域中
        model.addAttribute("house", house);

        //获取数据并将之放入到请求域中
        setRequestAttribute(model);

        return "house/edit";
    }

    //更新一个房源
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);

        return "common/successPage";
    }

    //删除一个房源
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        //删除一个房源（逻辑删除）
        houseService.delete(id);

        return "redirect:/house";
    }

    //发布和取消发布
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        //调用发布或取消发布的方法
        houseService.publish(id,status);

        return "redirect:/house";
    }

    //查询房源详情
    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        //根据房源id查询房源
        House house = houseService.getById(id);
        //将房源放入到请求域中
        model.addAttribute("house",house);

        //根据房源id查询小区
        Community community = communityService.getById(house.getCommunityId());
        //将小区放入到请求域中
        model.addAttribute("community",community);

        //根据房源id和图片类型查询房源图片或房产图片
        //房源图片
        List<HouseImage> houseImage1List =houseImageService.getHouseImageListByHouseIdAndType(id,1);
        //将房源图片放入到请求域中
        model.addAttribute("houseImage1List",houseImage1List);
        //房产图片
        List<HouseImage> houseImage2List =houseImageService.getHouseImageListByHouseIdAndType(id,2);
        //将房产图片放入到请求域中
        model.addAttribute("houseImage2List",houseImage2List);

        //根据房源id查询经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerListByHouseId(id);
        //将经纪人信息放入到请求域中
        model.addAttribute("houseBrokerList",houseBrokerList);

        //根据房源id查询房东信息
        List<HouseUser> houseUserList = houseUserService.getHouseUserListByHouseId(id);
        //将房东信息放入到请求域中
        model.addAttribute("houseUserList",houseUserList);

        return "house/show";
    }


    //获取数据并将之放入到请求域中
    private void setRequestAttribute(Model model) {
        //获取所有的小区
        List<Community> communityList = communityService.findAll();
        //获取所有的户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //获取所有的楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //获取所有的建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //获取所有的朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //获取所有的装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //获取所有的房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        //将数据共享到请求域中
        model.addAttribute("communityList", communityList);
        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("houseUseList", houseUseList);
    }
}
