/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphs;

import list.SinglyLinkedList;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class Vertex {
    public Object data;
    public SinglyLinkedList edgesList; //funciona unicamente para AdjacencyListGraph
    private boolean visited; //para los recorridas dfs(), bfs()
    
    //Constructor
    public Vertex(Object data){
        this.data = data;
        this.visited = false;
        this.edgesList = new SinglyLinkedList();
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return "Vertex{" + "data=" + data + '}';
    }
    
    
    
}
