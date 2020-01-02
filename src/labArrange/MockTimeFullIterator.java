package labArrange;

public class MockTimeFullIterator extends TimeFullIterator{
	private float[][] status;
	int totalNum, nowNum;
	public MockTimeFullIterator(float[][] timeFull) {
		totalNum=2;
		nowNum=0;
		status=new float[timeFull.length][timeFull[0].length];
		for(int i=0;i<timeFull.length;i++) {
			for(int j=0;j<timeFull[1].length;j++) {
				status[i][j] = timeFull[i][j];
			}
		}
	}
	@Override
	public boolean hasnext() {
		if(nowNum<totalNum) {
			return true;
		}
		return false;
	}
	@Override
	public float[][] next() {
		switch(nowNum) {
			case 0:
				status[0][2]=0;
				break;
			case 1:
				status[1][5]=0;
				break;
			default:
				try {
					throw new Exception("nowNum error");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		nowNum++;
		return status;
	}
}
