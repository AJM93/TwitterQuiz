import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import twitter4j.DirectMessage; 
import twitter4j.Status; 
import twitter4j.StatusDeletionNotice; 
import twitter4j.StatusListener; 
import twitter4j.Twitter; 
import twitter4j.TwitterException; 
import twitter4j.TwitterFactory; 
import twitter4j.TwitterStream; 
import twitter4j.TwitterStreamFactory; 
import twitter4j.User; 
import twitter4j.UserList; 
import twitter4j.UserStreamListener; 
import de.bezier.data.sql.*; 

import org.postgresql.xa.jdbc3.*; 
import twitter4j.examples.tweets.*; 
import org.postgresql.core.v3.*; 
import org.postgresql.core.v2.*; 
import com.mysql.jdbc.*; 
import com.mysql.jdbc.jdbc2.optional.*; 
import org.postgresql.geometric.*; 
import org.postgresql.core.types.*; 
import org.postgresql.xa.*; 
import twitter4j.media.*; 
import twitter4j.examples.friendsandfollowers.*; 
import com.mysql.jdbc.util.*; 
import twitter4j.internal.org.json.*; 
import org.postgresql.ds.common.*; 
import twitter4j.examples.suggestedusers.*; 
import twitter4j.examples.block.*; 
import com.mysql.jdbc.log.*; 
import org.postgresql.ds.*; 
import org.postgresql.translation.*; 
import twitter4j.examples.list.*; 
import twitter4j.examples.trends.*; 
import twitter4j.examples.timeline.*; 
import org.postgresql.largeobject.*; 
import twitter4j.examples.friendship.*; 
import org.postgresql.util.*; 
import org.ibex.nestedvm.*; 
import twitter4j.examples.media.*; 
import com.mysql.jdbc.integration.c3p0.*; 
import twitter4j.auth.*; 
import twitter4j.examples.directmessage.*; 
import twitter4j.examples.help.*; 
import twitter4j.examples.user.*; 
import de.bezier.data.sql.*; 
import twitter4j.json.*; 
import twitter4j.internal.async.*; 
import org.postgresql.ssl.jdbc3.*; 
import twitter4j.examples.async.*; 
import twitter4j.*; 
import twitter4j.examples.oauth.*; 
import twitter4j.examples.json.*; 
import org.postgresql.ds.jdbc23.*; 
import com.mysql.jdbc.integration.jboss.*; 
import twitter4j.examples.search.*; 
import twitter4j.internal.json.*; 
import org.postgresql.*; 
import org.postgresql.core.*; 
import twitter4j.conf.*; 
import org.postgresql.ssl.*; 
import org.sqlite.*; 
import org.postgresql.jdbc3.*; 
import org.postgresql.jdbc2.*; 
import twitter4j.internal.logging.*; 
import org.postgresql.fastpath.*; 
import twitter4j.internal.http.*; 
import twitter4j.util.*; 
import org.gjt.mm.mysql.*; 
import twitter4j.examples.spamreporting.*; 
import org.postgresql.jdbc2.optional.*; 
import org.ibex.nestedvm.util.*; 
import de.bezier.data.sql.mapper.*; 
import org.postgresql.copy.*; 
import twitter4j.internal.util.*; 
import twitter4j.examples.favorite.*; 
import twitter4j.examples.account.*; 
import com.mysql.jdbc.profiler.*; 
import twitter4j.examples.geo.*; 
import twitter4j.examples.savedsearches.*; 
import twitter4j.examples.stream.*; 
import twitter4j.management.*; 
import org.postgresql.gss.*; 
import twitter4j.api.*; 
import org.postgresql.jdbc3g.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class twitter_live_feed extends PApplet {

//import all of the librarys needed















//important numbers, start and end point of twitter stream and current question being used
int endpoint;
int startpoint;
int questions=0;

//string that will hold the name of the last twitter user to answer a question
String lastquestionanswerer="no one";;

//holds header and related twitter account
String Header_name = "YogehhDev";
String Twitter_name = "@YogehhDev";

//all of the stings needed to log on to twitter
String consumerKey = "7G5gjVwIt88Us9iHFSHw";
String consumerSecret = "S1HANzh7bC37iSAtICcwRMAl1CeFSq7iSKLzMkANgrQ";
String accessToken = "74799553-XPrnrcT7bGj8QojESDjW7Jxvp0gwMqSrNlOYyRKg";
String accessSecret = "jZ9fITEJoSvcsij2t6wXqHlosot3lk1whd8Kkduc";
//creates access token
AccessToken token;


int counter=0;
Tweet tweet1;
int numberoftweets = 0;
int count =0;

//arrays made to hold all tweets and questions, set to 0 because the append function will be used on them
Tweet[] Tweets = new Tweet[0];
Question[] Questions = new Question[0];
//all of the other objects needed, and sql object, a twittwe stream object and a twitter object
MySQL msql;
TwitterStream myTwitter;
Twitter twitter;
Question question;

public void setup() {
  //connects to the database holding the questions
  msql = new MySQL( this, "sql2.freesqldatabase.com", "sql27061", "sql27061", "bV5*aU1!" );
  msql.connect();
  //query to select all of the questions/answers from the database
  msql.query("SELECT * FROM questions");
  
  //while loop that will keep looping as long as another row is being read in from the database
  while(msql.next()){
    //creates a new object of typee question using the question and answer pulled from database
    question=new Question(msql.getString("question"),msql.getString("answer"));
    //appends this question object onto the question array
    Questions = (Question[]) append(Questions, question);
  }
  //this is how a random question is selected. (will use the random number to address and index of the array)
 questions=PApplet.parseInt(random(1,Questions.length));
  //question=new Question(msql.getString("question"),msql.getString("answer"));
  
  //Questions = (Question[]) append(Questions, question);
  println(Questions+"ijsdafoisdfj!");
  //test tweet
  tweet1 = new Tweet("Yogi", "Testing lol #MyQuiz #MyQuiz");
  tweet1.checkWord("#MyQuiz");
  frameRate(1);
  
  size(1200, 720);
//creates twitter stream object and adds a listener (this is how tweets will be read in)
  myTwitter = new TwitterStreamFactory().getInstance();
  myTwitter.addListener(userStreamListener);
//creates twitter objects, this is how tweets will be sent out
  Twitter twitter = TwitterFactory.getSingleton();
  //puts detaus in token and uses it for OAuth system
  token = new AccessToken(accessToken, accessSecret);
  twitter.setOAuthConsumer(consumerKey, consumerSecret);
  twitter.setOAuthAccessToken(token);
  

  myTwitter.setOAuthConsumer(consumerKey, consumerSecret);
  
  

  myTwitter.setOAuthAccessToken(token);
  //user() makes refresh rates in the object not an issue
  myTwitter.user();
  
   try{
     //tweets out the first question chosen at random earlier
  Status status = twitter.updateStatus(Questions[questions].getQuestion()+" answer using #MyQuiz");
    }catch (TwitterException te) {
  }
 

}







public void draw() {
  background(0,0,255);
   rect(0, 0, 1800, 100);
   fill(0,0,255);
   textSize(42);
   text(Header_name,10,50);
   textSize(14);
   text(Twitter_name,10,70);
   fill(255,255,255);
  //tweet1.DrawTweet(600,0);
  drawStream();
//text(startpoint,100,250);
fill(255,255,255);
//writes text to screen showing the question and telling people how to answer
textSize(16);
text(Questions[questions].getQuestion(),20,230);
//text(question.Answer,20,250);
textSize(16);
text ("Well done "+ lastquestionanswerer +" for getting",20,390);
text ("The last question right!",20,410);
text("Tweet your answers to @YogehhDev,",20,500);
text("using #MyQuiz at the beginning of the tweet",20,520);

  
  
}




  //method to decide the start point (of the twitter stream)
  //8 is the max amount of tweets that can be displayed, 
  //if there is <= 8 tweets it will start from the begining of the tweets array
  //if there is >8 it will add one to the startpoint for every tweet over 8
  public void decideStart(){
    if (numberoftweets <= 8) {
      startpoint=1;
    }
    else{
      startpoint++;
    }
    
  }

//method that displays tweets on the right of the screen
public void drawStream() {
  //noLoop();
  
  println("am workin");
  if(numberoftweets>0){
    //loops between 1 and 8 times number of tweets will go from 0 infanately
    //but only 8 tweets can be displayed so it removes startpoint from the number of tweets
    //(startpoint goes up 1 for every time numberoftweets goes 1 over 8
  for (int x = 0; x<numberoftweets-(startpoint-1) ; x++){
   // Tweets[x+startpoint-1].checkWord("#MyQuiz");
    //Tweets[x+startpoint-1].checkLanguage();
    //displays tweets (x will only go from 1-8 so it adds the startpoint to this number)
    Tweets[x+startpoint-1].DrawTweet(480, 100+(x*720/8));
  }
  }
  //loop();
}

UserStreamListener userStreamListener = new UserStreamListener() {
  
  //listener that does something everytime a tweet is sent to this user/this user tweets/tweet is retweeted
  public void onStatus(Status status) {
    background(0,0,125);
    text(status.getUser().getScreenName()+ " - " + status.getText(), 0, 250);
    println(status.getUser().getScreenName()+ " - " + status.getText());
    
    //creates a temp tweet object using info from the stauts that it has just detected
    Tweet temp = new Tweet(status.getUser().getScreenName(), status.getText());
    //appends this tweet onto the array of tweets
    Tweets = (Tweet[]) append(Tweets, temp);
    //adds one to the number of tweets
    numberoftweets+=1;
    //checks the language of the tweet that it has just recieved.
    Tweets[numberoftweets-1].checkLanguage();
    //runds the decide start method

    decideStart();
    //checks the tweet to see if it has answered the wuestion
    if(Questions[questions].CheckAnswer(Tweets[numberoftweets-1])){
      //if it has answered the wuestion then it changes the name of the last person to answer a wustion correctly
      lastquestionanswerer=Tweets[numberoftweets-1].getUsername();
     //chooses a new random question
      questions=PApplet.parseInt(random(1,Questions.length));
      Twitter twitter = TwitterFactory.getSingleton();
      try{
        twitter.updateStatus(Questions[questions].getQuestion()+" answer using #MyQuiz");
   
    }catch (TwitterException te) {
  }
    }
    
    
    println("would make an object here");
  }
  
  //below are all methods needed to run the program, but now used at all (to use on status you need these here)
  
  public void onStallWarning(StallWarning warning){
  }

  public void onException(Exception arg0) {
    // TODO Auto-generated method stub
  }

  public void onTrackLimitationNotice(int arg0) {
    // TODO Auto-generated method stub
  }

  public void onScrubGeo(long arg0, long arg1) {
    // TODO Auto-generated method stub
  }

  public void onDeletionNotice(StatusDeletionNotice arg0) {
    // TODO Auto-generated method stub
  }

  public void onUserProfileUpdate(User arg0) {
    // TODO Auto-generated method stub
  }

  public void onUserListUpdate(User arg0, UserList arg1) {
    // TODO Auto-generated method stub
  }


  public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
    // TODO Auto-generated method stub
  }

  public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
    // TODO Auto-generated method stub
  }

  public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
    // TODO Auto-generated method stub
  }

  public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
    // TODO Auto-generated method stub
  }

  public void onUserListDeletion(User arg0, UserList arg1) {
    // TODO Auto-generated method stub
  }

  public void onUserListCreation(User arg0, UserList arg1) {
    // TODO Auto-generated method stub
  }

  public void onUnfavorite(User arg0, User arg1, Status arg2) {
    // TODO Auto-generated method stub
  }

  public void onUnblock(User arg0, User arg1) {
    // TODO Auto-generated method stub
  }

  public void onRetweet(User arg0, User arg1, Status arg2) {
    println("Retweeted");
  }

  public void onFriendList(long[] arg0) {
    // TODO Auto-generated method stub
  }

  public void onFollow(User arg0, User arg1) {
    // TODO Auto-generated method stub
  }

  public void onFavorite(User arg0, User arg1, Status arg2) {
    // TODO Auto-generated method stub
  }

  public void onDirectMessage(DirectMessage message) {
    // TODO Auto-generated method stub
  }

  public void onDeletionNotice(long arg0, long arg1) {
    // TODO Auto-generated method stub
  }
  public void onBlock(User arg0, User arg1) {
    // TODO Auto-generated method stub
  }
};

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


class Question{
  
 String Question = "";
 String Answer = "";
  
  
 Question(String TempQ, String TempA){
   //.takes in a question and answer
   Question=TempQ;
   Answer=TempA;
  
 } 
  
  
  //boolean check answer takes in a tweet object.
public boolean  CheckAnswer(Tweet Answerr){
  println("im doing stuff\u00a3");
  //the checkword method is called and looks for the word myquiz (could be changed is another hash tag is to be used)
  if (Answerr.checkWord("#MyQuiz")){
    //if #myquiz is found then it checks the tweet for the answer to the question
    if(Answerr.checkWord(Answer)){
    println("HELL YEAH THATS RIGHT");
    //if answer is found it returns true
    return true;
    }
  }
  //if answer is not found it returns false
  return false;
}
  
 //getter to get the question of the object
  public String getQuestion(){
   return Question ;
  }
  
  //method that allows you to change the question in the object
  public void setQuestion(String q, String a){
    Question=q;
    Answer=a;
  }

  
  
}
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
   public String getUsername(){
    return Username; 
   }
   
   
   //method to draw a tweet. it simply draws a box and then draws the username and content of the tweet in the apprpriate place
   public void DrawTweet(int TweetX, int TweetY){
    fill(255,255,255);
    
    String[] parts = {
        Content.substring(0,(Content.length()/2))+" - ",
        Content.substring((Content.length()/2))
    };
    
    rect(TweetX, TweetY, 1300, 720/8);
    fill(0,0,255);
    textSize(20);
    text("@"+Username, TweetX+10, TweetY+30); 
    textSize(17);
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
   public boolean checkWord(String Stringtofind){
     
     
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
  public void checkLanguage(){
    //loops through the bad words array
    for (int x = 0; x < 7;x++){
      //if it finds the current word then it changes the content of the tweet to 'language innapropriate'
     if (checkWord(BadWords[x])){
      Content="Language innapropriate"; 
     }
    } 
    
    
    
  }
  
  
  
  
  
  
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "twitter_live_feed" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
