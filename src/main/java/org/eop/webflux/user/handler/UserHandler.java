package org.eop.webflux.user.handler;

import java.util.List;

import org.eop.webflux.user.bean.User;
import org.eop.webflux.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lixinjie
 * @since 2019-02-20
 */
@Component
public class UserHandler {
	
	@Autowired
	private IUserService userService;
	
	public Mono<ServerResponse> addUser(ServerRequest request) {
		Mono<User> user = extractUser(request);
		Mono<User> u = userService.addUser(user);
		return cramUser(u);
	}
	
	public Mono<ServerResponse> modifyUser(ServerRequest request) {
		return Mono.empty();
	}
	
	public Mono<ServerResponse> removeUser(ServerRequest request) {
		Mono<String> id = extractId(request);
		return Mono.empty();
	}

	public Mono<ServerResponse> getUser(ServerRequest request) {
		return Mono.empty();
	}
	
	public Mono<ServerResponse> listUsers(ServerRequest request) {
		Flux<User> users = userService.listUsers();
		return cramUsers(users);
	}
	
	private Mono<String> extractId(ServerRequest request) {
		return request.formData().map(map -> map.getFirst("id"));
	}
	
	private Mono<User> extractUser(ServerRequest request) {
		return request.bodyToMono(User.class);
	}
	
	private Mono<ServerResponse> cramUser(Mono<User> user) {
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(user, User.class);
	}
	
	private Mono<ServerResponse> cramUsers(Flux<User> users) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(users, User.class);
	}
}
