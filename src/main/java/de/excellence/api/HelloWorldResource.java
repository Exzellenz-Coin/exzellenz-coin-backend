package de.excellence.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.excellence.core.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final AtomicLong counter = new AtomicLong();

    /**
     * This Hello World method returns a JSON with the format
     * {
     * "id": 0,
     * "content": "Hello ..."
     * }
     * What is behind the hello depends on the parameters of the request.
     * If the query parameter name is set it will be behind the Hello.
     * If the request is by a logged in user the username will be added.
     * If none of the above is true it will simply say Hello World.
     *
     * @param user The logged in user if one exists
     * @param name The name query parameter
     * @return A JSON according to RFC 1149 standard
     */
    @GET
    @Timed
    public HelloWorld sayHello(@Auth Optional<User> user, @QueryParam("name") Optional<String> name) {
        return new HelloWorld(
                counter.incrementAndGet(),
                "Hello " + name.orElse(user.map(User::getName).orElse("World")) + "!"
        );
    }

    private static class HelloWorld {
        private long id;
        private String content;

        public HelloWorld() {

        }

        public HelloWorld(long id, String content) {
            this.id = id;
            this.content = content;
        }

        @JsonProperty
        public long getId() {
            return id;
        }

        @JsonProperty
        public String getContent() {
            return content;
        }
    }
}
