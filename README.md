# Wordle-Solver
This is a java project that helps you solve the wordle by computing the potential words that could the wordle word for the day.

How it works?
Each letter has a potential status:
- GREEN - letter is in word in correct index
- YELLOW - letter is in word in incorrect index
- GREY - letter is not in word
- UNUSED - letter has not be used yet

At first all letters are set to the status of UNUSED. 

Data structures used:
- A set of letters for each status
- Index tracker which holds information about GREEN and YELLOW letters in what index's over all attemps
- A Tree where each node holds a letter and a HashMap containing links different nodes which build up words. This data structure is very simlar to one that would be used for auto word completion applications but instead is used to build up all the posible links for the words.
- 
