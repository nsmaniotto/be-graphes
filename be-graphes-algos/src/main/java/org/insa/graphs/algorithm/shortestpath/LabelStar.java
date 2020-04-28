package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

/**
 * Label associated with a node for A-Star.
 * 
 * Must implement Comparable to be used in a BinaryHeap
 * 
 * @author Nathan Smaniotto.
 */
public class LabelStar extends Label implements Comparable<Label>{
	
	// Coût estimé à vol d'oiseau de la destination
	private double estimatedCost;

	/**
     * Construct a new label for A-star.
     * 
     * @param node Node associated with this label.
     */
	public LabelStar(Node node) {
		super(node);

		this.estimatedCost = Double.POSITIVE_INFINITY;
	}
	
	/**
     * @return current value of the estimated cost from this node.
     */
    public double getEstimatedCost() {
    	return this.estimatedCost;
    }
    
    /**
     * Set estimatedCost from this node
     *
     * @param estimatedCost
     */
    void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost ;
    }

	@Override
	public double getTotalCost() {
		return this.cost + this.estimatedCost;
	}
	
	@Override
    public int compareTo(Label label) {
        int comparison = Double.compare(this.getTotalCost(), label.getTotalCost());
        
        if(comparison == 0) {
        	comparison = Double.compare(this.getEstimatedCost(), label.getTotalCost() - label.getCost()) ;
        }
        
        return comparison;
    }

}
