package org.insa.graphs.algorithm.shortestpath;

import java.util.List;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	final ShortestPathData data = getInputData();
        final Graph graph = data.getGraph();
        final List<Node> nodes = graph.getNodes();
        double estimatedCost;
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        /* INITIALISATION */
        LabelStar[] labelTab = new LabelStar[nodes.size()];
        
        for(Node node: nodes) { // For every node of the graph
        	labelTab[node.getId()] = new LabelStar(node);
        	
        	
        	/* Calculer le coût estimé à vol d'oiseau selon le mode */
        	
        	if(data.getMode() == AbstractInputData.Mode.LENGTH) { // En distance
                estimatedCost = (double) node.getPoint().distanceTo(data.getDestination().getPoint());
            } else { // En temps
            	int speed = 70;
                //int speed = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed()); 
                estimatedCost = (double) (node.getPoint().distanceTo(data.getDestination().getPoint()) / speed * 1000.d / 3600.d);
            }
        	
        	labelTab[node.getId()].setEstimatedCost(estimatedCost);
        }
    	
    	return this.doDijkstra(data, graph, labelTab);
    }
}
