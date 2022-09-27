package sandbox;

import graphics.Window;

public class Main {
    public static void main(String [] args){
        System.out.println("hello, do");
        Window.PANEL = new PaintInk();
        //Window.PANEL = new PaintInk();
        Window.launch();

    }
}
