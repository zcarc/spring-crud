package com.wrkbr.mapper;

import com.wrkbr.domain.PlatformVO;
import com.wrkbr.domain.ProfileVO;
import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;

public interface UserMapper {

    public UserVO read(String userid);
    public void insert(UserVO vo);
    public void insertAuth(UserAuthVO authVO);

    public void insertPlatformUser(@Param("id") String id, @Param("pw") String pw);
    public void insertPlatformAuth(@Param("id") String id, @Param("auth") String auth);
    public void insertPlatformTable(PlatformVO platformVO);

    public String selectPrefix(PlatformVO platformVO);

    public ProfileVO readProfile(String userId);
    public String readNickname(String userId);
    public String readId(String userid);
    public String readEmail(String readEmail);

    public void updateUser(UserVO userVO);
    public void updatePW(@Param("userpw") String userpw, @Param("useremail") String useremail);





}
