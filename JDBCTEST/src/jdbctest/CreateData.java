package jdbctest;

// Based on Fig. 18.24: TableDisplay.java in Deitel & Deitel "Java How To 
// Program", Third Edition, Prentice Hall 1999 
// This program displays the contents of the Student table in the
// 675Examples Database
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreateData extends JFrame {  //CHANGE THIS!!

    private Connection connection;
    private JTable table;

    public CreateData() //CHANGE THIS!!
    {
        // The URL specifying the 675Examples database to which 
        // this program connects using JDBC to connect to a
        // Microsoft ODBC database.
        String url = "jdbc:odbc:CSC775";  //CHANGE THIS!
        String username = "Terry-Netbook\\Terry Wong";
        String password = "1234";

        // Load the driver to allow connection to the database
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            connection = DriverManager.getConnection(
                    url, username, password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "Failed to load JDBC/ODBC driver.");
            cnfex.printStackTrace();
            System.exit(1);  // terminate program
        } catch (SQLException sqlex) {
            System.err.println("Unable to connect");
            sqlex.printStackTrace();
        }

        getTable();

        setSize(450, 150);
        show();
    }

    private void getTable() {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
//	statement.executeUpdate("drop table student");
//	statement.executeUpdate("drop table course");
//	statement.executeUpdate("drop table enrolled");
            statement.executeUpdate("create table student (sno varchar(3), sname varchar(5), addr varchar(2))");
            statement.executeUpdate("insert into student values ('123','smith','sf')");
            statement.executeUpdate("insert into student values ('234','jones','la')");
            statement.executeUpdate("insert into student values ('456','wong','sf')");
            statement.executeUpdate("create table course (cno varchar(3), cname varchar(5))");
            statement.executeUpdate("insert into course values ('675','dbs')");
            statement.executeUpdate("insert into course values ('415','os')");
            statement.executeUpdate("create table enrolled (sno varchar(3), cno varchar(3),grade varchar(1))");
            statement.executeUpdate("insert into enrolled values ('123','675','a')");
            statement.executeUpdate("insert into enrolled values('123','415','b')");
            statement.executeUpdate("insert into enrolled values('234','675','a')");
            statement.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    private void displayResultSet(ResultSet rs)
            throws SQLException {
        // position to first record
        boolean moreRecords = rs.next();

        // If there are no records, display a message
        if (!moreRecords) {
            JOptionPane.showMessageDialog(this,
                    "ResultSet contained no records");
            setTitle("No records to display");
            return;
        }

        setTitle("Student Table from SQL Examples");  //CHANGE THIS!!

        Vector columnHeads = new Vector();
        Vector rows = new Vector();

        try {
            // get column heads
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                columnHeads.addElement(rsmd.getColumnName(i));
            }

            // get row data
            do {
                rows.addElement(getNextRow(rs, rsmd));
            } while (rs.next());

            // display table with ResultSet contents
            table = new JTable(rows, columnHeads);
            JScrollPane scroller = new JScrollPane(table);
            getContentPane().add(
                    scroller, BorderLayout.CENTER);
            validate();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    private Vector getNextRow(ResultSet rs,
            ResultSetMetaData rsmd)
            throws SQLException {
        Vector currentRow = new Vector();

        for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
            switch (rsmd.getColumnType(i)) {
                case Types.VARCHAR:
                    currentRow.addElement(rs.getString(i));
                    break;
                case Types.INTEGER:
                    currentRow.addElement(
                            new Long(rs.getLong(i)));
                    break;
                default:
                    System.out.println("Type was: "
                            + rsmd.getColumnTypeName(i));
            }
        }

        return currentRow;
    }

    public void shutDown() {
        try {
            connection.close();
        } catch (SQLException sqlex) {
            System.err.println("Unable to disconnect");
            sqlex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        final CreateData app = new CreateData();  //CHANGE THIS!

        app.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        app.shutDown();
                        System.exit(0);
                    }
                });
    }
}
