package labArrange;

import java.util.ArrayList;

public class TimeFullIterator {
	public float[][] timeFull, status;
	private boolean[][] chosenArr;
	private ArrayList<int[]> indexArrUG, indexArrG;
	private ArrayList<float[]> sumArrUG, sumArrG;
	private ArrayList<Boolean> isBothArrUG, isBothArrG, isNoneArrUG;
	private ArrayList<ArrayList<float[]>> statusSingleArr;
	int iUG, iG, maxSize, nowNum;
	public TimeFullIterator() {

	}
	public TimeFullIterator(float[][] timeFull) {
		this.timeFull=timeFull;
		chosenArr = new boolean[timeFull.length][timeFull[0].length];
		for(int i=0;i<chosenArr.length;i++) {
			for(int j=0;j<chosenArr.length;j++) {
				chosenArr[i][j] = false;
			}
		}
		indexArrUG = new ArrayList<int[]>();
		indexArrG = new ArrayList<int[]>();
		sumArrUG = new ArrayList<float[]>();
		sumArrG = new ArrayList<float[]>();
		isBothArrUG = new ArrayList<Boolean>();
		isBothArrG = new ArrayList<Boolean>();
		isNoneArrUG = new ArrayList<Boolean>();
		status = new float[timeFull.length][];
		statusSingleArr = new ArrayList<ArrayList<float[]>>();
		for(int i=0;i<timeFull.length;i++) {
			statusSingleArr.add(new ArrayList<float[]>());
		}
		generateStatusArr();
		iUG = 0;
		iG = 0;
		maxSize = indexArrUG.size() * indexArrG.size();
		nowNum = 0;
	}
	public boolean hasnext() {
		while(true) {
			if(iG>=indexArrG.size()) {
				return false;
			}
			boolean isValid = true;
			for(int i=0;i<timeFull[0].length;i++) {
				if(sumArrUG.get(iUG)[i]+sumArrG.get(iG)[i] < 8) {
					isValid = false;
					break;
				}
			}
			if(iG<indexArrG.size() && 
					isBothArrUG.get(iUG) && 
					isBothArrG.get(iG) || 
					!isValid) {
				indexAdd();
			}else {
				break;
			}
		}
		if(iG>=indexArrG.size()) {
			return false;
		}
		return true;
	}
	public float[][] next() {
		status[0] = statusSingleArr.get(0).get(indexArrUG.get(iUG)[0]);
		status[1] = statusSingleArr.get(1).get(indexArrUG.get(iUG)[1]);
		status[2] = statusSingleArr.get(2).get(indexArrG.get(iG)[0]);
		status[3] = statusSingleArr.get(3).get(indexArrG.get(iG)[1]);
		indexAdd();
		nowNum++;
		return status;
	}
	private void indexAdd() {
		iUG++;
		if(iUG >= indexArrUG.size()) {
			iUG = 0;
			iG++;
		}
	}
	public void generateStatusArr() {
		//generate statusSingleArr
		float timeSum, timesSum;
		float[] timeRequired = {10,10,8,8};
		for(int i=0;i<timeFull.length;i++) {
			for(int k=0;k<(int)Math.pow(2, 7);k++) {
				//generate index
				String binary = new StringBuffer(Integer.toBinaryString(k)).reverse().toString();
				String[] index = binary.split("");
				//generate a single by index
				float[] single = new float[timeFull[0].length];
				timeSum = 0;
				timesSum = 0;
				for(int j=0;j<timeFull[0].length;j++) {
					single[j] = (index.length<=j || index[j].equals("0")) ? 0f : timeFull[i][j];
					timeSum += single[j];
					if(single[j] != 0f) {
						timesSum++;
					}
				}
				//check its validity
				ArrayList<float[]> stu = statusSingleArr.get(i);
				if(timeSum >= timeRequired[i] 
						&& timesSum>=1 
						&& timesSum<=5 
						&& (stu.isEmpty() 
								|| !isEqual(stu.get(stu.size()-1),single))) {
					stu.add(single);
				}
			}
		}
		//generate indexArrUG
		ArrayList<float[]> stu1, stu2;
		stu1 = statusSingleArr.get(0);
		stu2 = statusSingleArr.get(1);
		for(int i=0;i<stu1.size();i++) {
			for(int j=0;j<stu2.size();j++) {
				float[] timeSumCol = new float[timeFull[0].length];
				for(int k=0;k<timeFull[0].length;k++) {
					if(stu1.get(i)[k] == 0f && 
							stu2.get(j)[k] == 0f) {
						isNoneArrUG.add(true);
						isBothArrUG.add(false);
					}else if(stu1.get(i)[k] != 0f && 
							stu2.get(j)[k] != 0f) {
						isNoneArrUG.add(false);
						isBothArrUG.add(true);
					}else {
						isNoneArrUG.add(false);
						isBothArrUG.add(false);
					}
					timeSumCol[k] = stu1.get(i)[k]+stu2.get(j)[k];
				}
				int[] indexArr = {i,j};
				indexArrUG.add(indexArr);
				sumArrUG.add(timeSumCol);
			}
		}
		//generate indexArrG
		stu1 = statusSingleArr.get(2);
		stu2 = statusSingleArr.get(3);
		for(int i=0;i<stu1.size();i++) {
			label:
			for(int j=0;j<stu2.size();j++) {
				float[] timeSumCol = new float[timeFull[0].length];
				for(int k=0;k<timeFull[0].length;k++) {
					if(stu1.get(i)[k] == 0f && 
							stu2.get(j)[k] == 0f) {
						continue label;
					}else if(stu1.get(i)[k] != 0f && 
							stu2.get(j)[k] != 0f) {
						isBothArrG.add(true);
					}else {
						isBothArrG.add(false);
					}
					timeSumCol[k] = stu1.get(i)[k]+stu2.get(j)[k];
				}
				int[] indexArr = {i,j};
				indexArrG.add(indexArr);
				sumArrG.add(timeSumCol);
			}
		}
	}
	public boolean isEqual(float[] array1, float[] array2) {
		if(array1.length != array2.length) {
			return false;
		}
		for(int i=0;i<array1.length;i++) {
			if(array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}
	public static void main(String args[]) {
		float[][] timeFull = IO.readFullTime();
		TimeFullIterator a = new TimeFullIterator(timeFull);
		//statusSingleArr
		System.out.println("statusSingleArr");
		for(ArrayList<float[]> b : a.statusSingleArr) {
			System.out.println(b.size());
			for(float[] c:b) {
				for(float d:c) {
					System.out.print(d+"\t");
				}
				System.out.println();
			}
		}
		//indexArrUG
		System.out.println("indexArrUG");
		System.out.println(a.indexArrUG.size());
		for(int[] b:a.indexArrUG) {
			System.out.print(b[0]+","+b[1]+"\t");
		}
//		for(float[] b:a.sumArrUG) {
//			IO.printArr(b);
//		}
		System.out.println();
		//indexArrG
		System.out.println("indexArrG");
		System.out.println(a.indexArrG.size());
		for(int[] b:a.indexArrG) {
			System.out.print(b[0]+","+b[1]+"\t");
		}
//		for(float[] b:a.sumArrG) {
//			IO.printArr(b);
//		}
		System.out.println();
		//iterator
		System.out.println("iterator");
		int i=0;
		while(a.hasnext()) {
			i++;
			IO.printArr(a.next(),i+"");
		}
		System.out.println("\n"+i);
	}
}
