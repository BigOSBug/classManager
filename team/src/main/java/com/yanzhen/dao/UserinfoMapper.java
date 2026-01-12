package com.yanzhen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 管理员信息表 Mapper 接口
 * </p>
 *
 * @author kappy
 * @since 2022-07-25
 */
@Component("userInfoDao")
public interface UserinfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据对象属性值查询对象是否存在
     * 用户名 密码 角色
     */
    UserInfo queryUserInfoByUserNameAndPasswordAndType(UserInfo userInfo);

    /**
     * 高级查询
     */
    List<UserInfo> queryUserInfoList(UserInfo userInfo);

    /**
     * 修改密码
     */
    int updatePasswordByIdAndNewPassword(@Param("id") Integer id,
                                         @Param("pwd") String  pwd);

}
