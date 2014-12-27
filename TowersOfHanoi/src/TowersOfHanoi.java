
/**
 *
 * @author Terry Wong
 */
import java.util.*;

public class TowersOfHanoi {

    static class Disk {

        private String diskName;
        private int diskWeight;

        public Disk(String Name) {
            diskName = Name;
            diskWeight = Integer.parseInt(Name);
        }

        public String getDiskName() {
            return diskName;
        }

        public void setDiskName(String Name) {
            diskName = Name;
        }

        public int getDiskWeight() {
            return diskWeight;
        }

        public void setDiskWeight(int Weight) {
            diskWeight = Weight;
        }

        public String toString() {
            return diskName;
        }
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Error: a single integer argument is required!");
            System.exit(1);
        } else if (Integer.parseInt(args[0]) >= 10) {
            System.err.println("Error: the number of disks should be < 10");
            System.exit(1);
        }
        Integer numDisk = new Integer(args[0]);
        char source = 'A', dest = 'C', utility = 'B';
        ArrayList<Disk> PegA = new ArrayList<Disk>();
        for (int i = numDisk.intValue(); i > 0; i--) {
            Disk tmp = new Disk(Integer.toString(i));
            PegA.add(tmp);
        }
        ArrayList<Disk> PegB = new ArrayList<Disk>();
        ArrayList<Disk> PegC = new ArrayList<Disk>();
        System.out.println("Move\t\t\tPeg Configuration");
        System.out.println("    \t\t\tA\t\tB\t\tC\t\t");
        System.out.println("init\t\t\t" + PegA.toString());
        moveDisk(numDisk.intValue(), source, dest, utility, PegA, PegB, PegC);
        System.exit(0);
    }

    static void moveDisk(int disk, char source, char dest, char utility, ArrayList<Disk> PegA, ArrayList<Disk> PegB, ArrayList<Disk> PegC) {
        if (disk > 0) {
            moveDisk(disk - 1, source, utility, dest, PegA, PegB, PegC);
            Display(source, dest, PegA, PegB, PegC);
            moveDisk(disk - 1, utility, dest, source, PegA, PegB, PegC);
        }
    }

    static void Display(char source, char dest, ArrayList<Disk> PegA, ArrayList<Disk> PegB, ArrayList<Disk> PegC) {
        //1 from A to C
        if (source == 'A' && dest == 'B') {
            PegB.add(PegA.remove(PegA.size() - 1));
            System.out.println(PegB.get(PegB.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        } else if (source == 'A' && dest == 'C') {
            PegC.add(PegA.remove(PegA.size() - 1));
            System.out.println(PegC.get(PegC.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        } else if (source == 'B' && dest == 'A') {
            PegA.add(PegB.remove(PegB.size() - 1));
            System.out.println(PegA.get(PegA.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        } else if (source == 'B' && dest == 'C') {
            PegC.add(PegB.remove(PegB.size() - 1));
            System.out.println(PegC.get(PegC.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        } else if (source == 'C' && dest == 'A') {
            PegA.add(PegC.remove(PegC.size() - 1));
            System.out.println(PegA.get(PegA.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        } else if (source == 'C' && dest == 'B') {
            PegB.add(PegC.remove(PegC.size() - 1));
            System.out.println(PegB.get(PegB.size() - 1).getDiskName() + " from " + source + " to " + dest + "\t\t" + PegA.toString() + "\t\t" + PegB.toString() + "\t\t" + PegC.toString());
        }
    }
}
