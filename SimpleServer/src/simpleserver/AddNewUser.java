package simpleserver;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Add new username/password pair to the user.txt file.
 *
 * @author Terry Wong
 */
public class AddNewUser {

    private static String username = null, password = null, rtpassword = null, line;

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st;
            System.out.println("Please enter the username:");
            st = new StringTokenizer(in.readLine());
            username = st.nextToken();
            System.out.println("Please enter the password:");
            st = new StringTokenizer(in.readLine());
            password = st.nextToken();
            System.out.println("Please retype the password:");
            st = new StringTokenizer(in.readLine());
            rtpassword = st.nextToken();
            if (password.equals(rtpassword)) {
                Base64Encoder encoder = new Base64Encoder(password);
                String usrpwd = username + ":" + encoder.processString();
                System.out.println(usrpwd);
                File file = new File("conf" + File.separator + "users.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedReader reader = new BufferedReader(new FileReader(file));
                line = reader.readLine();
                if (line == null || !line.contains(usrpwd)) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                    bw.write(usrpwd + " ");
                    bw.flush();
                    bw.close();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
