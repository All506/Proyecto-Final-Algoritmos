/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphs;

import list.ListException;
import queue.LinkedQueue;
import queue.QueueException;
import stack.LinkedStack;
import stack.StackException;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */

public class AdjacencyMatrixGraph implements Graph {

    private int n;
    private Vertex vertexList[];
    private Object adjacencyMatrix[][];
    private int count; //vertex counter

    //Para los recorridos dfs() y bfs()
    private LinkedStack stack;
    private LinkedQueue queue;

    //Constructor
    public AdjacencyMatrixGraph(int n) {
        if (n <= 0) {
            System.exit(1);
        }
        this.n = n;
        this.count = 0;
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = new Object[n][n];
        initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < vertexList.length; i++) {
            for (int j = 0; j < vertexList.length; j++) {
                adjacencyMatrix[i][j] = 0;
            }

        }
    }

    @Override
    public int size() throws ListException {
        return count; //vertexes counter
    }

    @Override
    public void clear() {
        this.vertexList = new Vertex[n];
        this.queue = new LinkedQueue();
        this.vertexList = new Vertex[n];
        this.count = 0;
        initMatrix();
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("El grafo esta vacio");
        }
        for (int i = 0; i < count; i++) {
            if (Util.Utility.equals(vertexList[i].data, element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("El grafo esta vacio");
        }
        return !Util.Utility.equals(adjacencyMatrix[indexOf(a)][indexOf(b)], 0);
    }

    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if (count >= vertexList.length) {
            throw new GraphException("El grafo de matriz de adyacencia esta lleno");
        }
        vertexList[count++] = new Vertex(element);

    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException { //Agregar arista
        if (!containsVertex(a) || !containsVertex(b)) {
            throw new GraphException("El grafo de matriz de adyacencia no posee estos objetos: " + a + ", " + b);
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = "edge";
        adjacencyMatrix[indexOf(b)][indexOf(a)] = "edge";
    }

    private int indexOf(Object data) {
        for (int i = 0; i < count; i++) {
            if (Util.Utility.equals(vertexList[i].data, data)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if (!containsEdge(a, b)) { //Solo se puede agregar peso si hay una arista entre a y b
            throw new GraphException("No hay una arista entre " + a + " y " + b);
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException { //Debe eliminar aristas y vertices que inciden con el
        int index = indexOf(element);
        if (index != -1) { //El elemento existe en el grafo

            for (int i = index; i < count - 1; i++) {
                vertexList[i] = vertexList[i + 1]; //Mueve todo lo de la derecha a la izquierda en el arreglo de vertices

                //Se mueven las filas, una posicion hacia arriba
                for (int j = 0; j < count; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
                }
            }

            //Se mueve las columnas, una posicion hacia la izquierda
            for (int i = 0; i < count; i++) {
                for (int j = index; j < count - 1; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
                }
            }
            count--;
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if (!containsEdge(a, b)) {
            throw new GraphException("No hay una arista entre " + a + " y " + b);
        }
        int i = indexOf(a);
        int j = indexOf(b);

        if (i != 1 && j != 1) { //Los dos vertices existen
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0;
        }
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false); //marca todos los vertices como no vistados // inicia en el vertice 0
        String info = vertexList[0].data + ", ";
        vertexList[0].setVisited(true); // lo marca
        stack.clear();
        stack.push(0); //lo apila
        while (!stack.isEmpty()) {
// obtiene un vertice adyacente no visitado,
//el que esta en el tope de la pila
            int index = adjacentVertexNotVisited((int) stack.top());
            if (index == -1) // no lo encontro
            {
                stack.pop();
            } else {
                vertexList[index].setVisited(true); // lo marca
                info += vertexList[index].data + ", "; //lo muestra
                stack.push(index); //inserta la posicion
            }
        }
        return info;
    }

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false); //marca todos los vertices como no visitados // inicia en el vertice 0
        String info = vertexList[0].data + ", ";
        vertexList[0].setVisited(true); // lo marca
        queue.clear();
        queue.enQueue(0); // encola el elemento
        int v2;
        while (!queue.isEmpty()) {
            int v1 = (int) queue.deQueue(); // remueve elvertice de la cola // hasta que no tenga vecinos sin visitar

            while ((v2 = adjacentVertexNotVisited(v1)) != -1) {
                // obtiene uno
                vertexList[v2].setVisited(true); // lo marca
                info += vertexList[v2].data + ", "; //lo muestra
                queue.enQueue(v2); // lo encola
            }
        }
        return info;

    }

    //PARA EL DFS
    private void setVisited(boolean value) {
        for (int i = 0; i < count; i++) {
            vertexList[i].setVisited(value);
        }
    }

    private int adjacentVertexNotVisited(int i) {
        for (int j = 0; j < count; j++) {
            //Si es diferente de 0 hay algo guardado
            if (!adjacencyMatrix[i][j].equals(0) && !vertexList[j].isVisited()) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String result = "ADJACENCY MATRIX GRAPH CONTENT \n";
        for (int i = 0; i < count; i++) {
            result += "\n Vertex en " + i + " es " + vertexList[i].data;
        }
        result += "\n";

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (!adjacencyMatrix[i][j].equals(0)) {
                    result += "\nHay una arista entre el vertice " + i + " y el vertice " + j;
                }
            }
        }

        result += "\n" + table();
        return result;
    }

    public String table() {
        String result = "ADJACENTY MATRIX GRAPH TABLE\n";
        for (int i = 0; i < count; i++) {
            result += "\t" + vertexList[i].data;
        }
        result += "\n";
        for (int i = 0; i < count; i++) {
            result += vertexList[i].data;
            for (int j = 0; j < count; j++) {
                result += "\t" + adjacencyMatrix[i][j];

            }
            result += "\n";
        }

        return result;

    }
    
    @Override
    public Vertex getVertexByIndex(int index){
    
        return vertexList[index];
    
    }
}
