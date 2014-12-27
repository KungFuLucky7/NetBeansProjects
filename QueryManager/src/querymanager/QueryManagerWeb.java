package querymanager;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * For CSC 775 Term Project: Implement a simple graphical user interface for executing
 * the queries.
 *
 * Query Manager Instructions: 
 * Enter a number between 1 to 10 and press "Enter" for retrieving the queries in Term Project. 
 * Users can edit the retrieved and predefined queries or type in their own queries. 
 * Press "Execute" to get the tuples from the database. 
 * Close the table to return to the Query Manager main frame.
 * Close the window to exit in Query Manger main frame.
 * Connect to MySQL database on Amazon Cloud server: sfsuswe.com 
 * Launch the WebApp from the url: http://sfsuswe.com/~twong/launchTermProject.html
 *
 * @author Terry Wong
 */
public class QueryManagerWeb extends javax.swing.JFrame {

    private int queryNum = 0;
    private String query = "";
    private static boolean error = false;
    private static boolean getTuples = false;
    private Connection connection;
    private JTable table;
    private String q1 = "INSERT INTO Photos (photo_filename, photo_date, stores_server_id, emails_user_id, owns_user_id, creates_user_id)"
            + "VALUES ('photo4.png', '2012-11-11 19:59:05',"
            + "(SELECT server_id FROM Remote_Servers WHERE server_name='Google Drive'),"
            + "(SELECT user_id FROM Users WHERE user_name='Sam'),"
            + "(SELECT user_id FROM Users WHERE user_name='Lisa'),"
            + "(SELECT user_id FROM Users WHERE user_name='Tom')); "
            + "INSERT INTO Tags (tag_name, tag_value, has_photo_id)"
            + "VALUES ('Subject', 'Sports', (SELECT photo_id FROM (SELECT photo_id, photo_filename FROM Photos WHERE photo_filename='photo4.png') AS X)); "
            + "INSERT INTO Tags (tag_name, tag_value, has_photo_id)"
            + "VALUES ('Location', 'Los Angeles, CA', (SELECT photo_id FROM (SELECT photo_id, photo_filename FROM Photos WHERE photo_filename='photo4.png') AS X)); "
            + "INSERT INTO Tags (tag_name, tag_value, has_photo_id)"
            + "VALUES ('Event', 'Basketball Game', (SELECT photo_id FROM (SELECT photo_id, photo_filename FROM Photos WHERE photo_filename='photo4.png') AS X));";
    private String q2 = "DELETE FROM Tags WHERE has_photo_id=5; "
            + "DELETE FROM Photos WHERE photo_id=5;";
    private String q3 = "UPDATE Photos SET stores_server_id=(SELECT X.server_id FROM (SELECT server_id, server_name FROM Remote_Servers WHERE server_name='Git Hub') AS X) WHERE photo_id=3;";
    private String q4 = "UPDATE Tags SET tag_value='Birthday Party' WHERE has_photo_id=3 AND tag_name='Subject';";
    private String q5 = "INSERT INTO Tags (tag_name, tag_value, has_photo_id) VALUES ('Location', 'Fremont, CA', 3);";
    private String q6 = "SELECT * FROM Photos,Tags WHERE photo_id=3 AND has_photo_id=3;";
    private String q7 = "SELECT photo_id FROM Photos,Users WHERE Photos.owns_user_id=Users.user_id AND Users.user_name='Megan';";
    private String q8 = "SELECT photo_id FROM Photos,Users WHERE Photos.emails_user_id=Users.user_id AND Users.user_name='Chris';";
    private String q9 = "SELECT photo_id FROM Photos,Users WHERE Photos.creates_user_id=Users.user_id AND Users.user_name='Megan' AND Photos.photo_date>'2012-10-10';";
    private String q10 = "SELECT photo_id FROM Photos,Tags WHERE Photos.photo_id=Tags.has_photo_id AND Tags.tag_name='Location' AND Tags.tag_value='San Francisco State University';";

    /**
     * Creates new form QueryManagerWeb
     */
    public QueryManagerWeb(String q) {
        if (getTuples && !q.isEmpty()) {
            query = q;
            getTuples();
        } else {
            initComponents();
        }
    }

    private void getTuples() {
        // The URL specifying the 675Examples database to which
        // this program connects using JDBC to connect to a
        // Microsoft ODBC database.
        String url = "jdbc:mysql://localhost/student_twong";  //CHANGE THIS!

        String username = "tw1123";
        String password = "1234";

        // Load the driver to allow connection to the database
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    url, username, password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "Failed to load JDBC/MYSQL driver.");
            cnfex.printStackTrace();
            System.exit(1);  // terminate program
        } catch (SQLException sqlex) {
            System.err.println("Unable to connect");
            sqlex.printStackTrace();
        }

        getTable();

        setSize(600, 400);
        show();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Query Manager", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 23))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Query Number:");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jButton1.setText("Enter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Execute");

        jButton3.setFont(new java.awt.Font("Times New Roman", 3, 15)); // NOI18N
        jButton3.setText("Execute");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(551, 551, 551)
                    .addComponent(jButton2))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton1)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(106, 106, 106))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jButton1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        queryNum = Integer.parseInt(jTextField1.getText());
        switch (queryNum) {
            case 1:
                query = q1;
                break;
            case 2:
                query = q2;
                break;
            case 3:
                query = q3;
                break;
            case 4:
                query = q4;
                break;
            case 5:
                query = q5;
                break;
            case 6:
                query = q6;
                break;
            case 7:
                query = q7;
                break;
            case 8:
                query = q8;
                break;
            case 9:
                query = q9;
                break;
            case 10:
                query = q10;
                break;
            default:
                System.err.println("Invalid query number!");
                error = true;
                queryNum = 0;
                break;
        }
        if (error) {
            jTextArea1.setText("Invalid query number! Please try again.");
            error = false;
        } else {
            jTextArea1.setText(query);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        getTuples = true;
        query = jTextArea1.getText();
        dispose();
        final QueryManagerWeb app = new QueryManagerWeb(query);
        app.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        app.shutDown();
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                final QueryManagerWeb app = new QueryManagerWeb("");
                                app.setVisible(true);
                                app.addWindowListener(
                                        new WindowAdapter() {
                                            public void windowClosing(WindowEvent e) {
                                                app.shutDown();
                                                System.exit(0);
                                            }
                                        });
                            }
                        });
                    }
                });
    }//GEN-LAST:event_jButton3ActionPerformed

    private void getTable() {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            //resultSet = statement.executeQuery(query);
            resultSet = statement.executeQuery(query);
            displayResultSet(resultSet);
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

        setTitle("Table from the SQL query");  //CHANGE THIS!!

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
                case Types.DECIMAL:
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
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlex) {
            System.err.println("Unable to disconnect");
            sqlex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QueryManagerWeb.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QueryManagerWeb.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QueryManagerWeb.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QueryManagerWeb.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final QueryManagerWeb app = new QueryManagerWeb("");
                app.setVisible(true);
                app.addWindowListener(
                        new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                app.shutDown();
                                System.exit(0);
                            }
                        });
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
