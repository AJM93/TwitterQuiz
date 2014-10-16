/*

version 1


in version 1 i set out to get both a twitter stream and twitter object set up.

set up the first version of the tweet class, more methods will be added to it soon.

set up the first version of the tweet stream on the right of the program, several adjustments
will be made to the size/position/number of tweets in it in later versions.

Version 2

Added a method that would generate a start point for the stream of tweets, this way if there
are more than eight tweets it will move up a place and still only display 8 tweets. (first tweet on the stream
is the first tweet to come back off it)

version 3

Created method that breaks a tweet up into words.

Note that it adds a space onto some words and so will need to take into account the space when comparing string to one 
and other, for the moment, it works.

For this reason, the array of swears will have a swear with a space after, for ease.

Now all is in place to find out if a tweet is answering a questions, the next version i will beging to bring in questions from the database
and check to see if each tweet is answering them, language filter will be added later.

version 4

Created the question class and the answer question method within it.

added .tolowercase() in the method for finding a word in a tweet so that it was no longer case sensitive.

established initial connection to database.
attempted a query to pull a question from the database(for now it will always pull the same question.).

made the language filter method work.
ammended checkword to return a boolean

version 5

got a version of check answer working
created an array of questions from the mysql connection

made program pick another random quesiton once a question is answered

resised the program and the tweet objects to that tweets would not be cut off

ammended draw tweet so that it would split the tweet into two strings if it is more than 
a certian length and display them below each other so that tweets did not run of screen.

made program a better size for displaying on screens (720p)

commented code

*/


