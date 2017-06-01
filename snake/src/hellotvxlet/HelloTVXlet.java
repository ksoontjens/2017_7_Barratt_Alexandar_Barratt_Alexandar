package hellotvxlet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Timer;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.ui.DVBColor;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HSceneTemplate;
import org.havi.ui.HScreenDimension;
import org.havi.ui.HScreenPoint;
import org.havi.ui.HStaticText;
import org.havi.ui.HTextButton;
import org.havi.ui.HVisible;
import org.havi.ui.event.HActionListener;
import org.havi.ui.event.HBackgroundImageListener;
import org.havi.ui.*;
import org.havi.ui.event.*;

public class HelloTVXlet implements Xlet, HActionListener, ResourceClient, HBackgroundImageListener     
{
    HStaticText scoreLabel;
    SnakeXlet gxlet;
    XletContext ctx;
    private HScene scene;
    private HSceneTemplate sceneTemplate = new HSceneTemplate();
    private HTextButton startButton;
    
    private HScreen screen;
    private HBackgroundDevice bgDevice;
    private HBackgroundConfigTemplate bgTemplate;
    private HStillImageBackgroundConfiguration bgConfiguration;
    private HBackgroundImage agrondimg = new HBackgroundImage("snakeGameBackground.jpg");
    public void notifyRelease(ResourceProxy proxy){}
    public void release(ResourceProxy proxy){}
    public boolean requestRelease(ResourceProxy proxy, Object requestData){return false;}
    public void imageLoaded(HBackgroundImageEvent e){
        try{
            bgConfiguration.displayImage(agrondimg);
        } catch (Exception s){
            System.out.println(s.toString());
        }
    }
    public void imageLoadFailed(HBackgroundImageEvent e){
        System.out.println("Image kan niet geladen worden");
    }
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {    
        System.out.println("destroyXlet");
        scene.setVisible(false);
        HSceneFactory.getInstance().dispose(scene);
        agrondimg.flush();  
    }

    public void initXlet(XletContext ctx) throws XletStateChangeException {
        // Start de GameXlet
        this.ctx=ctx;
        gxlet=new SnakeXlet(this);

        sceneTemplate.setPreference(org.havi.ui.HSceneTemplate.SCENE_SCREEN_DIMENSION,
        new HScreenDimension(1.0f,1.0f), org.havi.ui.HSceneTemplate.REQUIRED);
        sceneTemplate.setPreference(org.havi.ui.HSceneTemplate.SCENE_SCREEN_LOCATION,
        new HScreenPoint(0.0f,0.0f), org.havi.ui.HSceneTemplate.REQUIRED);
        scene = HSceneFactory.getInstance().getBestScene(sceneTemplate);

        screen = HScreen.getDefaultHScreen();
        bgDevice = screen.getDefaultHBackgroundDevice();
        if(bgDevice.reserveDevice(this)){
            System.out.println("Background has been reserved");
        } else{
            System.out.println("Background can not be reserved");
        }
        bgTemplate = new HBackgroundConfigTemplate();
        bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
        bgConfiguration = (HStillImageBackgroundConfiguration)bgDevice.getBestConfiguration(bgTemplate);
        try{
            bgDevice.setBackgroundConfiguration(bgConfiguration);
        } catch(java.lang.Exception e){
            System.out.println(e.toString());
        }

        startButton = new HTextButton("Start Game");
        startButton.setLocation(100,100);
        startButton.setSize(500,100);
        startButton.setBackground(new DVBColor(255,255,255,179));
        startButton.setBackgroundMode(HVisible.BACKGROUND_FILL);     

        startButton.setActionCommand("startGame");
        startButton.addHActionListener(this);
        scene.add(startButton);
        startButton.requestFocus();
        agrondimg.load(this);
    }

    public void pauseXlet() {

    }

    public void startXlet() throws XletStateChangeException {
       scene.validate();
       scene.setVisible(true);
       System.out.println("StartXlet");
       agrondimg.load(this);
    }
 
    public void respawn() throws XletStateChangeException
    {
     
        System.out.println("Restart Xlet!!!");
        gxlet.destroyXlet(true);
        gxlet=new SnakeXlet(this);
        gxlet.initXlet(ctx);
        gxlet.startXlet();
        
    }

    public void actionPerformed(ActionEvent arg0) {
        
        String answer = arg0.getActionCommand();
        
        System.out.println(answer);
        
        if(answer.equals("startGame")) {
            try {

                gxlet.initXlet(ctx);
                gxlet.startXlet();
            } catch (XletStateChangeException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }

  /*  public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void imageLoaded(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
*/

    
}