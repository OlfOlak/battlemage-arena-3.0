/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.menu;



//import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import javafx.stage.Stage;

/**
 *
 * @author OlafPC
 */
public class MainMenu extends Application{

    private MenuButton newGameButton;
    private MenuButton loadGameButton;
    private MenuButton creditsButton;
    private MenuButton exitButton;
    
    private Text titleLabel;
    private Text titleLabel2;
    private Text titleLabel3;
    
    private VBox buttons;
    private StackPane menu;
    
    public MainMenu(){
           
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //for(String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
        //    System.out.println(s);
        
        buttons = new VBox(15);
        menu = new StackPane();
        
        titleLabel = new Text("BATTLEMAGE ARENA\n             3.0");
        titleLabel.setFont(new Font("Viking-Normal", 40));
        titleLabel.setFill(Color.BLACK);
        //titleLabel.set
        //titleLabel.setTranslateX(640);
        titleLabel.setTranslateY(-250);

        newGameButton = new MenuButton("New Game");
        loadGameButton = new MenuButton("Load Game");
        creditsButton = new MenuButton("Credits");
        exitButton = new MenuButton("Exit");
        
        buttons.getChildren().addAll(newGameButton, loadGameButton, creditsButton, exitButton);
        
        //buttons.setTranslateX(640 - (newGameButton.getWidth() / 2));
        //buttons.setTranslateY(200);
        
        menu.getChildren().addAll(titleLabel, buttons);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(700, 300);
        menu.setTranslateX(640 - 350);
        menu.setTranslateY(300);
        
        newGameButton.button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("New game clicked!");
            }
        });
        
        loadGameButton.button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("Load game button clicked!");
            }
        });
        
        creditsButton.button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("Credits button clicked!");
            }
        });
        
        exitButton.button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });
        
        Pane root = new Pane();
        root.setPrefSize(1280, 768);
        
        InputStream is = Files.newInputStream(Paths.get("src/res/images/desertArena.jpg"));
        Image img = new Image(is);
        is.close();
        
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(1280);
        imgView.setFitHeight(768);
        
        root.getChildren().addAll(imgView, menu);
        
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Menu started");
        
    }
    
    private static class MenuButton extends StackPane{
        
        private Text text;
        private Rectangle bg;
        private Hyperlink button;
        
        public MenuButton(String text){
            
            this.text = new Text();
            this.text.setText(text);
            this.text.setFont(this.text.getFont().font(20));
            this.text.setFill(Color.WHITE);
            
            bg = new Rectangle(250, 50);
            this.setWidth(250);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));
            
            button = new Hyperlink();
            button.setPrefSize(250, 50);
            
            
            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, this.text, button);
            
            this.setOnMouseEntered(event -> {
                
                //bg.setTranslateX(10);
                //this.text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                this.text.setFill(Color.BLACK);
                
                
            });
            
            this.setOnMouseExited(event -> {
                
                //bg.setTranslateX(-10);
                //this.text.setTranslateX(-10);
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
