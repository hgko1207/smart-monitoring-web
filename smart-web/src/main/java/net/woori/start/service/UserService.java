package net.woori.start.service;

import net.woori.start.domain.db.User;

public interface UserService extends CRUDService<User, Integer> {

	User login(String username, String password);

}
