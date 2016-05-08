import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Salata on 2016-05-07.
 */
public class Tetris extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        //border creates the chessboard
        border(root);
        game(root);
        stage.setMaxHeight(680);
        stage.setMaxWidth(355);
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
        
        //      stage.setOnCloseRequest(closeEvent -> {});
    }

    public BorderPane border(BorderPane root) {
        Rectangle border = new Rectangle(20, 20, 300, 600);
        border.setStroke(Color.BLACK);
        border.setFill(null);
        for (int i = 0; i < 20; i++) {
            Line line = new Line(20, (20 + 30 * i), 320, (20 + 30 * i));
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);
        }
        for (int i = 0; i < 10; i++) {
            Line line = new Line((20 + 30 * i), 20, (20 + 30 * i), 620);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);
        }
        root.getChildren().add(border);
        return root;
    }

    public BorderPane game(BorderPane root) {

        List<Rectangle> squares = new ArrayList<>();
        tetrimino(squares, root);
        time(squares, root);
        return root;
    }

    public void tetrimino(List<Rectangle> squares, BorderPane root) {
        //tetrimino I
        for (int i = 0; i < 4; i++) {
            squares.add(new Rectangle(110 + i * 30, 20, 30, 30));
            root.getChildren().add(squares.get(squares.size() - 1));
        }
    }

    public void time(List<Rectangle> squares, BorderPane root) {
        int last = squares.size()-1;
        Runnable gravity = () -> {
//            for (int i = 0; i < 4; i++) {
//                System.out.println("kk " + i);
//                squares.get(last - i).setY(squares.get(last - i).getY() - 30);
//                Platform.runLater(() -> {
//                    root.getChildren().add(squares.get(last - i));
//                });
            squares.get(last).setY(squares.get(last).getY() + 30);
            Platform.runLater(() ->
                root.getChildren().add(squares.get(last)));

            squares.get(last - 1).setY(squares.get(last - 1).getY() + 30);
            Platform.runLater(() ->
                    root.getChildren().add(squares.get(last - 1)));

            squares.get(last - 2).setY(squares.get(last - 2).getY() + 30);
            Platform.runLater(() ->
                    root.getChildren().add(squares.get(last - 2)));

            squares.get(last - 3).setY(squares.get(last - 3).getY() + 30);
            Platform.runLater(() ->
                    root.getChildren().add(squares.get(last - 3)));

        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(gravity, 0, 150, TimeUnit.MILLISECONDS);
        //executor.shutdown();
    }
}
