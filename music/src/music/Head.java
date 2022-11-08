package music;

import reaction.Gesture;
import reaction.Mass;
import reaction.Reaction;

import java.awt.*;
import java.util.ArrayList;

public class Head extends Mass {
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
//        time.heads.add(this);

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
    }

    public int w(){return 24 * staff.fmt.H/10;}

    public void show(Graphics g){
        int H = staff.fmt.H;
        g.setColor(stem == null ? Color.RED : Color.BLACK);
        (forcedGlyph != null ? forcedGlyph : normalGlyph()).showAt(g, H, x(), y());
    }

    public int x(){
        //Stub - placeholder
        return time.x;
    }

    public int y(){return staff.yLine(line);}
    public Glyph normalGlyph(){
        //Stub
        return Glyph.HEAD_Q;
    }
    public void deleteHead(){
        //stub
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
