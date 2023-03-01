package com.shaluy.mapper;

import com.shaluy.entity.Dict;

import java.util.List;

public interface DictMapper {
    /**
     * 根据id查询该节点下所有的子节点
     * @param id id
     * @return
     */
    List<Dict> findListByParentId(Long id);

    /**
     * 根据id查询此节点下子节点的数量
     * @param id
     * @return
     */
    Integer countChildNodes(Long id);

    /**
     * 根据编码获得一个节点
     * @param dictCode 编码
     * @return
     */
    Dict getDictByDictCode(String dictCode);

    /**
     * 根据id获得节点的名字
     * @param id id
     * @return
     */
    String getNameById(Long id);


}
