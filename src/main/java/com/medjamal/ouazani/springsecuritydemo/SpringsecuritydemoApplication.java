package com.medjamal.ouazani.springsecuritydemo;

import com.medjamal.ouazani.springsecuritydemo.entities.Role;
import com.medjamal.ouazani.springsecuritydemo.helpers.Constants;
import com.medjamal.ouazani.springsecuritydemo.services.RoleService;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringsecuritydemoApplication implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	private static Logger logger = LoggerFactory.getLogger(SpringsecuritydemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritydemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.createRolesIfNotExist();
		logger.info("Application started successfully");
	}

	private void createRolesIfNotExist() {
		if(roleService.getAllRoles().isEmpty()){
			logger.info("There is no role in database, creating some roles");
			Set<Role> roles = new HashSet<>();
			roles.add(new Role(Constants.ROLE_ADMIN));
			roles.add(new Role(Constants.ROLE_USER));
			roles.add(new Role(Constants.ROLE_DEVELOPER));
			roleService.saveAll(roles);
		}
	}
}
