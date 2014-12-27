package averageseektime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This program calculates the average seek distance and seek time of disk
 * accesses
 *
 * Reference:
 * http://www.java-forums.org/new-java/7995-how-plot-graph-java-given-samples.html
 *
 * @author Terry Wong
 */
public class AverageSeekTime extends JPanel {

    private static final int MAX_QUEUE_SIZE = 20, DISK_ACCESSES = 10000;
    private static int[] dq;
    private static long[] seekDistanceArray, averageSeekDistanceArray;
    private static double[] seekTimeArray, averageSeekTimeArray;

    // The function for getting the seek distance & seek time give a queue size
    private static void Sd(final int queueSize) {

        Random generator = new Random(System.currentTimeMillis());
        for (int i = 0; i < queueSize; i++) {
            dq[i] = generator.nextInt();
        }

        int X = generator.nextInt();
        int index, value, k = 0;
        long seekDistance, tmpSeekDis;
        while (k < DISK_ACCESSES) {
            long startTime = System.nanoTime();
            index = 0;
            value = dq[0];
            seekDistance = Math.abs(X - dq[0]);
            for (int i = 1; i < queueSize; i++) {
                //checking the seekDistance using Math.abs()
                tmpSeekDis = Math.abs(X - dq[i]);
                if (tmpSeekDis <= seekDistance) {
                    seekDistance = tmpSeekDis;
                    index = i;
                    value = dq[i];
                }
            }
            X = value;
            dq[index] = generator.nextInt();
            seekDistanceArray[k] = seekDistance;
            seekTimeArray[k] = System.nanoTime() - startTime;
            //System.out.println("Seek distance: " + seekDistanceArray[k]);
            //System.out.println("Seek time: " + seekTimeArray[k]);
            k++;
        }
    }
    public static long[] seekDistanceData = new long[20];
    public static int[] seekTimeData = new int[20];
    final int PAD = 20;

    // Function for plotting the graph for average seek time
    // and average seek distance
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "SEEK TIME |G, SEEK DISTANCE |B";
        float sy = PAD + ((h - 2 * PAD) - s.length() * sh) / 2 + lm.getAscent();
        for (int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float) font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw) / 2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "QUEUE SIZE";
        sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
        float sw = (float) font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw) / 2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double) (w - 2 * PAD) / (seekTimeData.length - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.green.darker());
        for (int i = 0; i < seekTimeData.length - 1; i++) {
            double x1 = PAD + i * xInc;
            double y1 = h - PAD - scale * seekTimeData[i];
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * seekTimeData[i + 1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for (int i = 0; i < seekTimeData.length; i++) {
            double x = PAD + i * xInc;
            double y = h - PAD - scale * seekTimeData[i];
            g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
        }

        scale = (double) (h - 2 * PAD) / getMaxDistance();
        g2.setPaint(Color.blue.darker());
        for (int i = 0; i < seekDistanceData.length - 1; i++) {
            double x1 = PAD + i * xInc;
            double y1 = h - PAD - scale * seekDistanceData[i];
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * seekDistanceData[i + 1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for (int i = 0; i < seekDistanceData.length; i++) {
            double x = PAD + i * xInc;
            double y = h - PAD - scale * seekDistanceData[i];
            g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
        }
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < seekTimeData.length; i++) {
            if (seekTimeData[i] > max) {
                max = seekTimeData[i];
            }
        }
        return max;
    }

    private long getMaxDistance() {
        long max = -Integer.MAX_VALUE;
        for (int i = 0; i < seekDistanceData.length; i++) {
            if (seekDistanceData[i] > max) {
                max = seekDistanceData[i];
            }
        }
        return max;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int QUEUE_SIZE = 1;
        long seekDistanceSum = 0, averageSeekDistance;
        double seekTimeSum = 0, averageSeekTime;

        averageSeekDistanceArray = new long[MAX_QUEUE_SIZE];
        averageSeekTimeArray = new double[MAX_QUEUE_SIZE];
        while (QUEUE_SIZE <= MAX_QUEUE_SIZE) {
            dq = new int[QUEUE_SIZE];
            seekDistanceArray = new long[DISK_ACCESSES];
            seekTimeArray = new double[DISK_ACCESSES];

            Sd(QUEUE_SIZE);

            for (int i = 0; i < DISK_ACCESSES; i++) {
                seekDistanceSum += seekDistanceArray[i];
                seekTimeSum += seekTimeArray[i];
            }
            averageSeekDistance = seekDistanceSum / DISK_ACCESSES;
            averageSeekTime = seekTimeSum / DISK_ACCESSES;
            averageSeekDistanceArray[QUEUE_SIZE - 1] = averageSeekDistance;
            averageSeekTimeArray[QUEUE_SIZE - 1] = averageSeekTime;
            System.out.println("Queue size: " + QUEUE_SIZE);
            System.out.println("Average Seek Distance: " + averageSeekDistanceArray[QUEUE_SIZE - 1]);
            System.out.println("Average Seek Time: " + averageSeekTimeArray[QUEUE_SIZE - 1] + " nanoseconds");
            QUEUE_SIZE++;
        }

        //System.out.println(data.length + " " + averageSeekTimeArray.length);
        for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
            seekDistanceData[i] = averageSeekDistanceArray[i];
            seekTimeData[i] = (int) averageSeekTimeArray[i];
        }
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new AverageSeekTime());
        f.setSize(600, 600);
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}
