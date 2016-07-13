package utility;

import com.oracle.javafx.jmx.json.JSONException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import java.io.IOException;

import static utility.ApiManager.getClient;

/**
 * Created by sheriff on 7/13/16.
 */
public class SendMessageApi {

    public static int sendMessageJson(ApiCredentials credentials, String phoneNumber, String message)
            throws IOException, JSONException {

        Form f = new Form();
        f.add("User", credentials.getUserName());
        f.add("Password", credentials.getPassword());
        f.add("format", "json");
        f.add("PhoneNumbers", phoneNumber);
        f.add("Message", message);

        ClientResponse response = getClient().resource("https://app.stg2.eztexting.com/sending/messages")
                .post(ClientResponse.class, f);
        return response.getStatus();
    }
}
