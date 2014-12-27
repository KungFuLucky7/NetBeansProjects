/* 
 * Copyright (C) 2014 Terry Wong, Steven Senatori, Jung Hwan Kim
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package post;

import java.io.IOException;

/**
 * This class implements a store and its operations.
 *
 * @author Terry Wong
 */
public class Store {

    private String name, address;
    private static final String DEFAULT_NAME = "SFSU Merchandise Store";
    private static final String DEFAULT_ADDRESS = "1600 Holloway Avenue . San Francisco . CA 94132";
    private ProductCatalog productCatalog;

    public Store() {
        name = DEFAULT_NAME;
        address = DEFAULT_ADDRESS;
    }

    public Store(String nm, String addr) {
        if (nm == null) {
            name = DEFAULT_NAME;
        } else {
            name = nm;
        }
        if (addr == null) {
            address = DEFAULT_ADDRESS;
        } else {
            address = addr;
        }
    }

    /**
     * Puts up a product catalog.
     *
     * @param productFile
     */
    public void init(String productFile) {
        try {
            productCatalog = new ProductCatalog(productFile);
            productCatalog.loadProducts();
            productCatalog.close();
        } catch (IOException e) {
            System.err.println("**** " + e);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String nm) {
        name = nm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addr) {
        address = addr;
    }

    public ProductCatalog getProducts() {
        return productCatalog;
    }
}
