package simpleserver.method;

import java.io.PrintWriter;
import simpleserver.Response;

/**
 * GET, retrieve whatever information is identified by the request URL
 *
 * @author Terry Wong
 */
public class Get extends Method {

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
