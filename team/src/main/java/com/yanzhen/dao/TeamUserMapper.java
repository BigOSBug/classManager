package com.yanzhen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.model.TeamUser;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 社团成员管理 Mapper 接口
 * </p>
 *
 * @author kappy
 * @since 2022-07-25
 */
@Component("teamUserDao")
public interface TeamUserMapper extends BaseMapper<TeamUser> {

    /**
     * 高级查询
     */
    List<TeamUser> queryTeamUserList(@Param("email") String email,
                                     @Param("tel") String tel,
                                     @Param("userId")Integer userId);


    /**
     * 根据电话和 社团id判断是否存在该对象
     */
    TeamUser queryTeamUserByTeamIdAndTel(@Param("teamId") int teamId,
                                         @Param("tel")  String tel);


    /**
     * 根据用户名 密码 查询是否有当前对象
     */
    TeamUser queryByUserNameAndPwd(@Param("username") String username,
                                   @Param("password") String password);

}
