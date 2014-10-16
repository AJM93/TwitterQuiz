class Tweet {
  
  boolean Display;
  String Username;
  String Content;
  //String[] BadWords = new String[10];
  //array of words that are not allowed to be displayed
  String[] BadWords = {"shit", "fucking", "cock", "dick", "fuck", "cunt", "Prick"};
 
  
   Tweet(String TempUsername, String TempContent) {
     //constructor takes in the name of a person who has tweeted and the content of the tweet
    Username = TempUsername;
    Content = TempContent;
    
    
   } 
   
   
   //getter for username. returns a string.
   String getUsername(){
    return Username; 
   }
   
   
   //method to draw a tweet. it simply draws a box and then draws the username and content of the tweet in the apprpriate place
   void DrawTweet(int TweetX, int TweetY){
    fill(255,255,255);
    
    //splits content into two parts
    String[] parts = {
        Content.substring(0,(Content.length()/2))+" - ",
        Content.substring((Content.length()/2))
    };
    
    rect(TweetX, TweetY, 1300, 720/8);
    fill(0,0,255);
    textSize(20);
    text("@"+Username, TweetX+10, TweetY+30); 
    textSize(17);
    //if the content of tweet is above 70 chars it shows the split up version to ensure it doesnt go off the screen.
    if (Content.length() > 70){
    text(parts[0], TweetX+10, TweetY+50);
    text(parts[1], TweetX+10, TweetY+70);
    }
    else {
     text(Content, TweetX+10, TweetY+50); 
    }
    fill(255,255,255);
     
     
   }
   
   
   //method that checks through a tweet to find a word, and returns a boolean (true of false)
   boolean checkWord(String Stringtofind){
     
     
     String Stringtofind2=Stringtofind+" ";
     //line above is to deal with bug talked about in testing document.
     
     println(Stringtofind+" "+Stringtofind2);
     
     //new string called word to break. It's set to have the content of the content variable
     String wordtobreak = Content;
     //creates an array of strings. the .split method is there to split the tweet when it finds any of the following characters (?<=[ \\n])
     String broken[] = wordtobreak.split("(?<=[ \\n])");
     println(broken);
     //loops for the length of the array
     for( int x = 0; x <broken.length ; x++){
       println("loopin");
       
       //compares the current word in the array to the word it is trying to find. note that tolowercase is here so that the 
       //search criteria is not case sensitive.
      if ((broken[x].toLowerCase().equals(Stringtofind.toLowerCase()))  ||  ((broken[x].toLowerCase().equals(Stringtofind2.toLowerCase())) ))
      {
      //returns true if founf 
       println("found it!");
        return true;
      } 
      else{
        
        println("No luck!");
      }
     }
     //if not founf returns false
     return false;
   }
  
  //check languade, althought looks like a very small method, actually does quite alot
  void checkLanguage(){
    //loops through the bad words array
    for (int x = 0; x < 7;x++){
      //if it finds the current word then it changes the content of the tweet to 'language innapropriate'
     if (checkWord(BadWords[x])){
      Content="Language innapropriate"; 
     }
    } 
    
    
    
  }
  
  
  
  
  
  
}
