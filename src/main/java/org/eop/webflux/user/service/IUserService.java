package org.eop.webflux.user.service;

import org.eop.webflux.user.bean.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lixinjie
 * @since 2019-02-20
 */
public interface IUserService {

	Mono<User> addUser(Mono<User> user);
	
	User modifyUser(Mono<User> user);
	
	User removeUser(Mono<String> id);
	
	User getUser(Mono<String> id);
	
	Flux<User> listUsers();
}
