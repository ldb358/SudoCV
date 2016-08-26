package sudocv.areas;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int get_x(){
		return x;
	}

	public int get_y(){
		return y;
	}

	public boolean equals(Position p) {
	    return x == p.get_x() && y == p.get_y();
    }

    public boolean between(Position a, Position b) {
        return (this.get_x() >= a.get_x() && this.get_x() <= b.get_x() &&
                this.get_y() >= a.get_y() && this.get_y() <= b.get_y());
    }
}
