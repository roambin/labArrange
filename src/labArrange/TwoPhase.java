package labArrange;

import java.util.ArrayList;
import java.util.HashMap;

public class TwoPhase {
	float[][] A, A1, A2;
	float[] B, C, C1, C2;
	int []baseIndex;
	int totalRealX;
	float result;
	public TwoPhase() {

	}
	public TwoPhase(float[][] A, float[] B, float[] C, int totalRealX) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.totalRealX = totalRealX;
	}
	public TwoPhase(HashMap<String,Object> map) {
		setByMap(map);
	}
	public void setByMap(HashMap<String,Object> map) {
		this.A = (float[][])map.get("A");
		this.B = (float[])map.get("B");
		this.C = (float[])map.get("C");
		this.totalRealX = (int)map.get("totalRealX");
	}
	public void addArtificialVal() {
		ArrayList<Integer> needValIndex = new ArrayList<Integer>();
		//add index of column which needs add artificial variable
		for(int i=0;i<A.length;i++) {
			boolean isHasVal = false;
			for (int j=totalRealX;j<A[0].length;j++) {
				if(Math.abs(A[i][j])==1 && !isHasVal) {
					if(A[i][j]<0) {
						needValIndex.add(i);
					}
					isHasVal = true;
				}else if(A[i][j]==0) {
					continue;
				}else {
					try {
						throw new Exception("INVALID MARTRIX A: a row has more than 1 slack variable or it's not 1/-1");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(!isHasVal) {
				needValIndex.add(i);
			}
		}
		//generate A1 (add artificial variable)
		A1 = new float[A.length][A[0].length+needValIndex.size()];
		for(int i=0;i<A1.length;i++) {
			for(int j=0;j<A1[0].length;j++) {
				A1[i][j] = (j<A[0].length) ? A[i][j]:0;
			}
		}
		int index = A[0].length;
		for(int i : needValIndex) {
			A1[i][index++]=1;
		}
		//generate C1
		C1 = new float[A1[0].length];
		for(int i=0;i<C1.length;i++) {
			C1[i] = (i<C.length) ? 0:1;
		}
//		IO.printArr(A1, "A1");
//		IO.printArr(C1, "C1");
	}
	public float getResult() {
		float CbDotmuiltB=0;
		for(int i=0;i<baseIndex.length;i++) {
			CbDotmuiltB += C[baseIndex[i]]*B[i];
		}
//		IO.printArr(CbDotmuiltB, "result");
		result = CbDotmuiltB;
		return CbDotmuiltB;
	}
	public void step1() {
		//find base
		baseIndex = new int[A1.length];
		for(int i=0;i<A1.length;i++) {
			for(int j=totalRealX;j<A1[0].length;j++) {
				if(A1[i][j]==1) {
					baseIndex[i]=j;
				}
			}
		}
		simplex(A1, C1, B);
		switchAdd(A1, C1, B);
	}
	public void step2() {
		//generate A2, C2
		A2 = new float[A.length][A[0].length];
		C2 = new float[C.length];
		for(int i=0;i<A2.length;i++) {
			for(int j=0;j<A2[0].length;j++) {
				A2[i][j] = A1[i][j];
			}
		}
		for(int i=0;i<C2.length;i++) {
			C2[i] = C[i];
		}
		simplex(A2, C2, B);
	}
	public void simplex(float[][] A, float[] C, float[] B) {
		while(true) {
			//compute c - z
			float[] cMinusZ = new float[A[0].length];
			for(int j=0;j<cMinusZ.length;j++) {
				float dotProduct = 0;
				for(int i=0;i<A.length;i++) {
					dotProduct += C[baseIndex[i]] * A[i][j];
				}
				cMinusZ[j] = C[j]-dotProduct;
			}
//			IO.printArr(cMinusZ, "cMinusZ");
			//is optimum solution
			boolean isOptimum = true;
			for(int i=0;i<cMinusZ.length;i++) {
				if(cMinusZ[i]<0) {
					isOptimum = false;
					break;
				}
			}
			if(isOptimum) {
				break;
			}
			//find indexY
			int indexY = -1;
			float min = 0;
			for(int i=0;i<cMinusZ.length;i++) {
				if(cMinusZ[i]<min) {
					min = cMinusZ[i];
					indexY = i;
				}
			}
			//compute theta
			Float[] theta = new Float[A.length];
			for(int i=0;i<theta.length;i++) {
				theta[i] = (A[i][indexY]>0) ? B[i]/A[i][indexY] : null;
			}
			//find indexX
			int indexX = -1;
			for(int i=0;i<theta.length;i++) {
				if(theta[i]!=null) {
					min = theta[i];
					indexX = i;
					break;
				}
			}
			for(int i=indexX+1;i<theta.length;i++) {
				if(theta[i]!=null && theta[i]<min) {
					min = theta[i];
					indexX = i;
				}
			}
			//System.out.println("("+indexX+", "+indexY+")");
			//change base
			baseIndex[indexX] = indexY;
			//normalize (not needed here because every elements are 1/-1)
			float divisor = A[indexX][indexY];
			B[indexX] /= divisor;
			for(int i=0;i<A[0].length;i++) {
				A[indexX][i] /= divisor;
			}
			//minus
			for(int i=0;i<A.length;i++) {
				if(A[i][indexY] != 0 && i!=indexX) {
					float multiplier = A[i][indexY];
					B[i] -= multiplier*B[indexX];
					for(int j=0;j<A[0].length;j++) {
						A[i][j] -= multiplier*A[indexX][j];
					}
				}
			}
//			IO.printArr(A, "mid");
//			IO.printArr(B, "mid B");
//			IO.printArr(theta, "theta");
//			IO.printArr(baseIndex, "baseIndex");
//			int CbDotMuiltB=0;
//			for(int i=0;i<B.length;i++) {
//				CbDotMuiltB += C1[baseIndex[i]]*B[i];
//			}
//			System.out.println(CbDotMuiltB);
//			System.out.println("("+indexX+", "+indexY+")");
		}
	}
	public void switchAdd(float[][] A, float[] C, float[] B) {
		//elements can been switched
		boolean[] isBase = new boolean[this.C.length];
		for(int i=0;i<isBase.length;i++) {
			isBase[i]=false;
		}
		for(int i=0;i<baseIndex.length;i++) {
			if(baseIndex[i]<this.C.length) {
				isBase[baseIndex[i]]=true;
			}
		}
		//switch loop
		for(int k=0;k<baseIndex.length;k++) {
			//find indexX, indexY
			int indexX, indexY = -1;
			if(baseIndex[k]<this.C.length) {
				continue;
			}else {
				indexX = k;
			}
			for(int i=0;i<isBase.length;i++) {
				if(!isBase[i] && A[indexX][i]!=0) {
					indexY = i;
				}
			}
			//change base
			baseIndex[indexX] = indexY;
			//normalize (not needed here because every elements are 1/-1)
			float divisor = A[indexX][indexY];
			B[indexX] /= divisor;
			for(int i=0;i<A[0].length;i++) {
				A[indexX][i] /= divisor;
			}
			//minus
			for(int i=0;i<A.length;i++) {
				if(A[i][indexY] != 0 && i!=indexX) {
					float multiplier = A[i][indexY];
					B[i] -= multiplier*B[indexX];
					for(int j=0;j<A[0].length;j++) {
						A[i][j] -= multiplier*A[indexX][j];
					}
				}
			}
		}
//		IO.printArr(A1);
//		IO.printArr(baseIndex, "baseIndex");
	}
}
