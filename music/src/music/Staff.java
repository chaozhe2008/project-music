package music;

import reaction.Gesture;
import reaction.Mass;
import reaction.Reaction;

import java.awt.*;

import static sandbox.Music.PAGE;

public class Staff extends Mass {  //"E":Creating a new system, "E-W":Adding a staff to an existing system
    public Sys sys;
    public int iStaff;
    public Staff.Fmt fmt;

    public Staff(Sys sys, int iStaff, Staff.Fmt fmt){
        super("BACK");
        this.sys = sys;
        this.iStaff = iStaff;
        this.fmt = fmt;

        addReaction(new Reaction("S-S"){ // Draw a BarLine

            public int bid(Gesture gest) {
                int x = gest.vs.xM(), y1 = gest.vs.yL(), y2 = gest.vs.yH();
                if (x < PAGE.margins.left || x > PAGE.margins.right + UC.BarToMarginSnap){
                    return UC.noBid;
                }
                System.out.println("Top" + y1 + " " + Staff.this.yTop());
                int d = Math.abs(y1 - Staff.this.yTop()) + Math.abs(y2 - Staff.this.yBot());
                return (d < 50) ? d + UC.BarToMarginSnap : UC.noBid;
            }

            public void act(Gesture gest){
                int x = gest.vs.xM();
                if(Math.abs(x - PAGE.margins.right) < UC.BarToMarginSnap){
                    x = PAGE.margins.right;
                }
                new Bar(Staff.this.sys, x);

            }
        });
    }

    public int sysOff(){return sys.fmt.staffOffSet.get(iStaff);}
    public int yTop(){return sys.yTop() + sysOff();}
    public int yBot(){return yTop() + fmt.height();}

    //-------------------------------Format----------------//sys.format call staff.format to draw, staff does not draw itself
    public static class Fmt{
        public int nLines = 5;
        public int H = UC.defaultStaffSpace;

        public Integer height() {return 2 * H * (nLines - 1);}
        public void showAt(Graphics g, int y){
            int LEFT = PAGE.margins.left, RIGHT = PAGE.margins.right;
            for(int i = 0; i < nLines; i ++){g.drawLine(LEFT, y + 2 * H * i, RIGHT, y + 2 * H * i);
            }
        }
    }

}
