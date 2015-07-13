package com.syslab.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("assembler")
public class Assembler {

  @Transactional(readOnly = true)
  User buildUserFromUserEntity(com.syslab.entity.User userEntity) {
	 	  
	Integer id = userEntity.getId();
    String username = userEntity.getUsername();
    String password = userEntity.getPassword();
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    User user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    return user;
  }
}