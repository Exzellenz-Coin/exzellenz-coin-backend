package de.excellence.auth;

import de.excellence.ECBService;
import de.excellence.core.AuthToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.Optional;

/**
 * This authenticator is used for user authentication using tokens.
 * This can only be used after an initial login with {@link BasicAuthenticator}
 * on the /login endpoint.
 */
public class TokenAuthenticator implements Authenticator<String, Principal> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticator.class);

    @Override
    @UnitOfWork
    public Optional<Principal> authenticate(String token) throws AuthenticationException {
        final AuthToken authToken = ECBService.instance.authTokenDao.findByToken(token);
        if (authToken != null) {
            LOGGER.debug("Bearer login for %s successful", authToken.getUser().getName());
            return Optional.of(authToken.getUser());
        }
        LOGGER.debug("Bearer login failed");
        return Optional.empty();
    }
}
