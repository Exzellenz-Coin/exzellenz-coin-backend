package de.excellence.db;

import de.excellence.core.AuthToken;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class AuthTokenDao extends AbstractDAO<AuthToken> {
    public AuthTokenDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public AuthToken save(AuthToken token) {
        return persist(token);
    }

    @SuppressWarnings("unchecked")
    public AuthToken findByToken(String token) {
        return this.uniqueResult((Query<AuthToken>) namedQuery("excellence.Token.byToken").setParameter(1, token));
    }
}
