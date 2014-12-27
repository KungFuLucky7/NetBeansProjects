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

/**
 *
 * @author Steven Senatori
 */
public class CheckPayment implements IPayment {

    private double amountPaid;

    /**
     *
     * @param amountPaid
     */
    public CheckPayment(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     *
     * @return
     */
    @Override
    public String statePayment() {
        String paymentStatement = "Paid by check\n";

        return paymentStatement;
    }

    /**
     * The spec says it should do an authorization check on cards and checks.
     * What that entails, who knows
     *
     * @return nothing. it's unimplemented as of now
     */
    boolean checkAuthorization() {
        throw new UnsupportedOperationException("authorizationCheck method is currently not supported.");
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
