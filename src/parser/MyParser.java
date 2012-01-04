package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import index.Index;
import fichier.FluxReader;

public class MyParser implements ParserInterface {

	private FluxReader flux;
	private Index index;

	public MyParser(FluxReader flux) {
		this.flux = flux;
		this.index = new Index();
	}

	@Override
	public void parse() {
		
		while (flux.hasNextDocument()){
			String doc = flux.nextDocument(); 
			parseDoc(doc);
			index.increaseDocNumber();//count total number of document
		}
		
	}

	/**
	 * 
	 * @param doc document to be parsed
	 */
	private void parseDoc(String doc) { // doc  = element de la balise <doc>
		
		Pattern pattern = Pattern.compile("<docno>([\\d]+)</docno>(.*)");
		Matcher matcher = pattern.matcher(doc);
		boolean isFound = matcher.find();
		
		if (!isFound){
			System.out.println("error with matcher in doc" + doc);
			System.exit(0);
		}
		
		//extraction du titre : 
		String docno = matcher.group(1);
		System.out.println("docno : "+docno);
		
		//extraction des tokens : 
		String[] tokens = matcher.group(2).split("\\W+");
		
		
		//tratement du texte
		for (int i = 1; i < tokens.length; i++){
			
			//System.out.println(tokens[i]);
			this.index.add(docno,tokens[i]); // add the token to the index
		}
		
		this.index.setDocumentLength(docno, tokens.length);
	}

	@Override
	public Index getIndex() {
		// TODO Auto-generated method stub
		return this.index;
	}
	
	public void dump(){this.index.dump();}

}
