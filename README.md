# wordamentsolver

This is a simple applications which solves the wordament puzzle game by Microsoft. There are two versions in the same repository. 
1. C version (CLI)
2. Java Swing version (GUI)

You just need to type in the grid as a string on both versions (Don't bother about not finding any textbox in GUI version, just type it). After inputting the grid the solver starts to solve the puzzle. In CLI version it will just display the possible words while in the GUI version you need to press ENTER after every words detection as it helps you to solve puzzle.

The using of CLI version requires you to point to the dictionary file. To use it
````bash
$: gcc wordament.c -o solver
$: ./solver words
````

The hardware to solve the puzzle without human intervention is planned to build so any contributions are welcome!

