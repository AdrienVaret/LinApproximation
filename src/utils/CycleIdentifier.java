package utils;

import java.util.ArrayList;

import graphs.Molecule;
import solver.Approximation;

public class CycleIdentifier {

	public static boolean checkSize(ArrayList<Interval> intervals, int s1) {
		if (intervals.size() == 1) {
			if (intervals.get(0).size() == s1)
				return true;
		}
		return false;
	}
	
	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2) {
		if (intervals.size() == 2) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3) {
		if (intervals.size() == 3) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3, int s4) {
		if (intervals.size() == 4) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3
					&& intervals.get(3).size() == s4)
				return true;
		}
		return false;
	}

	public static boolean checkSize(ArrayList<Interval> intervals, int s1, int s2, int s3, int s4, int s5) {
		if (intervals.size() == 5) {
			if (intervals.get(0).size() == s1 && intervals.get(1).size() == s2 && intervals.get(2).size() == s3
					&& intervals.get(3).size() == s4 && intervals.get(4).size() == s5)
				return true;
		}
		return false;
	}
	
	public static int identifyCircuitV2(Molecule molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
		Interval i0 = null;
		Interval i1 = null;
		Interval i2 = null;
		Interval i3 = null;
		Interval i4 = null;

		for (int i = 0; i < intervals.size(); i++) {
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
		
		/*
		 * 0
		 */
		
		if (checkSize(intervals, 2))
			return 0;
		
		/* 
		 * 1
		 */
		
		if (checkSize(intervals, 4))
			return 1;
		
		if (checkSize(intervals, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1)
				return 2;
			
			if (i0.x2() == i1.x2() + 1)
				return 3;
		}
		
		
		/*
		 * 2
		 */
		
		if (checkSize(intervals, 6))
			return 4;
		
		if (checkSize(intervals, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1) 
				return 5;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 6;
		}
		
		/*
		 * 3
		 */
		
		if (checkSize(intervals, 4, 2)) {
			
			if (i0.x2() == i1.x2() - 1)
				return 7;
			
			if (i0.x1() == i1.x1() + 1)
				return 8;
		}
		
		if (checkSize(intervals, 2, 4)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 9;
			
			if (i0.x1() == i1.x1() - 1)
				return 10;
		}
		
		if (checkSize(intervals, 2, 2, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 11;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 12;
		}
		
		/*
		 * 4
		 */
		
		if (checkSize(intervals, 4, 4)) {
			
			if (i0.x1() == i1.x1() + 1)
				return 13;
			
			if (i0.x2() == i1.x2() -1)
				return 14;
		}
		
		if (checkSize(intervals, 2, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() -1)
				return 15;
		}
		
		/*
		 * 5
		 */
		
		if (checkSize(intervals, 8))
			return 16;
		
		if (checkSize(intervals, 2, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i2.x1() == i3.x1() - 1)
				return 17;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i2.x2() == i3.x2() + 1)
				return 18;
		}
		
		/*
		 * 6
		 */
		
		if (checkSize(intervals, 2, 6)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 19;
			
			if (i0.x1() == i1.x1() - 1)
				return 20;
			
		}
		
		if (checkSize(intervals, 6, 2)) {
		
			if (i0.x2() == i1.x2() - 1)
				return 21;
			
			if (i0.x1() == i1.x1() + 1)
				return 22;
		}
		
		if (checkSize(intervals, 2, 2, 2, 2)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1 && i1.x1() == i3.x1())
				return 23;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1 && i1.x2() == i3.x2())
				return 24;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1 && i3.x1() == i2.x1() - 1)
				return 25;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1 && i3.x2() == i2.x2() + 1)
				return 26;
		}
		
		if (checkSize(intervals, 4, 2, 2)) {
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 27;
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
				return 28;
		}
		
		if (checkSize(intervals, 2, 2, 4)) {
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 29;
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 30;
			
		}
		
		/*
		 * 7
		 */
		
		if (checkSize(intervals, 2, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 31;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 32;
		}
		
		/*
		 * 8
		 */
		
		if (checkSize(intervals, 4, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 33;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 34;
		}
		
		if (checkSize(intervals, 2, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 35;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 36;
		}
		
		if (checkSize(intervals, 2, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 37;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 38;
		}
		
		/*
		 * 9
		 */
		
		if (checkSize(intervals, 4, 6)) {
			
			if (i0.x2() == i1.x2() + 1)
				return 39;
			
			if (i0.x1() == i1.x1() - 1)
				return 40;
		}
		
		if (checkSize(intervals, 6, 4)) {
			
			if (i0.x2() == i1.x2() - 1)
				return 41;
			
			if (i0.x1() == i1.x1() + 1)
				return 42;
		}
		
		if (checkSize(intervals, 2, 2, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
				return 43;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1)
				return 44;
		}
		
		if (checkSize(intervals, 2, 4, 2, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() + 1)
				return 45;
				
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() - 1)
				return 46;
		}
		
		if (checkSize(intervals, 2, 4, 4)) {
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 47;
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 48;
		}
		
		if (checkSize(intervals, 4, 4, 2)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i2.x1() + 1)
				return 49;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i2.x2() - 1)
				return 50;
		}
		
		/*
		 * 10
		 */
		
		if (checkSize(intervals, 4, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() - 1)
				return 51;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() + 1)
				return 52;
		}
		
		if (checkSize(intervals, 2, 6, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 53;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 54;
		}
		
		if (checkSize(intervals, 4, 6, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 55;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 56;
		}
		
		/*
		 * 12
		 */
		
		if (checkSize(intervals, 4, 6, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 57;
		}
		
		/*
		 * 15
		 */
		
		if (checkSize(intervals, 6, 6)) {
			
			if (i0.x1() == i1.x1() + 1)
				return 58;
			
			if (i0.x2() == i1.x2() - 1)
				return 59;
		}
		
		if (checkSize(intervals, 4, 4, 4)) {
			
			if (i0.x1() == i1.x1() - 1 && i1.x1() == i2.x1() - 1)
				return 60;
			
			if (i0.x2() == i1.x2() + 1 && i1.x2() == i2.x2() + 1)
				return 61;
		}
		
		if (checkSize(intervals, 2, 4, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x2() == i3.x2() && i1.x1() == i0.x1() - 1)
				return 62;
			
			if (i0.x2() == i2.x2() && i1.x1() == i3.x1() && i1.x2() == i0.x2() + 1)
				return 63;
		}
		
		/*
		 * Redundant circuits
		 */
		
		/*
		 * 16
		 */
		
		if (checkSize(intervals, 4, 8, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 64;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 65;
		}
		
		/*
		 * 17
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1)
				return 66;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1)
				return 67;
		}
		
		/*
		 * 18
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1)
				return 68;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1)
				return 69;
		}
		
		/*
		 * 19
		 */
		
		if (checkSize(intervals, 4, 6, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x2() == i1.x2())
				return 70;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x1() == i1.x1())
				return 71;
		}
		
		/*
		 * 20
		 */
		
		if (checkSize(intervals, 4, 6, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i2.x2() == i1.x2() - 1)
				return 72;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1)
				return 73;
		}
		
		/*
		 * 21
		 */
		
		if (checkSize(intervals, 4, 8, 6)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 74;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 75;
		}
		
		/*
		 * 22
		 */
		
		if (checkSize(intervals, 6, 8, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 76;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 77;
		}
		
		/*
		 * 23
		 */
		
		if (checkSize(intervals, 2, 6, 6, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 78;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 79;
		}
		
		/*
		 * 24
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4)) {
			
			if(i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1)
				return 80;
			
			if(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1)
				return 81;
			
		}
		
		/*
		 * 25
		 */
		
		if (checkSize(intervals, 4, 10, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 3)
				return 82;
		}
		
		/*
		 * 26, 27
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i3.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
				return 83;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i3.x1() == i2.x1() + 1 && i4.x2() == i2.x2())
				return 84;
		}
		
		
		/*
		 * 28
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i2.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 85;
		}
		
		/*
		 * 29
		 */
		
		if (checkSize(intervals, 2, 2, 4, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 86;
		}
		
		/*
		 * 49
		 */
		
		if (checkSize(intervals, 2, 4, 8, 4)) {
			
			if (i1.x2() == i3.x2() && i2.x2() == i1.x2() + 3 && i0.x2() == i1.x2() + 1)
				return 87;
			
			if (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 3 && i0.x1() == i1.x1() - 1)
				return 88;
			
		}
		
		/*
		 * 50
		 */
		
		if (checkSize(intervals, 4, 8, 4, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 3)
				return 89;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 3)
				return 90;
		}
		
		/*
		 * 30
		 */
		
		if (checkSize(intervals, 4, 10, 6)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3)
				return 91;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3)
				return 92;
		}
		
		/*
		 * 31
		 */
		
		if (checkSize(intervals, 6, 10, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 3)
				return 93;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 3)
				return 94;
		}
		
		/*
		 * 32
		 */
		
		if (checkSize(intervals, 2, 4, 6, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
				return 95;
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
				return 96;
		}
		
		/*
		 * 33
		 */
		
		if (checkSize(intervals, 2, 4, 6, 6, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x2() == i2.x2())
				return 97;
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x1() == i2.x1())
				return 98;
		}
		
		/*
		 * 34
		 */
		
		if (checkSize(intervals, 2, 6, 6, 4, 2)) {
			
			if (i1.x1() == i3.x1() && i2.x1() == i4.x1() && i1.x1() == i2.x1() + 1 && i0.x1() == i1.x1() + 3)
				return 99;
			
			if (i1.x2() == i3.x2() && i2.x2() == i4.x2() && i1.x2() == i2.x2() - 1 && i0.x2() == i1.x2() - 3)
				return 100;
		}
		
		/*
		 * 35
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4, 2)) {
			
			if(i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i2.x2() - 1 && i4.x1() == i2.x1())
				return 101;
			
			if(i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i2.x1() + 1 && i4.x2() == i2.x2())
				return 102;
		}
		
		/*
		 * 36
		 */
		
		if (checkSize(intervals, 2, 2, 6, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 103;
			
			if (i0.x1() == i3.x1() && i2.x1() == i4.x1() && i2.x1() == i0.x1() + 1 && i1.x2() == i3.x2())
				return 104;
		}
		
		/*
		 * 37
		 */
		
		if (checkSize(intervals, 4, 6, 6, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i4.x2() && i2.x2() == i4.x2() - 1 && i3.x1() == i1.x1())
				return 105;
			 
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 106;
		}
		
		/*
		 * 38
		 */
		
		if (checkSize(intervals, 2, 4, 8, 6)) {
			
			if (i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1 && i0.x2() == i1.x2() - 3)
				return 107;
			
			if (i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1 && i0.x1() == i1.x1() + 3)
				return 108;
		}
		
		/*
		 * 39
		 */
		
		if (checkSize(intervals, 6, 8, 4, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 3)
				return 109;
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 3)
				return 110;
		}
		
		/*
		 * 40
		 */
		
		if (checkSize(intervals, 4, 8, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 111;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 112;
		}
		
		/*
		 * 41
		 */
		
		if (checkSize(intervals, 4, 4, 8, 4)) {
			
			if (i1.x1() == i3.x1() && i0.x1() == i1.x1() + 1 && i2.x1() == i3.x1() - 1)
				return 113;
			
			if (i1.x2() == i3.x2() && i0.x2() == i1.x2() - 1 && i2.x2() == i3.x2() + 1)
				return 114;
		}
		
		/*
		 * 42
		 */
		
		if (checkSize(intervals, 6, 10, 6)) {
			
			if (i0.x2() == i1.x2() - 1 && i2.x2() == i1.x2() - 3)
				return 115;
			
			if (i0.x1() == i1.x1() + 1 && i2.x1() == i1.x1() + 3)
				return 116;
		}
		
		/*
		 * 43
		 */
		
		if (checkSize(intervals, 4, 4, 6, 4, 4)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i3.x2() && i1.x2() == i0.x2() - 1 && i4.x2() == i3.x2() - 1)
				return 117;
			
			if (i0.x1() == i2.x1() && i1.x1() == i3.x1() && i1.x1() == i0.x1() + 1 && i4.x1() == i3.x1() + 1)
				return 118;
		}
		
		/*
		 * 44
		 */
		
		if (checkSize(intervals, 2, 6, 6, 6, 2)) {
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i3.x2() == i2.x2() - 1 && i4.x2() == i3.x2() - 3)
				return 119;
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i3.x1() == i2.x1() + 1 && i4.x1() == i3.x1() + 3)
				return 120;
		}
		
		/*
		 * 45
		 */
		
		if (checkSize(intervals, 2, 2, 8, 6, 4) && Approximation.intervalsOnSameLine(i0, i1)) {
			
			if (i0.x1() == i3.x1() && i2.x1() == i0.x1() - 1 && i4.x1() == i3.x1() + 1 && i1.x2() == i3.x2())
				return 121;
		}
		
		/*
		 * 46
		 */
		
		if (checkSize(intervals, 4, 6, 8, 2, 2) && Approximation.intervalsOnSameLine(i3, i4)) {
			
			if (i1.x1() == i3.x1() && i2.x1() == i3.x1() - 1 && i0.x1() == i1.x1() + 1 && i4.x2() == i1.x2())
				return 122;
		}
		
		/*
		 * 47
		 */
		
		if (checkSize(intervals, 6, 8, 4, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i2.x1() - 1 && i3.x1() == i2.x1() + 1)
				return 123;
			
			if (i0.x2() == i2.x2() && i1.x2() == i2.x2() + 1 && i3.x2() == i2.x2() - 1)
				return 124;
		}
		
		/*
		 * 48
		 */
		
		if (checkSize(intervals, 4, 4, 8, 6)) {
			
			if (i0.x1() == i1.x1() + 1 && i1.x1() == i3.x1() && i2.x1() == i1.x1() - 1)
				return 125;
			
			if (i0.x2() == i1.x2() - 1 && i1.x2() == i3.x2() && i2.x2() == i1.x2() + 1)
				return 126;
		}
		
		/*
		 * 49
		 */
		
		if (checkSize(intervals, 4, 10, 4)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1)
				return 127;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1)
				return 128;
		}
		
		/*
		 * 50
		 */
		
		if (checkSize(intervals, 2, 2, 4, 6, 4)) {
			
			if (i2.x1() == i4.x1() && i3.x1() == i2.x1() - 1 && i1.x2() == i3.x2() && i0.x2() == i1.x2() + 1)
				return 129;
			
			if (i2.x1() == i4.x1() && i3.x1() == i2.x1() - 1 && i1.x1() == i3.x1() && i0.x1() == i1.x1() - 1)
				return 130;
		}
		
		/*
		 * 51
		 */
		
		if (checkSize(intervals, 4, 6, 4, 2, 2)) {
			
			if (i0.x1() == i2.x1() && i1.x1() == i0.x1() - 1 && i1.x1() == i3.x1() && i4.x1() == i3.x1() - 1)
				return 131;
			
			if (i0.x2() == i2.x2() && i1.x2() == i0.x2() + 1 && i1.x2() == i3.x2() && i4.x2() == i3.x2() + 1)
				return 132;
		}
		
		/*
		 * Default
		 */
		
		return -1;
	}
	
}
