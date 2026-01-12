package com.yanzhen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.model.CostList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kappy
 * @since 2022-08-09
 */
@Component("costListDao")
public interface CostListMapper extends BaseMapper<CostList> {

    /**
     * 支持高级查询的接口
     */
    List<CostList> queryCostListAll(@Param("name") String name,
                                    @Param("teamId") Integer teamId);
}
