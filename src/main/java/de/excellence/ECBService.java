package de.excellence;

import de.excellence.db.AuthTokenDao;
import de.excellence.db.UserDao;
import de.excellence.node.NodeManager;
import mainpackage.database.dao.BlockDao;
import mainpackage.database.update.DatabaseUpdater;

public class ECBService {
    public static ECBService instance;

    public NodeManager nodeManager;
    public final BlockDao blockDao;
    public final DatabaseUpdater databaseUpdater;
    public final UserDao userDao;
    public final AuthTokenDao authTokenDao;

    public ECBService(NodeManager nodeManager,
                      BlockDao blockDao,
                      DatabaseUpdater databaseUpdater,
                      UserDao userDao,
                      AuthTokenDao authTokenDao) {
        this.nodeManager = nodeManager;
        this.blockDao = blockDao;
        this.databaseUpdater = databaseUpdater;
        this.userDao = userDao;
        this.authTokenDao = authTokenDao;
        instance = this;
    }
}
