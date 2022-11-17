package music;

import reaction.Gesture;
import reaction.Mass;
import reaction.Reaction;

import java.awt.*;
import java.util.ArrayList;

import static sandbox.Music.PAGE;

public class Sys extends Mass {
    public ArrayList<Staff> staffs = new ArrayList<>();
    public Page page;
    public int iSys;
    public Sys.Fmt fmt;
    public Time.List times;
    public Stem.List stems = new Stem.List();

    public Sys(Page page, int iSys, Sys.Fmt fmt){
        super("BACK");
        this.page = page;
        this.iSys = iSys;
        this.fmt = fmt;
        times = new Time.List(this);
        for (int i = 0; i < fmt.size(); i++){
            addStaff(new Staff(this, i, fmt.get(i)));
        }

        addReaction(new Reaction("E-E") {
            public int bid(Gesture gest) {
                int x1 = gest.vs.xL(), y1 = gest.vs.yL();
                int x2 = gest.vs.xH(), y2 = gest.vs.yH();
                if (stems.fastReject(y1, y2)){return UC.noBid;}
                ArrayList<Stem> temp = stems.allIntersectors(x1, y1, x2, y2);
                if (temp.size() < 2){return UC.noBid;}
                System.out.println("Crossed " + temp.size() + "stems");

                Beam beam = temp.get(0).beam;
                //rejection routine
                for(Stem s: temp){
                    if(s.beam != beam){if (s.beam != beam){return UC.noBid;}}
                }
                System.out.println("All stem s share beam");
                if (beam == null && temp.size() != 2){return UC.noBid;}
                if (beam == null && (temp.get(0).nFlag != 0 || temp.get(1).nFlag != 0)){return UC.noBid;}
                return 50;
            }
            public void act(Gesture gest) {
                int x1 = gest.vs.xL(), y1 = gest.vs.yL();
                int x2 = gest.vs.xH(), y2 = gest.vs.yH();
                ArrayList<Stem> temp = stems.allIntersectors(x1, y1, x2, y2);
                Beam beam = temp.get(0).beam;
                if(beam == null){
                    new Beam(temp.get(0), temp.get(1));
                }else{
                    for (Stem s:temp){s.incFlag();}
                }
            }
        });
    }

    public void addStaff(Staff s){staffs.add(s);}

    public int yTop(){return page.sysTop(iSys);}

    public int yBot(){return staffs.get(staffs.size() - 1).yBot();}
    public Time getTime(int x){return times.getTime(x);}

    public void show(Graphics g){
        int y = yTop(), x = PAGE.margins.left;
        g.drawLine(x,y,x,y + fmt.height());
    }

    //---------------------------Fmt:Format--------------------//
    public static class Fmt extends ArrayList<Staff.Fmt>{
        public ArrayList<Integer> staffOffSet = new ArrayList<>();


        public int height() {
            int last = size() - 1;
            return staffOffSet.get(last) + get(last).height();

        }
        public void showAt(Graphics g, int y){
            for(int i = 0; i < size(); i++){
                get(i).showAt(g,y + staffOffSet.get(i));
            }
        }
    }


}
