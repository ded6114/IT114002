
public class MyClass {

	public static void main(String[] args) {
		System.out.println ("Hello" );

	int myInt = 5;
	long myLong=1l;
	float myFloat = 1.0f;
	double  myDouble =2.0d;
	
	
	System.out.println("My int is " + myInt);
	System.out.println("My float is " + myFloat);
	System.out.println("My double is " + myDouble);
	System.out.println("My long is " + myLong);
	/*
	double a = 0d;
	double b = 1f;
	for(int i =0; i < 10; i++) {
		a += 0.1;
	}
	System.out.println("A equals B: " + (a ==b));
	System.out.println("A:" + a);
	System.out.println("B:" +b);
	
	*/
  String s = new String("Hello");
  String s2 = "";
  Integer integerClass = new Integer (5);
  int pointToAdd = 1000;
  int score =0;
  int loodped =0;
  for (int i =0; i < 5000000; i ++) {
	  if (score < Integer.MAX_VALUE - pointToAdd)
		  score += pointToAdd;
  }
  if (score <0) {
	  loodped++;
  }
  System.out.println("My Score: " + score);
  System.out.println("We overfloww" + loodped+ "times");
  }
	}
	


