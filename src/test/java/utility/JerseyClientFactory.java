package utility;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class JerseyClientFactory {

    private static final HostnameVerifier NO_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
            }
    };

    private static final SSLContext CTX = buildSSLContext();

    private static SSLContext buildSSLContext() {
        try {
            final SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, TRUST_ALL_CERTS, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(NO_VERIFIER);
            SSLContext.setDefault(ctx);
            return ctx;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not build SSLContext", e);
        }
    }

    public static Client newClientWithoutReadTimeout() {
        return newClient(-1);
    }

    public static Client newClient(final Integer readTimeoutMilis) {
//        Preconditions.checkArgument(readTimeoutMilis != null, "read timeout must not be null");
        final ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
                NO_VERIFIER, CTX));

        final Client c = Client.create(clientConfig);
        if (readTimeoutMilis > 0) {
            c.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, readTimeoutMilis);
        }
        c.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        return c;
    }
}
