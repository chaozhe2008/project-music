package sandbox;

import graphics.G;
import graphics.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Squares extends Window{
    public static Square.List theList = new List();
    //public static G.VS theVS = new G.VS(100,100,200,300);
    public static Color theColor = Color.BLUE;
    public Squares(){super("Squares",1000,750);} //function should be public
    public static Square theSquare;

    @Override
    public void paintComponent(Graphics g){
        G.clear(g); //clear background to white
        //theVS.fill(g, theColor);
        theList.draw(g);
    }
    public void mousePressed(MouseEvent me){
//        if (theVS.hit(me.getX(), me.getY())){
//            theColor = G.rndColor();
//        }
        theSquare = new Square(me.getX(),me.getY());
        theList.add(theSquare);
//        theList.addNew(me.getX(),me.getY());
        repaint();
    }

    public void mouseDragged(MouseEvent me){   //rubber band drag
        repaint();
    }
    //--------------------------Square-----------------------//
    public static class Square extends G.VS{
        public Color c = G.rndColor();
        public Square(int x, int y) {super(x, y, 100, 100);}

    //--------------------------List-----------------------//
    public static class List extends ArrayList<Square> {
            public void draw(Graphics g){for(Square s: this){s.fill(g, s.c);}}

        public void addNew(int x, int y){
            add(new Square(x,y));
        }
    }
    }


    private static class List extends Square.List {
    }
}
