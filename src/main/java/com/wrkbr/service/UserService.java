package com.wrkbr.service;

import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;

public interface UserService {

    public void insert(UserVO vo, String auth);
}
