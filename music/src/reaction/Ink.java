package reaction;

import graphics.G;
import music.I;
import music.UC;

import java.awt.*;
import java.util.ArrayList;

public class Ink extends G.PL implements I.Show{
    public static Buffer BUFFER = new Buffer();
    public Ink() {
        super(10);
        G.PL temp = BUFFER.subSample(10);
        for(int i = 0; i < 10; i++){
            points[i].set(temp.points[i]);

//        super(BUFFER.n);
//        for(int i = 0; i < BUFFER.n; i++){
//            points[i].set(BUFFER.points[i]);

        }
    }

    public void show(Graphics g) {
        g.setColor(Color.black);
        //g.drawLine(0,0,100,100);
        draw(g);

    }
    //------------------------Buffer----------------------------
    public static class Buffer extends G.PL implements I.Show, I.Area{
        public static final int MAX = UC.inkBufferMax;
        public int n;
        public G.BBox bbox = new G.BBox();

        private Buffer(){super(MAX);}
        public G.PL subSample(int k){
            G.PL res = new G.PL(k);
            for (int i = 0; i < k; i++){
                res.points[i].set(this.points[i * (n - 1)/(k - 1)]);
            }
            return res;
        }
        public void add(int x, int y){if(n<MAX){points[n++].set(x,y);bbox.add(x, y);}}

        public void clear(){n = 0;}

        @Override
        public boolean hit(int x, int y) {return true;}
        @Override
        public void dn(int x, int y) {clear();bbox.set(x,y); add(x,y);}

        @Override
        public void drag(int x, int y) {add(x,y);}

        @Override
        public void up(int x, int y) {

        }

        @Override
        public void show(Graphics g) {
            this.drawN(g,n);
            bbox.draw(g);
            g.setColor(Color.BLUE);
            this.drawNDots(g, n);
        }

    }
    //-----------------------List-------------------------------
    public static class List extends ArrayList<Ink> implements I.Show{
        @Override
        public void show(Graphics g){for (Ink ink: this){ink.show(g);}}
    }
}
