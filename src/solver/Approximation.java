package solver;

import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.chocosolver.graphsolver.GraphModel;
import org.chocosolver.graphsolver.variables.UndirectedGraphVar;
import org.chocosolver.util.objects.graphs.UndirectedGraph;
import org.chocosolver.util.objects.setDataStructures.SetType;
import graphs.Node;
import graphs.NodeSameLine;
import graphs.Molecule;
import parser.GraphParser;
import perfect_matching.PerfectMatchingSolver;
import utils.EdgeSet;
import utils.Interval;
import utils.SubMolecule;
import utils.Utils;
import org.chocosolver.solver.search.strategy.strategy.*;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;

public class Approximation {
	
	private static final int MAX_CYCLE_SIZE = 4;
	
	public static int [][][] energies = new int[127][11][4];
	public static int [][] circuits;		
	public static int [] circuitCount;
	
	public static ArrayList<Integer> getVerticalNeighborhood(Molecule molecule, int hexagon, int [][] edgesCorrespondances, boolean left) {
		
		ArrayList<Integer> edges = new ArrayList<Integer>();
		
		int [] hexagonVertices = molecule.getHexagons()[hexagon];
		
		int x, y1, y2;
		
		if (left) {
			edges.add(edgesCorrespondances[hexagonVertices[4]][hexagonVertices[5]]);
			System.out.println("adding (" + hexagonVertices[4] + ", " + hexagonVertices[5] + ")(" + edgesCorrespondances[hexagonVertices[4]][hexagonVertices[5]] + ")");
			x = molecule.getNodesRefs()[hexagonVertices[4]].getX();
			y1 = molecule.getNodesRefs()[hexagonVertices[4]].getY();
			y2 = molecule.getNodesRefs()[hexagonVertices[5]].getY();
		}
		
		else {
			edges.add(edgesCorrespondances[hexagonVertices[1]][hexagonVertices[2]]);
			System.out.println("adding (" + hexagonVertices[1] + ", " + hexagonVertices[2] + ")(" + edgesCorrespondances[hexagonVertices[1]][hexagonVertices[2]] + ")");
			x = molecule.getNodesRefs()[hexagonVertices[1]].getX();
			y1 = molecule.getNodesRefs()[hexagonVertices[1]].getY();
			y2 = molecule.getNodesRefs()[hexagonVertices[2]].getY();
		}
		
		
		
		for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
			
			for (int j = (i + 1) ; j < molecule.getNbNodes() ; j++) {
				
				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					
					Node u = molecule.getNodesRefs()[i];
					Node v = molecule.getNodesRefs()[j];
					
					if (left) {
						if ((u.getX() == v.getX()) && (u.getX() < x) && 
								((u.getY() == y1 && v.getY() == y2) ||  (u.getY() == y2 && v.getY() == y1))) {
									
								edges.add(edgesCorrespondances[i][j]);
								System.out.println("adding (" + i + ", " + j + ")(" + edgesCorrespondances[i][j] + ")");
							}
					}
					
					else {
						if ((u.getX() == v.getX()) && (u.getX() > x) && 
							((u.getY() == y1 && v.getY() == y2) ||  (u.getY() == y2 && v.getY() == y1))) {
								
							edges.add(edgesCorrespondances[i][j]);
							System.out.println("adding (" + i + ", " + j + ")(" + edgesCorrespondances[i][j] + ")");
						}
					}
					
					
				}
			}
		}
		
		System.out.println("");
		
		return edges;
	}
	
	public static void computeCyclesRelatedToOneHexagon(Molecule molecule, int hexagon) {
		
		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];
		
		GraphModel model = new GraphModel("Cycles");

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
		int [][] ME = new int [molecule.getNbNodes()][molecule.getNbNodes()];
			
		int index = 0;
		for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
			for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {

				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					boolEdges[index] = model.boolVar("(" + i + "--" + j + ")");
					model.edgeChanneling(g, boolEdges[index], i, j).post();
					firstVertices[index] = i;
					secondVertices[index] = j;
					ME[i][j] = index;
					ME[j][i] = index;
					index ++;
				}
			}
		}
			
		ArrayList<Integer> leftVerticalEdges = getVerticalNeighborhood(molecule, hexagon, ME, true);
		ArrayList<Integer> rightVerticalEdges = getVerticalNeighborhood(molecule, hexagon, ME, false);
		
		BoolVar[] left = new BoolVar[leftVerticalEdges.size()];
		BoolVar[] right = new BoolVar[rightVerticalEdges.size()];
		
		for (int i = 0 ; i < left.length ; i++) {
			left[i] = boolEdges[leftVerticalEdges.get(i)];
		}
		
		for (int i = 0 ; i < right.length ; i++) {
			right[i] = boolEdges[rightVerticalEdges.get(i)];
		}
		
		model.sum(left, ">=", 1).post();
		model.sum(right, ">=", 1).post();
		
		model.minDegrees(g, 2).post();
		model.maxDegrees(g, 2).post();
		model.connected(g).post();
			
		
		model.or(
			model.and(model.arithm(model.nbNodes(g), "=", 6), model.sum(boolEdges, "=", 6)),
			model.and(model.arithm(model.nbNodes(g), "=", 10), model.sum(boolEdges, "=", 10)),
			model.and(model.arithm(model.nbNodes(g), "=", 14), model.sum(boolEdges, "=", 14)),
			model.and(model.arithm(model.nbNodes(g), "=", 18), model.sum(boolEdges, "=", 18)),
			model.and(model.arithm(model.nbNodes(g), "=", 22), model.sum(boolEdges, "=", 22)),
			model.and(model.arithm(model.nbNodes(g), "=", 26), model.sum(boolEdges, "=", 26))
		).post();
			
		model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
		Solver solver = model.getSolver();

		Solution solution;
			
		while(solver.solve()){
			solution = new Solution(model);
			solution.record();
					
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			for (int i = 0 ; i < boolEdges.length ; i++) {
				if (solution.getIntVal(boolEdges[i]) == 1) {
					cycle.add(firstVertices[i]);
					cycle.add(secondVertices[i]);
				}
			}
			
			EdgeSet verticalEdges = computeStraightEdges(molecule, cycle);
			ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, verticalEdges);
			Collections.sort(intervals);
			int cycleConfiguration = Utils.identifyCycle(molecule, cycle, intervals);
			
			if (cycleConfiguration != -1) {
				List<Integer> hexagons = getHexagons(molecule, cycle, intervals);
				System.out.println(hexagons);
			}
			
			
			//treatCycle(molecule, cycle);
		}
		
		//displayResults();
	}
	
	public static void computeResonanceEnergy(Molecule molecule) {

		energies = Utils.initEnergies();
		circuits = new int[molecule.getNbHexagons()][MAX_CYCLE_SIZE];		
		circuitCount = new int[energies.length];
		
		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];
		
		GraphModel model = new GraphModel("Cycles");

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
			
		model.minDegrees(g, 2).post();
		model.maxDegrees(g, 2).post();
		model.connected(g).post();
			
		
		model.or(
			model.and(model.arithm(model.nbNodes(g), "=", 6), model.sum(boolEdges, "=", 6)),
			model.and(model.arithm(model.nbNodes(g), "=", 10), model.sum(boolEdges, "=", 10)),
			model.and(model.arithm(model.nbNodes(g), "=", 14), model.sum(boolEdges, "=", 14)),
			model.and(model.arithm(model.nbNodes(g), "=", 18), model.sum(boolEdges, "=", 18)),
			model.and(model.arithm(model.nbNodes(g), "=", 22), model.sum(boolEdges, "=", 22)),
			model.and(model.arithm(model.nbNodes(g), "=", 26), model.sum(boolEdges, "=", 26))
		).post();
			
		model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
		Solver solver = model.getSolver();

		Solution solution;
			
		while(solver.solve()){
			solution = new Solution(model);
			solution.record();
					
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			for (int i = 0 ; i < boolEdges.length ; i++) {
				if (solution.getIntVal(boolEdges[i]) == 1) {
					cycle.add(firstVertices[i]);
					cycle.add(secondVertices[i]);
				}
			}
			
			treatCycle(molecule, cycle);
		}
		
		displayResults();
	}
	
	public static void displayResults() {
		
		System.out.println("");
		System.out.println("LOCAL ENERGY");
		
		int [] globalEnergy = new int[MAX_CYCLE_SIZE];
		
		for (int i = 0 ; i < circuits.length ; i++) {
			System.out.print("H" + i + " : ");
			
			for (int j = 0 ; j < MAX_CYCLE_SIZE ; j++) {
				
				System.out.print(circuits[i][j] + " ");
				globalEnergy[j] += circuits[i][j];
			}
			
			System.out.println("");
		}
		
		System.out.println("");
		System.out.print("GLOBAL ENERGY : ");
		
		for (int i = 0 ; i < globalEnergy.length ; i++)
			System.out.print(globalEnergy[i] + " ");
		System.out.println("");
		
	}
	
	public static EdgeSet computeStraightEdges(Molecule molecule, ArrayList<Integer> cycle) {
		
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
	
	public static List<Interval> computeIntervals(Molecule molecule, ArrayList<Integer> cycle, EdgeSet edges){
		
		List<Interval> intervals = new ArrayList<Interval>();
	
		int [] edgesOK = new int [edges.size()];
		
		for (int i = 0 ; i < edges.size() ; i ++) {
			if (edgesOK[i] == 0) {
				edgesOK[i] = 1;
				Node u1 = edges.getFirstVertices().get(i);
				Node v1 = edges.getSecondVertices().get(i);
				
				int y1 = Math.min(u1.getY(), v1.getY());
				int y2 = Math.max(u1.getY(), v1.getY());
				
				List<NodeSameLine> sameLineNodes = new ArrayList<NodeSameLine>();
				
				for (int j = (i+1) ; j < edges.size() ; j++) {
					if (edgesOK[j] == 0) {
						Node u2 = edges.getFirstVertices().get(j);
						Node v2 = edges.getSecondVertices().get(j);
						
						int y3 = Math.min(u2.getY(), v2.getY());
						int y4 = Math.max(u2.getY(), v2.getY());
						
						if (y1 == y3 && y2 == y4) {
							edgesOK[j] = 1;
							sameLineNodes.add(new NodeSameLine(j, u2.getX()));
						}
					}
				}
				
				sameLineNodes.add(new NodeSameLine(i, u1.getX()));
				Collections.sort(sameLineNodes);
									
				for (int j = 0 ; j < sameLineNodes.size() ; j += 2) {
						
					NodeSameLine nsl1 = sameLineNodes.get(j);
					NodeSameLine nsl2 = sameLineNodes.get(j+1);
						
					Node n1 = edges.getFirstVertices().get(nsl1.getIndex());
					Node n2 = edges.getSecondVertices().get(nsl1.getIndex());
					Node n3 = edges.getFirstVertices().get(nsl2.getIndex());
					Node n4 = edges.getSecondVertices().get(nsl2.getIndex());
						
					intervals.add(new Interval(n1, n2, n3, n4));
				}
				
			}
		}
		
		return intervals;
	}
	
	public static boolean hasEdge(Molecule molecule, int [] vertices, int vertex) {
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (molecule.getAdjacencyMatrix()[vertex][u] == 1 && vertices[u] == 0)
				return true;
		}
		
		return false;
	}
	
	public static SubMolecule substractCycleAndInterior(Molecule molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
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
	
	public static boolean intervalsOnSameLine(Interval i1, Interval i2) {
		return (i1.y1() == i2.y1() && i1.y2() == i2.y2());
	}

	public static List<Integer> getHexagons(Molecule molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals){
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

	
	public static String displayCycle(ArrayList<Integer> cycle) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Integer i : cycle) {
			if (!list.contains(i)) list.add(i);
		}
		Collections.sort(list);
		return list.toString();
	}

	public static void treatCycle(Molecule molecule, ArrayList<Integer> cycle) {
		
		EdgeSet verticalEdges = computeStraightEdges(molecule, cycle);
		ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, verticalEdges);
		Collections.sort(intervals);
		int cycleConfiguration = Utils.identifyCycle(molecule, cycle, intervals);
		
		if (cycleConfiguration != -1) {
			
			circuitCount[cycleConfiguration] ++;
			
			ArrayList<Integer> hexagons = (ArrayList<Integer>) getHexagons(molecule, cycle, intervals);
			SubMolecule subMolecule = substractCycleAndInterior(molecule, cycle, intervals);
			int nbPerfectMatchings = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
			
			int [][] energiesCycle = energies[cycleConfiguration];
			
			for (int idHexagon = 0 ; idHexagon < hexagons.size() ; idHexagon++) {
				
				int hexagon = hexagons.get(idHexagon);
				for (int size = 0 ; size < 4 ; size ++) {
					
					if (energiesCycle[idHexagon][size] != 0)
						circuits[hexagon][size] += energiesCycle[idHexagon][size] * nbPerfectMatchings;			
				}
			}
			
		}
	}
	 
	public static void main(String[] args) throws IOException {
		
		//String path = "/Users/adrien/molecules/molecules_CP/molecule_5.graph_coord";
		
		String path = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph_coord";
		//String pathNoCoords = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph";
		
		/*
		if (args.length < 1) {
			System.err.println("ERROR: invalid argument(s)");
			System.err.println("USAGE: java -jar Approximation.jar ${input_file_name}");
			System.exit(1);
		}
		
		String path = args[0];
		*/
		
		/*
		System.out.println("computing " + path + "\n");
		
		Molecule molecule = GraphParser.parseUndirectedGraph(path, null, false);
		
		long begin = System.currentTimeMillis();
		computeResonanceEnergy(molecule); 
		long end = System.currentTimeMillis();
		long time = end - begin;
		System.out.println("time : " + time + " ms.");
		*/
		
		Molecule molecule = GraphParser.parseUndirectedGraph(path, null, false);
		computeCyclesRelatedToOneHexagon(molecule, 3);
		
	}
}
