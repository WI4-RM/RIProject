package index;

import index.Index.TokenInformations;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Index encapsule une structure de donnees pour indexer les documents :
 * @author fingon
 *
 */
public class Index {
	
	// la premierer hashtable contient le token et une hastable sur nom du document/ information
	private Hashtable<String, Hashtable<String,TokenInformations>> matrice;
	private int N; //documents number
	private Hashtable<String,Integer> documentsLength; 
	private ArrayList<String> documentName; // list of document names
	
	//classe interne qui contient des infos sur un documents
	public class TokenInformations {
		public String docnName; //numero du document
		public int df; // document frequency
		
		public TokenInformations(String doc, int frequency){
			docnName = doc;
			df = frequency;
		}
		
	}
	
	public Index(){
		this.matrice = new Hashtable<String, Hashtable<String,TokenInformations>>();
		this.N =0;
		this.documentsLength = new Hashtable<String, Integer>();
		this.documentName = new ArrayList<String>();
	}

	/**
	 * increments the token occurrence in docno
	 * @param docno is the document that contains token
	 * @param token is a token whom occurrence will be increased by 1
	 */
	public void add(String docno, String token){
		
		if (! matrice.containsKey(token)){//token non encore repertorie
			Hashtable<String,TokenInformations> list = new Hashtable<String,Index.TokenInformations>();//create hashtable
			list.put(docno,new TokenInformations(docno, 1)); // add a new token in docno
			matrice.put(token, list); // add new list to matrice
		}
		else {//la matrice contient deja le token
			Hashtable<String, TokenInformations> list = matrice.get(token);
			TokenInformations info = list.get(docno); //get last document in wich token is found
			
			if (info == null)
				list.put(docno,new TokenInformations(docno, 1)); // add a new token in docno			
			else  
				info.df+=1; // incremente le nombre d'occurence de token dans docno
			matrice.put(token, list);
		}
	}
	
	// pour voir se que contient l'index
	public void dump(){
		Enumeration<String> keys = this.matrice.keys();
		while (keys.hasMoreElements())
		{
			String token = keys.nextElement();
			Hashtable<String,TokenInformations> list = matrice.get(token);
			Enumeration<String> it = list.keys();
			System.out.println(token +":" );
			while (it.hasMoreElements()){
				TokenInformations doc = list.get(it.nextElement());
				System.out.println("\t"+doc.docnName +" : "+ doc.df );
			}
		}
	}

	public void increaseDocNumber() {
		this.N++;
		
	}
	
	/**
	 * 
	 * @param token
	 * @return df(token)
	 */
	public int getDocumentFrequency(String token){
		if (this.matrice.containsKey(token)){
			return matrice.get(token).size(); // size of the list = document frequency
		}
		else
			return 0;
	}
	
	/**
	 * 
	 * @param token
	 * @param docID
	 * @return tf(token,docID)
	 */
	public int getTermFrequencyInDocument(String token, String docID){
		if (this.matrice.containsKey(token)){
			Hashtable<String,TokenInformations> list = matrice.get(token);
			TokenInformations res = list.get(docID);
			if (res != null)
				return res.df;
		}
		return 0;
	}
	
	public int getN(){
		return N;
	}

	/**
	 *  set docno length to length
	 * @param docno document
	 * @param length of docno
	 */
	public void setDocumentLength(String docno, int length) {
		this.documentsLength.put(docno, new Integer(length));
		
	}
	
	public Integer getDocumentLength(String docno) {
		return this.documentsLength.get(docno);
	}
	
	public Enumeration<TokenInformations> getListIterator(String token){
		return this.matrice.get(token).elements();
	}

	public void addDocument(String doc) {
		this.documentName.add(doc);
		
	}

	public ArrayList getDocNameList() {
		// TODO Auto-generated method stub
		return this.documentName;
	}

}
