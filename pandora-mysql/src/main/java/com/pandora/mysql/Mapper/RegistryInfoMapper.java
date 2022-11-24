package com.pandora.mysql.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandora.mysql.model.RegistryInfo;
import org.apache.ibatis.annotations.Update;


public interface RegistryInfoMapper extends BaseMapper<RegistryInfo> {

    //清空指定表
    @Update("truncate table registry_info")
    void truncate();


}
