package com.wrkbr.security;

import com.wrkbr.domain.UserVO;
import com.wrkbr.mapper.UserMapper;
import com.wrkbr.security.domain.CustomUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("loadUserByUsername...");
        log.warn("Load user by username: " + username);

        // loadUserByUsername username means UserVO userid.
        UserVO vo = userMapper.read(username);

        log.warn("Result UserVO: " + vo);

        return vo == null ? null : new CustomUser(vo);
    }
}
