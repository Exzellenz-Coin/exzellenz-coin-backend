package de.excellence.db;

import de.excellence.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDao extends AbstractDAO<User> {
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User save(User user) {
        return persist(user);
    }

    @SuppressWarnings("unchecked")
    public User findByName(String username) {
        return this.uniqueResult((Query<User>) namedQuery("excellence.User.byName").setParameter(1, username));
    }
}
