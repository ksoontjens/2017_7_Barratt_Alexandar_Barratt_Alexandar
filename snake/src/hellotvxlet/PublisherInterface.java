/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

/**
 *
 * @author student
 */
public interface PublisherInterface {
        public abstract void register(ObserverInterface observer);
        public abstract void unregister(ObserverInterface observer);  
}
