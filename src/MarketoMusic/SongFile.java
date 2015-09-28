package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */

/*
 * SongFile structure to store song information
 */

public class SongFile implements Comparable<SongFile>{
	private String artist;
	private String title;
	private int id;
	
	public SongFile(int id, String artist,String title){
		this.id=id;
		this.artist=artist;
		this.title=title;
	}
	
	public void printSong(){
		System.out.println(this.artist + " [ " + this.title + " ]" );
	}
	
	public String toString(){
		return this.id + " : "+ this.artist+ " [ " + this.title + " ]" ;
	}
	
	public String getArtist(){
		return this.artist;
	}
	
	public String getTitle(){
		return this.title;
	}

	public int getID(){
		return this.id;
	}
	
	@Override
	public int compareTo(SongFile o) {
		return this.id-o.getID();
	}
	
}
