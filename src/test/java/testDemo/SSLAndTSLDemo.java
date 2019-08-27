package testDemo;

import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by user on 2018/3/28.
 */
public class SSLAndTSLDemo {

    @Test
    public void testDemo() throws Exception {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);

            SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket();

            String[] protocols = socket.getSupportedProtocols();

            System.out.println("Supported Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);
            }

            protocols = socket.getEnabledProtocols();

            System.out.println("Enabled Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);

        }
    }
}
