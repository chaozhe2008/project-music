package graphics;

import javax.xml.stream.XMLInputFactory;
import java.awt.*;
import java.util.Random;

public class G {
    public static Random RND = new Random(); //RND: Constant Value (Never change) Capital
    public static int rnd(int max){return RND.nextInt(max);}
    public static Color rndColor(){return new Color(rnd(256),rnd(256),rnd(256));}

    public static void clear(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 5000, 5000);
    }
    public static void drawCircle(Graphics g, int x, int y, int r) {
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
    }
    //-----------------------------V-----------------------------// Vector

    public static class V{
        public int x,y;
        public V(int x, int y){this.set(x, y);}
        public void set(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void add(V v){x += v.x; y += v.y;};
        public void set(V v){this.x = v.x; this.y = v.y;}
    }
    //-----------------------------VS-----------------------------//

    public static class VS{
        public V loc, size;
        public VS(int x, int y, int w, int h){loc = new V(x,y); size = new V(w,h);}
        public void fill(Graphics g, Color c){
            g.setColor(c);
            g.fillRect(loc.x, loc.y, size.x, size.y);
        }
        public boolean hit(int x, int y){
            return loc.x < x && loc.y < y && x < loc.x+size.x && y < loc.y+size.y;
        }
        public void resize(int x, int y){
            if(x > loc.x && y > loc.y){
                size.set(x - loc.x, y - loc.y);
            }
        }
        }


    //-----------------------------LoHi-----------------------------//

    public static class LoHi{
        public int lo, hi;
        public LoHi(int min, int max){lo = min;hi = max;}
        public void add(int x){if (x < lo){lo = x;} if (x > hi){hi = x;}}
        public void set(int x){lo = x; hi = x;}
        public int size(){return hi-lo==0 ? 1 : hi-lo;}
    }
    //-----------------------------BBox-----------------------------// Bounding Box
    public static class BBox{
        public LoHi h, v;
        public BBox(){h = new LoHi(0, 0); v = new LoHi(0, 0);}
        public void set(int x, int y){h.set(x); v.set(y);}
        public void add(V v){h.add(v.x); this.v.add(v.y);}
        public void add(int x, int y){h.add(x); this.v.add(y);}
        public VS getNewVS(){return new VS(h.lo, v.lo, h.size(), v.size());}
        public void draw(Graphics g){g.drawRect(h.lo, v.lo, h.size(), v.size());}

    }
    //-----------------------------PL-----------------------------// Pounding Line

    public static class PL{
        public V[] points;
        public PL(int count){
            points = new V[count];
            for(int i = 0; i < count; i++){points[i] = new V(0,0);}
        }
        public int size(){return points.length;}
        public void drawN(Graphics g, int n){
            for(int i = 1; i < n; i++){
                g.drawLine(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
            }
        }
        public void drawNDots(Graphics g, int n) {
            for (int i = 0; i < n; i++) {
                drawCircle(g, points[i].x, points[i].y, 2);
            }

        }
        public void draw(Graphics g){drawN(g, points.length);}
        }
}





