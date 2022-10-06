package reaction;

import graphics.G;
import music.UC;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Shape implements Serializable{
    public String name;
    public Prototype.List prototypes = new Prototype.List();
    public static DataBase DB = DataBase.load();
    //public Shape(String name){this.name = name;}
    //public static HashMap<String,Shape> DB = loadShapeDB();
    public static Shape DOT = DB.get("DOT");
    public Shape(String name) {this.name = name;}
    public static Collection<Shape> LIST = DB.values(); //研究Collection是什么

    public static void saveShapeDB(){DataBase.save();}
/*    public static HashMap<String, Shape> loadShapeDB(){
        String filename = UC.ShapeDBFilename;
        HashMap<String,Shape> res = new HashMap<>();
        res.put("DOT", new Shape("DOT"));
        try {
            System.out.println("Attempting DB load...");
            ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(filename));
            res = (HashMap<String, Shape>) OIS.readObject();
            System.out.println("Successful load-found" + res.keySet());
            OIS.close();
        } catch (IOException e) {
            System.out.println("load failed");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("load failed");
            System.out.println(e);
        }
        return res;
    }
    public static void saveShapeDB(){
        String filename = UC.ShapeDBFilename;
        try{
            System.out.println("save DB...");
            ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(filename));
            OOS.writeObject(DB);
            System.out.println("Saved " + filename);
            OOS.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed database save");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("Failed database save");
            System.out.println(e);
        }
    }*/
    public static Shape recognize(Ink ink) { //can return null (don't match)
        if (ink.vs.size.x < UC.dotThreshold && ink.vs.size.y < UC.dotThreshold) { //太小 认为是一个DOT
            return DOT;
        }
        Shape bestMatch = null;
        int bestSoFar = UC.noMatchDist;
        for(Shape s: LIST){
            int d = s.prototypes.bestDist(ink.norm);
            if (d < bestSoFar){
                bestMatch = s;
                bestSoFar = d;
            }
        }
        return bestMatch;
    }


    //-----------------------------Prototype--------------------------//
    public static class Prototype extends Ink.Norm implements Serializable{
        public int nBlend = 1;
        public void blend(Ink.Norm norm){  // not recursive call, same name, different number/type of arguments
            blend(norm, nBlend++);
        }
        //------------------------------List----------------------------//
        public static class List extends ArrayList<Prototype> {
            public static Prototype bestMatch; // side effect of bestDist
            private static int m = 10, w = 60;
            private static G.VS showBox = new G.VS(m, m, w, w);
            public int bestDist(Ink.Norm norm){
                bestMatch = null;
                int bestSoFar = UC.noMatchDist;
                for(Prototype p: this){    //keep updating best Match
                    int d = p.dist(norm);
                    if(d < bestSoFar){bestMatch = p; bestSoFar = d;}
                }
                return bestSoFar;
            }
            public void train (Ink.Norm norm){
                if (bestDist(norm) < UC.noMatchDist){
                    bestMatch.blend(norm);
                }else{
                    add(new Shape.Prototype());
                }
            }
            public void show(Graphics g){
                g.setColor(Color.blue);
                for(int i = 0; i < size(); i++){
                    Prototype p = get(i);
                    int x = m + i * (m + w); //m: margin x: coordinate of next box
                    showBox.loc.set(x, m);
                    p.drawAt(g, showBox);
                    g.drawString("" + p.nBlend,x,20);
                }
            }
        }
    }
    public static class DataBase extends HashMap<String, Shape>{
        private DataBase() {
            super();  //empty hashmap
            addNewShape("DOT");
        }
        public void addNewShape(String name){put(name, new Shape(name));}
        public Shape forceGet(String name){
            if(!DB.containsKey(name)){addNewShape(name);}
            return DB.get(name);
        }
        public void train(String name, Ink.Norm norm){
            if(isLegal(name)){forceGet(name).prototypes.train(norm);}
        }
        public static boolean isLegal(String name){return !(name.equals("")) && !name.equals("DOT");} //things we dont want to train
        public static DataBase load(){
            String filename = UC.ShapeDBFilename;
            DataBase res;
            try {
                System.out.println("Attempting DB load...");
                ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(filename));
                res = (DataBase) OIS.readObject();
                System.out.println("Successful load-found" + res.keySet());
                OIS.close();
            } catch (Exception e) {
                System.out.println("load failed");
                System.out.println(e);
                res = new DataBase();
            }
            return res;
        }
        public static void save(){
            String filename = UC.ShapeDBFilename;
            try{
                System.out.println("save DB...");
                ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(filename));
                OOS.writeObject(DB);
                System.out.println("Saved " + filename);
                OOS.close();
            } catch (Exception e) {
                System.out.println("Failed database save");
                System.out.println(e);
            }

        }
    }
}
