package com.example.benthumi;

public class Quiz {
    // static variable single_instance of type Singleton
    private static Quiz single_instance = null;

    private String patientName;
    private double fever;

    // variable of type String
    private String[] questionsArray= {
            "Have You Been In Contact\nWith A Corona Patient\nRecently?",
            "How Strong Is Your\nHead Ache?",
            "Do You Feel Any Muscle\nOr Body Pain?",
            "Do You Suffer From\nCongestion Or Runny\nNose?",
            "Do You Feel Loss\nOf Sleep?",
            "Do You Have Sore Throat?",
            "Do You Suffer From\nPhlegm?",
            "Do You Have Nausea Or\nVomiting?",
            "Do You Feel Any Breath\nDifficulty?",
            "Do You Have Chills\nRecently?",
            "Do You Suffer From\nDiarrhea?",
            "Do You Suffer From\nCoughs?"
    };
    private double[] precentageArray= {
            87.9,
            67.6,
            14.8,
            4.8,
            38.1,
            13.9,
            33.4,
            5.0,
            18.6,
            11.4,
            3.7,
            67.7
    };
    private Object[] answersArray=new Object[questionsArray.length];
    private int current=0;




    // private constructor restricted to this class itself
    private Quiz()
    {

    }

    public double getFever(){ return this.fever;}
    public void setFever(double fever){ this.fever=fever;}

    public String getPatientName(){return patientName;}

    public void setPatientName(String str){
        patientName=str;
    }


    public void resetQuiz(){
        current=0;
    }

    public double getPrecentage(int index){ return precentageArray[index];}

    public int getAmountQuestions(){ return questionsArray.length;}

    public int getCurrent(){ return current;}

    // static method to create instance of Singleton class
    public static Quiz getInstance()
    {
        if (single_instance == null)
            single_instance = new Quiz();

        return single_instance;
    }

    public boolean hasNext(){
        return current<questionsArray.length;
    }

    public String getPrevQuesiton(){
        if(current>0)
             current--;
        return questionsArray[current];
    }

    public String getNextQuesiton(){
        return questionsArray[current];
    }

    public void setObjectToArray(Object obj){
        answersArray[current]=obj;
        if(current<questionsArray.length)
              current++;
    }

    public Object[] getAnswersArray(){ return  answersArray;}
    public String[] getQuestionsArray(){ return  questionsArray;}
    public void updateAnswer(int index,Object updatedAnswer){
        answersArray[index]=updatedAnswer;
    }
}
