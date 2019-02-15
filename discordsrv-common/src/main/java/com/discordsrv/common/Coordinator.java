package com.discordsrv.common;

import com.discordsrv.common.abstraction.Log;
import io.moquette.broker.Server;

import java.io.IOException;
import java.util.Properties;

public class Coordinator extends Server {

    public Coordinator(String address, int port) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("host", address);
        properties.setProperty("port", String.valueOf(port));
        Log.info("Trying to bind coordinator to " + address + ":" + port);
        Server server = new Server();
        server.startServer(properties);
        Log.info("Coordinator bound successfully, listening for connections");
    }

}
