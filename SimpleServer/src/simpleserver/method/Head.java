package simpleserver.method;

import java.io.PrintWriter;
import simpleserver.Response;

/**
 * HEAD,identical to GET, except the server does not return the body in the
 * response
 *
 * @author Terry Wong
 */
public class Head extends Method {

    @Override
    public void execute(Response response, PrintWriter writer) {
        writer.println("");
        System.out.println("Cached: Finished generating response");
    }
}
