package de.excellence;

import de.excellence.db.AuthTokenDao;
import de.excellence.db.UserDao;

public class ECBService {
    public static ECBService instance;

    public final UserDao userDao;
    public final AuthTokenDao authTokenDao;

    public ECBService(UserDao userDao,
                      AuthTokenDao authTokenDao) {
        this.userDao = userDao;
        this.authTokenDao = authTokenDao;
        instance = this;
    }
}
