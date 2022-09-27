package sandbox;

import graphics.Window;
import music.UC;
import graphics.G;
import reaction.Ink;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PaintInk extends Window{
    public static Ink.List inkList = new Ink.List();
    //static{inkList.add(new Ink());}
    public PaintInk(){super("PaintInk", UC.initialWindowWidth,UC.initialWindowHeight);}
    public void paintComponent(Graphics g){
        G.clear(g);
        inkList.show(g);
        g.setColor(Color.red);
        Ink.BUFFER.show(g);
        g.drawString("points: " + Ink.BUFFER.n, 600, 30);
    }
    public void mousePressed(MouseEvent me){
        Ink.BUFFER.dn(me.getX(),me.getY());
        repaint();
    }
    public void mouseDragged(MouseEvent me){
        Ink.BUFFER.drag(me.getX(),me.getY());
        repaint();
    }
    public void mouseReleased(MouseEvent me){
        inkList.add(new Ink());
        repaint();
    }
}
