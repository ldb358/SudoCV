package sudocv.areas;
import sudocv.areas.Position;

public class Block implements Area {
	/*
	 * Returns the ith position in a block
	 */

	private int base_x;
	private int base_y;

	public Block(int block){
		base_x = block % 3;
		base_y = block / 3;
	}
	public Block(int x, int y){
		base_y = (y/3) * 3;
		base_x = (x/3) * 3;
	}

	public Position get(int i){
		return new Position(base_x + i/3, base_y + i%3);
	}
}
