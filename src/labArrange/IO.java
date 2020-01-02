package labArrange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class IO {
	private static boolean isFirstWrite = true;
	public static FileWriter openFileWriter(String filename) {
		File file = new File(filename);
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			if(isFirstWrite) {
				isFirstWrite = false;
				fw = new FileWriter(file,false);
			}else {
				fw = new FileWriter(file,true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fw;
	}
	public static float[][] readFullTime(){
		float[][] timeFull=null;
		String filename = "fullTime.txt";
		File file = new File(filename);  
        InputStream in = null;  
        System.out.println("reading student's time...");
        try {
        	int tempByte,tempNum = 0, fullTimeX = 1, fullTimeY = 0;
	        in = new FileInputStream(file);
	        while ((tempByte = in.read()) != -1) {
	        	if(tempByte==32) {
	        		fullTimeY++;
	        	} else if(tempByte==10) {
	        		fullTimeY++;
	        		fullTimeX++;
	        		break;
	        	}
	        }
	        while ((tempByte = in.read()) != -1) {
	        	if(tempByte==10) {
	        		fullTimeX++;
	        	}
	        }
	        in.close();
	        timeFull = new float[fullTimeX][fullTimeY];
	        in = new FileInputStream(file);
	        int i=0, j=0;
	        while ((tempByte = in.read()) != -1) {
	        	System.out.write(tempByte);
	        	switch(tempByte) {
	        		case 32:
	        			timeFull[i][j++]=tempNum;
	        			tempNum=0;
	        			break;
	        		case 10:
	        			timeFull[i++][j]=tempNum;
	        			j=0;
	        			tempNum=0;
	        			break;
	        		default:
	        			tempNum*=10;
	        			tempNum+=tempByte-48;
	        	}
	        }
	        System.out.write(10);
	        in.close();
        } catch(IOException e) {
        	e.printStackTrace();
        }
		return timeFull;
	}
	
	public static void printArr(float[][] arr, String title) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("\n"+title);
			bw.write(title+"\n");
			for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr[0].length;j++) {
					System.out.printf(arr[i][j]+"\t");
					bw.write(arr[i][j]+"\t");
				}
				System.out.write(10);
				bw.write(10);
			}
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printArr(float[] arr, String title) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("\n"+title);
			bw.write(title+"\n");
			for(int i=0;i<arr.length;i++) {
				System.out.print(arr[i]+"\t");
				bw.write(arr[i]+" ");
			}
			System.out.println();
			bw.write(10);
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printArr(int[] arr, String title) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("\n"+title);
			bw.write(title+"\n");
			for(int i=0;i<arr.length;i++) {
				System.out.print(arr[i]+"\t");
				bw.write(arr[i]+" ");
			}
			System.out.println();
			bw.write(10);
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printArr(Float[] arr, String title) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("\n"+title);
			bw.write(title+"\n");
			for(int i=0;i<arr.length;i++) {
				System.out.print(arr[i]+"\t");
				bw.write(arr[i]+" ");
			}
			System.out.println();
			bw.write(10);
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printArr(float arr, String title) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("\n"+title);
			bw.write(title+"\n");
			bw.write(arr+"");
			System.out.println(arr);
			bw.write(10);
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printLine(int length) {
		String filename="output.txt";
		FileWriter fw = openFileWriter(filename);
		try {
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println();
            bw.write(10);
            for(int i=0;i<length;i++) {
                System.out.print("-");
    			bw.write("-");
            }
			bw.write(10);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printArr(float[][] arr) {
		printArr(arr, "");
	}
	public static void printArr(float[] arr) {
		printArr(arr, "");
	}
	public static void printArr(int[] arr) {
		printArr(arr, "");
	}
	public static void printArr(Float[] arr) {
		printArr(arr, "");
	}
	public static void printArr(float arr) {
		printArr(arr, "");
	}
}
