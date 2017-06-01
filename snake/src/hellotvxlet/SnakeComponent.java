/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

/**
 *
 * @author student
 */
public class SnakeComponent extends Sprite
{    
    public SnakeComponent(int x, int y, String initImage)
    {
       super(x,y, initImage); //roep contrstructor van sprite aan
       this.setSize(20,20);       
    }
    public void update(int tijd)
    {
        
    }
}
