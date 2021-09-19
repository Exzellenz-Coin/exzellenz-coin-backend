package de.excellence;

import com.google.common.collect.Lists;
import de.excellence.api.*;
import de.excellence.auth.BasicAuthenticator;
import de.excellence.auth.TokenAuthenticator;
import de.excellence.core.AuthToken;
import de.excellence.core.User;
import de.excellence.db.AuthTokenDao;
import de.excellence.db.UserDao;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.List;

public class ECBApplication extends Application<ECBConfiguration> {
    private final HibernateBundle<ECBConfiguration> hibernateBundle =
            new HibernateBundle<>(
                    User.class,
                    AuthToken.class
            ) {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(ECBConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("No starting arguments given! Starting server in dev mode!");
            new ECBApplication().run("db", "migrate", "config-dev-h2.yml");
            new ECBApplication().run("server", "config-dev-h2.yml");
        } else {
            new ECBApplication().run(args);
        }
    }

    @Override
    public String getName() {
        return "ExcellenceCoinBackend";
    }

    @Override
    public void initialize(final Bootstrap<ECBConfiguration> bootstrap) {
        // Add hibernate bundle
        bootstrap.addBundle(hibernateBundle);
        // This activates the migrations function of dropwizard.
        // It will read the migration operations from the file migrations.sql
        bootstrap.addBundle(new MigrationsBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ECBConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

            @Override
            public String getMigrationsFileName() {
                return "migrations.sql";
            }
        });
    }

    @Override
    public void run(final ECBConfiguration configuration,
                    final Environment environment) {
        var sessionFactory = hibernateBundle.getSessionFactory();
        final ECBService eCBService = new ECBService(
                new UserDao(sessionFactory),
                new AuthTokenDao(sessionFactory)
        );

        // Registering the api endpoints
        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new LoginResource(eCBService));

        // Initializing and registering the authenticators
        environment.jersey().register(new AuthDynamicFeature(new ChainedAuthFilter<>(createAuthFilters())));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    /**
     * Creates the Basic and OAuth authenticators.
     *
     * @return The authentication filters
     */
    @SuppressWarnings("rawtypes")
    private List<AuthFilter> createAuthFilters() {
        BasicAuthenticator basicAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(BasicAuthenticator.class);
        final AuthFilter basicAuthFilter =
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(basicAuthenticator)
                        .setPrefix("Basic")
                        .buildAuthFilter();
        TokenAuthenticator tokenAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(TokenAuthenticator.class);
        final AuthFilter tokenAuthFilter =
                new OAuthCredentialAuthFilter.Builder<>()
                        .setAuthenticator(tokenAuthenticator)
                        .setPrefix("Bearer")
                        .buildAuthFilter();

        return Lists.newArrayList(basicAuthFilter, tokenAuthFilter);
    }
}
