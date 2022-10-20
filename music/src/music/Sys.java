package music;

import reaction.Mass;

import java.util.ArrayList;

public class Sys extends Mass {
    public ArrayList<Staff> staffs;
    public Page page;
    public int iSys;
    public Sys.Fmt fmt;


    public Sys(){
        super("BACK");
    }

    int yTop(){return page.sysTop(iSys);}

    //---------------------------Fmt:Format--------------------//
    public static class Fmt extends ArrayList<Staff.Fmt>{
        public ArrayList<Integer> staffOffSet = new ArrayList<>();


        public int height() {
            int last = size() - 1;
            return staffOffSet.get(last) + get(last).height();

        }
    }


}
