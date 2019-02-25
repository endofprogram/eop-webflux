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
		return user.doOnNext(u -> {
			users.add(u);
		});
	}

	@Override
	public Mono<User> modifyUser(Mono<User> user) {
		return user.doOnNext(u -> {
			Optional<User> userOpt = users.stream().filter(us -> us.getId().equals(u.getId())).findFirst();
			if (userOpt.isPresent()) {
				User ur = userOpt.get();
				ur.setName(u.getName());
			}
		});
	}

	@Override
	public Mono<User> removeUser(Mono<String> id) {
		return id.map(uid -> {
			Optional<User> userOpt = users.stream().filter(user -> user.getId().equals(uid)).findFirst();
			if (userOpt.isPresent()) {
				users.remove(userOpt.get());
				return userOpt.get();
			}
			User du = new User();
			du.setId(uid);
			du.setName("没有该用户");
			return du;
		});
	}

	@Override
	public Mono<User> getUser(Mono<String> id) {
		return id.map(uid -> {
			User du = new User();
			du.setId(uid);
			du.setName("没有该用户");
			return users.stream().filter(user -> user.getId().equals(uid)).findFirst().orElse(du);
		});
	}

	@Override
	public Flux<User> listUsers() {
		return Flux.fromIterable(users);
	}

}
