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
 *
 * @author Terry Wong
 * @author Jung Hwan Kim
 * @author Steven Senatori
 *
 */
public class CashPayment implements IPayment {

    private double amountPaid, amountDue, amountReturned;

    public CashPayment(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     *
     * @return
     */
    @Override
    public String statePayment() {
        String paymentStatement = "";
        try {
            amountReturned = amountPaid - amountDue;
            if (amountReturned < 0) {
                throw new IOException();
            }
            paymentStatement += String.format("Amount Tendered: %.2f\n", amountPaid);
            paymentStatement += String.format("Amount Returned: %.2f\n", amountReturned);
        } catch (IOException e) {
            System.err.println("**** Paid amount is not enough! **** " + e);
        }
        return paymentStatement;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
