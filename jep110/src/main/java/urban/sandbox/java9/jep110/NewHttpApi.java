package urban.sandbox.java9.jep110;


import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class NewHttpApi {

    public static void main(String[] args) throws URISyntaxException, CertificateException, IOException, InterruptedException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        final HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get")).GET().build();

        final HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandler.asString());
        /**
         * Ocorreu SSLHandshakeException: PKIX: unable to find valid certification path to requested target
         *
         * Foi necessário adicionar o certificado da página.
         * http://magicmonster.com/kb/prg/java/ssl/pkix_path_building_failed.html
         *
         * Para adicionar o certificado:
         * $ keytool -import -alias postman -keystore /opt/jdk-9.0.4/lib/security/cacerts -file ~/home/diego/Downloads/sca1b.crt
         *
         * Para remover o certificado:
         * $ keytool -delete -alias postman -keystore /opt/jdk-9.0.4/lib/security/cacerts
         */

        /**
         * Também é possível adicionar o certificado dinamicamente, ver buildPostmanContext()
         */
        //HttpResponse<String> response = HttpClient.newBuilder().sslContext(buildPostmanContext()).build().send(request, HttpResponse.BodyHandler.asString());

        System.out.println(response.body());
    }

    private static SSLContext buildPostmanContext() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final InputStream certificateFile = NewHttpApi.class.getResourceAsStream("/sca1b.crt");
        Certificate certificate;
        try {
            certificate = cf.generateCertificate(certificateFile);
        } finally {
            certificateFile.close();
        }

        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("postman", certificate);

        TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmFactory.init(keyStore);

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmFactory.getTrustManagers(), null);
        return sslContext;
    }

}
