package com.wrkbr.security.domain;

import com.wrkbr.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUser extends User {


    private UserVO boardVO;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


    public CustomUser(UserVO boardVO) {
        super(boardVO.getUserid(), boardVO.getUserpw(), boardVO.getAuthList().stream()
                                                                .map( auth -> new SimpleGrantedAuthority(auth.getAuth()) )
                                                                .collect(Collectors.toList())
                                                                );
        this.boardVO = boardVO;
    }


    public UserVO getBoardVO() {
        return boardVO;
    }
}
