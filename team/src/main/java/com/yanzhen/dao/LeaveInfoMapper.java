package com.yanzhen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.model.LeaveInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 在线留言管理 Mapper 接口
 * </p>
 *
 * @author kappy
 * @since 2022-07-25
 */
@Component("leaveDao")
public interface LeaveInfoMapper extends BaseMapper<LeaveInfo> {

    List<LeaveInfo> queryLeaveInfoAll(@Param("tel") String tel);
}
