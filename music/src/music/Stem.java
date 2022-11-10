package music;

import reaction.Gesture;
import reaction.Reaction;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

public class Stem extends Duration{ //line on notes, three rules (pic)
    public Head.List heads = new Head.List();
    public boolean isUp = true;
    public Stem(boolean up){
        isUp = up;

        addReaction(new Reaction("E-E") { //increment flag on stem
            public int bid(Gesture gest) {
                int y = gest.vs.yM(), x1 = gest.vs.xL(), x2 = gest.vs.xM();
                int xs = Stem.this.x(), y1 = Stem.this.yLo(), y2 = Stem.this.yHi(); //xs: x of stem
                if (xs < x1 || xs > x2 || y < y1 || y > y2){return UC.noBid;}
                return Math.abs(y - (y1 + y2)/2);
            }
            public void act(Gesture gest) {
                Stem.this.incFlag();
            }
        });

        addReaction(new Reaction("W-W") { //decrement flag on stem
            public int bid(Gesture gest) {
                int y = gest.vs.yM(), x1 = gest.vs.xL(), x2 = gest.vs.xM();
                int xs = Stem.this.x(), y1 = Stem.this.yLo(), y2 = Stem.this.yHi(); //xs: x of stem
                if (xs < x1 || xs > x2 || y < y1 || y > y2){return UC.noBid;}
                return Math.abs(y - (y1 + y2)/2);
            }
            public void act(Gesture gest) {
                Stem.this.decFlag();
            }
        });
    }

    public void show(Graphics g) {
        if (nFlag > -2 && heads.size() > 0) {
            int x = x(), H = UC.defaultStaffSpace;
            int yH = yFirstHead(), yB = yBeamEnd(); //beam: bar connecting the end of stem
            g.drawLine(x, yH, x, yB);
            if (nFlag > 0){
                if (nFlag == 1){(isUp ? Glyph.FLAG1D : Glyph.FLAG1U).showAt(g, H, x, yB);}
                if (nFlag == 2){(isUp ? Glyph.FLAG2D : Glyph.FLAG2U).showAt(g, H, x, yB);}
                if (nFlag == 3){(isUp ? Glyph.FLAG3D : Glyph.FLAG3U).showAt(g, H, x, yB);}
                if (nFlag == 4){(isUp ? Glyph.FLAG4D : Glyph.FLAG4U).showAt(g, H, x, yB);}
            }
        }
    }

    public Head firstHead(){return heads.get(isUp ? heads.size() - 1 : 0);}
    public Head lastHead(){return heads.get(isUp ? 0 : heads.size() - 1);}

    public int yFirstHead(){
        Head h = firstHead();
        return h.staff.yLine(h.line);
    }

    public int yBeamEnd(){
        Head h = lastHead();
        int line = h.line;
        line += isUp ? -7 : 7;
        int flagInc = nFlag > 2? 2 * (nFlag - 2) : 0;
        line += isUp ? -flagInc : flagInc;
        if((isUp && line > 4) || (!isUp && line < 4)){line = 4;} //extend to the fourth line
        return h.staff.yLine(line);
    }

    public int yLo() {return isUp ? yBeamEnd() : yFirstHead();}
    public int yHi() {return isUp ? yFirstHead() : yBeamEnd();}

    public int x(){
        Head h = firstHead();
        return h.time.x + (isUp ? h.w() : 0); //up stem on the right side of note, down stem on the left
    }

    public void deleteStem(){deleteMass();}
    public void setWrongSides(){
        Collections.sort(heads);
        // first head  is the top one(down stem)/ bottom one(up stem), which naturally goes to the right side
        int i, last, inc;
        if(isUp){i = heads.size() - 1; last = 0; inc = -1;}else{i = 0; last = heads.size() - 1; inc = 1;}
        Head ph = heads.get(i); // previous head
        ph.wrongSide = false;
        while (i != last){
            i += inc;
            Head nh = heads.get(i);
            nh.wrongSide = (ph.staff == nh.staff && Math.abs(nh.line - ph.line) <= 1) && !ph.wrongSide;
            // if on same staff and distance <= 1 then see if previous one is on the wrong side
            ph = nh;
        }
    }
}