/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.menu;



import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import javafx.stage.Stage;

/**
 *
 * @author OlafPC
 */
public class MainMenu extends Application{

    private MenuButton newGameButton;
    
    public MainMenu(){
        
        newGameButton = new MenuButton("New Game");
        
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Pane root = new Pane();
        root.setPrefSize(1280, 768);
        
        InputStream is = Files.newInputStream(Paths.get("src/res/images/desertArena.jpg"));
        Image img = new Image(is);
        is.close();
        
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(1280);
        imgView.setFitHeight(768);
        
        root.getChildren().addAll(imgView, newGameButton);
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Menu started");
        
    }
    
    private static class MenuButton extends StackPane{
        
        private Text text;
        private Rectangle bg;
        
        public MenuButton(String text){
            
            this.text = new Text(text);
            this.text.setFont(this.text.getFont().font(20));
            this.text.setFill(Color.WHITE);
            
            bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));
            
            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, this.text);
            
            this.setOnMouseEntered(event -> {
                
                //bg.setTranslateX(10);
                //this.text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                this.text.setFill(Color.BLACK);
                
                
            });
            
            this.setOnMouseExited(event -> {
                
                //bg.setTranslateX(10);
                //this.text.setTranslateX(10);
                bg.setFill(Color.BLACK);
                this.text.setFill(Color.WHITE);
    
            });
            
            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());
            
            setOnMousePressed(event -> setEffect(drop));
            setOnMousePressed(event -> setEffect(null));
        }
    }
    
    public void run(String[] args){
        launch(args);
    }
    
}
