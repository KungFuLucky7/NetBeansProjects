package simpleserver.method;

import java.io.PrintWriter;
import simpleserver.Response;

/**
 * PUT,used to modify existing resources or create new ones
 *
 * @author Terry Wong
 */
public class Put extends Method {

    @Override
    public void execute(Response response, PrintWriter writer) {
        try {
            if (response.getStatus() == "201" || response.getStatus() == "204") {
                response.processPutRequest();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Error writing file");
        }
    }
}
