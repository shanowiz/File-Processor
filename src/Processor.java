import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Processor {
	
	//declares variables.
	private String fileName;
	private String breakType;
	private String alignment;
	private int lineWidth;
	
	
	//default constructor.
	public Processor() {
		super();
		this.fileName = "testFile.txt";
		this.breakType = "";
		this.alignment = "";
		this.lineWidth = 0;
	}

	//primary constructor.
	public Processor(String breakType, String alignment, int linewidth) {
		super();
		this.fileName = "testFile.txt";
		this.breakType = breakType;
		this.alignment = alignment;
		this.lineWidth = linewidth;
	}
	
	//validates parameters.
	public void validateInput () {
		
		new Processor (breakType,alignment,lineWidth);
		
		if (!fileName.contains(".txt")) {
			System.out.println("Invalid File!!!\n");
		} else if (!breakType.equalsIgnoreCase("word") && !breakType.equalsIgnoreCase("character") ) {
			System.out.println("Invalid Break Type!!!\n");
		} else if (!alignment.equalsIgnoreCase("left") && !alignment.equalsIgnoreCase("center") && !alignment.equalsIgnoreCase("right")) {
			System.out.println("Invalid Alignment!!!\n");
		}else if (lineWidth <= 0) {
			System.out.println("Minimum Characters Per Line '1'!!!");
		} else {
			readTextFile ();
		}
		
	}
	
	//reads a normal .txt file.
	public void readTextFile () {
		
        //represents one line at a time.
        int asciiChar = 0;
        String line = "";

        try {
            //reads text files.
            FileReader fileReader = new FileReader(fileName);

            //BufferedReader gets one line or character at a time from the file.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            	
            //iterates through the file.
            while((asciiChar = bufferedReader.read()) != -1) {
            	//reads each character of the file and appends it to a single string.
            	char eachChar = (char) asciiChar;
            	line = line + eachChar;
            }
            
            processContent(line);
            
            //closes the file.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file \n");
            ex.printStackTrace();
        }
	}
	
	//processes the file contents to specifications.
	public void processContent (String line) {
		
		ArrayList<String> lineList = new ArrayList<String> ();
		String formattedLine = "";
		int lineWidthCount = 0;
		
		//removes new lines and carriage returns.
		line = line.replaceAll("\n","");
		line = line.replaceAll("\r"," ");
		
		if (breakType.equalsIgnoreCase("word")) {
			
			//splits the line into individual characters.
			String[] wordSplit = line.split(" ");
			
			//iterates through the line array.
			for (int i=0; i<wordSplit.length; i++) {
			
					lineWidthCount = lineWidthCount + wordSplit[i].length();
					
					//checks line width and appends or create a new line where necessary.
					if (lineWidthCount < lineWidth) {
						//displays each character.
						//System.out.print(wordSplit[i]+" ");
						
						//appends each word.
						formattedLine += wordSplit[i]+" "; 
						
						lineWidthCount = lineWidthCount + 1;
					} else {
						//starts a new line with a character.
						//System.out.print("\n"+wordSplit[i]+" ");
						
						//adds a formatted line to the line list array.
						lineList.add(formattedLine);
						
						//appends each word.
						formattedLine = wordSplit[i]+" ";
						
						lineWidthCount = wordSplit[i].length();
					}
					
					if (i+1 == wordSplit.length) {
						//adds the last formatted line to the line list array.
						lineList.add(formattedLine);
					}
			}
			
			alignChar (lineList);
			
		} else if (breakType.equalsIgnoreCase("character")) {
			
			//splits the line into individual characters
			String[] wordSplit = line.split(" ");
			
			//iterates through the line array
			for (int i=0; i<wordSplit.length; i++) {
				
					String[] charSplit = wordSplit[i].split("");
					//lineWidthCount = lineWidthCount + charSplit.length;
					
					for (int cnt=0; cnt < charSplit.length; cnt++) {
						//checks line width and appends or create a new line where necessary.
						if (lineWidthCount < lineWidth) {
							
							//appends each character.
							formattedLine += charSplit[cnt];
							lineWidthCount++;
						}else { 
							
							//adds a line the line list array
							lineList.add(formattedLine);
							
							//starts a new line with a character.
							formattedLine = charSplit[cnt];
							
							lineWidthCount = 1;
						}
						
					}
					//adds a space for each word.
					formattedLine += " ";
					
					lineWidthCount++;
					
					if (i+1 == wordSplit.length) {
						//adds the last formatted line to the line list array.
						lineList.add(formattedLine);
					}
			}
		
			alignChar (lineList);
		}
		
	}
	
	
	public void alignChar (ArrayList <String> lineList) {
		
		System.out.println("****************");
 		System.out.println("**File Content**");
 		System.out.println("****************\n");
 		
		//iterates through the array containing the formatted lines from the file.
		for (String line: lineList) {
			
			//check alignment and left align characters.
			if (alignment.equals("left")){
				System.out.println(line);
			}
			//checks alignment and center align characters.
			else if (alignment.equals("center")) {
				//gets the number of padding needed to center characters.
				int centerLineWidth = ((lineWidth-line.length())+1)/2;
				
				//center align characters.
				if (centerLineWidth > 0) {
					for (int i=0; i<centerLineWidth; i++) {
						System.out.print(" ");
					}
					System.out.println(line);
				} else {
					System.out.println(line);
				}
				
			}
			//checks alignment and center align characters.
			else if (alignment.equals("right")) {
				//gets the number of padding needed to right align characters
				int centerLineWidth = lineWidth-line.length();
			
				//splits the line into characters and removes the extra space at the end of the line.
				String[] temp = line.split("");
				if (temp.length <= lineWidth && temp[temp.length-1].equals(" ")) {
					temp[temp.length-1] = "";
					line = " "+String.join("", temp);
				}
				
				//right align characters.
				if (centerLineWidth > 0) {
					for (int i=0; i<centerLineWidth; i++) {
						System.out.print(" ");
					}
					System.out.println(line);
				} else {
					System.out.println(line);
				}
			}
		}
		
		System.out.println("\n\n"+"Developed by: Shane O. Barrett");
	}
}
