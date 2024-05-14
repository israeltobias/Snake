package es.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] row = new int[GAME_UNITS];
    final int[] column = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    Directions direction = Directions.RIGHT;
    Timer timer;
    Random random;
    MyKeyAdapter myKeyAdapter;
    String fontName = "Ink Free";
    String gameOver = "Game Over";
    String score = "Score: ";

    Game(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        myKeyAdapter = new MyKeyAdapter(direction,true);
        this.addKeyListener(myKeyAdapter);
        direction = myKeyAdapter.getDirection();
        startGame();
    }

    private void startGame() {
        newApple();
        myKeyAdapter.setRunning(true);
        timer = new Timer(DELAY, this);
        timer.start();
    }
    private void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }
    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            row[i] = row[i - 1];
            column[i] = column[i - 1];
        }

        switch (direction) {
            case Directions.UP -> column[0] = column[0] - UNIT_SIZE;
            case Directions.DOWN -> column[0] = column[0] + UNIT_SIZE;
            case Directions.LEFT -> row[0] = row[0] - UNIT_SIZE;
            case Directions.RIGHT -> row[0] = row[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((row[0] == appleX) && (column[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((row[0] == row[i]) && (column[0] == column[i])) {
                myKeyAdapter.setRunning(false);
                break;
            }
        }
        if (row[0] < 0 || row[0] >= SCREEN_WIDTH || column[0] < 0 || column[0] >= SCREEN_HEIGHT) {
            myKeyAdapter.setRunning(false);
        }
        if (!myKeyAdapter.isRunning()) {
            timer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (myKeyAdapter.isRunning()) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            g.setColor(Color.yellow);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(row[i], column[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(row[i], column[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font(fontName, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(score + applesEaten, (SCREEN_WIDTH - metrics.stringWidth(score + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font(fontName, Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(gameOver, (SCREEN_WIDTH - metrics1.stringWidth(gameOver)) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font(fontName, Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(score + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth(score + applesEaten)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myKeyAdapter.isRunning()) {
            move();
            checkApple();
            checkCollisions();
            direction = myKeyAdapter.getDirection();
        }
        repaint();
    }
}
