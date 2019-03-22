package com.wrkbr.service;

import com.wrkbr.domain.PlatformVO;
import com.wrkbr.domain.UserAuthVO;
import com.wrkbr.domain.UserVO;
import com.wrkbr.mapper.UserMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Log4j
public class UserServiceImpl implements UserService{

    @Setter(onMethod_ = @Autowired)
    UserMapper userMapper;

    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwEncoder;

    @Override
    @Transactional
    public void insert(UserVO vo, String auth){


        userMapper.insert(vo);

        UserAuthVO authVO = new UserAuthVO();
        authVO.setUserid(vo.getUserid());
        authVO.setAuth(auth);

        userMapper.insertAuth(authVO);

        // 소셜 로그인 체크
        //String id = userMapper.checkPlatform(vo.getUserid());
        //log.info("id: " + id);
        //if(id != null)
        //    userMapper.updateUserId(id);

    }


    @Override
    @Transactional
    public void insertAllInfoPlatformUser(PlatformVO platformVO){

        log.info("insertAllInfoPlatformUser...  platformVO: " + platformVO);
        log.info("===================================");

        String auth = "ROLE_MEMBER";
        String generate_id = null;

        if(platformVO.getKakao_id() != null){

            generate_id = platformVO.getKa_generate() + platformVO.getKakao_id();
            userMapper.insertPlatformUser(platformVO.getKakao_id(), pwEncoder.encode(generate_id));
            userMapper.insertPlatformAuth(platformVO.getKakao_id(), auth);
            userMapper.insertPlatformTable(platformVO);

        } else if(platformVO.getFacebook_id() != null){

            generate_id = platformVO.getFb_generate() + platformVO.getFacebook_id();
            userMapper.insertPlatformUser(platformVO.getFacebook_id(), pwEncoder.encode(generate_id));
            userMapper.insertPlatformAuth(platformVO.getFacebook_id(), auth);
            userMapper.insertPlatformTable(platformVO);

        } else if(platformVO.getGoogle_id() != null){

            generate_id = platformVO.getGg_generate() + platformVO.getGoogle_id();
            userMapper.insertPlatformUser(platformVO.getGoogle_id(), pwEncoder.encode(generate_id));
            userMapper.insertPlatformAuth(platformVO.getGoogle_id(), auth);
            userMapper.insertPlatformTable(platformVO);
        }


    }

}
