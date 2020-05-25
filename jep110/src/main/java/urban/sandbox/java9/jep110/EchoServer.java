package urban.sandbox.java9.jep110;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.util.Headers;

import java.util.logging.Logger;

public class EchoServer {

    private static final Logger LOGGER = Logger.getLogger(EchoServer.class.getSimpleName());

    private static final int PORT = 8888;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        final Undertow server = Undertow.builder().setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpListener(PORT, HOST)
                .setHandler(exchange -> {
                    LOGGER.info("Client address is: " + exchange.getConnection().getPeerAddress().toString());
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getRequestReceiver().receiveFullString((e, m) -> e.getResponseSender().send(m));
                }).build();

        server.start();
    }
}
