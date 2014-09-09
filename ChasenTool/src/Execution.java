import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Execution {
	public static void main(String[] args) throws Exception{
		
		String dir = "C:\\Users\\RoYui\\Desktop\\arg_data\\名古屋大演習データ";
		String dictFile = dir+"\\dict.txt";
		String symOutput = dir + "\\symfile.txt";
		
		File[] files = new File(dir, "texts").listFiles();
		
		convertFilesToSymbol(dictFile,  files, symOutput);
		
	}
	
	
	public static void countWordsFromDir(String dir, String output) throws Exception{
		ChasenTool chasen = new ChasenTool();
		WordCounter counter = new WordCounter();
		
		
		//String dir = "C:\\Users\\RoYui\\Desktop\\arg_data\\名古屋大演習データ\\texts";
		//String vobfile = "C:\\Users\\RoYui\\Desktop\\wordCounting.txt";
		
		Map<JapaneseWord, Integer> wordCounting = counter.countWordsFromDir(chasen, dir);
		counter.outputSortedWordCount(wordCounting, output);
	}
	
	public static void  convertFilesToSymbol(String dictFile, File [] files, String output) throws Exception{
		ChasenTool chasen = new ChasenTool();
		WordCounter counter = new WordCounter();
		
		
		File cf = new File("C:\\TEMP\\chasen.txt");
		cf.createNewFile();
		
		Map<JapaneseWord, Integer> dict = chasen.importDictionaryFromChasenFile(dictFile);
		
		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		
		for(File f: files){
			chasen.cmdExe(f.getAbsolutePath(), cf.getAbsolutePath());
			List<Integer> fsym = counter.convertFileToSymbol(dict, chasen.readWordsFromChasenFile(cf.getAbsolutePath()));
			for(Integer i : fsym){
				out.append(i + " ");
			}
			out.append("\n");
		}
		
		out.flush();
		out.close();
	}
	
	
}
