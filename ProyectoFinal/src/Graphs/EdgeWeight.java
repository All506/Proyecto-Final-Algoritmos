/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphs;

/**
 *
 * @author Alán
 */
public class EdgeWeight implements Comparable<EdgeWeight>{

    private Object edge; //Arista
    private Object weight; //Peso
    
    public EdgeWeight(Object b, Object object) {
        this.edge = b;
        this.weight = object;
    }

    public Object getEdge() {
        return edge;
    }

    public void setEdge(Object edge) {
        this.edge = edge;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        if (weight == null) return "Edge="+edge;
        else return "Edge="+edge+",Weight="+weight;
    }

    @Override
    public int compareTo(EdgeWeight o) {
       
       if(Util.Utility.greaterT(this.weight, o.weight))
           return 1;
       else
           return -1;
    }
    
//    public String toString(boolean b) {
//    
//    return String.format("(%s -> %s, %f)", source.name, destination.name, weight);
//}
    
    
    
}
