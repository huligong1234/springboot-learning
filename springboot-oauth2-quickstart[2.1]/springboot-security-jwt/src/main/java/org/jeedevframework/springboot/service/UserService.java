package org.jeedevframework.springboot.service;

import org.jeedevframework.springboot.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public User getUserByUsername(String username) {
		
		//模拟返回测试数据
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if("admin".equals(username)) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("123456"));
			user.setNickName("管理员");
			user.setAuthorities(new String[] {"ROLE_ADMIN"});
			return user;
		} else if("test".equals(username)) {
			User user = new User();
			user.setUsername("test");
			user.setPassword(passwordEncoder.encode("123456"));
			user.setNickName("测试员");
			user.setAuthorities(new String[] {"ROLE_VISITOR"});
			return user;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
	}

}
