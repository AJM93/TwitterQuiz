class Question{
  
 String Question = "";
 String Answer = "";
  
  
 Question(String TempQ, String TempA){
   //.takes in a question and answer
   Question=TempQ;
   Answer=TempA;
  
 } 
  
  
  //boolean check answer takes in a tweet object.
boolean  CheckAnswer(Tweet Answerr){
  println("im doing stuff£");
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
  String getQuestion(){
   return Question ;
  }
  
  //method that allows you to change the question in the object
  void setQuestion(String q, String a){
    Question=q;
    Answer=a;
  }

  
  
}
