package labArrange;

import java.util.HashMap;

public class TestData {
	public static HashMap<String,Object> getMap(){
		float[][] A = new float[3][5];
		A[0][0]=1;
		A[0][1]=-2;
		A[0][2]=1;
		A[0][3]=1;
		A[1][0]=-4;
		A[1][1]=1;
		A[1][2]=2;
		A[1][4]=-1;
		A[2][0]=-2;
		A[2][2]=1;
		float[] B = {11,3,1};
		float[] C = {-3,1,1,0,0};
		int totalRealX = 3;
		IO.printArr(A);
		IO.printArr(B);
		IO.printArr(C);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("totalRealX", totalRealX);
		return map;
	}
}
