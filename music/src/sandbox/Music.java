package sandbox;

import graphics.G;
import graphics.Window;
import music.Page;
import music.UC;
import reaction.Gesture;
import reaction.Ink;
import reaction.Layer;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Music extends Window {
    static {new Layer("BACK"); new Layer("FORE");}
    public static Page PAGE;
    public static void main(String[] args){(PANEL = new Music()).launch();}
    public Music(){
        super("Music Editor", UC.initialWindowWidth, UC.initialWindowHeight);
    }
    public void paintComponent(Graphics g){
        G.clear(g);
        g.setColor(Color.blue);
        g.drawString("Music", 100, 30);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
    }

    public void mousePressed(MouseEvent me){Gesture.AREA.dn(me.getX(), me.getY());repaint();}
    public void mouseDragged(MouseEvent me){Gesture.AREA.drag(me.getX(), me.getY());repaint();}
    public void mouseReleased(MouseEvent me){Gesture.AREA.up(me.getX(), me.getY());repaint();}
}
