package lk.ijse.dep10.media_player;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

public class AppInitializer extends Application {

    private MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage(primaryStage);
    }

    public void mainStage(Stage stage) {

        ImageView btnOpen = new ImageView();
        Label lblFile = new Label("Select a file to play");
        ImageView imgPlay = new ImageView();
        ImageView imgPause = new ImageView();
        ImageView imgStop = new ImageView();
        ImageView imgLoop = new ImageView();
        ImageView imgVolume = new ImageView();
        Slider volume = new Slider(0,1,0.7);
        Label lbTime = new Label("Duration: ");


        btnOpen.setCursor(Cursor.HAND);

        lblFile.setFont(new Font(20));
        lblFile.setTextFill(Color.WHITE);
        lbTime.setFont(new Font(13));
        lbTime.setTextFill(Color.WHITE);

        Image iconPlay = new Image(this.getClass().getResource("/data/play.png").toString());
        Image iconPause = new Image(this.getClass().getResource("/data/pause3.png").toString());
        Image iconStop = new Image(this.getClass().getResource("/data/stop.png").toString());
        Image iconLoop = new Image(this.getClass().getResource("/data/loop.png").toString());
        Image iconVolume = new Image(this.getClass().getResource("/data/volume2.png").toString());
        Image iconMute = new Image(this.getClass().getResource("/data/mute2.png").toString());
        Image open = new Image(this.getClass().getResource("/data/menu.png").toString());

        imgPlay.setCursor(Cursor.HAND);
        imgPause.setCursor(Cursor.HAND);
        imgStop.setCursor(Cursor.HAND);
        imgLoop.setCursor(Cursor.HAND);

        imgPlay.setFitWidth(70);
        imgPlay.setFitHeight(70);
        imgPause.setFitWidth(70);
        imgPause.setFitHeight(70);
        imgStop.setFitWidth(70);
        imgStop.setFitHeight(70);
        imgLoop.setFitWidth(70);
        imgLoop.setFitHeight(70);
        imgVolume.setFitWidth(40);
        imgVolume.setFitHeight(40);
        btnOpen.setFitWidth(70);
        btnOpen.setFitHeight(70);

        imgPlay.setImage(iconPlay);
        imgPause.setImage(iconPause);
        imgStop.setImage(iconStop);
        imgLoop.setImage(iconLoop);
        imgVolume.setImage(iconVolume);
        btnOpen.setImage(open);

        HBox hbox = new HBox(imgVolume,volume);
        hbox.setSpacing(10);
        volume.setPadding(new Insets(5,5,5,0));

        HBox pane1 = new HBox(imgPlay,imgPause,imgStop,imgLoop);
        pane1.setPadding(new Insets(20));
        pane1.setSpacing(25);

        //
        ImageView bgImg = new ImageView();
        Image background = new Image(this.getClass().getResource("/data/bg7.jpeg").toString());
        bgImg.setImage(background);
//        bgImg.setOpacity(0.6);
        bgImg.setScaleX(2.9);
        bgImg.setScaleY(2.7);
//        bgImg.setFitHeight(300);
//        bgImg.setFitWidth(900);


        AnchorPane pane2 = new AnchorPane(btnOpen,lblFile,pane1,hbox,lbTime);
//        pane2.setPrefSize(300,300);
        pane2.setPadding(new Insets(20));
        btnOpen.setLayoutX(40);
        btnOpen.setLayoutY(50);
        lblFile.setLayoutX(40);
        lblFile.setLayoutY(150);
        pane1.setLayoutX(20);
        pane1.setLayoutY(280);
        hbox.setLayoutX(350);
        hbox.setLayoutY(320);
        lbTime.setLayoutX(40);
        lbTime.setLayoutY(250);
        AnchorPane.setLeftAnchor(lblFile,20.0);
        AnchorPane.setRightAnchor(lblFile,20.0);
        AnchorPane.setLeftAnchor(pane1,20.0);
        AnchorPane.setRightAnchor(hbox,15.0);

        VBox pane3 = new VBox(pane2);
        pane3.setAlignment(Pos.CENTER);
//        pane3.setPrefSize(700,300);
//        pane3.setBackground(Background.fill());

        pane1.setAlignment(Pos.CENTER);
        lblFile.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgImg,pane3);
        root.setPrefSize(700,300);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("Audio Player");
        stage.show();
        stage.centerOnScreen();

        btnOpen.setOnMouseEntered(event -> btnOpen.setOpacity(0.8));
        btnOpen.setOnMouseExited(event -> btnOpen.setOpacity(1));
        btnOpen.setOnMousePressed(event -> btnOpen.setEffect(new InnerShadow(10,Color.BLACK)));
        btnOpen.setOnMouseReleased(event ->{
            btnOpen.setEffect(null);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select an audio to play");
            File audioFile = fileChooser.showOpenDialog(null);

            if (audioFile!=null){
                Media media = new Media(audioFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                String[] song = audioFile.toString().split("/");
                lblFile.setText(song[song.length-1]);
            }else mediaPlayer = null;
        });

        imgPlay.setOnMouseEntered(event -> imgPlay.setOpacity(0.8));
        imgPlay.setOnMouseExited(event -> imgPlay.setOpacity(1));
        imgPlay.setOnMousePressed(event -> imgPlay.setEffect(new InnerShadow(10,Color.BLACK)));
        imgPlay.setOnMouseReleased(event ->{
            imgPlay.setEffect(null);
            mediaPlayer.play();
            mediaPlayer.setVolume(volume.getValue());
//            lbTime.setText(mediaPlayer.getTotalDuration().toString());
            lbTime.setText(mediaPlayer.getCurrentTime().toString());
        });

        imgPause.setOnMouseEntered(event -> imgPause.setOpacity(0.8));
        imgPause.setOnMouseExited(event -> imgPause.setOpacity(1));
        imgPause.setOnMousePressed(evnent -> imgPause.setEffect(new InnerShadow(10,Color.BLACK)));
        imgPause.setOnMouseReleased(event ->{
            imgPause.setEffect(null);
            mediaPlayer.pause();
        });
        imgStop.setOnMouseEntered(event -> imgStop.setOpacity(0.8));
        imgStop.setOnMouseExited(event -> imgStop.setOpacity(1));
        imgStop.setOnMousePressed(evnent -> imgStop.setEffect(new InnerShadow(10,Color.BLACK)));
        imgStop.setOnMouseReleased(event ->{
            imgStop.setEffect(null);
            mediaPlayer.stop();
        });
        imgLoop.setOnMouseEntered(event -> imgLoop.setOpacity(0.8));
        imgLoop.setOnMouseExited(event -> imgLoop.setOpacity(1));
        imgLoop.setOnMousePressed(evnent -> imgLoop.setEffect(new InnerShadow(10,Color.BLACK)));
        imgLoop.setOnMouseReleased(event ->{
            imgLoop.setEffect(null);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        });

        imgVolume.setOnMouseEntered(event -> imgVolume.setOpacity(0.6));
        imgVolume.setOnMouseExited(event -> imgVolume.setOpacity(1));
        imgVolume.setOnMousePressed(event -> imgVolume.setEffect(new InnerShadow(10,Color.BLACK)));
        imgVolume.setOnMouseReleased(event ->{
            imgVolume.setEffect(null);
            if (!mediaPlayer.isMute()) {
                mediaPlayer.setMute(true);
                imgVolume.setImage(iconMute);
            }
            else {
                mediaPlayer.setMute(false);
                imgVolume.setImage(iconVolume);
            }
        });

//        volume.setOnMouseClicked(event ->{
//            mediaPlayer.setVolume(volume.getValue());
//        });

        volume.valueProperty().addListener(((observableValue, number, t1) -> {
            if (volume.isValueChanging()) mediaPlayer.setVolume(volume.getValue());
            if (volume.getValue()==0) imgVolume.setImage(iconMute);
            else imgVolume.setImage(iconVolume);
        }));

//        KeyFrame key1 = new KeyFrame(Duration.millis(0),event ->{
//            TranslateTransition translateX = new TranslateTransition(Duration.millis(5000),lblFile);
//            translateX.setFromX(-400);
//            translateX.setToX(500);
//            translateX.play();
//        });
//        KeyFrame key2 = new KeyFrame(Duration.millis(5000),event ->{});

        KeyFrame key1 = new KeyFrame(Duration.millis(0),new KeyValue(lblFile.translateXProperty(),-400));
        KeyFrame key2 = new KeyFrame(Duration.millis(2000),new KeyValue(lblFile.translateXProperty(),400));

        Timeline timeline = new Timeline(key1,key2);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }
}
