package labArrange;


public class Main {
	public static void main(String[] args) {
		LabArrange lab = new LabArrange();
		TimeFullIterator iter = new TimeFullIterator(lab.timeFull);
//		TimeFullIterator iter = new MockTimeFullIterator(lab.timeFull);
		TwoPhase compute = new TwoPhase();
		Float result = null;
		//show progress
		int accuracy = 100, percent = 0;
		IO.printArr(iter.maxSize, "maxSize");
		while(iter.hasnext()) {
			float[][] iterator = iter.next();
			lab.generateEquation(iterator);
			//compute.setByMap(TestData.getMap());
			compute.setByMap(lab.getMap());
			compute.addArtificialVal();
			compute.step1();
			compute.step2();
			compute.getResult();
			lab.getArrange(compute.baseIndex, compute.B);
			if(result==null) {
				result = compute.result;
				lab.arrangeAdd(lab.arrange);
			}else if(result == compute.result) {
				lab.arrangeAdd(lab.arrange);
			}else if(result > compute.result) {
				result = compute.result;
				lab.arrangeArr.clear();
				lab.arrangeAdd(lab.arrange);
			}
			//print
//			IO.printLine(55);
//			IO.printArr(iterator, "status:"+iter.nowNum);
//			IO.printArr(lab.A, "A");
//			IO.printArr(lab.B, "B");
//			IO.printArr(lab.C, "C");
//			IO.printArr(lab.arrange, "arrange");
//			IO.printArr(compute.getResult(), "result");
			//show progress
			if(iter.nowNum >= percent*accuracy) {
				System.out.print(">");
				percent++;
			}
		}
		IO.printLine(55);
		IO.printArr(result, "minimun salary");
		for(int i=0;i<lab.arrangeArr.size();i++) {
			IO.printArr(lab.arrangeArr.get(i), "arrangement("+i+")");
		}
	}
}
