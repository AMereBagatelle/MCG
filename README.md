# SimpleCoordinates
This is my mod for a quick and easy storage of coordinates, so they don't have to be stored outside of the game.
Often, I found myself taking out a notepad to keep track of my coordinates.
Realized there was bound to be a better way to do this, so I made one.

You may ask, why not just use a minimap mod?
I decided that most minimap mods are too cheaty, add too many extra features, or are just too large in general.

This mod aims to solve the problems of bloat in storing coordinates.
The mod adds only 2 GUIs and 1 command, at its current state.

## Use
This mod sets itself up, just drop it in your mods folder!
There are only 3 things to know.

1. The `/coord` command.  This is the all-in-one command.  There are two main arguments, `add` and `del`.  You should be able to guess the uses.
Note: `add` adds a coordinate at your current coordinates.  If you want to set other coordinates, use the GUI.
2. The GUI.  The GUI is opened with a hotkey, set in the Options > Controls menu.  Once you are there, the GUI is easy to understand.

## Important Notes
Coordinates made with version 0.1.1 or lower will not be accessible in version 0.2.0 or above!
This is because of a change made for allowing coordinates to be saved to specific servers.
How to get your coordinates back:
1. Open Minecraft with the mod installed once, and go into the server you were storing coordinates for.
2. Find your original coordinates file.  It will be named `coordinates.json` in the main directory.
3. Open the file and copy the contents to your clipboard.
4. Find the `coordinates` folder in the same directory.  There should be a file inside, named the same as the server you logged into.  
Just open that file, and paste your coordinates.

Thanks for using my mod!  If you find any issues, just let me know on discord at AMereBagatelle#6536.  Also, open a issue tagged with feature request if you want to suggest something.
