package sandbox;
import graphics.G;
import graphics.Window;
import music.UC;
import reaction.Ink;
import reaction.Shape;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static sandbox.PaintInk.inkList;

public class ShapeTrainer extends Window{
    public static void main(String [] args){(Window.PANEL = new ShapeTrainer()).launch();}
    public ShapeTrainer(){super("ShapeTrainer", UC.initialWindowWidth, UC.initialWindowHeight);}
    public static String UNKNOWN = " <- this name is unknown";
    public static String KNOWN = " <- this is known shape";
    public static String ILLEGAL = " <- this is not legal shape";
    public static String currName = "";
    public static String currState = ILLEGAL;
    public static Shape.Prototype.List pList = new Shape.Prototype.List();
    public void setState(){
        currState = (currName.equals("") || currName.equals("DOT")) ? ILLEGAL : UNKNOWN;
        if(currState == UNKNOWN){
            if(Shape.DB.containsKey(currName)){
                currState = KNOWN;
                pList = Shape.DB.get(currName).prototypes;
            }else{
                pList = null;
            }
        }
    }
    public void paintComponent(Graphics g){
        G.clear(g);
        g.setColor(Color.black);
        g.drawString(currName, 600, 30);
        g.drawString(currState, 700, 30);
        g.setColor(Color.red);
        Ink.BUFFER.show(g);
        if (pList != null){
                pList.show(g);
        }
    }
    public void mousePressed(MouseEvent me) {
        Ink.BUFFER.dn(me.getX(), me.getY());
        repaint();
    }
    public void mouseDragged(MouseEvent me){
        Ink.BUFFER.drag(me.getX(), me.getY());
        repaint();
    }
//    public void mouseReleased(MouseEvent me){
//        //Ink.BUFFER.drag(me.getX(), me.getY());
//        if (currState != ILLEGAL){
//            Ink ink = new Ink();
//            reaction.Shape.Prototype proto;
//            if (pList == null){
//                Shape s = new Shape(currName);
//                Shape.DB.put(currName, s);
//                pList = s.prototypes;
//            }
//            if(pList.bestDist(ink.norm) < UC.noMatchDist){
//                proto = reaction.Shape.Prototype.List.bestMatch;
//                proto.blend(ink.norm);
//            }else {
//                proto = new reaction.Shape.Prototype();
//                pList.add(proto);
//            }
//        }
//        repaint();
//    }
    public void mouseReleased(MouseEvent me){
        Ink ink = new Ink();
        Shape.DB.train(currName, ink);
        setState();
        repaint();
    }

    public void keyTyped(KeyEvent ke){
        char c = ke.getKeyChar();
        System.out.println("type: " + c);
        currName = (c == ' ' || c == 0x0D || c == 0x0A) ? "" : currName + c;  //hex decimal number ASCII, microsoft:0x0D, Linux:0x0A (end of a line)
        if (c == 0x0D || c == 0x0A) {Shape.saveShapeDB();}
        setState();
        repaint();
    }

}
