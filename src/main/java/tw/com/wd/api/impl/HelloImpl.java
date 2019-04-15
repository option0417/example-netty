package tw.com.wd.api.impl;

import com.google.gson.Gson;
import tw.com.wd.api.Hello;
import tw.com.wd.obj.HelloResponse;

import javax.inject.Singleton;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


@Singleton
public class HelloImpl implements Hello {
    private byte[] responseBytes;
    private String responseContent;
    private HelloResponse helloResponse;


    public HelloImpl() {
        super();

        try(InputStream ins = this.getClass().getClassLoader().getResourceAsStream("Response.txt")) {
            responseBytes = new byte[ins.available()];

            System.out.printf("Read %d bytes from Response.txt\n", ins.read(responseBytes));

            responseContent = new String(responseBytes, Charset.forName("UTF-8"));

            helloResponse = new HelloResponse().setContent(responseContent);

            responseContent = new Gson().toJson(helloResponse);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public Response postHello(HttpHeaders httpHeader, String body) {
        System.out.printf("Req Body: %s\n", body);
        return Response.ok().build();
    }

    @Override
    public Response getHello(HttpHeaders httpHeader) {
        System.out.printf("Send Response\n");
        return Response.ok(responseContent).build();
    }
}
