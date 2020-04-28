package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

/**
 * Label associated with a node.
 * 
 * Must implement Comparable to be used in a BinaryHeap
 * 
 * @author Nathan Smaniotto.
 */
public class Label implements Comparable<Label> {
	
	// sommet associé à ce label
	protected Node currentNode;
	
	// vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	private boolean marked;
	
	// valeur courante du plus court chemin depuis l'origine vers le sommet
	protected double cost;
	
	// correspond à l'arc du sommet précédent sur le chemin correspondant au plus court chemin courant
	private Arc arcFromFather;
	
	/**
     * Construct a new label.
     * 
     * @param node Node associated with this label.
     */
    public Label(Node node) {
        this.currentNode = node;
        this.marked = false;
        this.cost = Double.POSITIVE_INFINITY;
        this.arcFromFather = null;
    }

    /**
     * @return Node associated with this label.
     */
    public Node getNode() {
    	return this.currentNode;
    }

    /**
     * @param status of the label.
     */
    public void setMarked(boolean marked) {
    	this.marked = marked;
    }

    /**
     * @return boolean true when marked.
     */
    public boolean isMarked() {
    	return this.marked;
    }

    /**
     * @return boolean true when not marked.
     */
    public boolean isNotMarked() {
    	return !this.marked;
    }

    /**
     * @param cost value of the cost to this node.
     */
    public void setCost(double cost) {
    	this.cost = cost;
    }

    /**
     * @return current value of the cost to this node.
     */
    public double getCost() {
    	return this.cost;
    }

    /**
     * @param arcFromFather new value to the arc.
     */
    public void setArcFromFather(Arc arcFromFather) {
    	this.arcFromFather = arcFromFather;
    }

    /**
     * @return arc from the father.
     */
    public Arc getArcFromFather() {
    	return this.arcFromFather;
    }

    /**
     * @return current value of the total cost of this node.
     */
    public double getTotalCost() {
    	return this.getCost();
    }
    
    @Override
    public int compareTo(Label label) {
        return Double.compare(this.getTotalCost(), label.getTotalCost()) ;
    }

}