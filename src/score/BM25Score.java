package score;

import java.util.Hashtable;

import index.Index;
import request.RequestInterface;

public class BM25Score implements ScoreCalculator {
	
	private String[] request = {"olive","oil","health","benefits"};
	private Hashtable<String,Double> requestWeights;
	private Index index;
	
	private double avdl;
	private double k1;
	private double b;

	public BM25Score(RequestInterface request, Index index) {
		this.index = index;
	}

	@Override
	public void printResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void proceed() {
		for (int i=0; i < this.request.length;i++){
			double weight = calculateWeight(request[i]);
		}
		
	}

	private double calculateWeight(String word) {
		int df = index.getDocumentFrequency(word);
		return (1*( k1+1)*( Math.log( (index.getN() - df +0.5)/ (df + 0.5))))/ (k1*( (1-b) +b* (1/1)) );
		
	}


}