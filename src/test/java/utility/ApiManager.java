package utility;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public abstract class ApiManager {

    private static Client client;

    public static Client getClient() {
        if (client == null) {
            client = ClientHolder.CLIENT;
            client.addFilter(new LoggingFilter(System.out));
        }
        return client;
    }

    protected static ClientResponse getClientResponse(String uri) {
        WebResource r = getClient().resource(uri);
        return r.get(ClientResponse.class);
    }

    private static final class ClientHolder {
        static final Client CLIENT = JerseyClientFactory.newClientWithoutReadTimeout();
    }
}
