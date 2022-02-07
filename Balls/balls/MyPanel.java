package balls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    private final Timer spawnTimer;
    private final Timer timer;
    private Point cursor;
    private int countOfBalls = 0;
    private int result = 10;
    private boolean win = false;
    private Ball[] enemies = new Ball[100];
    private Stage curStage = Stage.GAME;
    private final int width;
    private final int height;

    public MyPanel(int width, int height) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        cursor = new Point((int) b.getX(), (int) b.getY());
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);
        setBackground(Color.BLACK);

        spawnTimer = new Timer(100, null);
        spawnTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curStage != Stage.PAUSE) {
                    if (countOfBalls < 100) {
                        enemies[countOfBalls] = new Ball(width, height, result);
                        countOfBalls++;
                    }
                }
            }
        });
        spawnTimer.start();

        timer = new Timer(1, null);
        timer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (curStage == Stage.GAME) {
                    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
                    setCursor(blankCursor);
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                if (curStage != Stage.PAUSE) {
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    cursor = new Point((int) b.getX(), (int) b.getY());
                    for (int i = 0; i < countOfBalls; i++) {
                        enemies[i].move();
                        if (curStage == Stage.GAME) {
                            double dif = Math.sqrt(Math.pow((cursor.getX() - enemies[i].getX()), 2) + Math.pow((cursor.getY() - enemies[i].getY()), 2));
                            if (dif < result + enemies[i].getScore()) {
                                if (result >= enemies[i].getScore()) {
                                    result++;
                                    enemies[i] = new Ball(width, height, result);
                                } else {
                                    curStage = Stage.END;
                                }
                            }
                        }
                        if (enemies[i].getX() < -enemies[i].getScore() || enemies[i].getX() > width + enemies[i].getScore() ||
                                enemies[i].getY() < -enemies[i].getScore() || enemies[i].getY() > height + enemies[i].getScore()
                        ) {
                            enemies[i] = new Ball(width, height, result);
                        }
                    }
                }
                if (result > Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2)) && curStage == Stage.GAME) {
                    curStage = Stage.END;
                    win = true;
                    result = 10;
                    countOfBalls = 0;
                }
                repaint();
            }
        });
        timer.start();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && curStage == Stage.END) {
                    curStage = Stage.GAME;
                    countOfBalls = 0;
                    win = false;
                    result = 10;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void pause() {
        if (curStage == Stage.PAUSE) {
            curStage = Stage.GAME;
        } else if (curStage == Stage.GAME) {
            curStage = Stage.PAUSE;
        }
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x - r;
        y = y - r;
        g.fillOval(x, y, 2 * r, 2 * r);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < countOfBalls; i++) {
            g2d.setColor(enemies[i].getColor());
            drawCenteredCircle(g2d, (int) enemies[i].getX(), (int) enemies[i].getY(), (int) enemies[i].getScore());
        }
        g2d.setColor(new Color(0xffffff));
        if (curStage == Stage.GAME) {
            drawCenteredCircle(g2d, (int) cursor.getX(), (int) cursor.getY(), result);
        } else if (curStage == Stage.PAUSE) {
            drawCenteredCircle(g2d, (int) cursor.getX(), (int) cursor.getY(), result);
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, width / 33));
            g2d.drawString("Paused", 15 * width / 32, 2 * height / 5);
            g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, width / 133));
            g2d.drawString("(Press space to unpause)", 15 * width / 32, 4 * height / 9);
        } else {
            if (win == false) {
                g2d.setFont(new Font("Comic Sans MS", Font.BOLD, width / 33));
                g2d.drawString("You dead", 27 * width / 64, 2 * height / 5);
                g2d.drawString((result - 10) + " pts", 27 * width / 64, height / 2);
                g2d.setFont(new Font("Comic Sans MS", Font.BOLD, width / 133));
                g2d.drawString("(Click to restart)", 27 * width / 64, 10 * height / 18);
            }
            if (win == true) {
                g2d.setFont(new Font("Comic Sans MS", Font.BOLD, width / 33));
                g2d.drawString("You win", 28 * width / 64, 2 * height / 5);
                g2d.setFont(new Font("Comic Sans MS", Font.BOLD, width / 133));
                g2d.drawString("(Click to restart)", 28 * width / 64, 4 * height / 9);
            }
        }
    }
}
