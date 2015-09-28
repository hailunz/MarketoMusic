package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */

import java.util.Comparator;

/*
 * CompareByArtist
 * Sort SongFiles list first by Artist, then by title
 */

public class CompareByArtist implements Comparator<SongFile>{

	@Override
	public int compare(SongFile o1, SongFile o2) {
		int artResult= o1.getArtist().compareTo(o2.getArtist());
		if (artResult == 0){
				return o1.getTitle().compareTo(o2.getTitle());
		}else
			return artResult;
	}

}
