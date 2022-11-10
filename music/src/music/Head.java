package music;

import reaction.Gesture;
import reaction.Mass;
import reaction.Reaction;

import java.awt.*;
import java.util.ArrayList;

public class Head extends Mass implements Comparable<Head>{
    public Staff staff;
    public int line;
    public Time time;
    public Glyph forcedGlyph = null; //placeholder for future glyph
    public Stem stem = null;
    public boolean wrongSide = false; // if distance between notes are only one H, one have to go to the other side (otherwise overlap)

    public Head(Staff staff, int x, int y) {
        super("NOTE");
        this.staff = staff;
        time = staff.sys.getTime(x);
        time.heads.add(this);
        int H = staff.fmt.H;
//        int top = staff.yTop() - H;
//        line = ((y - top + H/2) / H) - 1;
        this.line = staff.lineOfY(y);
//        System.out.println("line: " + line);

        addReaction(new Reaction("S-S") {
            public int bid(Gesture gest) {
                int x = gest.vs.xM(), y1 = gest.vs.yL(), y2 = gest.vs.yH();
                int w = Head.this.w(), hY = Head.this.y();

                if(y1 > y || y2 < y){return UC.noBid;}
                int hLeft = Head.this.time.x, hRight = hLeft + w;
                if (x < hLeft - w || x > hRight + w){return UC.noBid;}
                if (x < hLeft + w/2){return hLeft - x;}
                if (x > hRight - w/2){return x - hRight;}
                return UC.noBid;
            }


            public void act(Gesture gest) {
                int x = gest.vs.xL(), y1 = gest.vs.yL(), y2 = gest.vs.yH();
                Time t = Head.this.time;
                int w = Head.this.w();
                boolean up = x > t.x + w/2;
                if (Head.this.stem == null){
                    System.out.println("STEM");
                    t.stemHeads(up, y1, y2);
                }else{
                    System.out.println("UNSTEM");
                    t.unStemHeads(y1, y2);
                }
            }
        });
        addReaction(new Reaction("DOT") { //dot belongs to stem, all heads on a stem share same dot notation
            public int bid(Gesture gest) {
                if (Head.this.stem == null){return UC.noBid;}
                int xh = Head.this.x(), yh = Head.this.y(), h = Head.this.staff.fmt.H, w = Head.this.w();
                int x = gest.vs.xM(), y = gest.vs.yM();
                if (x < xh || x > xh + 2 * w || y < yh - h || y > yh + h){return UC.noBid;}
                return Math.abs(xh + w - x) + Math.abs(yh - y);
            }


            public void act(Gesture gest) {
                Head.this.stem.cycleDot(); // multi dots bug need to be fixed later
            }
        });
    }

    public int compareTo(Head h) { // sorting heads: first compare staff, if equal, compare line
        return(staff.iStaff != h.staff.iStaff ? staff.iStaff - h.staff.iStaff : this.line - h.line);
    }

    public int w(){return 24 * staff.fmt.H/10;}

    public void show(Graphics g){
        int H = staff.fmt.H;
        g.setColor(wrongSide ? Color.RED : Color.BLACK); // this line is to test wrongSide function
        (forcedGlyph != null ? forcedGlyph : normalGlyph()).showAt(g, H, x(), y());
        if (stem != null){
            int off = UC.augDotOffset, sp = UC.augDotSpace;
            for (int i = 0; i < stem.nDot; i++){
                g.fillOval(time.x + off + i * sp, y() - 3*H/2, 2*H/3, 2*H/3);
            }
        }
    }

    public int x(){
        int res = time.x;
        if (wrongSide){res += (stem != null && stem.isUp ? w() : -w());} //shift wrongSide heads
        return res;
    }

    public int y(){return staff.yLine(line);}
    public Glyph normalGlyph(){
        if (stem == null){return Glyph.HEAD_Q;}
        if (stem.nFlag == -1){return Glyph.HEAD_HALF;}
        if (stem.nFlag == -2){return Glyph.HEAD_W;}
        return Glyph.HEAD_Q;
    }
    public void deleteHead(){
        // stub
        time.heads.remove(this);
    }

    public void unStem(){
        if (stem != null){
            stem.heads.remove(this);
            if (stem.heads.size() == 0){stem.deleteStem();}
            stem = null;
            wrongSide = false;
        }
    }

    public void joinStem(Stem s){
        if(stem != null){unStem();}
        s.heads.add(this);
        stem = s;
    }



    //------------------------List-------------------------//
    public static class List extends ArrayList<Head> {}
}
