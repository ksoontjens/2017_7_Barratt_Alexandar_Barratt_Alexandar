/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

import java.awt.Image;
import java.awt.MediaTracker;
import org.havi.ui.HIcon;

/**
 *
 * @author student
 */
public abstract class Sprite extends HIcon implements ObserverInterface
{
    Image mijnimage;
    int x,y;   
    public Sprite(int x, int y, String initImage)
    {
       super(); //roept contructor op van HICOn (superklasse) dit moet op eerste regel
       this.x=x;
       this.y=y;
       this.setLocation(x, y);
       this.setBordersEnabled(false);
       
       mijnimage=  this.getToolkit().getImage(initImage);
       MediaTracker mt = new MediaTracker(this);
       mt.addImage(mijnimage, 1);
       try {
           mt.waitForAll();
       } catch (InterruptedException ex) {
           ex.printStackTrace();
       }
       
       //this.setSize(mijnimage.getWidth(this), mijnimage.getHeight(this));
       this.setGraphicContent(mijnimage, this.NORMAL_STATE);        
    }
    public abstract void update(int tijd);   
}
