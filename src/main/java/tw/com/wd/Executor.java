package tw.com.wd;

import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import tw.com.wd.api.impl.HelloImpl;
import tw.com.wd.server.NettyServer;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;


public class Executor {
    private static final String HOST = "localhost";
    private static final int PORT = 8090;

    public static void main( String[] args ) throws Exception {
        startNettyServer();
    }

    private static void startNettyServer() throws Exception {
        NettyServer server = new NettyServer(HOST, PORT);
        server.start();
    }

    private void startJerseyWithNetty() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig resourceConfig = new ResourceConfig(HelloImpl.class);

        Channel server = NettyHttpContainerProvider.createServer(baseUri, resourceConfig, false);

        System.out.printf("IsActive: %s\n", server.isActive());
        System.out.printf("isOpen: %s\n", server.isOpen());
        System.out.printf("isRegistered: %s\n", server.isRegistered());
        System.out.printf("isWritable: %s\n", server.isWritable());

        System.out.printf("Server started.\n");
    }
}
