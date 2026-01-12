package com.yanzhen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yanzhen.dao.TypeInfoMapper;
import com.yanzhen.model.TypeInfo;
import com.yanzhen.service.ITypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 社团分类和介绍 服务实现类
 * </p>
 *
 * @author kappy
 * @since 2022-07-25
 */
@Service
public class TypeInfoServiceImpl extends ServiceImpl<TypeInfoMapper, TypeInfo> implements ITypeInfoService {

    @Autowired
    private TypeInfoMapper typeDao;

    @Override
    public IPage<TypeInfo> findListByPage(Integer page, Integer pageCount){
        IPage<TypeInfo> wherePage = new Page<>(page, pageCount);
        TypeInfo where = new TypeInfo();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(TypeInfo typeInfo){
        return baseMapper.insert(typeInfo);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(TypeInfo typeInfo){
        return baseMapper.updateById(typeInfo);
    }

    @Override
    public TypeInfo findById(Long id){
        return  baseMapper.selectById(id);
    }

    @Override
    public PageInfo<TypeInfo> queryTypeList(int page, int pageSize, TypeInfo info) {
        PageHelper.startPage(page,pageSize);
        //获取查询到的结果集合
        List<TypeInfo> list=typeDao.queryTypeList(info);
        PageInfo<TypeInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
