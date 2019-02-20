package org.eop.webflux.user.router;

import org.eop.webflux.user.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author lixinjie
 * @since 2019-02-20
 */
@Configuration
public class UserRouter {

	@Bean
	public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
		return RouterFunctions.route().path("/user", builder -> {
			builder.POST("", userHandler::addUser)
					.PUT("", userHandler::modifyUser)
					.DELETE("/{id}", userHandler::removeUser)
					.GET("/{id}", userHandler::getUser)
					.GET("", userHandler::listUsers);
		}).build();
	}
}
