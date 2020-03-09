package ru.job4j.multithreading.pingpong;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Class PingPong.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 22.11.2019
 */
public class PingPong extends Application {

    /**
     * A window title.
     */
    private static final String JOB4J = "Пинг-понг www.job4j.ru";

    @Override
    public void start(Stage stage) {
        int limitX = 300;
        int limitY = 300;
        Group group = new Group();
        Rectangle ball = new Rectangle(50, 100, 20, 20);
        group.getChildren().add(ball);
        Thread game = new Thread(new RectangleMove(ball, limitX, limitY));
        game.start();
        stage.setScene(new Scene(group, limitX, limitY));
        stage.setTitle(JOB4J);
        stage.setOnCloseRequest(event -> game.interrupt());
        stage.setResizable(false);
        stage.show();
    }
}
