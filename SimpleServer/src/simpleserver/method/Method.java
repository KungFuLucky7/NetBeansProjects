package simpleserver.method;

import java.io.PrintWriter;
import simpleserver.Response;

/**
 * The abstract class for methods.
 *
 * @author Terry Wong
 */
public abstract class Method {

    public abstract void execute(Response response, PrintWriter writer);
}
