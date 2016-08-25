package sudocv.areas;
import sudocv.areas.Position;

public class Column implements Area {
	/*
	 * Returns the ith position in a block
	 */
	int column;

	public Column(int column){
		this.column = column;
	}

	public Position get(int i){
		return new Position(column, i);
	}
}
