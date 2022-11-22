package reaction;

import graphics.G;
import music.I;

import java.awt.*;

public abstract class Mass extends Reaction.List implements I.Show{
    public Layer layer;
    public int hashcode = G.rnd(1000000000); //redefine equality. (default "equals()" is for layer interface, and deleteMass does not work
    //hashcode is same not necessarily means strings are same, but if hashcode is different, stirngs are different
    public boolean equals(Object obj){return this == obj;}
    public int hashCode(){return hashcode;}
    public Mass(String layerName){
        this.layer = Layer.byName.get(layerName);
        if(layer != null){
            layer.add(this);
        }else{
            System.out.println("Bad layer name " + layerName);
        }
    }

    public void deleteMass(){
        clearAll();
        layer.remove(this);
    }

    public void show(Graphics g){

    }
}
