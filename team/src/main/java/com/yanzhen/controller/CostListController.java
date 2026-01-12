package com.yanzhen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.yanzhen.jwt.JwtUtil;
import com.yanzhen.model.CostList;
import com.yanzhen.model.Team;
import com.yanzhen.model.TypeInfo;
import com.yanzhen.service.ICostListService;
import com.yanzhen.service.ITeamService;
import com.yanzhen.util.JsonObject;
import com.yanzhen.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kappy
 * @since 2022-08-09
 */
@RestController
@RequestMapping("/costlist")
public class CostListController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ICostListService costListService;
    @Resource
    private ITeamService teamService;

    @ApiOperation(value = "新增")
    @RequestMapping("/addInfo")
    public R add(@RequestBody CostList costList,
                 HttpServletRequest request){
        //通过header 获取token
        String token=request.getHeader("token");
        //获取用户id
        Integer userId= JwtUtil.getUserId(token);
        //根据用户id 获取用户所在的社团信息
        Team team=teamService.queryTeamInfoByUserId(userId);
        //加入当前社团的id
        costList.setTeamId(team.getId());//获取当前社团id
        costList.setCtime(new Date());//当前时间

        int num= costListService.add(costList);
        if(num>0){
            return R.ok();
        }
        return R.fail("添加失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping("/deleteById")
    public R delete(Long id){
        int num= costListService.delete(id);
        if(num>0){
            return R.ok();
        }
        return R.fail("删除失败");
    }

    @ApiOperation(value = "更新")
    @RequestMapping("/updateInfo")
    public R update(@RequestBody CostList costList){
        int num= costListService.updateData(costList);
        if(num>0){
            return R.ok();
        }
        return R.fail("修改失败");
    }


    @RequestMapping("/queryCostList")
    public JsonObject queryCostList(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "15") Integer limit,
                                    String name,
                                    HttpServletRequest request){
        //通过header 获取token
        String token=request.getHeader("token");
        //获取用户id
        Integer userId= JwtUtil.getUserId(token);
        //根据用户id 获取用户所在的社团信息
        Team team=teamService.queryTeamInfoByUserId(userId);
        Integer teamId=null;
        if(team!=null){
            teamId=team.getId();
        }
        //创建对象模型
        JsonObject object=new JsonObject();
        //获取结果集合
        PageInfo<CostList> pageInfo = costListService.queryCostListAll(name,teamId ,page, limit);
        object.setCode(20000);
        object.setTotal(pageInfo.getTotal());
        object.setData(pageInfo.getList());
        return object;
    }





}
