/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import post.ClientManagerImpl;
import post.Post;
import serverInterfaces.Manager;
import serverInterfaces.Store;
import serverSharedClasses.TransactionRecord;

/**
 *
 * @author senatori
 */
public class POSTUIFrame2 extends javax.swing.JFrame
{

    //These Objects are java swing library jPanel objects
    public CustNamePanel customerNamePanel;
    public ProductPanel productPanel;
    public InvoicePanel invoicePanel;
    public PaymentPanel paymentPanel;

    //Post Server & Client objects
    public Manager manager;
    public Post post;
    public Store store;
    public TransactionRecord transaction;
    String output = "";

    /**
     * Creates new form POSTUIFrame2
     */
    public POSTUIFrame2(Manager manager, Store store)
    {

        this.store = store;
        this.manager = manager;
        this.post = new Post(store);

        initComponents();

        //adding the respective panels to the jFrame here.
        this.customerNamePanel = new CustNamePanel();
        this.customerNamePanel.setBounds(0, 25, 300, 75);
        this.add(customerNamePanel);

        //passing in the current object(this) because previously we had easy access to all of the POST and swing objects
        //and I have little or no desire to create getters and setters for everything. 
        this.productPanel = new ProductPanel(this);
        this.productPanel.setBounds(440, 0, 349, 138);
        this.add(productPanel);

        this.invoicePanel = new InvoicePanel(this);
        this.invoicePanel.setBounds(15, 140, 775, 374);
        this.add(invoicePanel);

        this.paymentPanel = new PaymentPanel(this);
        this.paymentPanel.setBounds(259, 515, 531, 124);
        this.add(paymentPanel);

        pack();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel13.setText("jLabel13");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addContainerGap(719, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(605, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(POSTUIFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(POSTUIFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(POSTUIFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(POSTUIFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try
        {
            //get manager object through RMI connection
            Registry rmtReg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            final Manager manager = (Manager) rmtReg.lookup("manager");
            final Store store = (Store) rmtReg.lookup("store");

            System.out.println("Welcome to the point of sale terminal (POST)!");
            // Give store a default name
            store.setName("SFSU Bookstore");
            // Set up the product catalog
            manager.setupProductCatalog("productFile");

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    new POSTUIFrame2(manager, store).setVisible(true);
                }
            });
        } catch (RemoteException | NotBoundException ex)
        {
            Logger.getLogger(PostUI.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                //Since RMI connection failed, here we create local instances of the POST objects
                final Manager localManager = new ClientManagerImpl();
                localManager.openStore("Default Name");
                final Store localStore = ((ClientManagerImpl) localManager).getStore();

                System.out.println("Welcome to the local point of sale terminal (POST)!");
                // Give store a default name
                localStore.setName("SFSU Bookstore");
                // Set up the product catalog
                localManager.setupProductCatalog("productFile");

                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try
                {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
                    {
                        if ("Nimbus".equals(info.getName()))
                        {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException e)
                {
                    java.util.logging.Logger.getLogger(POSTUIFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        new POSTUIFrame2(localManager, localStore).setVisible(true);
                    }
                });
            } catch (Exception e)
            {
                System.err.println(e);
            }
        } catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel13;
    // End of variables declaration//GEN-END:variables

}