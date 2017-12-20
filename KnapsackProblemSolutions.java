/*
Dan Beaupre
12/6/17
Program 3
Assignement was to create a dynamic programming algorithm to solve knapsack
and a backtracking algorithm to solve knapsack.  We then have to compare
theoretical and actual run times.
*/

import java.util.*;

public class KnapsackProblemSolutions{
  //Global variables used in backtracking to store results
  public static int best = 0;
  public static int[] chosen;

  /*
  @Param: None
  @Return: None
  Main get number of items and capacity of knapsack. Generates random list of
  sizes
  */
  public static void main(String[] args){
		Scanner in = new Scanner(System.in);
    //Gets number of items
		System.out.print("Enter a positive integer for number of items: ");
		int n = in.nextInt();

    //Gets size of knapsack
    System.out.print("Enter a positive integer for capacity of the knapsack: ");
		int k = in.nextInt();

    Random rand = new Random();

    //array of ints to store each items
		int[] s = new int[n];
    for(int i = 0; i < n; i++){
      s[i] = rand.nextInt(20);    //Generates random sizes

    }

    //Dynamic knapsack test
    long startTimeDynamic = System.nanoTime();
    dynamicKnapsack(s, n, k);
    long estimatedTimeDynamic = System.nanoTime() - startTimeDynamic;
    System.out.println("Dynamic Time " + estimatedTimeDynamic);


    //Backtracking knapsack test
    long startTimeBacktrack = System.nanoTime();
    int[] intList = new int[n];
    chosen = new int[n];

    rec_knapsack(s, n, 0, k, intList);

    //Searches through array of chosen sizes, and prints
    for (int i = 0; i < n; i++){
      if (chosen[i] == 1){
        System.out.println("Item " + i + ", Size " + s[i]);
      }
    }
    long estimatedTimeBacktrack = System.nanoTime() - startTimeBacktrack;
    System.out.println("Backtracking Time " + estimatedTimeBacktrack);

	}

  /*
  @Param: array of sizes, number of items, capacity of knapsack
  @return: None
  Solves the knapsack problem using dynamic programming
  */
  public static void dynamicKnapsack(int[]s, int n, int k){
    //Creates the boolean arrays
    boolean[][] exist = new boolean[n+1][k+1];
    boolean[][] belong = new boolean[n+1][k+1];

    exist[0][0] = true;
    for (int j = 1; j < k; j++){
      exist[0][j] = false;            //initialize first row to false
    }

    for (int i = 1; i < n+1; i++){
      for (int j = 0; j < k+1; j++){

        exist[i][j] = false;        //Start off as false

        if (exist[i-1][j]){
          exist[i][j] = true;
          belong[i][j] = false;
        }

        else if (j - s[i-1] >= 0){

          if (exist[i-1][j-s[i-1]]){

            exist[i][j] = true;
            belong[i][j] = true;
          }
        }
      }
    }
    //Prints the results
    int i = n;
    int j = k;
    boolean[] chosen = new boolean[n];    //Array of sizes that are being used

    //Finds the largest cpacity that can be made using the sizes given
    while(j > 0){
      if (exist[i][j] == false){
        j --;
      }
      else{
        break;
      }
    }
    //Finds the sizes used
    while(j > 0){
      if (belong[i][j] == false){
        i--;
      }

      else if (belong[i][j]){
        chosen[i-1] = true;
        j -= s[i-1];
        i--;
      }
    }
    for (int x = 0; x < n; x++){
      if (chosen[x]){
        System.out.println("Item " + x + ", Size " + s[x]);
      }
    }
  }

  /*
  @param: array of sizes, number of items, current index to fill,
  capacity of knapsack, array to indicate what sizes to use
  @return: None
  Solves the knapsack problem using backtracking
  */
  public static void rec_knapsack(int[] sizes, int n, int k, int c, int[] s){
      for (int i = 0; i <= 1; i++){
        s[k] = 0 + i;

        if (k == n-1){
          int current_size = 0;
          for (int j = 0; j < n; j++){

            if(s[j] == 1){
              current_size += sizes[j];
            }

            if (current_size <= c){
              if (current_size > best){
                best = current_size;

                for (int x = 0; x < n; x++){
                  chosen[x] = s[x];
                }
              }
            }
          }
        }
        else{
          rec_knapsack(sizes, n, k+1, c, s);
        }
      }
  }
}
