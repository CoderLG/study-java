package com.company.project.service.impl;

import com.company.project.dao.TUserMapper;
import com.company.project.model.TUser;
import com.company.project.service.TUserService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/03/23.
 */
@Service
@Transactional
public class TUserServiceImpl extends AbstractService<TUser> implements TUserService {
    @Resource
    private TUserMapper tUserMapper;

}
