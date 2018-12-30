//Shane O. Barrett

public class Driver {

	public static void main(String[] args) {
		
		//creates a Processor object.
		//first parameter: Break Type (work,character).
		//second parameter: Alignment (left,center,right).
		//third parameter: Characters Per Line (>0).
		Processor processor = new Processor ("word", "right", 15);
		
		//validates parameters and produces the desired output.
		processor.validateInput();
	}

}
