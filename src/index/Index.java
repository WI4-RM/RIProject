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
	
	// la premierer hashtable contient le token et une liste
	private Hashtable<String, ArrayList<TokenInformations>> matrice;
	
	
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
		this.matrice = new Hashtable<String, ArrayList<TokenInformations>>();
	}

	/**
	 * increments the token coccurence in docno
	 * @param docno is the document that contains token
	 * @param token is a token whom occurence will be increased by 1
	 */
	public void add(String docno, String token){
		
		if (! matrice.containsKey(token)){//token non encore repertorie
			ArrayList<TokenInformations> list = new ArrayList<Index.TokenInformations>();//create list
			list.add(new TokenInformations(docno, 1)); // add a new token in docno
			matrice.put(token, list); // add new list to matrice
		}
		else {//la matrice contient deja le token
			ArrayList<TokenInformations> list = matrice.get(token);
			TokenInformations info = list.get(list.size() -1); //get last document in wich token is found
			
			if (! info.docnName.equals(docno))//premier occurence de token dans docno
				list.add(new TokenInformations(docno, 1)); // add a new token in docno			
			else // ce n'est pas le premiere occurence : 
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
			ArrayList<TokenInformations> list = matrice.get(token);
			Iterator< TokenInformations> it = list.iterator();
			System.out.println(token +":" );
			while (it.hasNext()){
				TokenInformations doc = it.next();
				System.out.println("\t"+doc.docnName +" : "+ doc.df );
			}
		}
	}

}
