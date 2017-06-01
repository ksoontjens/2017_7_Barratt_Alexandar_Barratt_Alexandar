/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

/**
 *
 * @author student
 */


import java.util.ArrayList;
import org.dvb.ui.DVBColor;
import org.havi.ui.HComponent;
import org.havi.ui.HScene;
import org.havi.ui.HStaticText;
import org.havi.ui.HVisible;

public class SnakeChain implements ObserverInterface{
    HScene scene;
    Publisher pub;
    private ArrayList chain = new ArrayList();
    private int chainLength = 10;  
    private int score = 0;
    private boolean moveDown = true;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;    
    private boolean gameOver = false;
    private String componentImage = "snakePart.png";    
    private SnakeComponent firstComp; 
    private HStaticText scoreLabel;
    
    private  HStaticText gameOverInfoText;
    
    
    private boolean directionChosen = false;
    
    public SnakeChain(HScene initScene, Publisher initPub)
    {
        scene = initScene;
        pub = initPub;
        firstComp = new SnakeComponent(72,72, componentImage);
        firstComp.setSize(24,24);
        chain.add(firstComp);
        
        scoreLabel = new HStaticText("Score = " + score);
        scene.add(scoreLabel); 
        /*removeLabel();*/
        updateScore();
    }
    
    public ArrayList getSnakeChain()
    {
        return chain;
    }
    public void setSnakeChain(ArrayList newChain)
    {
        this.chain = newChain;
    }
    
    
    public boolean isGameOver()
    {
        return gameOver;
    }
    
    public void setGameOver(boolean value)
    {
        this.gameOver = value;
    }
    public void updateScoreLabel(){
         
        scene.remove(scoreLabel);
        scoreLabel = new HStaticText("Score = " + score);
        scene.add(scoreLabel); 
       
    }
    public void updateScore(){       
        updateScoreLabel();
        scoreLabel.setLocation(5,20);
        scoreLabel.setSize(100,20);
        scoreLabel.setBackground(new DVBColor(255,255,255,0));
        scoreLabel.setBackgroundMode(HVisible.BACKGROUND_FILL);       
             
       
    }
    public void restart()
    {
        removeChain();
        chain = new ArrayList();
        chainLength = 4;
        score = 0;
        moveDown = true;
        moveLeft = false;
        moveRight = false;
        moveUp = false;
        gameOver = false;
        firstComp = new SnakeComponent(60,60, componentImage);
        chain.add(firstComp);
        
        updateScore();
    }
    
    public void move(int tijd)
    {
        if(chain.size() >= chainLength)
        {
            scene.remove((HComponent)chain.get(0));
            chain.remove(0);
        }
        SnakeComponent lastChain = (SnakeComponent) chain.get(chain.size()-1);
        int x = lastChain.getX();
        int y = lastChain.getY();
        int width = lastChain.getWidth();
        int height = lastChain.getHeight();
        int sceneWidth = scene.getWidth();
        int sceneHeight = scene.getHeight();    
        SnakeComponent comp;
    
        if(moveDown)
        {
            if(y>=(sceneHeight-height))
            {
                comp = new SnakeComponent(x, 0,componentImage);
            }
            else
            {
                comp = new SnakeComponent(x, (y+height),componentImage);
            }            
        }
        else if(moveUp)
        {
            if(y<=0)
            {  
                comp = new SnakeComponent(x, (580-height),componentImage);
            }
            else
            {    
             comp = new SnakeComponent(x, (y-height), componentImage);
            }            
        }            
        else if(moveLeft)
        {
            if(x<=0)
            {
                comp = new SnakeComponent((sceneWidth-width), y, componentImage);
            
            }                
            else
            {
                comp = new SnakeComponent((x-width), y, componentImage);
            }            
        }

        else if(moveRight)
        {
            if(x>=(sceneWidth-width))
            {                    
                comp = new SnakeComponent((0), y, componentImage);
           
            }
            else
            {  
                comp = new SnakeComponent((x+width), y, componentImage);
            }            
        } 
        else
        {
            comp = new SnakeComponent(x, (y+height), componentImage);
        }            
        comp.setSize(24,24);
        chain.add(comp);
        updateChain();           
    }    
    public void checkTailCollision()
    {
        SnakeComponent head = (SnakeComponent)chain.get(chain.size()-1);
        for(int i = 0; i< (chain.size()-1); i++)
        {
            SnakeComponent tailComp = (SnakeComponent)chain.get(i);
            if(head.getBounds().intersects(tailComp.getBounds()))
            {
               
                gameOver = true;
                gameOverInfo();
            }
        }
    }    
    
    public void gameOverInfo()
    {
        
       
        gameOverInfoText = new HStaticText("score = " + score + "\n" + " press space");
        
        int sceneWidth = scene.getWidth();
        int sceneHeight = scene.getWidth();
        gameOverInfoText.setSize(300,100);    
        int xpos = sceneWidth/2 - (gameOverInfoText.getWidth()/2);
        int ypos = sceneHeight/2 - (gameOverInfoText.getHeight()/2);
               
         gameOverInfoText.setLocation(xpos,ypos);
        gameOverInfoText.setBackground(new DVBColor(255,255,255,0));  
        
        scene.add(gameOverInfoText); 
        scene.repaint();
    }
    
    public void removeChain()
    {
        for(int i = 0; i < chain.size(); i++)
        {
            /*pub.register((ObserverInterface) chain.get(i));*/
            scene.remove((HComponent)chain.get(i));
        }
    }   
    public void updateChain()
    {
        for(int i = 0; i < chain.size(); i++)
        {
            /*pub.register((ObserverInterface) chain.get(i));*/
            scene.add((HComponent)chain.get(i));
        }
    }
    public void update(int tijd) 
    {
        if(!gameOver)
        {
            move(tijd);
            checkTailCollision();
            directionChosen = false;
        }
    }    
    public void pointUp()
    {
        chainLength += 1;
        score = score + 1;
        updateScore();
        
    }            
    public void moveUp()
    {
        if(!moveDown && !directionChosen)
        {
            moveUp = true;
            moveLeft = false;
            moveRight = false;
            moveDown = false;
            directionChosen = true;
        }
    }    
    public void moveDown()
    {
        if(!moveUp  && !directionChosen)
        {
            moveUp = false;
            moveLeft = false;
            moveRight = false;
            moveDown = true;
            directionChosen = true;
        }
    }
    public void moveLeft()
    {
        if(!moveRight  && !directionChosen)
        {
            moveUp = false;
            moveLeft = true;
            moveRight = false;
            moveDown = false;
            directionChosen = true;
        }
    }
    public void moveRight()
    {
        if(!moveLeft  && !directionChosen)
        {
            moveUp = false;
            moveLeft = false;
            moveRight = true;
            moveDown = false;
            directionChosen = true;
        }
    }    
}
