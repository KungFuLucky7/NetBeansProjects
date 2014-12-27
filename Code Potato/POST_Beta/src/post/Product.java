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
 * This class describes a product.
 *
 * @author Terry Wong
 */
public class Product {

    private String description, UPC;
    private double price;
    private static final int UPC_LENGTH = 4;

    /**
     * Construct a new product. Check for valid UPC entry.
     *
     * @param desc
     * @param pc
     * @param upc
     */
    public Product(String desc, double pc, String upc) {
        description = desc;
        price = pc;
        try {
            if (String.valueOf(upc).length() != UPC_LENGTH) {
                throw new IOException();
            }
            UPC = upc;
        } catch (IOException e) {
            System.err.println("**** Invalid UPC Entry **** " + e);
        }
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(double p) {
        price = p;
    }

    public double getPrice() {
        return price;
    }

    public void setUPC(String upc) {
        try {
            if (upc.length() != UPC_LENGTH) {
                throw new IOException();
            }
            UPC = upc;
        } catch (IOException e) {
            System.err.println("**** Invalid UPC Entry **** " + e);
        }
    }

    public String getUPC() {
        return UPC;
    }
}
