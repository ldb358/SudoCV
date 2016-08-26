package sudocv.areas;
import sudocv.areas.Position;

public class Column implements Area {
	/*
	 * Returns the ith position in a block
	 */
	private int column;
    private int pos;

    //allow for the passing of (x, y) and iterate over the right value
	public Column(int column){
		this.column = column;
        pos = 0;
	}

	@Override
	public Position get(int i){
		return new Position(column, i);
	}

    @Override
    public Position get_by_offset(int offset) {
        return new Position(column, pos + offset);
    }

    @Override
    public void move(int delta) {
        column += delta;
    }

    @Override
    public void move(int x, int y) {
        column = x;
        pos = y;
    }

}
