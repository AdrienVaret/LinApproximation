package solver;

import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.cnf.LogOp;
import org.chocosolver.solver.exception.SolverException;
import org.chocosolver.solver.variables.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
						

					
						Node u1 = molecule.getNodeRef(i);
						Node u2 = molecule.getNodeRef(j);
/*
						if (u1.getX() == u2.getX()) {
							//boolStraightEdges[indexStraightEdges] = model.boolVar("(" + i + "--" + j + ")");
							boolStraightEdges[indexStraightEdges] = boolEdges[index];
							model.edgeChanneling(g, boolStraightEdges[indexStraightEdges], i, j).post();
							indexStraightEdges ++;
						}
*/					
						index ++;
						
					}
				}
			}

			
			model.cycle(g).post();
			model.arithm(model.nbNodes(g), "=", size).post();
			//model.sum(boolEdges, "=", size);
			//model.sum(boolEdges, ">", 0);
			//model.sum(boolStraightEdges, ">=", 6).post();
			//model.sum(boolStraightEdges, "<=", 10).post();
			
			model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
			Solver solver = model.getSolver();

			Solution solution;
			
			try {
				while(solver.solve()){
					//Solution solution = new Solution(model);
					solution = new Solution(model);
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
			} catch (SolverException e) {
				e.printStackTrace();
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
								edges.getFirstVertices().get(sameLineNodes.get(0)), edges.getSecondVertices().get(sameLineNodes.get(0))));
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
	
	public static SubMolecule substractCycleAndInterior(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
		int [][] newGraph = new int [molecule.getNbNodes()][molecule.getNbNodes()];
		int [] vertices = new int [molecule.getNbNodes()];
		int [] subGraphVertices = new int[molecule.getNbNodes()];
		
		List<Integer> hexagons = getHexagons(molecule, cycle, intervals);
		
		for (Integer hexagon : hexagons) {
			int [] nodes = molecule.getHexagons()[hexagon];
			
			for (int i = 0 ; i < nodes.length ; i++)
				vertices[nodes[i]] = 1;
		}
		
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

	public static List<Integer> getHexagons(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals){
		List<Integer> hexagons = new ArrayList<Integer>();



		for (Interval interval : intervals){

			int [] hexagonsCount = new int [molecule.getNbHexagons()];

			for (int x = interval.x1() ; x <= interval.x2() ; x += 2){
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

	public static int identifyCircuit(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
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

	public static void computeEnergy(UndirGraph molecule) throws IOException {
		
		int [] cyclesConfigurations = new int [] {2, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1};
		int [] circuits = new int [MAX_CYCLE_SIZE];
		
		Utils.initSubstractTable();
		
		List<ArrayList<Integer>> cycles = computeCycles(molecule);
		
		List<ArrayList<ArrayList<Integer>>> oldMethod = new ArrayList<ArrayList<ArrayList<Integer>>>();
		List<ArrayList<ArrayList<Integer>>> newMethod = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		for (int i = 0 ; i <= 15 ; i++) {
			oldMethod.add(new ArrayList<ArrayList<Integer>>());
			newMethod.add(new ArrayList<ArrayList<Integer>>());
		}
		
		for (ArrayList<Integer> cycle : cycles) {
			
			EdgeSet edges = computeStraightEdges(molecule, cycle);
			ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, edges);
			Collections.sort(intervals);
			
			int cycleConfigurationOLD = Utils.getCycleIndex(molecule, cycle);
			
			int cycleConfiguration = Utils.identifyMinimalCycle(molecule, cycle, intervals);
			
			if (cycleConfigurationOLD != -1)
				oldMethod.get(cycleConfigurationOLD).add(cycle);
			if (cycleConfiguration != -1)
				newMethod.get(cycleConfiguration).add(cycle);
			
			if (cycleConfiguration != -1) {
				
				SubMolecule subMolecule = substractCycle(molecule, cycle);
				int nbPerfectMatching = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
				int size = ((cycle.size() / 2) - 2)/4;
				
				circuits[size - 1] += (nbPerfectMatching * cyclesConfigurations[cycleConfiguration]);
			}
		}

		List<ArrayList<Integer>> redundantCycles = computeRedundantCycles(molecule);

		List<ArrayList<ArrayList<Integer>>> redCycles = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		for (int i = 0 ; i <= 48 ; i++) 
			redCycles.add(new ArrayList<ArrayList<Integer>>());
		
		BufferedWriter w = new BufferedWriter(new FileWriter(new File("red_circuits")));
		
		for (ArrayList<Integer> cycle : redundantCycles){
			
			if (displayCycle(cycle).equals("[14, 18, 21, 23, 25, 26, 27, 30, 32, 33, 35, 37, 40, 42, 43, 44, 47, 48, 49, 50, 52, 53]"))
				System.out.print("");
			
			EdgeSet edges = computeStraightEdges(molecule, cycle);
			ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, edges);
			Collections.sort(intervals);
			
			int configuration = Utils.identifyCircuit(molecule, cycle, intervals);
			
			if (configuration != -1) {
				
				redCycles.get(configuration).add(cycle);
				
				int [] toSubstract = circuitsToSubstract[configuration];
				
				SubMolecule subMolecule = substractCycleAndInterior(molecule, cycle, intervals);
				int nbPerfectMatchings = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
				
				circuits[2] -= (toSubstract[0] * nbPerfectMatchings);
				circuits[3] -= (toSubstract[1] * nbPerfectMatchings);
				
				w.write("(" + configuration + ") : " + displayCycle(cycle) + " : " + nbPerfectMatchings + " matchings." + "\n");
			}
		}

		String l0 = "";
		String l1 = "";
		String l2 = "";
		
		System.out.println("Minimal cycles comparaisons");
		
		for (int i = 0 ; i < 16 ; i++) {
			l0 += i + "\t";
			l1 += oldMethod.get(i).size() + "\t";
			l2 += newMethod.get(i).size() + "\t";
		}
		
		System.out.println(l0);
		System.out.println(l1);
		System.out.println(l2);
		
		System.out.println("---------------------");
		
		String l3 = "";
		String l4 = "";
		
		for (int i = 0 ; i <= 48 ; i++) {
			l3 += i + "\t";
			l4 += redCycles.get(i).size() + "\t";
		}
		
		System.out.println(l3);
		System.out.println(l4);
		
		w.close();
		
		for (int i = 0 ; i < circuits.length ; i++){
			System.out.print(circuits[i] + " ");
		}
		System.out.println("");
		
	}
	
	public static void displayTime() {
		System.out.println("computeCycles() : " + computeCyclesTime + " ms.");
	}
	 
	public static void main(String[] args) throws IOException {
		String path = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph_coord";
		String pathNoCoords = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph";
		
		UndirGraph molecule = GraphParser.parseUndirectedGraph(path, pathNoCoords);

		computeEnergy(molecule);

	}
}
