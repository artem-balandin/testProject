package newpackage;

import org.junit.Test;
import utility.ApiCredentials;
import java.io.IOException;
import static utility.SendMessageApi.sendMessageJson;

/**
 * Created by sheriff on 6/13/16.
 */
public class SendMessageTest {

    @Test
    public void test1() throws IOException {
        sendMessageJson(new ApiCredentials("RulonOboev", "123asdQ"), "4803813020", "new project");
    }
}
