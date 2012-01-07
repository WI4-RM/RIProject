package score;

import java.util.Hashtable;
import java.util.Iterator;

import index.Index;
import index.Index.TokenInformations;
import request.RequestInterface;

public class BM25Score implements ScoreCalculator {
	
	private String[] request = {"oil","olive","health","benefit"};
	private Hashtable<String,Double> requestWeights;
	//private Hashtable<String,Double> documentScore;
	private Index index;
	
	private double avdl;
	private double k1;
	private double b;
	private Heap heap;

	public BM25Score(RequestInterface request, Index index) {
		this.index = index;
		this.requestWeights = new Hashtable<String, Double>();
		//this.documentScore = new Hashtable<String, Double>();
		this.heap = new MyHeap();
		this.k1=1.2;
		this.b=0.75;
	}

	@Override
	public void printResult() {
		System.out.println("N: "+ index.getN());
		for (int i=0; i<index.getN() && i<10 ;i++){
			System.out.println(heap.get(i));
		}
		
	}

	@Override
	public void proceed() {
		//to store pointer refering to lists  fo each request term
		Hashtable<String, Iterator<TokenInformations>> listPointer = new Hashtable<String, Iterator<TokenInformations>>();
		
		for (int i=0; i < this.request.length;i++){
			double weight = calculateWeight(request[i],1);
			this.requestWeights.put(request[i], weight);
			
			//initialisation des pointeurs sur les listes chaines
			//listPointer.put(request[i], index.getListIterator(request[i]));
			
		}
		for (int d=0;d< index.getDocNameList().size(); d++){ //for each document
			float score =0;
			String docName = (String) index.getDocNameList().get(d);
			for (int i=0;i<request.length;i++){//for each t of the request
				if (request[i] != null ){
					
					score += requestWeights.get(request[i]) * calculateWeight(request[i],index.getTermFrequencyInDocument(request[i], docName));
					
					/*if (listPointer.get(request[i]).hasNext()){
						String currentDoc = listPointer.get(request[i]).next().docnName;
						if (currentDoc.equals(docName)){
							float tf = index.getTermFrequencyInDocument(request[i], docName);
							score += requestWeights.get(request[i]) * calculateWeight(request[i],tf );
						}
					}*/
				}
			}
			score /= (float) index.getDocumentLength(docName);
			heap.insert(docName,score);
		}
		
		heap.sort();
		
		
	}

	private double calculateWeight(String word,float tf) {
		double df = index.getDocumentFrequency(word);
		double frac = (index.getN() - df +0.5)/(df + 0.5);
		double idf = Math.log( frac);
		double numerateur = tf*( k1+1); 
		double denominateur =  k1*( (1-b) +b* (1/1)) + tf;
		double res = idf*numerateur/denominateur;
		if (res < 0)
			res = 0;
		return res;
	}


}