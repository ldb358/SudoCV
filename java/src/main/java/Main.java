import java.io.FileNotFoundException;
import sudocv.Cache;
import sudocv.Sudo;
import sudocv.exceptions.NoPosibleMovesException;


public class Main {
	public static void main(String arg[]){
		if(arg.length < 1) {
			System.out.println("Usage: ./app file_name");
		}
		Sudo test = new Sudo();
		try {
			test.load(arg[0]);
			test.print();
			test.solve();
			test.print();
		} catch(FileNotFoundException fnfe){
			System.out.printf("Error loading file %s", arg[0]);
		} catch(NoPosibleMovesException npme){
			System.out.printf("Invalid board in file %s. No move at (%d,%d)!", arg[0], npme.x, npme.y); 
			test.print();
		}
	}
}
