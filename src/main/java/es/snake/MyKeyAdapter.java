package es.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.io.Serializable;

public class MyKeyAdapter extends KeyAdapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    Directions direction;
    boolean running;

    public MyKeyAdapter(Directions direction, boolean running) {
        this.direction = direction;
        this.running = running;
    }

    public Directions getDirection() {
        return direction;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (direction != Directions.RIGHT) {
                    direction = Directions.LEFT;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != Directions.LEFT) {
                    direction = Directions.RIGHT;
                }
            }
            case KeyEvent.VK_UP -> {
                if (direction != Directions.DOWN) {
                    direction = Directions.UP;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != Directions.UP) {
                    direction = Directions.DOWN;
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                running = false;
            }
            default -> {
                // Do nothing for unmatched characters
            }
        }
    }
}
