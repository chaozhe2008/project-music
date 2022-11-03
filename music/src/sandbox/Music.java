package sandbox;

import graphics.G;
import graphics.Window;
import music.Glyph;
import music.Page;
import music.Sys;
import music.UC;
import reaction.Gesture;
import reaction.Ink;
import reaction.Layer;
import reaction.Reaction;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Music extends Window {
    static {
        new Layer("BACK");
        new Layer("NOTE");
        new Layer("FORE");
    }
    public static Page PAGE;
    public static void main(String[] args){(PANEL = new Music()).launch();}
    public Music(){
        super("Music Editor", UC.initialWindowWidth, UC.initialWindowHeight);
        Reaction.initialReactions.addReaction(new Reaction("E-E"){

            public int bid(Gesture gest) {
                return 10;
            }

            public void act(Gesture gest) {
                int y = gest.vs.yM();
                Sys.Fmt sysFmt = new Sys.Fmt();
                PAGE  = new Page(sysFmt);
                PAGE.margins.top = y;
                PAGE.addNewSys();
                PAGE.addNewStaff(0);
                this.disable();
            }
        });
    }
    public void paintComponent(Graphics g){
        G.clear(g);
        g.setColor(Color.blue);
        g.drawString("Music", 100, 30);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
//        if(PAGE != null){
//            Glyph.CLEF_G.showAt(g, 8, 100, PAGE.margins.top + 4 * 8);
//            Glyph.HEAD_HALF.showAt(g, 8, 200, PAGE.margins.top + 4 * 8);
//            Glyph.HEAD_Q.showAt(g, 8, 300, PAGE.margins.top + 4 * 8);
//            Glyph.HEAD_W.showAt(g, 8, 400, PAGE.margins.top + 4 * 8);
//        }
    }

    public void mousePressed(MouseEvent me){Gesture.AREA.dn(me.getX(), me.getY());repaint();}
    public void mouseDragged(MouseEvent me){Gesture.AREA.drag(me.getX(), me.getY());repaint();}
    public void mouseReleased(MouseEvent me){Gesture.AREA.up(me.getX(), me.getY());repaint();}
}
