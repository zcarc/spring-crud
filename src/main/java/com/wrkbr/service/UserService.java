package com.wrkbr.service;

import com.wrkbr.domain.PlatformVO;
import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;

import java.util.Date;

public interface UserService {

    public void insert(UserVO vo, String auth);

    public void insertAllInfoPlatformUser(PlatformVO platformVO);
}
