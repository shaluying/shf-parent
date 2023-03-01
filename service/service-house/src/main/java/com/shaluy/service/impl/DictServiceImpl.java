package com.shaluy.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.Dict;
import com.shaluy.mapper.DictMapper;
import com.shaluy.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //根据id查询该节点下所有的子节点
        List<Dict>  dictList = dictMapper.findListByParentId(id);

        //创建返回的List [{ id:'0331',name:'n3.3.n1',isParent:true}]
        List<Map<String, Object>> zNodes = new ArrayList<>();

        //遍历dictList
        for (Dict dict : dictList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());

            //根据id查询此节点下子节点的数量
            Integer amount = dictMapper.countChildNodes(dict.getId());

            map.put("isParent",amount > 0 ? true:false);

            zNodes.add(map);
        }

        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long id) {
        return dictMapper.findListByParentId(id);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictMapper.getDictByDictCode(dictCode);

        if (dict == null) return null;

        List<Dict> dictList = dictMapper.findListByParentId(dict.getId());

        return dictList;
    }

    @Override
    public String getNameById(Long id) {
        String nameById = dictMapper.getNameById(id);

        return nameById;
    }

}
