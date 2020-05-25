package net.woori.start.repository;

import net.woori.start.domain.db.User;

public interface UserRepository extends DefaultRepository<User, Integer> {

	User findByUserIdAndPassword(String userId, String password);

}
