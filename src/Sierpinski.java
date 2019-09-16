import java.awt.*;
import javax.swing.*;

public class Sierpinski extends JPanel {
    private static final int TOTAL_DOTS = 10000;
    private static final int WINDOW = 1000;

    private static final int BASE_LENGTH = 800;
    private static final int TRIANGLE_HEIGHT = (int) Math.sqrt((BASE_LENGTH * BASE_LENGTH) - (BASE_LENGTH >> 1) * (BASE_LENGTH >> 2));
    private static final int ANGLE = 60;

    private static final Point POINT_1;
    private static final Point POINT_2;
    private static final Point POINT_3;

    static {
        POINT_1 = new Point((WINDOW - BASE_LENGTH) / 2, (WINDOW - (WINDOW - BASE_LENGTH) / 2));
        POINT_2 = new Point((WINDOW - (WINDOW - BASE_LENGTH) / 2), (WINDOW - (WINDOW - BASE_LENGTH) / 2));
        POINT_3 = new Point((WINDOW / 2), (WINDOW - (WINDOW - BASE_LENGTH) / 2) - TRIANGLE_HEIGHT);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("sierpinski");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Sierpinski());
        f.setSize(WINDOW, WINDOW);
        f.setLocation(550, 25);
        f.setVisible(true);
    }

    public void paint(Graphics g) {
        //draw triangle
        g.drawLine(POINT_1.getX(), POINT_1.getY(), POINT_2.getX(), POINT_2.getY());
        g.drawLine(POINT_1.getX(), POINT_1.getY(), POINT_3.getX(), POINT_3.getY());
        g.drawLine(POINT_2.getX(), POINT_2.getY(), POINT_3.getX(), POINT_3.getY());

        //draw random point inside the triangle
        final Point randomPoint = getRandomPoint();
        Point nextPoint = getNextPoint(randomPoint);
        for (int x = 0; x < TOTAL_DOTS; x++) {
            g.drawLine(nextPoint.getX(), nextPoint.getY(), nextPoint.getX(), nextPoint.getY());
            nextPoint = getNextPoint(nextPoint);
        }
    }
    
    private Point getRandomPoint() {
        // randomX
        int maxX = POINT_2.getX();
        int minX = POINT_1.getX();
        int randomX = getRandomNumberBetween(minX, maxX);

        //randomY
        int maxY = POINT_1.getY();
        int minY;

        if (randomX < (WINDOW / 2)){
            minY = maxY - (int) Math.tan(Math.toRadians(ANGLE)) * (randomX - POINT_1.getX());
        } else {
            minY = maxY - (int) Math.tan(Math.toRadians(ANGLE)) * (POINT_2.getX() - randomX);
        }

        int randomY = getRandomNumberBetween(minY, maxY);

        return new Point(randomX, randomY);
    }

    private Point getNextPoint(Point prevPoint) {
        //choose a random corner
        int chooseCorner = getRandomNumberBetween(1, 3);
        Point midPoint = new Point(0, 0);

        //draw a dot in between the dot and the chosen corner
        switch (chooseCorner) {
            case 1:
                midPoint.setX(((prevPoint.getX() - POINT_1.getX()) / 2) + POINT_1.getX());
                midPoint.setY(((prevPoint.getY() - POINT_1.getY()) / 2) + POINT_1.getY());
                break;
            case 2:
                midPoint.setX(((prevPoint.getX() - POINT_2.getX()) / 2) + POINT_2.getX());
                midPoint.setY(((prevPoint.getY() - POINT_2.getY()) / 2) + POINT_2.getY());
                break;
            case 3:
                midPoint.setX(((prevPoint.getX() - POINT_3.getX()) / 2) + POINT_3.getX());
                midPoint.setY(((prevPoint.getY() - POINT_3.getY()) / 2) + POINT_3.getY());
                break;
            default:
                throw new IndexOutOfBoundsException();
        }

        return midPoint;
    }

    private int getRandomNumberBetween(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

}
