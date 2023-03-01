package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.HouseUser;
import com.shaluy.service.HouseUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/houseUser")
public class HouseUseController {

    @Reference
    private HouseUserService houseUserService;

    //去到添加页面
    @RequestMapping("/create")
    public String toCreatePage(@Param("houseId") Long houseId, Model model){
        model.addAttribute("houseId",houseId);

        return "houseUser/create";
    }

    //添加一个房东
    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        //添加一个房东
        houseUserService.insert(houseUser);

        return "common/successPage";
    }

    //去到修改页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id, Model model){
        //根据id获取房东
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser", houseUser);

        return "houseUser/edit";
    }

    //修改房东
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        //更新房东信息
        houseUserService.update(houseUser);

        return "common/successPage";
    }

    //删除一个房东
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id){
        //删除房东
        houseUserService.delete(id);

        return "redirect:/house/"+houseId;
    }
}
