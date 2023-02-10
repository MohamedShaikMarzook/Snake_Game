/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake_game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author CLIENT-1
 */
public final class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
    static int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int score=0;
    int ballX;
    int ballY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
   
    
    //GAME WINDOW
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
        


    public void startGame(){
        newBall(); 
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
        
     
        
    }
    @Override
    public void paintComponent(Graphics g){
         
       
        super.paintComponent(g);
        draw(g);
    }
        
        
   
    
    
    public void draw(Graphics g){
        if(running){
            /*
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }*/
            
        // BALL DESIGN
        g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255)));
        g.fillOval(ballX, ballY, UNIT_SIZE, UNIT_SIZE);
        
        
        //SNAKE DESIGN
        for(int i=0; i<bodyParts; i++){
            if(i == 0){
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                
                
               
            }
            else{
                //g.setColor(new Color(45,100,0));
                 g.setColor(Color.LIGHT_GRAY);
                 g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                
            }
            
        }
    
        
        //FONT
        g.setColor(Color.green);
        g.setFont(new Font("Times New Rome",Font.ITALIC,30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : "+score,(SCREEN_WIDTH - metrics.stringWidth("Score : "+score))/2, g.getFont().getSize());
          
        
        
        g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255)));
        g.setFont(new Font("Times New Rome",Font.ITALIC,8));
        FontMetrics metrics1 =getFontMetrics(g.getFont());
        g.drawString("BY MARZOOK",(SCREEN_WIDTH - metrics1.stringWidth("BY MARZOOK : ")), g.getFont().getSize());
        
       
        }
        
        else{
            
            gameOver(g);
        }
               
    }
    
    
    public void newBall(){
       ballX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
       ballY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; 
    }
    public void move(){
      for(int i=bodyParts; i>0; i--){
          x[i] = x[i-1];
          y[i] = y[i-1];
         
      }
      switch (direction){
      case 'U' -> y[0] = y[0]-UNIT_SIZE;
      case 'D' -> y[0] = y[0]+UNIT_SIZE;
      case 'L' -> x[0] = x[0]-UNIT_SIZE;
      case 'R' -> x[0] = x[0]+UNIT_SIZE;
          
      }
          
                  
                          
    }
    public void checkBall(){
       if((x[0] == ballX)&& (y[0] == ballY)){
            bodyParts++;
            score=score+2;
            newBall();
            
       } 
    }
    
    // FAIL DETECTION
    public void checkCollisions(){
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i])&&(y[0] == y[i])){
                running = false;
                 
            }
        }
        if(x[0] < 0 || x[0] > SCREEN_WIDTH){
            running = false;
        }
        
        else if(y[0] < 0 || y[0] > SCREEN_HEIGHT){
            running = false;
        }
     
        else if(!running){
            timer.stop();
        }
        
        
    }
    
    // FAIL AFTER SCREEN
       public void gameOver(Graphics g){
        g.setColor(Color.green);
        g.setFont(new Font("Times New Rome",Font.ITALIC,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : "+score,(SCREEN_WIDTH - metrics1.stringWidth("Score : "+score))/2, g.getFont().getSize());
          
    
        g.setColor(Color.red);
        g.setFont(new Font("Times New Rome",Font.ITALIC,75));
        FontMetrics metrics2 =getFontMetrics(g.getFont());
        g.drawString("Game Over !",(SCREEN_WIDTH - metrics2.stringWidth("Game Over !"))/2, SCREEN_HEIGHT/2);
       
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   if(running){
       move();
       checkBall();
       checkCollisions();
     
      }
   repaint();
   
    
    }
    
    public class MyKeyAdapter extends KeyAdapter{
    
       
        @Override
        public void keyPressed(KeyEvent e){
            //GAME 2X
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
          startGame();
          repaint();
            }
         switch(e.getKeyCode()){
             //LEFT
             case KeyEvent.VK_LEFT:
                 if(direction != 'R'){
                     direction = 'L';
             }   
                 break;
                 //RIGHT
                 case KeyEvent.VK_RIGHT:
                 if(direction != 'L'){
                     direction = 'R';
             }     
                 break;
                 //UP
                 case KeyEvent.VK_UP:
                 if(direction != 'D'){
                     direction = 'U';
             }   
                 break;
                 //DOWN
                 case KeyEvent.VK_DOWN:
                 if(direction != 'U'){
                     direction = 'D';
             }      
               break;
                 
         }   
         
        }

    
    
    
    }}
   
    

