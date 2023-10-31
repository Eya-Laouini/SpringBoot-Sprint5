package com.ons.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ons.users.entities.Role;
import com.ons.users.entities.User;
import com.ons.users.repos.RoleRepository;
import com.ons.users.repos.UserRepository;


@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRep;
	@Autowired
	RoleRepository roleRep;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRep.save(user);
	}
	@Override

	public User addRoleToUser(long id, Role r) {
		User usr = userRep.findUserById(id);

		List<Role> roles = usr.getRoles();
		roles.add(r);

		usr.setRoles(roles);

		return userRep.save(usr);
	}


	@Override
	public List<User> findAllUsers() {
		return userRep.findAll();
	}




	@Override
	public User findUserByUsername(String username) {
		return userRep.findByUsername(username);
	}


	@Override
	public User findUserById(Long id) {
		return userRep.findById(id).get();
	}

	@Override
	public List<Role> findAllRoles() {
		return roleRep.findAll();
	}
	@Override
	public Role findRoleById(Long id) {
		return roleRep.findRoleById(id);
	}

	@Override
	public void deleteUser(long id) {
		userRep.deleteByUserId(id);
	}
	@Override
	public User removeRoleFromUser(long id,Role r)
	{
		User user = userRep.findUserById(id);
		List<Role> listOfRoles = user.getRoles();
		listOfRoles.remove(r);
		userRep.save(user);
		return user;
	}

	@Override
	public List<Role> findUserRolesById(Long id) {
		User user = userRep.findUserById(id);
		List<Role> listOfRoles = user.getRoles();
		return listOfRoles;
	}

}