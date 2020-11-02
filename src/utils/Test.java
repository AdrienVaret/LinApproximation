package utils;

import java.awt.Point;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Test {

	public static double angle(Vector2D u, Vector2D v) {
		double angle = Vector2D.angle(u, v);
		
		if(!(u.getX() * v.getY() - u.getY() * v.getX() < 0))
		    angle = -angle;
		
		return angle;
	}
	
	public static void test1(){
		Vector2D u = new Vector2D(1.0, 2.0);
		Vector2D w = new Vector2D(2.0, 0.0);
		
		System.out.println(angle(u, w));
	}
	
	public static void test2() {
		Vector2D u = new Vector2D(1.0, -2.0);
		Vector2D v = new Vector2D(1.0, 2.0);
		Vector2D w = new Vector2D(2.0, 0.0);
		
		System.out.println("(w,u) = " + angle(w,u));
		System.out.println("(w,v) = " + angle(w, v));
	}
	
	public static void test3() {
		Vector2D u = new Vector2D(-1.0, -2.0);
		Vector2D v = new Vector2D(-1.0, 2.0);
		Vector2D w = new Vector2D(-2.0, 0.0);
		
		System.out.println("(w,u) = " + angle(w,u));
		System.out.println("(w,v) = " + angle(w, v));
	}
	
	public static void test4() {
		Vector2D u = new Vector2D(-2.0, 0.0);
		Vector2D v = new Vector2D(-1.0, 2.0);
		double angle = Utils.angle(u, v);
		System.out.println("(u,v) = " + angle);
		System.out.println(angle == 0);
	}
	
	public static void testPosition(int pos) {
		System.out.println(pos + " -> " + (pos + 3) % 6);
	}
	
	public static double orientedAngle(Point p1, Point p2, Point p3) {

	   Vector2D v1 = new Vector2D(p2.getX() - p1.getX(), p2.getY() - p1.getY());
	   Vector2D v2 = new Vector2D(p3.getX() - p2.getX(), p3.getY() - p2.getY());
	   
	   
	 
	   return Math.atan2(v1.getX(), v1.getY()) - Math.atan2(v2.getX(), v2.getY());
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(3600000);
	}
}
