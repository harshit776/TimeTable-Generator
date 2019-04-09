package scheduler;

import java.util.Random;
import java.io.*;

//gene represents permutation of slots as timetable for a single student group(looks like {5,22,14,1,...} )
public class Gene implements Serializable
{

	public int slotno[];
	public int rooms[];
	int days=inputdata.daysperweek;
	int hours=inputdata.hoursperday;
	public int temp;
	public int lab_index;
	Random r=new Random();
	public int free=1;
	public int maxfree;
	Gene(int i)
	{
		//System.out.println("------------------------"+i+"---------------------------");
		boolean[] flag=new boolean[days*hours];
		//boolean[] flag=new boolean[days*hours];
		/*  generating an array of slot no corresponding to index of gene eg suppose index
		 *	is 2 then slotno will vary from 2*hours*days to 3*hours*days
		 */
		maxfree=(days*hours)-inputdata.studentgroup[i].total_hrs;
		//System.out.println("-------"+maxfree+"-----------");
		slotno=new int[days*hours];
		rooms=new int[days*hours];
		for(int j=0;j<days*hours;j++)
		{
			
			int rnd;
			//System.out.println(free);
			while(flag[rnd=r.nextInt(days*hours)]==true){}
			temp=i*days*hours + rnd;
			//System.out.println(j);
			if(TimeTable.slot[temp]==null && free<=maxfree)
			{
				free++;
				slotno[j]=temp;
				flag[rnd]=true;
				rooms[j]=r.nextInt(11);
				continue;
			}
			else if(TimeTable.slot[temp]==null && free>maxfree)
			{
				j--;
				continue;
			}
			if((j+1)%hours==0 && TimeTable.slot[temp].length==2)
			{
				j--;
				continue;
			}
			//System.out.println(" "+TimeTable.slot[temp].length);
			if(TimeTable.slot[temp].length==2)
			{
				//System.out.println(j);
				slotno[j]=temp;
				slotno[j+1]=temp;
				flag[rnd]=true;
				lab_index=11+r.nextInt(4);
				rooms[j]=lab_index;
				rooms[j+1]=lab_index;
				j++;
			}
			else
			{
				slotno[j]=temp;
				flag[rnd]=true;
				rooms[j]=r.nextInt(11);
			}
			/*	Slot[] slot=TimeTable.returnSlots();
			 *	if(slot[slotno[j]]!=null)System.out.print(slot[slotno[j]].subject+" ");
			 *	else System.out.print("break ");
			 */
		}
		
	}
	
	public Gene deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Gene) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}