package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Playlist {
	private String playlistName;
	private ArrayList<SongFile> songs;

	// store songId-> index in playlist
	private HashMap<Integer,Integer> indexMap;
	private int id;
	private String currentOrder;
	private CentralStore centralStore;
	
	/*
	 * Constructor
	 * @param: name: playlist name
	 * @param: id: playlistId
	 * @param: CentralStore 
	 */
	public Playlist(String name, int id, CentralStore cs){
		this.playlistName=name;
		this.id=id;
		this.currentOrder="default";
		this.songs= new ArrayList<SongFile>();
		this.indexMap = new HashMap<Integer,Integer>();
		this.centralStore=cs;
	}
	
	/*
	 * Delete a song
	 * @param: song Id
	 */
	private void delete(int id){
		if (!this.indexMap.containsKey(id)){
			System.out.println("Delete Error: The playlist does not have this song!");
			return;
		}
		int index=this.indexMap.get(id);
		int size= this.songs.size();
		for (int i=index+1;i<size;i++){
			this.indexMap.put(this.songs.get(i).getID(), i-1);
		}
		this.indexMap.remove(id);
		this.songs.remove(index);
	}
	
	/*
	 * Insert a song
	 * @param: songId
	 */
	private void insert(int id){
		if (this.centralStore.songs.size()<=id){
			System.out.println("Error: Wrong song id for insert!");
			return;
		}
		if (this.indexMap.containsKey(id)){
			System.out.println("Error: Song["+ id +"] is already existed in this playlist.");
		}else{
			this.indexMap.put(id, this.songs.size());
			this.songs.add(this.centralStore.songs.get(id));
		}
	}
	
	/*
	 * Search and insert a range of songs 
	 * @param: option: title or artist
	 * @param: string of words to search
	 */
	private void insert_search(String option,String words){
		
		if (option.equals("artist")){
			for (SongFile sf: this.centralStore.songs){
				if (!this.indexMap.containsKey(sf.getID()) && sf.getArtist().toLowerCase().contains(words)){
					this.indexMap.put(sf.getID(),this.songs.size());
					this.songs.add(sf);
				}
			}
			
		}else if (option.equals("title")){
			
			for (SongFile sf: this.centralStore.songs){
				if (!this.indexMap.containsKey(sf.getID()) && sf.getTitle().toLowerCase().contains(words)){
					this.indexMap.put(sf.getID(),this.songs.size());
					this.songs.add(sf);
				}
			}
			
		}else{
			
			System.out.println("Error: wrong option for command insert_search!");
		}
	}
	
	/*
	 * Print playlist based on option 
	 * @param: option: title or artist
	 * 
	 * Default order is the inserted order.
	 * If the option is not title nor artist, using the default order.
	 */
	private void print(){
		ArrayList<SongFile> tmp = new ArrayList<SongFile>(this.songs);
		if (currentOrder.equals("title")){
			Collections.sort(tmp,new CompareByTitle());
		}else if (currentOrder.equals("artist")){
			Collections.sort(tmp,new CompareByArtist());
		}
		for (SongFile sf : tmp){
			sf.printSong();
		}
	}
	
	/*
	 * Search songs in the playlist
	 * @param: option: title or artist
	 * @param: string of words
	 */
	private void search(String option, String words){
		if (option.equals("title")){
			for (SongFile sf: this.songs ){
				if (sf.getTitle().toLowerCase().contains(words)){
					sf.printSong();
				}
			}
			
		}else if (option.equals("artist")){
			for (SongFile sf: this.songs ){
				if (sf.getArtist().toLowerCase().contains(words)){
					sf.printSong();
				}
			}
			
		}else{
			System.out.println("Error: wrong option for search.");
		}

	}
	
	/*
	 * Sort the playlist
	 * @param: option: title or artist
	 * 
	 * Default order is the inserted order
	 * If the option is not title nor artist, using the default order.
	 */
	private void sort (String option){
		if (option.equals("artist")){
			this.currentOrder="artist";
			this.print();
		}else if (option.equals("title")){
			this.currentOrder="title";
			this.print();
		}else{
			this.currentOrder="default";
			this.print();
		}
	}
	
	/*
	 * Playlist Menu application
	 */
	public void PlaylistMenu(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String main = "PlayList Menu: PlaylistId: "+ this.id + "  Playlist Name: "+this.playlistName;
		String command="";
		while(true){
			System.out.println(main);
			System.out.print(">");
			try {
				command=br.readLine().trim();
				if (command.startsWith("delete")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for delete.\n");
						continue;
					}
					try{
						int id = Integer.parseInt(pair[1].trim());
						delete(id);
					}catch (NumberFormatException e){
						System.out.println("Erro: wrong command format for delete.");
					}
				}else if (command.startsWith("insert_search")){
					String pair[]= command.split("\\s+",3);
					if (pair.length!=3){
						System.out.println("Error: wrong command format for insert_search.\n");
						continue;
					}
					insert_search(pair[1],pair[2].replaceAll("\"", "").toLowerCase());
					
				}else if (command.startsWith("insert")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for insert.\n");
						continue;
					}
					try{
						int id = Integer.parseInt(pair[1].trim());
						insert(id);
					}catch (NumberFormatException e){
						System.out.println("Erro: wrong command format for insert.");
					}
					
				}else if (command.startsWith("print")){
					print();
					
				}else if (command.startsWith("search")){
					String pair[]= command.split("\\s+",3);
					if (pair.length!=3){
						System.out.println("Error: wrong command format for search.\n");
						continue;
					}
					search(pair[1],pair[2].replaceAll("\"", "").toLowerCase());
					
				}else if (command.startsWith("sort")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for sort.\n");
						continue;
					}
					sort(pair[1].trim());
					
				}else if (command.equals("main")){
					System.out.println("Return to main.");
					return;
				}else {
					System.out.println("Wrong command!");
					System.out.println("Usage:");
					System.out.println("delete <songId>: delete a song.");
					System.out.println("insert <songId>: insert a song.");
					System.out.println("insert_search <option> <\"string of words\">: search and insert.");
					System.out.println("print : print playlist.");
					System.out.println("search <option> <\"string of words\">: option: artist or title.");
					System.out.println("sort <option>: option: artist or title.");
					System.out.println("main: return to Main Menu.");
					System.out.println();
				}
				
			} catch (IOException e) {	
				e.printStackTrace();
			}
			System.out.println();
		}
		
	}
	
	public void printPL(){
		print();
	}
	
	public String getName(){
		return this.playlistName;
	}
	public int getId(){
		return this.id;
	}
}
