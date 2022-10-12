package reaction;

import music.I;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Reaction implements I.React{
    public Shape shape;
    private static Map byShape = new Map();
    public static List initialReactions = new List();
    public Reaction(String shapeName){
        shape = Shape.DB.get(shapeName);
        if(shape == null){System.out.println("wtf-shapeDB doesn't know "+ shapeName);}
    }
    public void enable(){
        List list = byShape.getList(shape); if(!list.contains(this)){list.add(this);}
    }
    public void disable(){

    }
    //----------------------------List----------------------?//
    public static class List extends ArrayList<Reaction> {}
    //----------------------------Map------------------//
    public static class Map extends HashMap<Shape, List>{
        public List getList(Shape s){ // Always succeeds
            List res = get(s);
            if(res == null){res = new List();put(s, res);}
            return res;
        }
    }
}


















