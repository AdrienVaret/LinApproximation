package solver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.chocosolver.parser.flatzinc.ast.searches.IntSearch;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.alldifferent.PropAllDiffAC;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.search.strategy.strategy.IntStrategy;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

import graphs.Molecule;
import parser.GraphParser;

public class KekuleStructureGenerator {

	private static BufferedWriter w;
	
	private static long nbFailSum = 0;
	private static long nbFailAllD = 0;
	
	private static long nbBTsum = 0;
	private static long nbBTallD = 0;
	
	private static long nbNodeSum = 0;
	private static long nbNodesAllD = 0;
	
	private static long timeAllDiffConstraintFirstSolution;
	private static long timeSumConstraintsFirstSolution;
	
	private static long timeAllDiffConstraint;
	private static long timeSumConstraints;
	
	public static int computeKekuleStructuresAllDiffConstraint(Molecule molecule) {
	
		long begin = System.currentTimeMillis();
		
        int nbNode = molecule.getNbNodes();

        int[] nodesSet = new int[nbNode];
        int[] visitedNodes = new int[nbNode];

        int deep = 0;
        int n = 0;

        ArrayList<Integer> q = new ArrayList<Integer>();

        q.add(0);

        visitedNodes[0] = 1;

        int count = 1;


        //Récupérer l'ensemble des "atomes étoilés"
        while (n < nbNode / 2) {

            int newCount = 0;

            for (int i = 0; i < count; i++) {

                int u = q.get(0);

                if (deep % 2 == 0) {
                    nodesSet[u] = 1;
                    n++;
                }

                for (int j = 0; j < molecule.getNbNodes(); j++) {
                    if (molecule.getAdjacencyMatrix()[u][j] != 0) {

                        if (visitedNodes[j] == 0) {
                            visitedNodes[j] = 1;
                            q.add(j);
                            newCount++;
                        }

                    }
                }

                q.remove(0);
            }
            deep++;
            count = newCount;
        }
		
        /*
         * Générer le modèle
         */
        
        Model model = new Model("Kekule structures with all diff constraint");
        
        IntVar[] variables = new IntVar[molecule.getNbNodes() / 2];
        
        int indexVariable = 0;
        
        for (int i = 0 ; i < nodesSet.length ; i++) {
        	if (nodesSet[i] == 1) {
        		
        		int nbAdjacentEdges = molecule.getEdgeMatrix().get(i).size();
        		int [] domain = new int [nbAdjacentEdges];
        		
        		int indexDomain = 0;
        		
        		for (int j = 0 ; j < molecule.getNbNodes() ; j++) {
        			if (molecule.getAdjacencyMatrix()[i][j] != 0) {
        				domain[indexDomain] = j;
        				indexDomain ++;
        			}
        		}
        		
        		variables[indexVariable] = model.intVar("x_" + i, domain);
        		
        		indexVariable ++;
        	}
        }
        
        //model.allDifferent(variables).post();
        //model.allDifferent(variables, "Options.C_ALLDIFFERENT_AC").post();
        model.allDifferent(variables, "AC_REGIN").post();     
        
        //model.getSolver().setSearch(new IntStrategy(variables, new FirstFail(model), new IntDomainMin()));
        //model.getSolver().setSearch(Search.intVarSearch(variables));
        //model.getSolver().setSearch(Search.domOverWDegSearch(variables));
        //model.getSolver().setSearch(Search.activityBasedSearch(variables));
        model.getSolver().setSearch(Search.defaultSearch(model));
       
        Solver solver = model.getSolver();
        
        int nbStructures = 0;
        
        while (solver.solve()) {
        	Solution solution = new Solution(model);
            solution.record();
            nbStructures ++;
            
            if (nbStructures == 1) {
            	long end = System.currentTimeMillis();
            	timeAllDiffConstraintFirstSolution = end - begin;
            	System.out.println(solver.toOneLineString());
            }
        }
        
        long end = System.currentTimeMillis();
        timeAllDiffConstraint = end - begin;
        
        nbBTallD = solver.getBackTrackCount();
        nbNodesAllD = solver.getNodeCount();
        nbFailAllD = solver.getFailCount();
        
        //System.out.println(solver.toOneLineString());
        
        System.out.println("PROF : " + solver.getMaxDepth() + "  VARS : " + model.getNbVars());
        
        return nbStructures;
	}
	
	/*
	 * SOMME
	 */
	
	public static int computeKekuleStructuresSumConstraints(Molecule molecule) {
		
		long begin = System.currentTimeMillis();
		
		Model model = new Model("Kekule structures with sum constraints");
		
		BoolVar[] edges = new BoolVar[molecule.getNbEdges()];

        for (int i = 0; i < molecule.getNbEdges(); i++) {
            edges[i] = model.boolVar("edge " + (i));
        }
        
        for (int i = 0; i < molecule.getEdgeMatrix().size(); i++) {
            int nbAdjacentEdges = molecule.getEdgeMatrix().get(i).size();
            BoolVar[] adjacentEdges = new BoolVar[nbAdjacentEdges];

            for (int j = 0; j < nbAdjacentEdges; j++) {
                adjacentEdges[j] = edges[molecule.getEdgeMatrix().get(i).get(j)];
            }

            model.sum(adjacentEdges, "=", 1).post();
        }

        model.getSolver().setSearch(new IntStrategy(edges, new FirstFail(model), new IntDomainMin()));
        Solver solver = model.getSolver();
        
        int nbStructures = 0;
        
        while (solver.solve()) {
        	Solution solution = new Solution(model);
            solution.record();
            nbStructures ++;
            
            if (nbStructures == 1) {
            	long end = System.currentTimeMillis();
            	timeSumConstraintsFirstSolution = end - begin;
            }
            
            System.out.println(solver.getDecisionPath());
            
        }
        
        nbBTsum = solver.getBackTrackCount();
        nbNodeSum = solver.getNodeCount();
        nbFailSum = solver.getFailCount();
        
        long end = System.currentTimeMillis();
        timeSumConstraints = end - begin;
        
        System.out.println(solver.toOneLineString());
        
        System.out.println("PROF : " + solver.getMaxDepth() + "  VARS : " + model.getNbVars());
        
        
        
        return nbStructures;
	}
	
	public static void displayUsage() {
		System.err.println("ERROR: Invalids args.");
		System.err.println("USAGE: java -jar ${EXEC_NAME} ${FILENAME} ${FILENAME_WO_COORDS}");
	}
	
	public static void main(String [] args) throws IOException {

		//if (args.length < 2) {
		//	displayUsage();
		//	System.exit(1);
		//}
		
		//String filename = args[0];
		//String filenameNoCoords = args[1];
	
		//String filename = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/4_crowns.graph_coord";
		//String filenameNoCoords = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/4_crowns.graph";
		
		String filename = "rectangle_4_3.graph_coord";
		String filenameNoCoords = "rectangle_4_3.graph";
		
		w = new BufferedWriter(new FileWriter(new File("kekule_structures"), true));
		
		Molecule molecule = GraphParser.parseUndirectedGraph(filename, filenameNoCoords, true);
		
		int nbStructuresSumConstraints = computeKekuleStructuresSumConstraints(molecule);
		int nbStructuresAllDiffConstraint = computeKekuleStructuresAllDiffConstraint(molecule);
		
		//filename nbStructures nbDecision nbBacktrack firstSolutionTime allSolutionsTime	
		w.write(filenameNoCoords + " " + nbStructuresSumConstraints + " "    + nbNodeSum   + " " + nbBTsum  + " " + nbFailSum  + " " + timeSumConstraintsFirstSolution + " " + timeSumConstraints + "\n");
		w.write(filenameNoCoords + " " + nbStructuresAllDiffConstraint + " " + nbNodesAllD + " " + nbBTallD + " " + nbFailAllD + " " + timeAllDiffConstraintFirstSolution + " " + timeAllDiffConstraint + "\n");
		w.write("\n");
		
		w.close();
	}
}
