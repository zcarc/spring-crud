package com.wrkbr.security.domain;

import com.wrkbr.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUser extends User {


    private UserVO vo;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


    public CustomUser(UserVO vo) {
        super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream()
                                                                .map( auth -> new SimpleGrantedAuthority(auth.getAuth()) )
                                                                .collect(Collectors.toList())
                                                                );
        this.vo = vo;
    }


    public UserVO getVo() {
        return vo;
    }
}
