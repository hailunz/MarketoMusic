package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */
import java.util.Comparator;

/*
 * CompareByTitle
 * Sort SongFiles list first by title, then by artist
 */

public class CompareByTitle implements Comparator<SongFile>{

	@Override
	public int compare(SongFile o1, SongFile o2) {
		int titleResult = o1.getTitle().compareTo(o2.getTitle());
		if (titleResult == 0)
			return o1.getTitle().compareTo(o2.getTitle());
		else
			return titleResult;
	}
	
}
