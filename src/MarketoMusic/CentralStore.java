package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
 * CentralStore 
 * Store songs data
 */
public class CentralStore {
	public ArrayList<SongFile> songs;
	public String currentOrder;
	
	public CentralStore(String file) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(file));
		String line = null;
		int index=0;
		this.currentOrder="id";
		this.songs= new ArrayList<SongFile>();
		
		while(scan.hasNext()){
			line=scan.nextLine();
			String pair[]= line.split("\\s{2,}");
			
			SongFile song = new SongFile(index,pair[0],pair[1]);
			songs.add(song);
			index++;
		}
	}
	
	/*
	 * printSongs
	 * print songFile on the current order
	 */
	public void printSongs(){
		
		if (this.songs.isEmpty()){
			System.out.println("No song exits!");
			return;
		}
		
		ArrayList<SongFile> tmp = new ArrayList<SongFile>(this.songs);
		if (this.currentOrder.equals("title")){
			Collections.sort(tmp, new CompareByTitle());
		}else if (this.currentOrder.equals("artist")){
			Collections.sort(tmp, new CompareByArtist());
		}
		System.out.println("All the songs:Id: Artist [ Title ]");
		for (SongFile sf: tmp){
			System.out.println(sf.toString());
		}
		System.out.println();
	}
}
