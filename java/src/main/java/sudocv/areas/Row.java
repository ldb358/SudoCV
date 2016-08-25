package sudocv.areas;
import sudocv.areas.Position;

public class Row implements Area {
	/*
	 * Returns the ith position in a block
	 */
	int row;

	public Row(int row){
		this.row = row;
	}

	public Position get(int i){
		return new Position(i, row);
	}
}
