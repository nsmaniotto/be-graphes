package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> binaryHeap = new BinaryHeap<Label>() ;
        ArrayList<Arc> arcPath = new ArrayList<Arc>() ;
        
        final Graph graph = data.getGraph();
        final List<Node> nodes = graph.getNodes();
        final int origin = data.getOrigin().getId();
        final int destination = data.getDestination().getId();
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        /* INITIALISATION */
        Label[] labelTab = new Label[nodes.size()];
        
        for(Node node: nodes) { // For every node of the graph
        	labelTab[node.getId()] = new Label(node) ;
        }
        
        labelTab[origin].setCost(0.); // Cost(s) <- 0
        
        binaryHeap.insert(labelTab[origin]); // Insert(s, heap)
        
        
        /* ITERATIONS */
        
        while(labelTab[destination].isNotMarked() && !binaryHeap.isEmpty()) { // While nodes are not marked and there are nodes to mark
        	
        	/* Finding and deleting the min label of our binary heap */
        	Label x = binaryHeap.findMin();
        	binaryHeap.deleteMin();
        	
        	/* Marking our current label */
        	x.setMarked(true);
        	labelTab[x.getNode().getId()].setMarked(true);
        	
        	// Notify all observers that a node has been marked, i.e. its final value has been set.
            notifyNodeMarked(x.getNode());
        	
        	/* For every successor of x */
        	for(Arc arcToY: x.getNode().getSuccessors()) {
        		
        		// Small test to check allowed roads...
                if(!data.isAllowed(arcToY)) {
                    continue;
                }
        			
        		Node y = arcToY.getDestination(); // get current Y
        		
        		if(labelTab[y.getId()].isNotMarked()) {
        			
        			double currentCost = labelTab[y.getId()].getCost();
        			double potentialNewCost = x.getCost() + this.data.getCost(arcToY);
        			
        			// Notify all observers that a node has been reached for the first time.
        			if(Double.isInfinite(currentCost) && Double.isFinite(potentialNewCost)) {
                        notifyNodeReached(arcToY.getDestination());
                    }
        			
        			if(potentialNewCost < currentCost) {
        				
        				/* Updates values*/
        				labelTab[y.getId()].setCost(potentialNewCost);
                        labelTab[y.getId()].setArcFromFather(arcToY);
                        
                        /* Updating our binary heap */
                        try {
                        	
                            binaryHeap.remove(labelTab[y.getId()]);
                            
                        } catch(Exception e) {
                        	
                        }

                        binaryHeap.insert(labelTab[y.getId()]);
                        
        			}
        			
        		}
	        		
        	}
        	
        }
        
        // The solution is infeasible...
        if(labelTab[destination].isNotMarked()) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
            /* Generate the path from the previous selected arcs, starting from the end of the path */
            Label currentLabel = labelTab[destination];
            Arc currentArcFromFather = currentLabel.getArcFromFather();
            
            while(currentArcFromFather != null) {
            	
            	arcPath.add(currentArcFromFather);
            	
            	currentLabel = labelTab[currentArcFromFather.getOrigin().getId()];
            	
            	currentArcFromFather = currentLabel.getArcFromFather();
            	
            }
            
            /* Reverse our list of arcs since we started from the end of the path */
            Collections.reverse(arcPath);
            
            /* Generate the path solution from our list of arcs */
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcPath));
        }
        
        return solution;
    }

}
