package scheduler;

//slot is single block of timetable

public class Slot
{
	public StudentGroup studentgroup;
	public int teacherid;
	public String subject;
	public int length;
	
	//non parametrized constructor for allowing free periods
	Slot(){};
	
	Slot(StudentGroup studentgroup,int teacherid,String subject,int len)
	{
		
		this.studentgroup=studentgroup;
		this.subject=subject;
		this.teacherid=teacherid;
		this.length=len;
	}
}
