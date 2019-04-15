package tw.com.wd.server;

public abstract class AbstractNettyServer implements Server {
    protected String host;
    protected int port;


    public AbstractNettyServer(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }
}
