package com.yanzhen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.model.TypeInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 社团分类和介绍 Mapper 接口
 * </p>
 *
 * @author kappy
 * @since 2022-07-25
 */
@Component("typeDao")
public interface TypeInfoMapper extends BaseMapper<TypeInfo> {

    /**
     * 支持高级查询
     */
    List<TypeInfo> queryTypeList(TypeInfo info);
}
