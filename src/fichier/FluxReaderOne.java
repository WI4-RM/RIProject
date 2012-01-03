package fichier;

public class FluxReaderOne implements FluxReader {

	private String fileContent;
	
	//constant field 
	private final int CLOSEDOCTAGLENGTH = 6; //length of (</doc> )
	
	/**
	 * 
	 * @param args file names, each file contains several documents
	 */
	public FluxReaderOne(String[] args) {
		this.fileContent = "<doc><docno>432</docno> it !! is, just-A test., a test.</doc><doc><docno>5</docno> it </doc>";
		
	}

	@Override
	public String nextDocument() {
		int beginOffset = this.fileContent.indexOf("<docno>");
		int endOffset = this.fileContent.indexOf("</doc>");
		
		String result = this.fileContent.substring(beginOffset, endOffset); // curent doc
		System.out.println(result);
		
		//remove the current document
		String reste = this.fileContent.substring(endOffset + CLOSEDOCTAGLENGTH);
		this.fileContent = reste;
		
		return result;
	}

	@Override
	public boolean hasNextDocument() {
		
		return this.fileContent.length() > 22;
	}

}
