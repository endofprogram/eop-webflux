package org.eop.webflux.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eop.webflux.user.bean.User;
import org.eop.webflux.user.service.IUserService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lixinjie
 * @since 2019-02-20
 */
@Service
public class UserServiceImpl implements IUserService {

	private List<User> users = new ArrayList<>(20);
	
	@Override
	public Mono<User> addUser(Mono<User> user) {
		return user.doOnNext(u -> users.add(u));
	}

	@Override
	public User modifyUser(Mono<User> user) {
		return null;
	}

	@Override
	public User removeUser(Mono<String> id) {
		User user = userById(id);
		if (user == null) {
			return null;
		}
		users.remove(user);
		return user;
	}

	@Override
	public User getUser(Mono<String> id) {
		return userById(id);
	}

	@Override
	public Flux<User> listUsers() {
		return Flux.fromIterable(users);
	}

	private User userById(Mono<String> id) {
		Optional<User> userOpt = users.stream().filter(user -> user.getId().equals(id)).findFirst();
		return userOpt.isPresent() ? userOpt.get() : null;
	}
}
