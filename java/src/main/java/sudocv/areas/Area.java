package sudocv.areas;
import sudocv.areas.Position;

public interface Area {
	/*
	 * Returns the ith position in a block
	 */
	Position get(int i);
	// returns the value based on the 'current' position in the Area
	Position get_by_offset(int offset);
	void move(int delta);
	void move(int x, int y);
}
