
// Class used to measure the time for each
// algorithm to run.
public class ExecutionTimer 
{			
	private long start;  
	
	private long end;  
			
	public ExecutionTimer() 
	{    			
		reset();  
	}
			
	public void start()
	{    
		start = System.nanoTime();  
	}
		
	public void end() 
	{    
		end = System.nanoTime();  
	}  
	
	public long duration()
	{    
		return (end-start);  
	}  
	
	public void reset() 
	{    
		start = 0;      
		end = 0;  
	}  
	
	public void print() 
	{    
		System.out.print(duration()+",");  
	}			
}