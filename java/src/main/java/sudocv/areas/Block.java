package sudocv.areas;

public class Block implements Area {
	/*
	 * Returns the ith position in a block
	 */

	private int base_x;
	private int base_y;
	private int x;
	private int y;

	public Block(int block){
		base_x = block % 3;
		base_y = block / 3;
        x = 0;
        y = 0;
	}
	public Block(int x, int y){
	    move(x, y);
	}

	@Override
	public Position get(int i){
		return new Position(base_x + i/3, base_y + i%3);
	}

	@Override
	public Position get_by_offset(int offset) {
		/*TODO Fix this equation*/
		return new Position((x + offset)/3, (y + offset)%3);
	}

	@Override
	public void move(int delta) {
		//
	}

	@Override
	public void move(int x, int y) {
		base_y = (y/3) * 3;
		base_x = (x/3) * 3;
		this.x = x;
		this.y = y;
	}
}
