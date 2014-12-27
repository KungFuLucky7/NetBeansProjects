/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverInterfaces;

import java.rmi.RemoteException;
import java.io.IOException;

/**
 *
 * @author David
 */
public interface Manager extends java.rmi.Remote {

    public void openStore(String storeName) throws Exception, RemoteException;

    public void setupProductCatalog(String productCatalogFile) throws IOException, RemoteException;
    //public void initPost() throws Exception, RemoteException;
    //public Post getPost() throws Exception, RemoteException;

}
