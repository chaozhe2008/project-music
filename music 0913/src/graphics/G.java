package graphics;

import java.awt.*;
import java.util.Random;

public class G {
    public static Random RND = new Random();
    public static int rnd (int max){ return RND.nextInt(max);}
    public static Color rndColor(){
        return new Color(rnd(256),rnd(256),rnd(256));
    }
    public static void clear(Graphics g){
        g.setColor(Color.white); g.fillRect(0,0,5000,5000);
    }
}
