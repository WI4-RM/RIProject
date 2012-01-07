package main;

import parser.MyParser;
import parser.ParserInterface;
import request.RequestInterface;
import request.RequestOne;
import score.BM25Score;
import score.ScoreCalculator;
import fichier.FLuxReaderTextAscii;
import fichier.FluxReader;
import fichier.FluxReaderOne;
/**
 * donner les fichiers en arguments dans "runconfiguration > arguments"
 * @author fingon
 *
 */
public class Main {

	/**
	 * @param args : file to be indexed
	 */
	public static void main(String[] args) {
		
		if (args.length <= 0){
			System.out.println("no argument !!");
			System.exit(0);
		}
		
		//open file(s) and prepare them
		FluxReader flux = new FLuxReaderTextAscii(args);
		System.out.println("file opened !");
		
		//create parser
		ParserInterface parser = new MyParser(flux);
		parser.parse(); //parse documents
		System.out.println("parsed !");
		
		//((MyParser)parser).dump();
		//request
		RequestInterface request = new RequestOne();
		
		//score	calculation	
		ScoreCalculator score = new BM25Score(request,parser.getIndex());
		score.proceed();
		score.printResult();
		
		

	}

}
