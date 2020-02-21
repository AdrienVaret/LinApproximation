package solver;

import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.chocosolver.graphsolver.GraphModel;
import org.chocosolver.graphsolver.variables.UndirectedGraphVar;
import org.chocosolver.util.objects.graphs.UndirectedGraph;
import org.chocosolver.util.objects.setDataStructures.SetType;
import graphs.Node;
import graphs.UndirGraph;
import parser.GraphParser;
import perfect_matching.PerfectMatchingSolver;
import utils.Couple;
import utils.EdgeSet;
import utils.Interval;
import utils.SubMolecule;
import utils.Utils;
import org.chocosolver.solver.search.strategy.strategy.*;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;

public class Approximation {

	private static final int MAX_CYCLE_SIZE = 4;
	private static long computeCyclesTime;
	public static int [][] circuitsToSubstract;

	public static boolean isRedundantCycle(int index) {
		if (index == 10 || index > 15)
			return true;
		return false;
	}
	
	public static List<ArrayList<Integer>> computeRedundantCycles(UndirGraph molecule){

		ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();

		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];

		for (int n = 4 ; n <= 6 ; n++) {

			GraphModel model = new GraphModel("Cycles");

			int size = 4 * n + 2;

			UndirectedGraph GLB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);
			UndirectedGraph GUB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);

			for (int i = 0; i < molecule.getNbNodes(); i++) {
				GUB.addNode(i);

				for (int j = (i + 1); j < molecule.getNbNodes(); j++) {
					if (molecule.getAdjacencyMatrix()[i][j] == 1) {
						GUB.addEdge(i, j);
					}
				}
			}

			UndirectedGraphVar g = model.graphVar("g", GLB, GUB);

			BoolVar[] boolEdges = new BoolVar[molecule.getNbEdges()];
			BoolVar[] boolStraightEdges = new BoolVar[molecule.getNbStraightEdges()];

			int index = 0;
			int indexStraightEdges = 0;
			for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
				for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {

					if (molecule.getAdjacencyMatrix()[i][j] == 1) {
						boolEdges[index] = model.boolVar("(" + i + "--" + j + ")");
						model.edgeChanneling(g, boolEdges[index], i, j).post();
						firstVertices[index] = i;
						secondVertices[index] = j;
						index ++;

						Node u1 = molecule.getNodeRef(i);
						Node u2 = molecule.getNodeRef(j);

						if (u1.getX() == u2.getX()) {
							boolStraightEdges[indexStraightEdges] = model.boolVar("(" + i + "--" + j + ")");
							model.edgeChanneling(g, boolStraightEdges[indexStraightEdges], i, j).post();
							indexStraightEdges ++;
						}
					}
				}
			}

			model.cycle(g).post();
			model.arithm(model.nbNodes(g), "=", size).post();
			model.sum(boolStraightEdges, ">=", 6).post();
			model.sum(boolStraightEdges, "<=", 10).post();

			model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
			Solver solver = model.getSolver();

			while(solver.solve()){
				Solution solution = new Solution(model);
				solution.record();

				ArrayList<Integer> cycle = new ArrayList<Integer>();

				for (int i = 0 ; i < boolEdges.length ; i++) {
					if (solution.getIntVal(boolEdges[i]) == 1) {
						cycle.add(firstVertices[i]);
						cycle.add(secondVertices[i]);
					}
				}

				cycles.add(cycle);
			}
		}

		return cycles;
	}

	public static List<ArrayList<Integer>> computeCycles(UndirGraph molecule){
		
		long begin = System.currentTimeMillis();
		
		ArrayList<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();
		
		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];
		
		for (int n = 1 ; n <= 4 ; n++) {
			
			int size = 4 * n + 2;
			
			GraphModel model = new GraphModel("Cycles");
			
			UndirectedGraph GLB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);
	        UndirectedGraph GUB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false); 
	        
	        for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
	        	GUB.addNode(i);
	        	
	        	for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {
	        		if (molecule.getAdjacencyMatrix()[i][j] == 1) {
	        			GUB.addEdge(i, j);
	        		}
	        	}
	        }
	        
	        UndirectedGraphVar g = model.graphVar("g", GLB, GUB);
	        
	        BoolVar[] boolEdges = new BoolVar[molecule.getNbEdges()];
	        
	        int index = 0;
	        for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
	        	for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {
	        		
	        		if (molecule.getAdjacencyMatrix()[i][j] == 1) {
	        			boolEdges[index] = model.boolVar("(" + i + "--" + j + ")");
	                    model.edgeChanneling(g, boolEdges[index], i, j).post();
	                    firstVertices[index] = i;
	                    secondVertices[index] = j;
	                    index ++;
	        		}
	        	}
	        }
	        
	        model.cycle(g).post();
	        model.arithm(model.nbNodes(g), "=", size).post();
	    /*    
	        model.or(model.arithm(model.nbNodes(g), "=", 6),
	        		 model.arithm(model.nbNodes(g), "=", 10),
	        		 model.arithm(model.nbNodes(g), "=", 14),
	        		 model.arithm(model.nbNodes(g), "=", 18),
	        		 model.arithm(model.nbNodes(g), "=", 22),
	        		 model.arithm(model.nbNodes(g), "=", 26)).post();
	    */    
	        model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
			Solver solver = model.getSolver();
			
			while(solver.solve()) {
				Solution solution = new Solution(model);
				solution.record();
				
				ArrayList<Integer> cycle = new ArrayList<Integer>();
				
				for (int i = 0 ; i < boolEdges.length ; i++) {
					if (solution.getIntVal(boolEdges[i]) == 1) {
						cycle.add(firstVertices[i]);
						cycle.add(secondVertices[i]);
					}
				}
				
				cycles.add(cycle);
			}
		}
		
		long end = System.currentTimeMillis();
		computeCyclesTime = end - begin;
		
		return cycles;
	}
	
	public static EdgeSet computeStraightEdges(UndirGraph molecule, ArrayList<Integer> cycle) {
		
		List<Node> firstVertices = new ArrayList<Node>();
		List<Node> secondVertices = new ArrayList<Node>();
		
		for (int i = 0 ; i < cycle.size() - 1 ; i += 2) {
			int uIndex = cycle.get(i);
			int vIndex = cycle.get(i + 1);
			
			Node u = molecule.getNodesRefs()[uIndex];
			Node v = molecule.getNodesRefs()[vIndex];
			
			if (u.getX() == v.getX()) {
				firstVertices.add(u);
				secondVertices.add(v);
			}
		}
		
		return new EdgeSet(firstVertices, secondVertices);
	}
	
	public static List<Interval> computeIntervals(UndirGraph molecule, ArrayList<Integer> cycle, EdgeSet edges){
		
		List<Interval> intervals = new ArrayList<Interval>();
	
		int [] edgesOK = new int [edges.size()];
		
		for (int i = 0 ; i < edges.size() ; i ++) {
			if (edgesOK[i] == 0) {
				edgesOK[i] = 1;
				Node u1 = edges.getFirstVertices().get(i);
				Node v1 = edges.getSecondVertices().get(i);
				
				int y1 = Math.min(u1.getY(), v1.getY());
				int y2 = Math.max(u1.getY(), v1.getY());
				
				List<Integer> sameLineNodes = new ArrayList<Integer>();
				
				for (int j = (i+1) ; j < edges.size() ; j++) {
					if (edgesOK[j] == 0) {
						Node u2 = edges.getFirstVertices().get(j);
						Node v2 = edges.getSecondVertices().get(j);
						
						int y3 = Math.min(u2.getY(), v2.getY());
						int y4 = Math.max(u2.getY(), v2.getY());
						
						if (y1 == y3 && y2 == y4) {
							edgesOK[j] = 1;
							sameLineNodes.add(j);
						}
					}
				}
				
				if (sameLineNodes.size() == 1) {
					intervals.add(new Interval(edges.getFirstVertices().get(i), edges.getSecondVertices().get(i), 
								edges.getFirstVertices().get(sameLineNodes.get(0)), edges.getFirstVertices().get(sameLineNodes.get(0))));
				}
				
				else {
					
					int minIndex1 = i;
					int minIndex2 = -1;
					
					int minX1 = edges.getFirstVertices().get(i).getX();
					int minX2 = Integer.MAX_VALUE;
					
					for (Integer j : sameLineNodes) {
						int x = edges.getFirstVertices().get(j).getX();
						
						if (x < minX1) {
							minX2 = minX1;
							minX1 = x;
							minIndex2 = minIndex1;
							minIndex1 = j;
						}
						
						else if (x < minX2 && x > minX1) {
							minX2 = x;
							minIndex2 = j;
						}
					}
					
					Interval interval1 = new Interval(edges.getFirstVertices().get(minIndex1), edges.getSecondVertices().get(minIndex1), 
							                          edges.getFirstVertices().get(minIndex2), edges.getSecondVertices().get(minIndex2));
					
					intervals.add(interval1);
					
					int index1 = -1, index2 = -1;
					
					for (Integer j : sameLineNodes) {
						if (j != minIndex1 && j != minIndex2) {
							if (index1 == -1)
								index1 = j;
							else
								index2 = j;
						}
					}
					
					Interval interval2 = new Interval(edges.getFirstVertices().get(index1), edges.getSecondVertices().get(index1), 
							                          edges.getFirstVertices().get(index2), edges.getSecondVertices().get(index2));
					
					intervals.add(interval2);
					
				}
			}
		}
		
		return intervals;
	}
	
	public static boolean hasEdge(UndirGraph molecule, int [] vertices, int vertex) {
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (molecule.getAdjacencyMatrix()[vertex][u] == 1 && vertices[u] == 0)
				return true;
		}
		
		return false;
	}
	
	public static SubMolecule substractCycle(UndirGraph molecule, ArrayList<Integer> cycle){
		
		int [][] newGraph = new int [molecule.getNbNodes()][molecule.getNbNodes()];
		int [] vertices = new int [molecule.getNbNodes()];
				
		int [] subGraphVertices = new int[molecule.getNbNodes()];
		
		for (Integer u : cycle)
			vertices[u] = 1;
		
		int subGraphNbNodes = 0;
		
		int nbEdges = 0;
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (vertices[u] == 0) {
				for (int v = (u+1) ; v < molecule.getNbNodes() ; v++) {
					if (vertices[v] == 0) {
						newGraph[u][v] = molecule.getAdjacencyMatrix()[u][v];
						newGraph[v][u] = molecule.getAdjacencyMatrix()[v][u];
						
						if (molecule.getAdjacencyMatrix()[u][v] == 1)
							nbEdges ++;
						
						if (subGraphVertices[u] == 0) {
								subGraphVertices[u] = 1;
								subGraphNbNodes ++;
						}
						
						if (subGraphVertices[v] == 0) {
								subGraphVertices[v] = 1;
								subGraphNbNodes ++;
						}
					}
				}
			}
		}
		
		return new SubMolecule(subGraphNbNodes, nbEdges, molecule.getNbNodes(), subGraphVertices, newGraph);
	}
	
	public static boolean intervalsOnSameLine(Interval i1, Interval i2) {
		return (i1.y1() == i2.y1() && i1.y2() == i2.y2());
	}

	public static List<Integer> getHexagons(UndirGraph molecule, ArrayList<Interval> cycle, ArrayList<Interval> intervals){
		List<Integer> hexagons = new ArrayList<Integer>();



		for (Interval interval : intervals){

			int [] hexagonsCount = new int [molecule.getNbHexagons()];

			for (int x = interval.x1() ; x <= interval.x2() ; x ++){
				int u1 = molecule.getCoords().get(x, interval.y1());
				int u2 = molecule.getCoords().get(x, interval.y2());

				for (Integer hexagon : molecule.getHexagonsVertices().get(u1)) {
					hexagonsCount[hexagon] ++;
					if (hexagonsCount[hexagon] == 4)
						hexagons.add(hexagon);
				}

				for (Integer hexagon : molecule.getHexagonsVertices().get(u2)){
					hexagonsCount[hexagon] ++;
					if (hexagonsCount[hexagon] == 4)
						hexagons.add(hexagon);
				}
			}
		}

		return hexagons;
	}

	public static int identifyCircuitsUnion(UndirGraph molecule, ArrayList<Integer> cycle) {
		
		EdgeSet edges = computeStraightEdges(molecule, cycle);
		List<Interval> intervals = computeIntervals(molecule, cycle, edges);
		Collections.sort(intervals);

		int size = cycle.size() / 2;
		
		
		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;
		Interval i4 = null;
		
		for (int i = 0 ; i < intervals.size() ; i++) {
			if (i == 0)
				i0 = intervals.get(i);
			if (i == 1)
				i1 = intervals.get(i);
			if (i == 2)
				i2 = intervals.get(i);
			if (i == 3)
				i3 = intervals.get(i);
			if (i == 4)
				i4 = intervals.get(i);
		}
		
		if (intervals.size() == 3 && size == 18 && 
			intervals.get(0).size() == 4 && 
			intervals.get(1).size() == 6 && 
			intervals.get(2).size() == 4 &&
			i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1) {
			
			return 10;
		}
		
		else if (intervals.size() == 3 && size == 22 && 
			intervals.get(0).size() == 4 && 
			intervals.get(1).size() == 8 && 
			intervals.get(2).size() == 4) {
				
			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3) ||
				(i0.x1() == i2.x1() && i1.x1() == i2.x2() - 3))
					return 18;
		}
		
		else if (intervals.size() == 4 && size == 22 && 
			intervals.get(0).size() == 4 && 
			intervals.get(1).size() == 6 && 
			intervals.get(2).size() == 4 &&
			intervals.get(3).size() == 2 ) {
					
			
			if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i1.x2()) ||
				(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i2.x1() - 1))
					return 17;
		}
		
		else if (intervals.size() == 4 && size == 22 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 4 ) {
					
				if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i3.x1() == i2.x1() + 1) ||
					(i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1 && i3.x2() == i1.x2()))
						return 19;
		}
		
		else if (intervals.size() == 4 && size == 22 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 4 ) {
						
			
				if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i1.x2()) ||
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i1.x1()))
						return 20;
		}
		
		else if (intervals.size() == 3 && size == 22 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 6 ) {
						
				if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1) ||
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1))
						return 22;
		}
		
		else if (intervals.size() == 3 && size == 22 && 
				intervals.get(0).size() == 6 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 ) {
						
			
				if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1) ||
					(i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)) 
						return 21;
		}
	
		else if (intervals.size() == 4 && size == 22 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 4 ) {
						
				if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() - 1 && i3.x2() == i1.x2()) ||
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() + 1 && i3.x1() == i1.x1()))
						return 23;
		}
		
		else if (intervals.size() == 4 && size == 22 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 2 ) {
					
				if ((i1.x2() == i3.x2() && i2.x2() == i3.x2() + 1 && i0.x2() == i1.x2() - 1) ||
					(i1.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i0.x1() == i1.x1() + 1))
						return 24;
		}
		
		else if (intervals.size() == 4 && size == 22 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 4 ) {
						
			
				if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1) || 
					(i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1))
						return 25;
		}
		
		// TODO: check if correct
		else if (intervals.size() == 3 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 10 && 
				intervals.get(2).size() == 4 ) {
		
				//Disambiguation
			
				int distance1 = Math.abs(intervals.get(0).x1() - intervals.get(1).x1());
				int distance2 = Math.abs(intervals.get(0).x2() - intervals.get(1).x2());
				
				if ((distance1 == 5 && distance2 == 1) || (distance1 == 1 && distance2 == 5))
					return 26;
				
				else if (distance1 == 3 && distance2 == 3)
					return 29;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 2 &&
				intervals.get(4).size() == 2) {
						
				if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i3.x2() - 1 && i4.x2() == i3.x2() + 1) || 
					(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i3.x1() + 1 && i4.x1() == i3.x1() - 1))
						return 27;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 2 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 4) {
				
				if ((i1.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i1.x1() + 1 && i0.x1() == i1.x1() - 1) || 
					(i1.x2() == i3.x2() && i2.x2() == i4.x2() && i2.x2() == i1.x2() - 1 && i0.x2() == i1.x2() + 1))
						return 50;
		}
		
		//TODO: check if correct
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 4 &&
				intervals.get(4).size() == 2) {
						
				if (Math.abs(i0.x1() - i4.x2()) == 6 || Math.abs(i0.x2() - i4.x2()) == 6)
					return 28;
				
				else if (i0.x1() == i4.x1() && i0.x2() == i4.x2())
					return 53;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 2 &&
				intervals.get(4).size() == 2 &&
				intervalsOnSameLine(intervals.get(3), intervals.get(4))) {
						
				if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
					return 30;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 2 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 4 &&
				intervalsOnSameLine(intervals.get(0), intervals.get(1))) {
						
				if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
					return 31;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 4 ) {
				
				if ((i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 3) || 
					(i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 3))
						return 32;

				if (Math.abs(i0.x1() - i3.x2()) == 8 || Math.abs(i0.x2() - i3.x1()) == 8)
					return 52;
					
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 2 ) {
			
				if ((i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 3) ||
					(i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3))
						return 33;
			
				if (Math.abs(i0.x1() - i3.x2()) == 8 || Math.abs(i0.x2() - i3.x1()) == 8)
					return 54;
					
		}
		
		
		// Coronène + 3
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 2) {
						
				if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x1() == i4.x1() && i2.x1() == i3.x1() + 1) || 
					(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x2() == i4.x2() && i2.x2() == i3.x2() - 1))
						return 34;

				if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i4.x2() == i3.x2() - 1 && i2.x2() == i1.x2() + 1) ||
					(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1))
						return 55;
		}
		
		if (intervals.size() == 5 && size == 26 && 
				i0.size() == 2 && i1.size() == 6 && i2.size() == 6 && i3.size() == 4 && i4.size() == 2) {
			
			if ((i0.x2() == i2.x2() && i3.x2() == i2.x2() - 1 && i1.x1() == i3.x1() && i2.x1() == i4.x1()) ||
				(i0.x1() == i2.x1() && i3.x1() == i2.x1() + 1 && i1.x2() == i3.x2() && i2.x2() == i4.x2()))
					return 35;

			 if ((i1.x1() == i3.x1() && i2.x1() == i4.x1() && i3.x1() == i2.x1() + 1 && i0.x1() == i1.x1() + 1) ||
				 (i1.x2() == i3.x2() && i2.x2() == i4.x2() && i3.x2() == i2.x2() - 1 && i0.x2() == i1.x2() - 1))
			 		return 57;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 2) {
						
			
				if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i3.x2()) ||
				    (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i3.x1() + 1))
						return 54;			
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 2 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 4 &&
				intervalsOnSameLine(intervals.get(0), intervals.get(1))) {
					
				if ((i0.x1() == i3.x1() && i1.x2() == i3.x2() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1) || 
					(i0.x1() == i3.x1() && i1.x2() == i3.x2() && i2.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1))
						return 36;			
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 2 &&
				intervals.get(4).size() == 2 &&
				intervalsOnSameLine(intervals.get(3), intervals.get(4))) {
					
				if ((i0.x2() == i2.x2() && i1.x1() == i2.x1() + 1 && i1.x1() == i3.x1() && i1.x2() == i4.x2()) || 
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i1.x1() == i3.x1() && i1.x2() == i4.x2()))
						return 37;			
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 6) {
						
				
				if ((i1.x2() == i3.x2() && i0.x1() == i1.x1() - 1 && i2.x1() == i3.x1() - 1) || 
					(i1.x1() == i3.x1() && i0.x2() == i1.x2() + 1 && i2.x2() == i3.x2() + 1))
						return 38;			
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 6 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 2) {

				if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3) ||
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 3))
						return 40;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 4) {

				if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1) ||
					(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1))
						return 41;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 4) {
					
				if ((i0.x2() == i1.x2() - 1 && i1.x2() == i3.x2() && i2.x2() == i3.x2() + 1) || 
					(i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1))
						return 39;			
		}
		
		//Coronene + 4
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 2) {

				if ((i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1 && i4.x2() == i2.x2() - 4) ||
					(i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1 && i4.x1() == i2.x1() + 4))
						return 43;
		}
		
		else if (intervals.size() == 3 && size == 26 &&
				intervals.get(0).size() == 6 &&
				intervals.get(1).size() == 10 &&
				intervals.get(2).size() == 6) {

					if ((i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() - 3) ||
						(i0.x1() == i1.x1() + 1 && i2.x2() == i1.x1() + 3))
							return 44;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 6 &&
				intervals.get(3).size() == 4 &&
				intervals.get(4).size() == 4) {

				if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1 && i1.x2() == i3.x2() && i4.x2() == i3.x2() - 1) ||
					(i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i1.x1() == i3.x1() && i4.x1() == i3.x1() + 1))
						return 45;
		}
			
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 2 && 
				intervals.get(1).size() == 2 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 6 &&
				intervals.get(4).size() == 4 &&
				intervalsOnSameLine(intervals.get(0), intervals.get(1))) {

				if (i0.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i4.x1() == i3.x1() + 1 &&
					i1.x2() == i3.x2() && i2.x2() == i3.x2() + 1 && i4.x2() == i3.x2() - 1)
						return 46;
		}
		
		else if (intervals.size() == 5 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 6 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 2 &&
				intervals.get(4).size() == 2 &&
				intervalsOnSameLine(intervals.get(3), intervals.get(4))) {

				if (i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() - 1 &&
					i2.x2() == i4.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() + 1)
						return 47;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 4 && 
				intervals.get(1).size() == 4 && 
				intervals.get(2).size() == 8 &&
				intervals.get(3).size() == 6) {

				if ((i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1) ||
					(i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() + 1))
						return 48;
		}
		
		else if (intervals.size() == 4 && size == 26 && 
				intervals.get(0).size() == 6 && 
				intervals.get(1).size() == 8 && 
				intervals.get(2).size() == 4 &&
				intervals.get(3).size() == 4) {

				if ((i0.x1() == i2.x1() && i3.x1() == i2.x1() + 1 && i1.x1() == i2.x1() - 1) ||
					(i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1))
						return 49;
		}

		else if (intervals.size() == 5 && size == 26 &&
		         i0.size() == 2 &&
				 i1.size() == 4 &&
				 i2.size() == 6 &&
				 i3.size() == 4 &&
				 i4.size() == 4) {

				if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 1) ||
					(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1))
						return 56;
		}

		if (intervals.size() == 5 && size == 26 &&
		    i0.size() == 4 &&
			i1.size() == 4 &&
			i2.size() == 6 &&
			i3.size() == 4 &&
			i4.size() == 2) {

			if ((i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3) ||
				(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3))
					return 58;
		}

		if (intervals.size() == 3 && size == 26 &&
		    i0.size() == 4 &&
			i1.size() == 10 &&
			i2.size() == 6){

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 3) ||
				(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3))
					return 59;
		}

		if (intervals.size() == 3 && size == 26 &&
		    i0.size() == 6 &&
			i1.size() == 8 &&
			i2.size() == 6) {

			if ((i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1) ||
				(i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1))
					return 60;
		}

		if (intervals.size() == 3 && size == 26 &&
		    i0.size() == 6 &&
			i1.size() == 10 &&
			i2.size() == 4) {

			if ((i0.x1() == i2.x1() && i1.x1() == i0.x1() - 3) ||
				(i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3))
					return 61;
		}

		return -1;
	}
	
	public static int getCycleIndex(UndirGraph molecule, ArrayList<Integer> cycle) {
		
		int n = ((cycle.size() / 2) - 2)/4;
		
		if (n == 1)
			return 0;
		
		else if (n == 2)
			return 1;
		
		else {
			EdgeSet edges = computeStraightEdges(molecule, cycle);
			
			if (edges.size() == 2) {
				
				if (n == 3)
					return 2;
				
				if (n == 4)
					return 5;
			}
			
			
			else if (edges.size() == 4) {
				
				if (n == 3) {
					
					List<Interval> intervals = computeIntervals(molecule, cycle, edges);
					boolean condition = false;
					
					for (Interval interval : intervals) {
						if (interval.size() == 2) {
							condition = true;
							break;
						}
					}
					
					if (condition)
						return 3;
					
					else
						return 4;
				
				}
				
				else if (n == 4) {
					
					List<Interval> intervals = computeIntervals(molecule, cycle, edges);
					boolean condition = false;
					
					for (Interval interval : intervals) {
						if (interval.size() == 2) {
							condition = true;
							break;
						}
					}
					
					if (condition)
						return 6;
					
					else
						return 9;
				}
			}
			
			
			else if (edges.size() == 6) {
				
				List<Interval> intervals = computeIntervals(molecule, cycle, edges);
				
				List<Interval> intervalsSize2 = new ArrayList<Interval>();
				List<Interval> intervalsSize4 = new ArrayList<Interval>();
				List<Interval> intervalsSize6 = new ArrayList<Interval>();
				
				for (Interval interval : intervals) {
					if (interval.size() == 2) intervalsSize2.add(interval);
					if (interval.size() == 4) intervalsSize4.add(interval);
					if (interval.size() == 6) intervalsSize6.add(interval);
				}
				
				if (intervalsSize2.size() == 2 && intervalsSize4.size() == 1 && intervalsSize6.size() == 0) {
					
					Interval i2_1 = intervalsSize2.get(0);
					Interval i2_2 = intervalsSize2.get(1);
					Interval i4 = intervalsSize4.get(0);
					
					
					if (i2_1.x1() == i2_2.x1() && i2_1.x2() == i2_2.x2()) {
						
						if (i4.x1() < i2_1.x1() && i4.x2() > i2_1.x2())
							return 4;
						
						if ((i4.x1() < i2_1.x1() && i4.x2() < i2_1.x2()) ||
							(i4.x1() > i2_1.x1() && i4.x2() > i2_1.x2()))
							return 7;
							
					}
					
					else if (i2_1.x1() != i2_2.x1() && i2_1.x1() != i2_2.x2()) {
						
						if (i2_1.y1() == i2_2.y1() && i2_1.y2() == i2_2.y2())
							return 11;
						
						if ((i2_1.x1() == i4.x1() || i2_2.x1() == i4.x1()) ||
							(i2_1.x2() == i4.x2() || i2_2.x2() == i4.x2()))
							return 11;
						
						if ((i4.y1() > Math.min(i2_1.y1(), i2_2.y1()) && i4.y1() < Math.max(i2_1.y1(), i2_2.y1())) &&
							(i4.y2() > Math.min(i2_1.y2(), i2_2.y2()) && i4.y2() < Math.max(i2_1.y2(), i2_2.y2())))
							return 13;
						
						else 
							return 6;
					}
				}
				
				if (intervalsSize2.size() == 1 && intervalsSize4.size() == 1 && intervalsSize6.size() == 1) {
					return 10;
				}
				
				if (intervalsSize2.size() == 0 && intervalsSize4.size() == 3 && intervalsSize6.size() == 0) {
					return 10;
				}
				
				if (intervalsSize2.size() == 2 && intervalsSize4.size() == 0 && intervalsSize6.size() == 1) {
					
					Interval i2_1 = intervalsSize2.get(0);
					Interval i2_2 = intervalsSize2.get(1);
					
					if (i2_1.x1() == i2_2.x1() && i2_1.x2() == i2_2.x2()) 	
						return 8;
					else
						return 14;
				}
				
				if (intervalsSize2.size() == 1 && intervalsSize4.size() == 2 && intervalsSize6.size() == 0) {
					
					Interval i2 = intervalsSize2.get(0);
					Interval i4_1 = intervalsSize4.get(0);
					Interval i4_2 = intervalsSize4.get(1);
					
					if ((i2.y1() > Math.min(i4_1.y1(), i4_2.y1()) && i2.y1() < Math.max(i4_1.y1(), i4_2.y1())) &&
						(i2.y2() > Math.min(i4_1.y2(), i4_2.y2()) && i2.y2() < Math.max(i4_1.y2(), i4_2.y2()))) 
						return 14;
					
					
					else if ((i4_1.x1() == i2.x1() || i4_1.x2() == i2.x2()) ||
						(i4_2.x1() == i2.x1() || i4_2.x2() == i2.x2()))
						return 8;
					
					else
						return 9;
						
				}
				
				if (intervalsSize2.size() == 0 && intervalsSize4.size() == 2 && intervalsSize6.size() == 1)
					return 12;
				
				if (intervalsSize2.size() == 3 && intervalsSize4.size() == 0 && intervalsSize6.size() == 0) {
					
					Interval i2_1 = intervalsSize2.get(0);
					Interval i2_2 = intervalsSize2.get(1);
					Interval i2_3 = intervalsSize2.get(2);
					
					if ((i2_1.x1() == i2_2.x1() && i2_1.x2() == i2_2.x2()) || 
						(i2_1.x1() == i2_3.x1() && i2_1.x2() == i2_3.x2()) ||
						(i2_2.x1() == i2_3.x1() && i2_3.x2() == i2_3.x2())) 
						return 3;
					
					else
						return 2;
				}
					
			}
			
			else if (edges.size() == 8) {
				
				List<Interval> intervals = computeIntervals(molecule, cycle, edges);
				
				Interval i2_1 = intervals.get(0);
				Interval i2_2 = intervals.get(1);
				Interval i2_3 = intervals.get(2);
				Interval i2_4 = intervals.get(3);
				
				if ((i2_1.x1() == i2_2.x1() && i2_1.x2() == i2_2.x2()) || 
					(i2_1.x1() == i2_3.x1() && i2_1.x2() == i2_3.x2()) ||
					(i2_1.x1() == i2_4.x1() && i2_1.x2() == i2_4.x2()) ||
					(i2_2.x1() == i2_3.x1() && i2_2.x2() == i2_3.x2()) ||
					(i2_2.x1() == i2_4.x1() && i2_2.x2() == i2_4.x2()) ||
					(i2_3.x1() == i2_4.x1() && i2_3.x2() == i2_4.x2()))
					return 13;
				
				else
					return 5;
			}
		}
		
		return -1;
	}
	
	public static String displayCycle(ArrayList<Integer> cycle) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Integer i : cycle) {
			if (!list.contains(i)) list.add(i);
		}
		Collections.sort(list);
		return list.toString();
	}

	public static void computeEnergy(UndirGraph molecule) {
		
		int [] cyclesConfigurations = new int [] {2, 2, 2, 1, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0};
		int [] circuits = new int [MAX_CYCLE_SIZE];
		
		Utils.initSubstractTable();
		
		List<ArrayList<Integer>> cycles = computeCycles(molecule);
		
		for (ArrayList<Integer> cycle : cycles) {
			
			
			int cycleConfiguration = getCycleIndex(molecule, cycle);
			
			//TODO: changer la condition
			if (cycleConfiguration <= 10) {
				
				SubMolecule subMolecule = substractCycle(molecule, cycle);
				int nbPerfectMatching = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
				int size = ((cycle.size() / 2) - 2)/4;
				
				circuits[size - 1] += (nbPerfectMatching * cyclesConfigurations[cycleConfiguration]);
			}
		}

		List<ArrayList<Integer>> redundantCycles = computeRedundantCycles(molecule);

		System.out.println("size = " + redundantCycles.size());
		
		int j = 0;
		
		for (ArrayList<Integer> cycle : redundantCycles){

			if (j == 7)
				System.out.print("");
			else 
				j++;
			
			int configuration = identifyCircuitsUnion(molecule, cycle);
			if (configuration != -1) {
				int [] toSubstract = circuitsToSubstract[configuration];
				circuits[2] -= toSubstract[0];
				circuits[3] -= toSubstract[1];
			}
		}

		for (int i = 0 ; i < circuits.length ; i++){
			System.out.print(circuits[i] + " ");
		}
		System.out.println("");
	}
	
	public static void displayTime() {
		System.out.println("computeCycles() : " + computeCyclesTime + " ms.");
	}
	 
	public static void main(String[] args) {
		String path = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph_coord";
		String pathNoCoords = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph";
		
		UndirGraph molecule = GraphParser.parseUndirectedGraph(path, pathNoCoords);

		computeEnergy(molecule);

	}
}
