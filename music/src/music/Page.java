package music;

import reaction.Mass;

import java.awt.*;
import java.util.ArrayList;

public class Page extends Mass {
    public Margins margins = new Margins();
    public Sys.Fmt sysFmt;
    public int sysGap;
    public ArrayList<Sys> sysList = new ArrayList<>();

    public Page(Sys.Fmt sysFmt){
        super("BACK");
        this.sysFmt = sysFmt;
    }

    int sysTop(int iSys){return margins.top + iSys * (sysFmt.height() + sysGap);}

    public void show(Graphics g){
        }

    //-------------------------Margins-----------------------//
    public static class Margins{
        private static int M = 50;
        public int top = M, left = M, bot = UC.initialWindowHeight - M, right = UC.initialWindowWidth - M;
    }

}
