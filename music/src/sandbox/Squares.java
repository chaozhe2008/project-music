package sandbox;

import graphics.G;
import graphics.Window;
import music.I;
import music.UC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//import static graphics.G.VS.theColor;

public class Squares extends Window implements ActionListener {
    public static Square.List theList = new Square.List();
    public static Square theSquare;
    //public static G.VS theVS = new G.VS(100,100,200,300);
    public static Color theColor = Color.BLUE;
    public static boolean dragging = false;
    public static G.V mouseDelta = new G.V(0,0);
    public static Timer timer;
    public static I.Area currentArea;
    public static Square BACKGROUND = new Square(0,0){
        public void dn(int x, int y) {
            dragging = false;
            theSquare = new Square(x, y);
            theList.add(theSquare);
        }
        public void drag(int x, int y){theSquare.resize(x, y);} //initialization
    };
    static{
        BACKGROUND.c = Color.white;
        BACKGROUND.size.set(5000,5000);
        theList.add(BACKGROUND);


    }
    public static final int W = UC.initialWindowWidth; //final:不变的(可以大写)
    public static final int H = UC.initialWindowWidth;
    public Squares(){super(
            "Squares",W, H);
        timer = new Timer(30, this);
        timer.setInitialDelay(1000);
        timer.start();
    } //function should be public

    @Override
    public void paintComponent(Graphics g){
        G.clear(g); //clear background to white
        //theVS.fill(g, theColor);
        theList.draw(g);
    }
    public void mousePressed(MouseEvent me) {
//        if (theVS.hit(me.getX(), me.getY())){
//            theColor = G.rndColor();
//        }
        int x = me.getX(), y = me.getY();
        currentArea = theList.hit(x, y);
        currentArea.dn(x, y);

//        if (theSquare == null) {
//        dragging = false;
//        theSquare = new Square(me.getX(), me.getY());
//        theList.add(theSquare);
//        theList.addNew(me.getX(),me.getY());
//        }
//        else{
//            dragging = true;
//            mouseDelta.set(x - theSquare.loc.x, y - theSquare.loc.y);
//        }
        repaint();
    }

    public void mouseDragged(MouseEvent me){   //rubber band drag
        int x = me.getX(), y = me.getY();
        currentArea.drag(x, y);
//        if(dragging){
//            theSquare.move(x-mouseDelta.x, y-mouseDelta.y); //0920这一步拖动方块时方块的左上角会瞬间跑到鼠标位置 改善需要加上相对位置
//        }else{
//            theSquare.resize(me.getX(), me.getY());
//        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
    //--------------------------Square-----------------------//
    public static class Square extends G.VS implements I.Area{ //create some other squares
        public Color c = G.rndColor();
        //public G.V dv = new G.V(G.rnd(40) - 20, G.rnd(40) - 20); //生成在-20～20中的随机数
        public G.V dv = new G.V(0, 0);
        public void move(int x, int y){       //0920 点空白处：新方块 方块处：拖动
            loc.x = x;
            loc.y = y;
        }
        public void moveAndBounce(){
            loc.add(dv);
            if (loc.x < 0 && dv.x < 0){dv.x = -dv.x;}
            if (loc.y < 0 && dv.y < 0){dv.y = -dv.y;}
            if (loc.x + size.x > W && dv.x > 0){dv.x = -dv.x;}
            if (loc.y + size.y > H && dv.y > 0){dv.y = -dv.y;}
        }
        public void draw(Graphics g){
            fill(g,c);
            moveAndBounce();
        }
//        public void draw(Graphics g){
//            for (Square s: this){s.draw(g)};
//        }
        public Square(int x, int y) {super(x, y, 100, 100);}

        @Override
        public void dn(int x, int y) {
            dragging = true;
            mouseDelta.set(x - theSquare.loc.x, y - theSquare.loc.y);
        }

        @Override
        public void drag(int x, int y){
            theSquare.move(x-mouseDelta.x, y-mouseDelta.y);
        }
        @Override
        public void up(int x, int y) {

        }

    //--------------------------List-----------------------//
    public static class List extends ArrayList<Square> implements I.Draw{
            public void draw(Graphics g){for(Square s: this){
                s.draw(g);
                }
            }
            public Square hit(int x, int y){
                Square res = null;
                for (Square s: this){
                    if (s.hit(x,y)){
                        res = s;
                    }
                }
                return res;
            }

        public void addNew(int x, int y){
            add(new Square(x,y));
        }
    }
    }



}

