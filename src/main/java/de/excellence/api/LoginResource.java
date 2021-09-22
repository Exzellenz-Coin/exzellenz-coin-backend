package de.excellence.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.excellence.core.User;
import de.excellence.core.AuthToken;
import de.excellence.ECBService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
	/**
	 * This methods creates a token for a user and returns both in a hashmap.
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@UnitOfWork
	public Map<String, Object> login(@Auth User user) {
        final Map<String, Object> map = new HashMap<>();
        
        final AuthToken authToken = new AuthToken(user);
		map.put("token", authToken);
		map.put("user", user);
		
		return map;
	}
	
	/**
	 * Register a new User.
	 * 
	 * @param createUser
	 * @return
	 */
	@PUT
	@UnitOfWork
	public User registerUser(@Valid CreateUser createUser) {
		final User user = new User(createUser.username,
				createUser.password
				);
		ECBService.instance.userDao.save(user);
		return user;
	}
	/**
	 * Wrapper class for registration of user.
	 * 
	 * @author Garbers
	 *
	 */
	private static class CreateUser {
		@NotNull
		@JsonProperty
		private String username;
		@NotNull
		@JsonProperty
		private String password;
		
	}
}
