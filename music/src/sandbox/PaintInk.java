package sandbox;

import graphics.Window;
import music.UC;
import graphics.G;
import reaction.Ink;
import reaction.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PaintInk extends Window{
    public static String recognized = "";
    public static Ink.List inkList = new Ink.List();
    public static reaction.Shape.Prototype.List pList = new reaction.Shape.Prototype.List();
    //static{inkList.add(new Ink());}
    public PaintInk(){super("PaintInk", UC.initialWindowWidth,UC.initialWindowHeight);}
    public void paintComponent(Graphics g){
        G.clear(g);
        inkList.show(g);
        g.setColor(Color.red);
        Ink.BUFFER.show(g);
        if(inkList.size() > 1){
            int last = inkList.size() - 1;
            int dist = inkList.get(last).norm.dist(inkList.get(last-1).norm);
            g.setColor((dist < UC.noMatchDist ? Color.GREEN : Color.red)); //超出最大距离 提示未能识别
            g.drawString("dist: " + dist, 600, 60);
        }
        g.drawString("points: " + Ink.BUFFER.n, 600, 30);
        pList.show(g);
        g.drawString(recognized,700,40);
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
        Ink ink = new Ink();
        Shape s = Shape.recognize(ink);
        recognized = "recognized, " + (s != null ? s.name : "unrecognized");
        Shape.Prototype proto = new Shape.Prototype();
        inkList.add(ink);
        if(pList.bestDist(ink.norm) < UC.noMatchDist){
            proto = Shape.Prototype.List.bestMatch;
            proto.blend(ink.norm);
        }else {
            pList.add(new Shape.Prototype());  //这里可能有问题
            pList.add(proto);
        }
        ink.norm = proto;
        repaint();
    }
}
