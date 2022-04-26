package application;
	
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Stack;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	File file ;
	@Override
	public void start(Stage stage) {
		try {
			
			
			Group group = new Group();
			Scene scene = new Scene(group, 1000,400);
			stage.setResizable(false);
			stage.setTitle("Huffman Bahaa");
			VBox button = new VBox(40);
			
			//Choose File
			Button choseFile = new Button("Choose File");
			choseFile.setMinHeight(50);
			choseFile.setMinWidth(100);
			Label result= new Label("");
			HBox hbox = new HBox(10);
			hbox.getChildren().addAll(choseFile, result);
			
			//Compression
			Button comp = new Button("Compression");
			Label result1 = new Label("");
			comp.setMinHeight(40);
			HBox hbox1 = new HBox(10);
			hbox1.getChildren().addAll(comp, result1);
			hbox1.setVisible(false);

			//De-Compression
			Button deComp = new Button("De-Compression");
			Label result2 = new Label();
			deComp.setMinHeight(40);
			HBox hbox2 = new HBox(10);
			hbox2.getChildren().addAll(deComp, result2);
			hbox2.setVisible(false);
			
			button.setLayoutX(100);
			button.setLayoutY(30);
			
			button.getChildren().addAll(hbox, hbox1, hbox2);
			
			
			//result area
			TextArea text = new TextArea();
			text.setLayoutX(40);
			text.setLayoutX(550);
			text.setMaxWidth(400);
			
			
			FileChooser choose =new FileChooser();
			
			choseFile.setOnAction(e -> {
				try {
					file = choose.showOpenDialog(new Stage());
					if(file.length() == 0)
						throw new Exception();
					
					result.setText("File has been read");
					result.setTextFill(Color.GREEN);
					hbox1.setVisible(true);
					hbox2.setVisible(true);
					
					result1.setText("");
					result2.setText("");
					text.setText("");
					
					//fileCompression();
					//decompress();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					hbox1.setVisible(false);
					hbox2.setVisible(false);
					result.setText("No file or the file is empty");
					result.setTextFill(Color.RED);
					result1.setText("");
					result2.setText("");
					text.setText("");
				}
			});
			
			

		
			
			
			comp.setOnAction(e -> {
				try {
					String ss = fileCompression();
					result1.setText(ss);
					
					
					if(ss == "Done") {
						String str ="Path for new File : " + path + "\n"+
								"Header : " + headForMain + "\n" +
										"Huffman Code for all character : \n" + hufCodeMethode(hufCodeStrigArr);
						
						text.setText(str);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			deComp.setOnAction(e -> {
				try {
					String ss = decompress();
					result2.setText(ss);

					if(ss == "Done") {
						String str ="Path for new File : " + path + "\n"+
								"Header : " + headForMain + "\n" +
										"Huffman Code for all character : \n" + hufCodeMethode(hufCodeStrigArr);
						
						text.setText(str);
					}
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			
			//
			
			
			Line l = new Line(20, 370, 980, 370);
			Line l1 = new Line(40, 350, 960, 350);
			Line l2 = new Line(60, 330, 940, 330);

			
			
			
			
			
			
			
			
//			10 ###
//			29 ;;;;;;;;;;;
//			65 A --- 01000001
//			130 ? --- 10000010
//			195 Ã --- 11000011
//			29 ...................
			
			
			group.getChildren().addAll(button, text, l, l1, l2);
			//.......................
			

			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public String hufCodeMethode(String[] arr)
	{
		String result = "";
		for(int i = 0 ; i< 256 ; i++)
			if(arr[i] != null)
				result += ((char)i) + " : " + arr[i] + "\n";
		
		return result;
	}
	
	
	
	String path ;
	int[] freqArray;
	BufferedInputStream readFile;
	BufferedOutputStream writeFile;
	int sizeOfHeadBits= 0;
	String[] hufCodeStrigArr ;
	//_______________________________________________________________________________
	//_______________________________________________________________________________
	//________________________________Compression____________________________________
	//_______________________________________________________________________________
	
	
	public String fileCompression() throws IOException
	{
		if(!file.getPath().substring(file.getPath().indexOf(".")+1).equals("huf")) {
			
			path = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + ".huf";
			sizeOfHeadBits= 0;
			freqArray = new int[256];
			readFile = new BufferedInputStream(new FileInputStream(file));
			writeFile = new BufferedOutputStream(new FileOutputStream(path));
			buffer = 0;
			
			int numOfCharacter = 0;
			while(readFile.available() != 0)
			{
				byte[] read = new byte[8];
				int num = readFile.read(read);
				
				for(int i = 0 ; i < num ; i++)//28
				{
					if (read[i] < 0)
						freqArray[read[i] + 256]++;// 10011100 & 11111111
					else
						freqArray[read[i]]++;
					
					numOfCharacter++;

				}

			}
			readFile.close();
			
			for(char i = 0 ; i < 256 ; i++)
			{
				if(freqArray[i] > 0)
					System.out.println(i + " " + (int)i+" " + freqArray[i]);
			}
			
			
			Tree root = buildTree();
			//write formula
			writeString(file.getPath().substring(file.getPath().indexOf(".")+1));
			writeFile.write(' ');
			writeInt(numOfCharacter);
			//write header in file
			String head = makeHeader(root);
			headForMain = head;
			System.out.println(head);
			int lenthofH = lengthS(head);
			writeInt(lenthofH);
			System.out.println(numOfCharacter + " ###");
			System.out.println(lenthofH + " ;;;;;;;;;;;");
			for(int i = 0; i < head.length() ; i++)
			{
				if(head.charAt(i) == '1')
				{
					writeBit(true);
					sizeOfHeadBits++;
					
					i++;
					char character =  head.charAt(i);
					int byteCh = (byte) character;
					sizeOfHeadBits+=8;
					
					if(byteCh < 0)
					byteCh += 256;


					String toBinary = String.format("%8s", Integer.toBinaryString(byteCh)).replace(' ', '0');
					System.out.println(byteCh + " " + (char)byteCh + " --- " + toBinary);
					
					for(int j = 0 ;  j < toBinary.length() ; j++)
					{
						if(toBinary.charAt(j) == '1')
						{
							writeBit(true);
						}else if(toBinary.charAt(j) == '0')
						{
							writeBit(false);
						}
						
					}//end small for	
				}//end if 
				else if(head.charAt(i) == '0')
				{
					writeBit(false);
					sizeOfHeadBits++;
				}
			}
			System.out.println(sizeOfHeadBits + " ...................");
			 checkIfFullByte((long)sizeOfHeadBits);

			String[] arrHuffman = new String[256];
			String str = "";
			huffCode(root, arrHuffman, str);
			hufCodeStrigArr = arrHuffman;
			//test
			for(int i = 0 ; i < 256 ; i++)
			{
				if(arrHuffman[i] != null)
					System.out.println((char)i + " " + arrHuffman[i]);
			}
			
			//..........
			
			
			long counterHuff = 0;
			readFile = new BufferedInputStream(new FileInputStream(file));
			while(readFile.available() != 0)
			{
				byte[] bytes = new byte[8];
				int count = readFile.read(bytes);
				for(int j = 0 ; j < count ; j++)
				{
					int bytes2 = bytes[j];
					if(bytes[j] < 0)
						bytes2 += 256;
					for(int x = 0 ; x < arrHuffman[bytes2].length() ; x++)
					{
						if(arrHuffman[bytes2].charAt(x) == '1')
						{
							writeBit(true);
							counterHuff++;
							//System.out.println(true);
						}else if(arrHuffman[bytes2].charAt(x) == '0')
						{
							writeBit(false);
							counterHuff++;
							//System.out.println(false + " ...");
						}
					}
				}
				
			}
			checkIfFullByte(counterHuff);
			
			readFile.close();
			writeFile.close();
			
			return "Done";
		}//else>>>>>>>>>>>>>>>>>>
		else {
			return "The file is already compressed in huffman format";
		}

	}//end of read file
	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACCCCCCCCCCCCBBBBBBBBBBBBBFFFFFEEEEEEEEEDDDDDDDDDDDDDDDD

	
	public int lengthS (String s)
	{
		int sum = 0;
		for(int i = 0 ; i < s.length() ;i++)
		{
			if(s.charAt(i) == '0' || s.charAt(i) == '1')
				sum++;
			else
				sum += 8;
		}
		return sum;
	}
	
	//..........
	
	//_______________________________________________________________________________
	//_______________________________________________________________________________
	//________________________________De-Compression____________________________________
	//_______________________________________________________________________________
//	String path ;
//	int[] freqArray;
//	BufferedInputStream readFile;
//	BufferedOutputStream writeFile;
//	int sizeOfHeadBits= 0;
	
	
	String headForMain = "";
	public String decompress() throws IOException
	{
		if(file.getPath().substring(file.getPath().indexOf(".")+1).equals("huf"))
		{
			readFile = new BufferedInputStream(new FileInputStream(file));
			sizeOfHeadBits= 0;
			String formatOutputFile = "";
			char format = (char)readFile.read();
			while(format != ' ')
			{
				formatOutputFile += format;
				format = (char) readFile.read();
			}
			
			System.out.println(formatOutputFile);
			
			path = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + "." + formatOutputFile;
			writeFile = new BufferedOutputStream(new FileOutputStream(path));

			System.out.println(path);
			
			//numOfCharacter _____________________________
			byte[] num = new byte[4];
			readFile.read(num);

			int numOfCaracter = readInt(num);
			//__________________________________________
			
			System.out.println(numOfCaracter + " lllll");
			
			//Size of header _____________________________
			byte[] head = new byte[4];
			readFile.read(head);
			int sizeOfHeaderBits = readInt(head);//////////////////////////////
			
			System.out.println(sizeOfHeaderBits);
			
			//___________________________________________
			
			//Read Header
			int sizeOfHeadByte = sizeOfHeadByte(sizeOfHeaderBits);
			System.out.println(sizeOfHeadByte);
			
			byte[] header = new byte[sizeOfHeadByte];
			readFile.read(header);
			
			
			String s = "01010000010110000010111000011000";
			System.out.println(s.substring(2, 10));
			System.out.println(s);
			
			String expHeader  = Decrypt(header, sizeOfHeaderBits);
			headForMain = expHeader;
			
			System.out.println(expHeader + " /////////");
			
			Tree root = reBuildTree(expHeader);
			String wow = "";
			String[] strArr = new String[256];
			huffCode(root, strArr, wow);
			hufCodeStrigArr = strArr;
//			
//			
//			for(int i = 0 ; i < 256 ; i++)
//				if(strArr[i]!= null)
//					System.out.println(strArr[i] + " " + (char)i);
			
			String binary = "";
			while(readFile.available() != 0)
			{
				byte[] read = new byte[8];
				int count  = readFile.read(read);
				for(int i = 0 ; i < count ; i++ )
				{
					int temp = read[i];
					if(temp < 0)
						temp += 256;
//					System.out.println((char)temp +  " ;;;; " + temp);
					binary += String.format("%8s", Integer.toBinaryString(temp)).replace(' ', '0');
					Tree location = root;

					String binary2 = binary;
					for(int j = 0 ; j < binary.length() && numOfCaracter != 0 ; j++)
					{
						if(binary.charAt(j) == '0')
							location = location.left;
						else
							location = location.right;
						
						
						if(location.isLeaf())
						{
							writeFile.write(location.getC());
							numOfCaracter--;
							
							if((j+1) <= binary.length())
							{
								binary2 = binary.substring(j+1);
								
							}
							location = root;
						}//end if
					}//end small for
					if(binary2 != null)
						binary = binary2;
				}//end big for
			}//end while
			writeFile.flush();
			readFile.close();
			writeFile.close();
			
			return "Done";			
		}
		return "The file could not be decrypted. It must be in huff . format";
	}
	//_________________________________________________________________________
	
	public String Decrypt(byte[] arr, int size)
	{
		System.out.println(size + " " + arr.length + " wwwwwwwwwwwwwwwwwwwww");
		String result = "";
		String str = "";
//		System.out.println(arr.length + " ;;;;;;;;;");
		for (int x = 0; x < arr.length; x++) {
			int temp = arr[x];
			if(temp < 0)
				temp += 256;
			str += String.format("%8s", Integer.toBinaryString(temp)).replace(' ', '0');
//			System.out.println(str);
//			System.out.println(arr[x] + " ......... " +  ((char) arr[x]));

		}
		System.out.println(str.length());
		int i = 0;
		while (i < size) {
			if(str.charAt(i) == '0')
			{
				result += '0';
				i++;
			}
			else if(str.charAt(i) == '1')
			{
				result += '1';
				i++;
				if(i + 8 < str.length())	
				result +=  fromStringBinaryToByte(str.substring(i, i+8));
				i+=8;
			}
		}
	//01A01?1Ã	
		return result;
	}
	
	public char fromStringBinaryToByte(String s)
	{
		int sum = 0;
		
		
		int x = 0;
		for(int i = 7 ; i >= 0 ; i--)
		{
			if(s.charAt(i) == '1')
			{
				sum += Math.pow(2, x);
			}
			x++;	
		}
		
		return(char) sum;
	}
	
	
	public int sizeOfHeadByte(int num)
	{
		if(num % 8 == 0)
		{
			return num/8;
		}else 
		{
			return (num/8) + 1;
		}
	}
	
	public int readInt(byte[] bytes) {
			if (bytes != null) {
				int value = 0;
				value += (bytes[3] & 0x000000FF) << 24;
				System.out.println(value);
				value += (bytes[2] & 0x000000FF) << 16;
				System.out.println(value);

				value += (bytes[1] & 0x000000FF) << 8;
				System.out.println(value);

				value += (bytes[0] & 0x000000FF);
				System.out.println(value);

				return value;
			}
			return 0;
		}
	
	//________________________________________________________________________
	
	//..........................................
	public void writeString(String str) throws IOException
	{
		for(int i = 0 ; i  < str.length() ; i++)
		{
			writeFile.write((byte)str.charAt(i));
		}
	}
	
	int buffer = 0;
	int counterBits = 0;
	public void writeBit(boolean bit) throws IOException //shifting byte 
	{
		buffer <<= 1;
		if (bit)
			buffer |= 1;

		counterBits++;
		if (counterBits == 8) {
			writeFile.write((byte) buffer);
			buffer = 0;
			counterBits = 0;
		}
	}
	
	public void writeInt(int number) throws IOException {
		//System.out.println(number + " wwwwwwwwwwww");
		for (int i = 0; i < 4; i++) { //4- 00000000 3- 00000000 2- 00000000 1- 00000000   ->shifting integer 
			writeFile.write((byte)number);
			System.out.println((byte)number);
			number >>= 8;
		}	
	}
	
	//______________________________________
	
	public boolean checkIfFullByte(long num) throws IOException {

		if (num % 8 == 0) 
		{
			return false;
		}
		else
		{ 
			long mod = (8 - (num % 8));
			
			for (int k = 0; k < mod; k++) 
				writeBit(false);
			
			return true;
		}

	}
	public  Tree reBuildTree(String str)
	{
		Tree root= new Tree('\0');
		
		Stack<Tree> stack = new Stack<>();
		stack.push(root);
		
		for (int i = 0; i < str.length(); i++) {
			Tree newNode = new Tree('\0');

			if (!stack.isEmpty()) {
				newNode = stack.pop();
			}

			if (str.charAt(i) == '0') {
				newNode.right = new Tree('\0');

				newNode.left = new Tree('\0');

				stack.push(newNode.right);

				stack.push(newNode.left);
			} else {
				if (i + 1 < str.length())
					newNode.setC(str.charAt(i + 1));
				i++;
			}
		}
		
		
		return root;
		
	}
	 
	public void huffCode(Tree root, String[] arr, String s) {

		if (!root.isLeaf()) {

			if (root.left != null) {
				huffCode(root.left, arr, s + '0');
			}
			huffCode(root.right, arr, s + '1');

		} else {
			arr[root.getC()] = s;
		}

	}
	
	//...........................
	public Tree buildTree()
	{
		PriorityQueue<Tree> minHeap = new PriorityQueue<Tree>();
		
		for(char i =0 ; i < 256 ; i++)//insert Heap
		{
			if(freqArray[i] > 0)
			{
				minHeap.offer(new Tree(i, freqArray[i]));
				//System.out.println("bahaa");
			}
		}

		//build tree and return the root
		if(minHeap.size() == 1)
		{
			Tree node = minHeap.poll();
			Tree root = new Tree('\0', node.getFreq() , new Tree('\0'), node);
			minHeap.offer(root);
		}

			
		while(minHeap.size() > 1)
			{
				Tree left = minHeap.poll();
				Tree right = minHeap.poll();
				
				Tree root = new Tree('\0', left.getFreq()+right.getFreq(), left, right);
				minHeap.offer(root);
			}
		return minHeap.poll();
	}
	
	
	public String makeHeader(Tree root) 
	{
		Stack<Tree> stack = new Stack<Tree>();
		stack.push(root);
		String head = "";
		
		
		Tree temp;
		while(!stack.isEmpty())
		{
			temp = stack.pop();
			if(temp.isLeaf())
			{
				head += "1" + temp.getC();
			}else {
				head+= "0";
			}
			
			if(temp.right != null)
				stack.push(temp.right);
			if(temp.left != null)
				stack.push(temp.left);	
		}
		return head;	
	}
	
	
	
	
	
	
	
	
	
}
