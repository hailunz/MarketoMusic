# Author: Hailun Zhu
# Email: hailunz@andrew.cmu.edu

input: contains artists names to get test songs for application.
getSong.py: use Spotify to get song files for test cases.
output.txt: test input files.
testCases: 
	- testInputCases: test input cases;
	- testMainMenuOutput: output for testing MainMenu
	- testNormalCommandOutput: output for testing Normal command
	- testOptOnPlaylistOutput: output for testing operations on playlist
	- testWrongCommandMainOutput: output for wrong command on MainMenu
	- testWrongCommandPLOutput: output for wrong command on Playlist Menu

SongList.txt: given input file.
src:
	- MarketoMusic: contains java file for the programm. 