import java.util.ArrayList;

public class Cluster {
	private ArrayList<Point> points;
	private Point centroid;
	
	public Cluster(ArrayList<Point> points) {
		this.points = points;
		this.centroid = points.get(0);
	}
	
	public void calcCentroid() {
		double x = 0.0;
		double y = 0.0;
		for(Point p : points) {
			x += p.getX();
			y += p.getY();
		}
		x /= points.size();
		y /= points.size();
		centroid = new Point(x,y);
	}
	public double updateCentroid() {
		Point prev = centroid;
		calcCentroid();
		return Point.calcDistance(prev,centroid);
	}
	public Point getCentroid() {
		return centroid;
	}
	public void addPoint(Point point) {
		points.add(point);
	}

}
