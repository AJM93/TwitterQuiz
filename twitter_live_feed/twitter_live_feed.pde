//import all of the librarys needed

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
String consumerKey = "add your own";
String consumerSecret = "add your own";
String accessToken = "add your own";
String accessSecret = "add your own";
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

void setup() {
  //connects to the database holding the questions
  msql = new MySQL( this, "sql2.freesqldatabase.com", "sql210535", "sql210535", "xQ5!bH2*" );
  //database now not active
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
 questions=int(random(1,Questions.length));
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







void draw() {
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
  void decideStart(){
    if (numberoftweets <= 8) {
      startpoint=1;
    }
    else{
      startpoint++;
    }
    
  }

//method that displays tweets on the right of the screen
void drawStream() {
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
      questions=int(random(1,Questions.length));
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

