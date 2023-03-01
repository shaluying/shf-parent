package com.shaluy.service;

import com.shaluy.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    /**
     * 根据id查询数组字典中的数据
     * @param id id
     * @return 返回zTree可以渲染的数据格式 例如：[{ id:'0331',name:'n3.3.n1',isParent:true}]
     */
    List<Map<String, Object>> findZnodes(Long id);

    /**
     * 根据id获取子节点数据列表
     * @param id id
     * @return
     */
    List<Dict> findListByParentId(Long id);

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode 编码
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);

    /**
     * 根据id获得节点名字
     * @param id
     * @return
     */
    String getNameById(Long id);
}
