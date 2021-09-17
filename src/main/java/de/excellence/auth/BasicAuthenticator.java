package de.excellence.auth;

import com.lambdaworks.crypto.SCryptUtil;
import de.excellence.ECBService;
import de.excellence.core.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * This authenticator is used for the initial login of a user with their base64
 * encoded username and password. It simply retrieves a user with the username
 * from the database and checks if the password matches.
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthenticator.class);

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials basicCredentials) {
        final User user = ECBService.instance.userDao.findByName(basicCredentials.getUsername());
        if (user != null && SCryptUtil.check(basicCredentials.getPassword(), user.getHashedPassword())) {
            LOGGER.debug("Basic login for %s success", user.getName());
            return Optional.of(user);
        }
        LOGGER.debug("Basic login for %s failed", basicCredentials.getUsername());
        return Optional.empty();
    }
}
