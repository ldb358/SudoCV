import java.io.FileNotFoundException;

import sudocv.Sudo;
import sudocv.exceptions.NoPossibleMovesException;


public class Main {
	public static void main(String arg[]){
		if(arg.length < 1) {
			System.out.println("Usage: ./app file_name");
		}
		Sudo test = new Sudo();
		try {
			test.load(arg[0]);
			//test.print();
			test.solve();
			//test.print();
			if(!test.solved()){
				test.print();
			}else{
				System.out.println("A solution was found!");
			}
		} catch(FileNotFoundException fnfe){
			System.out.printf("Error loading file %s", arg[0]);
		} catch(NoPossibleMovesException npme){
			System.out.printf("Invalid board in file %s. No move at (%d,%d)!", arg[0], npme.x, npme.y); 
			test.print();
		}
	}
}
