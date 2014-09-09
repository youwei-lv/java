import java.io.BufferedReader;
import java.io.IOException;

public class JapaneseFullNameReader implements JapaneseWordReader {
	
	@Override
	public JapaneseWord getNext(BufferedReader read) throws IOException{
		String line = null;
		while ((line=read.readLine())!=null){
			String[] s = line.split("\\s+");
			if (s.length>=4 && checkWord(s[3])==true){
					return new JapaneseWord(s[0],s[1],s[2],s[3]);
			}
		}
		
		return null;
	}
	
	@Override
	public boolean checkWord(String wordProperty){
		String []c = wordProperty.split("-");
		boolean flag = false;
		for (int i=0; i<c.length; i++){
			if (c[i].equals("–¼ŽŒ") || c[i].equals("“®ŽŒ") || c[i].equals("Œ`—eŽŒ") )
				flag=true;
			if (c[i].equals("ŒÅ—L–¼ŽŒ") )
				flag=true;
			if(c[i].equals("Ú”ö") && flag==true){
				return false;
			}
		}
		return flag;
	
	}
}
