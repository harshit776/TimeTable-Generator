package scheduler;

//through this class slots are generated to match minimum hours criteria for each subject
//also this class holds slots in order
public class TimeTable {

	public static Slot[] slot;

	TimeTable() {

		int k = 0;
		int subjectno = 0;
		int labcnt=0;
		int hourcount = 1;
		int days = inputdata.daysperweek;
		int hours = inputdata.hoursperday;
		int nostgrp = inputdata.nostudentgroup;

		// creating as many slots as the no of blocks in overall timetable
		slot = new Slot[hours * days * nostgrp];

		// looping for every student group
		for (int i = 0; i < nostgrp; i++) 
		{

			subjectno = 0;
			labcnt=0;
			// for every slot in a week for a student group
			for (int j = 0; j < hours * days; j++) 
			{

				StudentGroup sg = inputdata.studentgroup[i];

				// if all subjects have been assigned required hours we give
				// free periods
				if (subjectno+labcnt >= sg.nosubject+sg.nolabs) 
				{
					slot[k++] = null;
				}

				// if not we create new slots
				else 
				{
					if(subjectno < sg.nosubject)
					{
						slot[k++] = new Slot(sg,sg.teacherid[subjectno],sg.subject[subjectno],1);
						inputdata.studentgroup[i].total_hrs++;
					}
					else if(subjectno >= sg.nosubject)
					{
						slot[k++] = new Slot(sg,sg.labinstructor[labcnt],sg.labs[labcnt],2);
						inputdata.studentgroup[i].total_hrs+=2;
						labcnt++;
					}
					// suppose java has to be taught for 5 hours then we make 5
					if (hourcount < sg.hours[subjectno]) 
					{
						hourcount++;
					}
					else 
					{
						hourcount = 1;
						subjectno++;
					}

				}

			} // end inner for

		} // end outer for

	}// end constructor

	public static Slot[] returnSlots() 
	{
		return slot;
	}
}