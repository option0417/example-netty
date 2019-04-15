package tw.com.wd.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class NettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        System.out.println("Context: " + ctx.getClass().getName());
        System.out.println("HttpObj: " + msg.getClass().getName());

        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            boolean keepAlive = HttpUtil.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());



            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }

            processRequest(ctx, req);
            System.out.println("");
            System.out.println("");
        } else if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            processContent(ctx, content);
            System.out.println("");
            System.out.println("");
        }
    }

    private Response processRequest(ChannelHandlerContext ctx, HttpRequest req) {
        HttpMethod httpMethod = req.method();
        String uri = req.uri();
        HttpHeaders httpHeaders = req.headers();
        HttpVersion httpVersion = req.protocolVersion();
        DecoderResult decoderResult = req.decoderResult();

        System.out.println("HttpMethod: " + httpMethod.name());
        System.out.println("Uri: " + uri);

        System.out.println("Headers");
        List<Map.Entry<String, String>> entryList = httpHeaders.entries();
        for (Map.Entry<String, String> entry : entryList) {
            System.out.printf("K/V - %s : %s\n", entry.getKey(), entry.getValue());
        }

        System.out.println("HttpVersion: " + httpVersion.text());
        System.out.println("DeciderResult: " + decoderResult.toString());
        return null;
    }

    private Response processContent(ChannelHandlerContext ctx, HttpContent content) {
        System.out.println("Body: " + content.toString());

        ByteBuf contentByteBuf = content.content();
        System.out.println("ByteBuf: " + contentByteBuf.getClass().getName());
        System.out.println("toString: " + contentByteBuf.toString());

        System.out.println("BodyContent: " + contentByteBuf.toString(Charset.forName("UTF8")));

        for (int i = 0; i < contentByteBuf.capacity(); i ++) {
            byte b = contentByteBuf.getByte(i);
            System.out.println((char) b);
        }

        return null;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
