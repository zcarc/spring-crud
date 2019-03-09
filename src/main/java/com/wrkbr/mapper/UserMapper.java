package com.wrkbr.mapper;

import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;

public interface UserMapper {

    public UserVO read(String userid);
    public void insert(UserVO vo);
    public void insertAuth(UserAuthVO authVO);
}
