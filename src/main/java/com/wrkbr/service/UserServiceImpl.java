package com.wrkbr.service;

import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;
import com.wrkbr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void insert(UserVO vo, String auth){


        userMapper.insert(vo);

        UserAuthVO authVO = new UserAuthVO();
        authVO.setUserid(vo.getUserid());
        authVO.setAuth(auth);

        userMapper.insertAuth(authVO);
    }

}
