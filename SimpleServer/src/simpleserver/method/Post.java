package simpleserver.method;

import java.io.PrintWriter;
import simpleserver.Response;

/**
 * POST, instructs the server that the request includes a block of data, which
 * is typically used as input to a server-side application
 *
 * @author Terry Wong
 */
public class Post extends Method {

    @Override
    public void execute(Response response, PrintWriter writer) {
        try {
            response.writeBody(writer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Error writing body");
        }
    }
}
