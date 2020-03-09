package ru.job4j.multithreading.pingpong;

import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class RectangleMove.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 22.11.2019
 */
public class RectangleMove implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RectangleMove.class);

    /**
     * A ball.
     */
    private final Rectangle ball;

    /**
     * A frame limit based on X coordinate.
     */
    private final int limitX;

    /**
     * A frame limit based on Y coordinate.
     */
    private final int limitY;

    /**
     * A constructor.
     *
     * @param ball,   a ball.
     * @param limitX, a frame limit, X coordinate.
     * @param limitY, a frame limit, Y coordinate.
     */
    public RectangleMove(Rectangle ball, final int limitX, final int limitY) {
        this.ball = ball;
        this.limitX = limitX;
        this.limitY = limitY;
    }

    @Override
    public void run() {
        while (true) {
            int deltaX = 1;
            int deltaY = 0;
            while (!Thread.currentThread().isInterrupted()) {
                this.ball.setX(this.ball.getX() + deltaX);
                this.ball.setY(this.ball.getY() + deltaY);
                if (this.ball.getX() >= (this.limitX - this.ball.getWidth())) {
                    deltaX *= -1;
                    deltaY += 1;
                } else if (this.ball.getY() >= (this.limitY - this.ball.getHeight())) {
                    deltaX *= -1;
                    deltaY += -1;
                } else if (this.ball.getX() <= 0) {
                    deltaX += 1;
                    deltaY += -1;
                } else if (this.ball.getY() <= 0) {
                    deltaX *= 1;
                    deltaY += 1;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOG.error(e.getMessage(), e);
                }
            }
            break;
        }
    }
}