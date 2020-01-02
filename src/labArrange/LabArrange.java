package labArrange;

import java.util.ArrayList;
import java.util.HashMap;

public class LabArrange {
	public float[][] timeFull;
	public int totalTime, minTimeWeekUG, minTimeWeekG, minTimeSingle, wageUG, wageG, num = 0, totalRealX;
	public float[] B, C;
	public float[][] A, arrange;
	public String[] sign;
	ArrayList<int[]> coordinate;
	ArrayList<float[][]> arrangeArr = new ArrayList<float[][]>();
	public LabArrange() {
		timeFull=IO.readFullTime();
		for(int i=0;i<timeFull.length;i++) {
			for(int j=0;j<timeFull.length;j++) {
				if(timeFull[i][j]<2) {
					timeFull[i][j]=0;
				}
			}
		}
		totalTime=8;
		minTimeWeekUG=10;
		minTimeWeekG=8;
		minTimeSingle=2;
		wageUG=10;
		wageG=15;
	}
	public void generateEquation(float[][] timeFullIterator) {
//		IO.printLine(55);
//		IO.printArr(timeFullIterator, "status: "+(++num));
		int xLen = timeFullIterator.length;
		int yLen = timeFullIterator[0].length;
		boolean[][] isArrange = new boolean[xLen][yLen];
		int[] stuTimes = new int[xLen];
		int[] dayTimes = new int[yLen];
		int totalX = 0, totalRealX = 0, totalEquation = 0;
		coordinate = new ArrayList<int[]>();
		for(int i=0;i<stuTimes.length;i++) {
			stuTimes[i] = 0;
		}
		for(int i=0;i<dayTimes.length;i++) {
			dayTimes[i] = 0;
		}
		for(int i=0;i<xLen;i++) {
			for(int j=0;j<yLen;j++) {
				isArrange[i][j] = (timeFullIterator[i][j]<2) ? false : true;
				if(isArrange[i][j]) {
					stuTimes[i]++;
					dayTimes[j]++;
					totalRealX++;
					int[] coordinateElem = {i,j};
					coordinate.add(coordinateElem);
				}
			}
		}
		this.totalRealX = totalRealX;
		totalEquation = totalRealX*2+xLen+yLen;
		totalX = totalRealX*3+xLen+yLen;
		B = new float[totalEquation];
		C = new float[totalX];
		A = new float[totalEquation][totalX];
		//generate B
		int index = 0;
		for(int i=0;i<totalRealX;i++) {
			B[index] = minTimeSingle;
			B[index+totalRealX] = timeFullIterator[coordinate.get(i)[0]][coordinate.get(i)[1]];
			index++;
		}
		index+=totalRealX;
		for(int i=0;i<xLen;i++) {
			B[index] = (i<2) ? minTimeWeekUG:minTimeWeekG;
			index++;
		}
		for(int i=0;i<yLen;i++) {
			B[index] = totalTime;
			index++;
		}
		//generate C
		index = 0;
		for(int i=0;i<C.length;i++) {
			if(i<stuTimes[0]+stuTimes[1]) {
				C[index] = wageUG;
			}else if(i<totalRealX) {
				C[index] = wageG;
			}else {
				C[index] = 0;
			}
			index++;
		}
		//generate A
		int indexSlack = totalRealX;
		for(int i=0;i<A.length;i++) {
			for(int j=0;j<A[0].length;j++) {
				A[i][j]=0;
			}
		}
		for(int i=0;i<totalRealX;i++) {
			A[i][i]=1;
			A[i][indexSlack++]=-1;
		}
		for(int i=0;i<totalRealX;i++) {
			A[i+totalRealX][i]=1;
			A[i+totalRealX][indexSlack++]=1;
		}
		index = totalRealX*2;
		int indexY = 0;
		for(int i=0;i<xLen;i++) {
			for(int j=0;j<yLen;j++) {
				if(isArrange[i][j]) {
					A[index][indexY++]=1;
				}
			}
			A[index++][indexSlack++]=-1;
		}
		for(int j=0;j<yLen;j++) {
			for(int i=0;i<xLen;i++) {
				if(isArrange[i][j]) {
					A[index][coordinateTrans(i,j)]=1;
				}
			}
			A[index++][indexSlack++]=-1;
		}
//		IO.printArr(A,"A");
//		IO.printArr(B,"B");
//		IO.printArr(C,"C");
	}
	public HashMap<String, Object> getMap() {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("totalRealX", totalRealX);
		return map;
	}
	public int coordinateTrans(int i, int j) {
		for(int[] iter:coordinate) {
			if(iter[0]==i&&iter[1]==j) {
				return coordinate.indexOf(iter);
			}
		}
		return -1;
	}
	public void getArrange(int[] baseIndex, float[] B) {
		arrange = new float[timeFull.length][timeFull[0].length];
		for(int i=0;i<arrange.length;i++) {
			for(int j=0;j<arrange[0].length;j++) {
				arrange[i][j] = 0;
			}
		}
		for(int i=0;i<baseIndex.length;i++) {
			if(baseIndex[i]<totalRealX) {
				int[] index = coordinate.get(baseIndex[i]);
				arrange[index[0]][index[1]] = B[i];
			}
		}
//		IO.printArr(arrange, "arranges");
	}
	
	public void arrangeAdd(float[][] arrange){
		for(int k=0;k<arrangeArr.size();k++) {
			boolean isEqual = true;
			label:
			for(int i=0;i<arrange.length;i++) {
				for(int j=0;j<arrange[0].length;j++) {
					if(arrangeArr.isEmpty()
							|| arrange[i][j] 
							!= arrangeArr.get(k)[i][j]) {
						isEqual = false;
						break label;
					}
				}
			}
			if(isEqual) {
				return;
			}
		}
		arrangeArr.add(arrange);
	}
}
