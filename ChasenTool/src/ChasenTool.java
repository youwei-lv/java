import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChasenTool {
	JapaneseWordReader read;
	private  static final String  CHASEN_CMD_FORMAT = "chasen -F %M\\t%y1\\t%a1\\t%U(%P-)\\n  -o";
	
	public ChasenTool(){
		this(new JapaneseFullNameReader());
	}
	
	public ChasenTool(JapaneseWordReader read){
		this.read = read;
	}
	
	public boolean cmdExe(String infile, String outfile) throws Exception{
		String cmd = getExeCmd(infile, outfile);		
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();

		if (process.exitValue()!=0){
			System.err.println("chasen's processing of "+ infile+ " has failed.");
		}
		
		return new File(outfile).exists();
	}
	
	public  List<JapaneseWord> readWordsFromChasenFile(String fileSource, String encoding) throws Exception {
		List<JapaneseWord> inputWords  = new ArrayList<JapaneseWord>();
		BufferedReader tempReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileSource), encoding));
		JapaneseWord word = null;
		while ((word = read.getNext(tempReader)) != null){
			inputWords.add(word);
		}
		tempReader.close();
		
		return inputWords;
	}
	
	public Map<JapaneseWord, Integer> importDictionaryFromChasenFile(String fileSource) throws Exception{
		List<JapaneseWord> list =  readWordsFromChasenFile(fileSource);
		Map<JapaneseWord, Integer> dict = new HashMap<JapaneseWord, Integer>();
		for(JapaneseWord jw: list){
			dict.put(jw, dict.size() + 1);
		}
		return dict;
	}
	
	public  List<JapaneseWord> readWordsFromChasenFile(String fileSource) throws Exception {
		return this.readWordsFromChasenFile(fileSource, "shift_jis");
	}

	
	private String getExeCmd(String infile, String outfile){
		return CHASEN_CMD_FORMAT +" "+ outfile + " "+ infile;
	}
}
