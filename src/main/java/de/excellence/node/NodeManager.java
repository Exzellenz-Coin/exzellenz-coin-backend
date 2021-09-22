package de.excellence.node;

import de.excellence.ECBConfiguration;
import io.dropwizard.lifecycle.Managed;
import mainpackage.database.DatabaseConfiguration;
import mainpackage.database.DatabaseManager;
import mainpackage.server.node.FullNode;
import mainpackage.server.node.INode;

public class NodeManager implements Managed {
    private INode node;

    public NodeManager(ECBConfiguration configuration) {
        // TODO: Maybe replace the JDBI DAOs with Hibernate DAOs?
        final var jdbcUrl = configuration.getDataSourceFactory().getUrl();
        final var user = configuration.getDataSourceFactory().getUser();
        final var password = configuration.getDataSourceFactory().getPassword();
        DatabaseManager.databaseConfiguration = new DatabaseConfiguration(jdbcUrl, user, password);
    }

    @Override
    public void start() throws Exception {
        this.node = new FullNode();
        node.start();
    }

    @Override
    public void stop() throws Exception {
        node.stop();
    }

    public INode getNode() {
        return node;
    }
}
