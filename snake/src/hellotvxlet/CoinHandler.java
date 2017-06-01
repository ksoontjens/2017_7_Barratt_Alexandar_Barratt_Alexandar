/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

import java.util.ArrayList;
import java.util.Random;
import org.havi.ui.HScene;

/**
 *
 * @author student
 */
public class CoinHandler implements ObserverInterface{
    
    HScene scene;
    private SnakeChain snake;
    private Coin coin;
    
    public CoinHandler(HScene initScene, SnakeChain initSnake)
    {
        scene = initScene;
        snake = initSnake;
        spawnCoin();        
    }    
    public void collisionDetection()
    {        
        ArrayList chain = snake.getSnakeChain();
        SnakeComponent snakeHead = (SnakeComponent) chain.get(chain.size()-1);        
        if(coin!=null)
        {
               if(snakeHead.getBounds().intersects(coin.getBounds()))
               {
                   snake.pointUp();
                   scene.remove(coin);
                   coin = null;
                   spawnCoin();
               }
        }        
    }
    
    public void spawnCoin()
    {
        ArrayList spawnLocations = new ArrayList();        
        int sceneWidth = scene.getWidth();
        int sceneHeight = scene.getHeight();
        ArrayList chain = snake.getSnakeChain();
        /*spawnLocations vullen met al de mogelijke spawnlocations*/       
        for(int y = 0; y < sceneHeight; y+=24)
        {
            for(int x = 0; x<sceneWidth; x+=24)
            {
                boolean noConflict = true;                               
                for(int i = 0; i<chain.size(); i++)
                {
                    SnakeComponent comp = (SnakeComponent) chain.get(i);                    
                    if((x == comp.getX()) && (y == comp.getY()))
                    {
                        noConflict = false;
                    }                                   
                }
                if(noConflict)
                {
                        int[] newPos = {x,y};
                        spawnLocations.add(newPos);
                }
            }
        }
        /*System.out.println(spawnLocations.size() + "  " + chain.size());*/        
        /*een random index nemen van spawnLocations*/        
        Random rand = new Random();
        int random = rand.nextInt(spawnLocations.size()-1)+0;                       
        int[] coord = (int[]) spawnLocations.get(random);        
        int xpos = coord[0];
        int ypos = coord[1];                
        coin = new Coin(xpos,ypos, "cherry.png");
        
        coin.setSize(24,24);
        
        scene.add(coin);
    }

    public void update(int tijd)
    {
        collisionDetection();        
    }
}
