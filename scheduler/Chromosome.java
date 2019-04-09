package scheduler;

import java.io.*;
import java.util.*;

//Chromosome represents array of genes as complete timetable (looks like gene[0]gene[1]gene[2]...)
public class Chromosome implements Comparable<Chromosome>,Serializable{
	
	static double crossoverrate=inputdata.crossoverrate;
	static double mutationrate=inputdata.mutationrate;
	static int hours=inputdata.hoursperday,days=inputdata.daysperweek;
	static int nostgrp=inputdata.nostudentgroup;
	double fitness;
	int point;
	int roomclash;
	int adjclash;
	
	public Gene[] gene;
	
	Chromosome()
	{
		
		gene=new Gene[nostgrp];
		
		for(int i=0;i<nostgrp;i++){
			
			gene[i]=new Gene(i);
			
			//System.out.println("");
		}
		fitness=getFitness();		
		
	}
	
	public Chromosome deepClone()
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Chromosome) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public double getFitness()
	{
		point=0;
		roomclash=0;
		adjclash=0;
		for(int i=0;i<hours*days;i++)
		{
			
			List<Integer> teacherlist=new ArrayList<Integer>();
			List<Integer> roomslist=new ArrayList<Integer>();
			for(int j=0;j<nostgrp;j++)
			{
			
				Slot slot;
				//System.out.println("i="+i+" j="+j);
				if(TimeTable.slot[gene[j].slotno[i]]!=null)
				{
					slot=TimeTable.slot[gene[j].slotno[i]];
				}
				else 
				{
					slot=null;
				}

				if(slot!=null)
				{
					if(teacherlist.contains(slot.teacherid))
					{
						point++;
					}
					else 
					{
						teacherlist.add(slot.teacherid);
					}
					if(roomslist.contains(gene[j].rooms[i]))
					{
						roomclash++;
					}
					else 
					{
						roomslist.add(gene[j].rooms[i]);
					}
				}
			}	
		}
		for(int i=0;i<nostgrp;i++)
		{
			for(int j=0;j<hours*days-1;j++)
			{
				if( (j%(hours-1)!=0) && (TimeTable.slot[gene[i].slotno[j]]!=null) &&  (TimeTable.slot[gene[i].slotno[j+1]]!=null) )
				{
					if (TimeTable.slot[gene[i].slotno[j]].subject==TimeTable.slot[gene[i].slotno[j+1]].subject) 
					{
						if(TimeTable.slot[gene[i].slotno[j]].length==1 && TimeTable.slot[gene[i].slotno[j+1]].length==1)
						{
							//System.out.print(TimeTable.slot[gene[i].slotno[j]].subject+" "+j);
							adjclash++;
						}
					}
				}
			}
		}
		//System.out.println(point);
		fitness=1-((adjclash+point+roomclash)/(2*(nostgrp-1.0)*hours*days));
		return fitness;
	}
	
	
	
	//printing final timetable
	public void printTimeTable(){
		
		//for each student group separate time table
		for(int i=0;i<nostgrp;i++)
		{	
			//status used to get name of student grp because in case first class is free it will throw error
			boolean status=false;
			int l=0;
			while(!status){
				
				//printing name of batch
				if(TimeTable.slot[gene[i].slotno[l]]!=null){
					System.out.println("Batch "+TimeTable.slot[gene[i].slotno[l]].studentgroup.name+" Timetable-");
					
					status=true;
				}
				l++;
			
			}
			
			//looping for each day
			for(int j=0;j<days;j++){
				
				//looping for each hour of the day
				for(int k=0;k<hours;k++){
				
					//checking if this slot is free otherwise printing it
					if(TimeTable.slot[gene[i].slotno[k+j*hours]]!=null)
					{
						String name=inputdata.teacher[TimeTable.slot[gene[i].slotno[k+j*hours]].teacherid].name;
						System.out.print(TimeTable.slot[gene[i].slotno[k+j*hours]].subject+" "+gene[i].rooms[k+j*hours]+"    ");
					}
					else System.out.print("   *FREE*   ");
				
				}
				
				System.out.println("");
			}
			
			System.out.println("\n\n\n");
		
		}

	}
	
	
	
	public void printChromosome()
	{
		System.out.print("Teacherclash "+point);
		System.out.println("");
		System.out.print("Roomsclash "+roomclash);
		System.out.println("");
		System.out.print("Adj "+adjclash);
		System.out.println("");
		for(int i=0;i<nostgrp;i++)
		{
			for(int j=0;j<hours*days;j++)
			{
				System.out.print(gene[i].slotno[j]+" ");
			}
			System.out.println("");
		}
		
	}



	public int compareTo(Chromosome c) {
		
		if(fitness==c.fitness) return 0;
		else if(fitness>c.fitness) return -1;
		else return 1;
	
	}
}