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
 * Main class for POSTs.
 *
 * @author Terry Wong
 */
public class POST {

    private Manager manager;

    public POST(String productFile, String transactionFile) {
        manager = new Manager(productFile, transactionFile);
    }

    void run() {
        manager.manage();
    }

    /**
     * Take -p <product file> -t <transaction file> as input arguments
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0 || args.length != 4) {
            System.err.println("**** Incorrect usage, try: java post.POST -p <product file> -t <transaction file>");
            System.exit(1);
        }
        if (args[0].equals("-p") && args[2].equals("-t")) {
            (new POST(args[1], args[3])).run();
        } else if (args[0].equals("-t") && args[2].equals("-p")) {
            (new POST(args[3], args[1])).run();
        }
    }
}
