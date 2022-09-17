package graphics;

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
    //-----------------------------V-----------------------------// Vector

    public static class V{
        public int x,y;
        public V(int x, int y){this.set(x, y);}
        public void set(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void add(V v){x += v.x; y += v.y;};
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

    }
    //-----------------------------BBox-----------------------------// Bounding Box

    public static class BBox{

    }
    //-----------------------------PL-----------------------------// Pounding Line

    public static class PL{

    }


}
