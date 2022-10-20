package music;

import reaction.Mass;

import java.awt.*;

import static sandbox.Music.PAGE;

public class Staff extends Mass {  //"E":Creating a new system, "E-W":Adding a staff to an exsting system
    public Sys sys;
    public int iStaff;
    public Staff.Fmt fmt;

    public Staff(){
        super("BACK");
    }

    //-------------------------------Format---------------------//
    public static class Fmt{
        public int nLines = 6;
        public int H = 8;

        public Integer height() {return 2 * H * (nLines - 1);}
        public void showAt(Graphics g, int y){
            int LEFT = PAGE.margins.left, RIGHT = PAGE.margins.right;
            for(int i = 0; i < nLines; i ++){g.drawLine(LEFT, y + 2 * H * i, RIGHT, y + 2 * H * i);
            }
        }
    }

}
