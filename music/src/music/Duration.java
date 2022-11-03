package music;

import reaction.Mass;

import java.awt.*;

public abstract class Duration extends Mass {
    public int nFlag = 0, nDot = 0;
    public Duration(){
        super("NOTE");
    }
    public abstract void show(Graphics g);
    public void incFlag(){if(nFlag < 4){nFlag++;}}  // 加 flag 最多4个 0 flag: 1/4 notes
    public void decFlag(){if(nFlag > -2){nFlag--;}}  // 去 flag 最少-2
    public void cycleDot(){nDot++; if(nDot > 3){nDot = 0;}}
}
