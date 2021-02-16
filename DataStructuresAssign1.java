// Samir Ali
// Data Structures CSCI 3320
// Programming Assignment 1

import java.util.Random;
import java.util.Scanner;

public class DataStructuresAssign1
{
	
	// Variables sequenceStart and sequenceEnd are used to initialize indices
	static public int sequenceStart = 0;
	static public int sequenceEnd = -1;
	
	// SEQMIN and SEQMAX are the max and min values that
	// the program will randomly generate to add to the
	// array.
	public static int SEQMIN = -9999;
	public static int SEQMAX = 9999;
	
	private static Random rand = new Random();

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		ExecutionTimer timer = new ExecutionTimer();
		
		System.out.println("Enter the sequence size");
		int seqSize = input.nextInt();
		
		// Creates an array of size equal to user input
		int sequence[] = new int[seqSize];
		
		int[] outputSeq = new int[3];
		
		
		// Counter to measure how many indices of the array have been
		// printed on one line.
		int printCount = 0;
		
		// Randomly generates numbers between 9999 - (-9999)
		// and adds to the array.
		for (int i = 0; i < sequence.length; i++)
		{
			int x = rand.nextInt((SEQMAX + 1) - SEQMIN) + SEQMIN;
			if (x >= SEQMIN && x <= SEQMAX)
			{
				sequence[i] = x;
			}
		}
		
		// If the array size is less than 50, the sequence will
		// be printed to the console.
		if (sequence.length < 50)
		{
			System.out.println();
			
			for (int i = 0; i < sequence.length; i++)
			{
				if (printCount < 10)
				{
					System.out.printf("%6d", sequence[i]);
					printCount++;
				}
				
				else
				{
					System.out.printf("%6d", sequence[i]);
					printCount = 0;
				}
			}
			System.out.println();
		}
		
		// Below are three loops that loop through 10 times
		// for each algorithm and output the Max Sum , the starting index, 
		// and the ending index.
		for (int i = 0; i < 10; i++)
		{
			timer.start();
			outputSeq = maxSubSum2(sequence);
			timer.end();
			System.out.println("Algorithm 2:");
			System.out.println("MaxSum: " + outputSeq[0] + 
					", S_index: " + outputSeq[1] + ", E_index: " + outputSeq[2]);
			System.out.print("Execution Time: ");
			timer.print();
			System.out.println();
			System.out.println();
			timer.reset();
		}
		
		for (int i = 0; i < 10; i++)
		{
			timer.start();
			outputSeq = maxSumRecDriver(sequence);
			timer.end();
			System.out.println("Algorithm 3:");
			System.out.println("MaxSum: " + outputSeq[1] + 
					", S_index: " + outputSeq[0] + ", E_index: " + outputSeq[2]);
			System.out.print("Execution Time: ");
			timer.print();
			System.out.println();
			System.out.println();
			timer.reset();
		}
		
		for (int i = 0; i < 10; i++)
		{
			timer.start();
			outputSeq = maxSubSum4(sequence);
			timer.end();
			System.out.println("Algorithm 4:");
			System.out.println("MaxSum: " + outputSeq[0] + 
					", S_index: " + outputSeq[1] + ", E_index: " + outputSeq[2]);
			System.out.print("Execution Time: ");
			timer.print();
			System.out.println();
			System.out.println();
			timer.reset();
		}
		
		input.close();
	}
	
	/**
	* Quadratic maximum contiguous subsequence sum algorithm.
	*/
	public static int[] maxSubSum2(int[] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++)
        {
            int thisSum = 0;
            for( int j = i; j < a.length; j++ )
            {
                thisSum += a[j];

                if( thisSum > maxSum )
                {
                    maxSum = thisSum;
                    sequenceStart = i;
                    sequenceEnd   = j;
                }
            }
        }

        return new int[] {maxSum, sequenceStart, sequenceEnd};
    }
	
	
	
	// Recursive part for algorithm 3
	private static int[] maxSumRec(int[] a, int left, int right)
    {
		int center = (left + right) / 2;
        int maxLeftBorderSum = 0, maxRightBorderSum = 0;
        int leftBorderSum = 0, rightBorderSum = 0;
        
        int leftIndStart = left;
        int leftIndEnd = center;
        
        int midIndStart = center;
        int midIndEnd = center + 1;
        
        int rightIndStart = center + 1;
        int rightIndEnd = right;
        

        if( left == right )  // Base case
        {
        	if (a[left] > 0)
        	{
        		return new int[] {left, a[left], right};
        	}
        	
        	else
        	{
        		return new int[] {leftIndStart, 0, rightIndEnd};
        	}
        }
            

        int[] maxLeftSum  = maxSumRec(a, left, center);
        leftIndStart = maxLeftSum[0];
        leftIndEnd = maxLeftSum[2];
        
        int[] maxRightSum = maxSumRec(a, center + 1, right);
        rightIndStart = maxRightSum[0];
        rightIndEnd = maxRightSum[2];
        
        
        for (int i = center; i >= left; i--)
        {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum)
            {
                maxLeftBorderSum = leftBorderSum;
                midIndStart = i;
            }
        }
        
        
        for (int i = center + 1; i <= right; i++)
        {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum)
            {   
            	maxRightBorderSum = rightBorderSum;
            	midIndEnd = i;
            }
        }

        return maxOfSeq(maxLeftSum, maxRightSum,
                     new int[] {midIndStart, maxLeftBorderSum + maxRightBorderSum, midIndEnd} );
    }
	
	// Returns array that contains the largest one index
	public static int[] maxOfSeq(int[] x, int[] y, int[] z)
	{
		return x[1] > y[1] ? x[1] > z[1] ? x : z : y[1] > z[1] ? y : z;
	}
		
	
	//Driver for the maxSumRec function
	public static int[] maxSumRecDriver(int[] a)
	{
		if (a.length > 0)
		{
			return maxSumRec(a, 0, a.length - 1);
		}
		
		else
		{
			return new int[] {0,0,0};
		}
	}
	
	
	// Linear-time maximum contiguous subsequence sum algorithm
		
	public static int[] maxSubSum4(int[] a)
	{
		
		int maxSum = 0, thisSum = 0;
	
		for (int i = 0, j = 0; j < a.length; j++)
		{
			thisSum += a[j];				
			if (thisSum > maxSum)
			{
				maxSum = thisSum;
				sequenceStart = i;
				sequenceEnd = j;
			}				
			else if (thisSum < 0)
			{
				thisSum = 0;
				i = j + 1;
			}			
		}
			
			return new int[] {maxSum, sequenceStart, sequenceEnd};
	}
	
}
