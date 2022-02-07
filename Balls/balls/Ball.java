package balls;

import java.awt.*;

public class Ball {
    private double x;
    private double y;
    private final double speedX;
    private final double speedY;
    private final double score;
    private final Color color;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }

    public Ball(int width, int height, int res) {
        int coef = 35;
        if (res > 20) {
            coef = 25;
        }
        if (res > 35) {
            coef = 20;
        }
        if (res > 50) {
            coef = 15;
        }
        if (res > 75) {
            coef = 10;
        }
        score = (int) (Math.random() * (coef + 2 * res / 5) + 3 * res / 5);
        int pos = (int) (Math.random() * 4);
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        color = new Color(r, g, b);
        switch (pos) {
            case 0:
                x = -score;
                y = Math.random() * height;
                speedX = Math.random() * 9 + 1;
                speedY = Math.random() * 20 - 10;
                break;
            case 1:
                x = Math.random() * width;
                y = -score;
                speedX = Math.random() * 20 - 10;
                speedY = Math.random() * 9 + 1;
                break;
            case 2:
                x = width + score;
                y = Math.random() * height;
                speedX = Math.random() * 9 - 10;
                speedY = Math.random() * 20 - 10;
                break;
            default:
                x = Math.random() * width;
                y = height + score;
                speedX = Math.random() * 20 - 10;
                speedY = Math.random() * 9 - 10;
                break;
        }

    }

    public void move() {
        x += speedX / 3;
        y += speedY / 3;
    }
}
