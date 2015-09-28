package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MainMenu {

	private ArrayList<Playlist> playlists;
	private CentralStore centralStore;
	
	public MainMenu(String file) throws FileNotFoundException{
		this.playlists = new ArrayList<Playlist>();
		this.centralStore = new CentralStore(file);
	}
	
	/*
	 * Create a playlist
	 * @param: playlist name
	 */
	public void create(String name){
		Playlist pl = new Playlist(name,this.playlists.size(),this.centralStore);
		this.playlists.add(pl);
		pl.PlaylistMenu();
	}
	
	/*
	 * Edit a playlist
	 * @param: playlist Id
	 */
	
	public void edit(int id){
		if (this.playlists.size()<=id){
			System.out.println("Error: Wrong playlist id or no playlist exists!");
			return;
		}
		this.playlists.get(id).PlaylistMenu();
	}
	
	/*
	 * Print songFile
	 * @param: song Id
	 */
	public void song(int id){
		
		if (this.centralStore.songs.size()<=id){
			System.out.println("Error: Wrong song id or the CentralStore is Empty!");
			return;
		}
		this.centralStore.songs.get(id).printSong();
	}
	
	/*
	 * Print playlist
	 * @param: playlist Id
	 */
	public void playlist(int id){
		
		if (this.playlists.size()<=id){
			System.out.println("Error: Wrong playlist id or no playlist exists!");
			return;
		}
		this.playlists.get(id).printPL();
	}
	
	/*
	 * Print 
	 * @param: option: song or playlist
	 */
	public void print(String option){
		if (option.equals("song")){
			this.centralStore.printSongs();	
		}else if (option.equals("playlist")){
			if (this.playlists.isEmpty()){
				System.out.println("No playlist exits!");
				return;
			}
			for (Playlist pl: this.playlists){
				System.out.println("Playlist Id: " + pl.getId() + " Playlist Name: " + pl.getName());
				pl.printPL();
				System.out.println();
			}
		}else{
			System.out.println("Error: Wrong option for print.");
		}
	}
	
	/*
	 * Search by title or artist
	 * @param: option, title or artist
	 * @param: string of words ( case insensitive )
	 *
	 */
	public void search(String option, String words){
		ArrayList<SongFile> searchResult = new ArrayList<SongFile>();
		// if option is title
		if (option.equals("title")){
			for (SongFile sf: this.centralStore.songs ){
				if (sf.getTitle().toLowerCase().contains(words)){
					searchResult.add(sf);
				}
			}
			Collections.sort(searchResult, new CompareByTitle());
			for (SongFile sf : searchResult){
				sf.printSong();
			}
			
		}
		// if option is artist
		else if (option.equals("artist")){
			for (SongFile sf: this.centralStore.songs ){
				if (sf.getArtist().toLowerCase().contains(words)){
					searchResult.add(sf);
				}
			}
			Collections.sort(searchResult, new CompareByArtist());
			for (SongFile sf : searchResult){
				sf.printSong();
			}
			
		}else{
			System.out.println("Error: wrong option for search.");
		}
	}
	
	/*
	 * Sort the CentralStore
	 * @param: option: title or artist
	 * 
	 * Default currentOrder is id. 
	 * If option is not title nor artist, use default order.
	 */
	public void sort (String option){
		if (option.equals("artist")){
			this.centralStore.currentOrder="artist";
			this.centralStore.printSongs();
		}else if (option.equals("title")){
			this.centralStore.currentOrder="title";
			this.centralStore.printSongs();
		}else{
			this.centralStore.currentOrder="id";
			this.centralStore.printSongs();
		}
	}
	
	/*
	 * Application of Main Menu
	 */
	public void application() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String main = "Main Menu:";
		String command="";
		while(true){
			System.out.println(main);
			System.out.print(">");
			try {
				command=br.readLine().trim();
				if (command.startsWith("create")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for create.");
						continue;
					}
					create(pair[1]);
					
				}else if (command.startsWith("edit")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for edit.");
						continue;
					}
					try{
						int id = Integer.parseInt(pair[1].trim());
						edit(id);
					}catch (NumberFormatException e){
						System.out.println("Erro: wrong command format for edit.");
					}
					
				}else if (command.startsWith("song")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for song.");
						continue;
					}
					try{
						int id = Integer.parseInt(pair[1].trim());
						song(id);
					}catch (NumberFormatException e){
						System.out.println("Erro: wrong command format for song.");
					}
					
				}else if (command.startsWith("playlist")){
					
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for playlist.");
						continue;
					}
					try{
						int id = Integer.parseInt(pair[1].trim());
						playlist(id);
					}catch (NumberFormatException e){
						System.out.println("Erro: wrong command format for playlist.");
					}
					
				}else if (command.startsWith("print")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for print.");
						continue;
					}
					print(pair[1].trim());
					
				}else if (command.startsWith("search")){
					String pair[]= command.split("\\s+",3);
					if (pair.length!=3){
						System.out.println("Error: wrong command format for search.");
						continue;
					}
					search(pair[1],pair[2].replaceAll("\"", "").toLowerCase());
					
				}else if (command.startsWith("sort")){
					String pair[]= command.split("\\s+",2);
					if (pair.length!=2){
						System.out.println("Error: wrong command format for sort.");
						continue;
					}
					sort(pair[1].trim());
					
				}else if (command.equals("quit")){
					System.out.println("Quit.");
					break;
				}else {
					System.out.println("Wrong command!");
					System.out.println("Usage:");
					System.out.println("create <playlist name>: create a playlist.");
					System.out.println("edit <playlistId>: edit a playlist.");
					System.out.println("song <songId>: print song.");
					System.out.println("playlist <playlistId>: print a playlist.");
					System.out.println("print <print option>: print all songs or playlists. option:song or playlist.");
					System.out.println("search <option> <\"string of words\">: option: artist or title.");
					System.out.println("sort <option>: option: artist or title.");
					System.out.println("quit: quit the application.");
					System.out.println();
				}
				System.out.println();
				
			} catch (IOException e) {	
				e.printStackTrace();
			}
			
		}
	}
}
