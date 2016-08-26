package sudocv.areas;
import sudocv.areas.Position;

public class Row implements Area {
	/*
	 * Returns the ith position in a block
	 */
	private int row;
    private int pos;

	public Row(int row){
		this.row = row;
        pos = 0;
	}

	@Override
	public Position get(int i){
		return new Position(i, row);
	}

    @Override
    public Position get_by_offset(int offset) {
        return new Position(pos + offset, row);
    }

    @Override
    public void move(int delta) {
        this.row += delta;
    }

    @Override
    public void move(int x, int y) {
        this.row = y;
        this.pos = x;
    }

}
