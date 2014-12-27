package simpleserver.method;

import java.io.PrintWriter;
import java.util.ArrayList;
import simpleserver.Response;

/**
 * TRACE, traces the requests in a chain of web proxy servers
 *
 * @author Terry Wong
 */
public class Trace extends Method {

    private ArrayList<String> savedHeaders;

    @Override
    public void execute(Response response, PrintWriter writer) {
        this.savedHeaders = response.getSavedHeaders();
        writer.println("");
        for (int i = 0; i < savedHeaders.size(); i++) {
            writer.println(savedHeaders.get(i));
        }
        System.out.println("Cached: Finished generating response");
    }
}
