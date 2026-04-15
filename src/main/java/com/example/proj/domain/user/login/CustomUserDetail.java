package com.example.proj.domain.user.login;



//여기서부터 계속
//TODO:userDetail, detailService, 로그인 후 세션이 어떻게 생성되는지, 생성된 세션에 어떻게 접근하는지


import com.example.proj.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//1.단 인증까지만 만들고
//2. 세션기능 활용 찾아보고
//3. 유저 role 찾아보고
//4. 영속쿠키 확인
//spring Security에서 호출하는 인증을 위한 userModel의 정보를 담은 객체

@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private final UserModel user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswd();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    public UserModel getUserModel(){
        return user;
    }


    //계정 상태 반환 메서드
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
