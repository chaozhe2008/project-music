package music;

import reaction.Gesture;
import reaction.Mass;
import reaction.Reaction;

import java.awt.*;

import static sandbox.Music.PAGE;

public class Bar extends Mass {
    private static final int FAT = 2, RIGHT = 4, LEFT = 8;
    public Sys sys;
    public int x, barType = 0;
    public Bar(Sys sys, int x) {
        super("NOTE");
        this.sys = sys;
        this.x = x;

        addReaction(new Reaction("S-S") { // cycle the barType
            @Override
            public int bid(Gesture gest) {
                int x = gest.vs.xM();
                if(Math.abs(x - Bar.this.x) > UC.BarToMarginSnap){return UC.noBid;}
                int y1 = gest.vs.yL(), y2 = gest.vs.yH();
                if(y1 < Bar.this.sys.yTop() - 20){return UC.noBid;}
                if(y2 > Bar.this.sys.yBot() + 20){return UC.noBid;}
                return Math.abs(x - Bar.this.x);
            }

            @Override
            public void act(Gesture gest) {
                Bar.this.cycleType();
            }
        });

        addReaction(new Reaction("S-S"){
            @Override
            public int bid(Gesture gest) {
                return 0;
            }

            @Override
            public void act(Gesture gest) {

            }


        });
    }

    public void cycleType(){barType++; if(barType > 2){barType = 0;}}
    public void toggleLeft(){barType = barType ^ LEFT;} // exclusive or
    public void toggleRight(){barType = barType ^ RIGHT;} // exclusive or

    public void show(Graphics g){
        int yTop = sys.yTop(), N = sys.fmt.size();
        if(barType == 0){g.setColor(Color.BLACK);}
        if(barType == 1){g.setColor(Color.RED);}
        if(barType == 2){g.setColor(Color.GREEN);}

        for(int i = 0; i < N; i++){
            Staff.Fmt sf = sys.fmt.get(i);
            int topLine = yTop + sys.fmt.staffOffSet.get(i);
            g.drawLine(x, topLine, x, topLine + sf.height());
        }
    }

    public static void wings(Graphics g, int x, int y1, int y2, int dx, int dy){ //wings on the bar line (like bracket)
        g.drawLine(x, y1, x + dx, y1 - dy);
        g.drawLine(x, y2, x + dx, y2 + dy);
    }
    public static void fatBar(Graphics g, int x, int y1, int y2, int dx){
        g.fillRect(x, y1, dx, y2 - y1);
    }

    public static void thinBar(Graphics g, int x, int y1, int y2){
        g.drawLine(x, y1, x, y2);
    }

    public void drawDots(Graphics g, int x, int top){
        int H = UC.defaultStaffSpace;
        if ((barType & LEFT) != 0){
            g.fillOval(x - 3 * H, top + (11 * H)/4, H / 2, H / 2);
            g.fillOval(x - 3 * H, top + (19 * H)/4, H / 2, H / 2);
        }

        if ((barType & RIGHT) != 0){
            g.fillOval(x + 3 * H, top + (11 * H)/4, H / 2, H / 2);
            g.fillOval(x + 3 * H, top + (19 * H)/4, H / 2, H / 2);
        }
    }
}
