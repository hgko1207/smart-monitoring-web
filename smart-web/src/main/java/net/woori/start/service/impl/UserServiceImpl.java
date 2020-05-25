package net.woori.start.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.start.domain.db.User;
import net.woori.start.repository.UserRepository;
import net.woori.start.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User get(Integer id) {
		return userRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getList() {
		return userRepository.findAll();
	}

	@Override
	public boolean regist(User domain) {
		if (isNew(domain)) {
			return userRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(User domain) {
		if (!isNew(domain)) {
			return userRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(Integer id) {
		userRepository.deleteById(id);
		return true;
	}

	private boolean isNew(User domain) {
		return !userRepository.existsById(domain.getId());
	}

	@Override
	public User login(String username, String password) {
		return userRepository.findByUserIdAndPassword(username, password);
	}
}
