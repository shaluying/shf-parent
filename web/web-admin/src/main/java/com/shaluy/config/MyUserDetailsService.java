package com.shaluy.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Admin;
import com.shaluy.service.AdminService;
import com.shaluy.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    //登录时，Spring Security会自动调用该方法，并将用户名传入到改方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询admin对象
        Admin admin = adminService.getByUsername(username);

        if (admin == null){
            //用户不存在
            throw new UsernameNotFoundException("用户名不存在");
        }

        //用户存在，给用户授权
        //根据用户id获取当前用户的权限码
        List<String> permissionCodeList =  permissionService.getPermissionCodesByAdminId(admin.getId());
        //创建一个用于授权的集合
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        //遍历授权码
        for (String permissionCode : permissionCodeList) {
            if (!StringUtils.isEmpty(permissionCode)){
                //创建GrantedAuthority实现类对象
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
                //将SimpleGrantedAuthority对象放入到权限集合中
                grantedAuthorityList.add(simpleGrantedAuthority);
            }
        }
        //授权
//        User user = new User(username, admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        User user = new User(username, admin.getPassword(), grantedAuthorityList);
        return user;
    }
}
