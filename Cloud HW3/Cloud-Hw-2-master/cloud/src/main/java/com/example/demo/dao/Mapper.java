package com.example.demo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Weather;
@RestController
public class Mapper {
@Autowired
FileRead fe;

//static List<Weather> ls=fe.getList();

@GetMapping("/weather")
public ArrayList<Weather> getList() throws IOException
{
	return fe.getList();
}

@GetMapping("/historical")
public List<String> getData() throws IOException
{
	String st;
	List<String> local=new ArrayList();
	List<Weather> fr=fe.getList();
	Iterator it=fr.iterator();
	while(it.hasNext())
	{
		Weather wea=(Weather) it.next();
		st=wea.getDate();
		local.add(st);
	}
	
	return local;
}


@GetMapping("/historical/{date}")
public ResponseEntity<Weather> getDate(@PathVariable String date) throws IOException 
{
	
	ResponseEntity<Weather> re;
	String st;
	List<Weather> fr=fe.getList();
	Iterator it=fr.iterator();
	while(it.hasNext())
	{
		Weather wea=(Weather) it.next();
		st=wea.getDate();
		if(wea.getDate().equals(date))
		{
	
			re=new ResponseEntity<Weather>(wea,HttpStatus.OK);
			return re;	
		}
	}
	re=new ResponseEntity<Weather>(HttpStatus.BAD_REQUEST);
	return re;
}
@DeleteMapping("/historical/{date}")
public ResponseEntity<HttpStatus> deleteWeather(@PathVariable String date) throws IOException
{
	
	String st;
	List<Weather> fr=fe.getList();
	Iterator it=fr.iterator();
	while(it.hasNext())
	{
		Weather wea=(Weather)it.next();
		if(wea.getDate().equals(date));
		{
			fr.remove(wea);
			System.out.println("Date deleated");
			ResponseEntity<HttpStatus> re=new ResponseEntity(HttpStatus.ACCEPTED);
			return re;
		}
	}
	ResponseEntity<HttpStatus> re=new ResponseEntity(HttpStatus.CONFLICT);
	return re;
}
@PostMapping("/historical")
public ResponseEntity<Map> postWeather(@RequestBody Weather weather) throws IOException
{
	List<Weather> fr=fe.getList();
	Iterator it=fr.iterator();
	while(it.hasNext())
	{
		Weather wea=(Weather) it.next();
		if((wea.getDate()).equals(weather.getDate()))
		{
			wea.setTmax(weather.getTmax());
			wea.setTmin(weather.getTmin());
			Map<String,String> mp=new HashMap<String, String>();
			mp.put("Date", weather.getDate());
			ResponseEntity<Map> re=new ResponseEntity(mp,HttpStatus.ACCEPTED);
			return re;
		}
	}
	Map<String,String> mp=new HashMap();
	mp.put("Date", weather.getDate());
	ResponseEntity<Map> re=new ResponseEntity(mp,HttpStatus.ACCEPTED);
	fr.add(weather);
	return re;
}
@GetMapping("/forecast/{date}")
public List<Weather> forecastWeather(@PathVariable String date) throws IOException
{
	Random rd=new Random();
	List<Weather> fr=fe.getList();
	ListIterator it=fr.listIterator();
	List<Weather> ret=new ArrayList();
	boolean check=false;
	while(it.hasNext())
	{
	 Weather wea=(Weather)it.next();
	 Weather wea2=new Weather();
	 int i;
	        
	 
	 if((wea.getDate()).equals(date))
	 {
	  check=true;
	  System.out.println(wea.getDate());
	  double stmax,stmin;
	  
	  for(i=7;i>0;i--)
	  {
	   if(it.hasNext())
	   {
	   wea2=(Weather)it.next();
	   ret.add(wea2);
	   }
	   else
	    break;
	  }
	  if(i==0)
	   return ret;

	  String d=wea2.getDate();
	  for(int j=0;j<i;j++)
	  {
	   int year=Integer.parseInt(d.substring(0, 4)),month=Integer.parseInt(d.substring(4,6)),day=Integer.parseInt(d.substring(6,8));
	   //int[] odd={1,3,5,7,8,10,12};
	   Set<Integer> st_odd=new HashSet();
	      st_odd.add(1);
	      st_odd.add(3);
	      st_odd.add(5);
	      st_odd.add(7);
	      st_odd.add(8);
	      st_odd.add(10);
	      st_odd.add(12);
	      
	   int[] even= {4,6,9,11};
	   Set<Integer> st_even=new HashSet(Arrays.asList(even));
	   st_odd.add(4);
	      st_odd.add(6);
	      st_odd.add(9);
	      st_odd.add(11);
	      
	      
	   if(st_odd.contains(month))
	   {
	    if(day!=31)
	    {
	     day++;
	    }
	    else
	    {
	     day=1;
	     if(month==12)
	     {
	      month=1;
	      year++;
	     }
	     else
	     {
	      month++;
	     }
	     
	    }
	   }
	   else if(st_even.contains(month))
	   {
	    if(day!=30)
	    {
	     day++;
	    }
	    else
	    {
	     day=1;
	     month++;
	    }
	   }
	   else
	   {
	    //checking leap
	    boolean leap = false;

	          if(year % 4 == 0)
	          {
	              if( year % 100 == 0)
	              {
	                  // year is divisible by 400, hence the year is a leap year
	                  if ( year % 400 == 0)
	                      leap = true;
	                  else
	                      leap = false;
	              }
	              else
	                  leap = true;
	          }
	          else
	              leap = false;
	          
	          //if not leap
	          if(leap==false)
	          {
	           if(day!=28)
	           {
	            day++;
	           }
	           else
	           {
	            day=1;
	            month++;
	           }
	          }
	          else
	          {
	           if(day!=29)
	           {
	            day++;
	           }
	           else
	           {
	            day=1;
	            month++;
	           }
	          }
	   }
	      
	   //converting into string
	   String year_string=String.valueOf(year);
	   String month_string,day_string;
	      month_string=String.valueOf(month);
	   if(month<10)
	    month_string="0"+month_string;
	   day_string=String.valueOf(day);
	   if(day<10)
	    day_string="0"+day_string;
	      String date_new=year_string+month_string+day_string;
	      //generating t_max and t_min
	      int count_years=0;
	      double t_max_sum=0,t_min_sum=0;
	      ListIterator itr=fr.listIterator();
	      
	   while(itr.hasNext())
	   {
	      Weather weather=(Weather) itr.next();
	     // System.out.println(weather.getDate().substring(4).equals(month_string+day_string));
	      if(weather.getDate().substring(4).equals(month_string+day_string))
	      {
	       double temp_1=Double.parseDouble(weather.getTmax());
	       double temp_2=Double.parseDouble(weather.getTmin());
	       t_max_sum=t_max_sum+temp_1+rd.nextInt(3);
	       t_min_sum=t_min_sum+temp_2+rd.nextInt(3);
	       count_years++;
	      }
	   }
	      t_max_sum/=count_years;
	      t_min_sum/=count_years;
	      Weather temp=new Weather();
	      temp.setDate(date_new);
	      temp.setTmax(String.valueOf(t_max_sum));
	      temp.setTmin(String.valueOf(t_min_sum));
	      ret.add(temp);
	      d=date_new;
	  }
	  
	 }
	 

	 
	}


	if(check==false)
	{
	 for(int i=0;i<7;i++) {
	 int year=Integer.parseInt(date.substring(0, 4)),month=Integer.parseInt(date.substring(4,6)),day=Integer.parseInt(date.substring(6,8));
	 //int[] odd={1,3,5,7,8,10,12};
	 Set<Integer> st_odd=new HashSet();
	    st_odd.add(1);
	    st_odd.add(3);
	    st_odd.add(5);
	    st_odd.add(7);
	    st_odd.add(8);
	    st_odd.add(10);
	    st_odd.add(12);
	    
	 int[] even= {4,6,9,11};
	 Set<Integer> st_even=new HashSet(Arrays.asList(even));
	 st_odd.add(4);
	    st_odd.add(6);
	    st_odd.add(9);
	    st_odd.add(11);
	    
	    
	 if(st_odd.contains(month))
	 {
	  if(day!=31)
	  {
	   day++;
	  }
	  else
	  {
	   day=1;
	   if(month==12)
	   {
	    month=1;
	    year++;
	   }
	   else
	   {
	    month++;
	   }
	   
	  }
	 }
	 else if(st_even.contains(month))
	 {
	  if(day!=30)
	  {
	   day++;
	  }
	  else
	  {
	   day=1;
	   month++;
	  }
	 }
	 else
	 {
	  //checking leap
	  boolean leap = false;

	        if(year % 4 == 0)
	        {
	            if( year % 100 == 0)
	            {
	                // year is divisible by 400, hence the year is a leap year
	                if ( year % 400 == 0)
	                    leap = true;
	                else
	                    leap = false;
	            }
	            else
	                leap = true;
	        }
	        else
	            leap = false;
	        
	        //if not leap
	        if(leap==false)
	        {
	         if(day!=28)
	         {
	          day++;
	         }
	         else
	         {
	          day=1;
	          month++;
	         }
	        }
	        else
	        {
	         if(day!=29)
	         {
	          day++;
	         }
	         else
	         {
	          day=1;
	          month++;
	         }
	        }
	 }
	    
	 //converting into string
	 String year_string=String.valueOf(year);
	 String month_string,day_string;
	    month_string=String.valueOf(month);
	 if(month<10)
	  month_string="0"+month_string;
	 day_string=String.valueOf(day);
	 if(day<10)
	  day_string="0"+day_string;
	    String date_new=year_string+month_string+day_string;
	    //generating t_max and t_min
	    int count_years=0;
	    double t_max_sum=0,t_min_sum=0;
	    ListIterator itr=fr.listIterator();
	    
	 while(itr.hasNext())
	 {
	    Weather weather=(Weather) itr.next();
	   // System.out.println(weather.getDate().substring(4).equals(month_string+day_string));
	    if(weather.getDate().substring(4).equals(month_string+day_string))
	    {
	     double temp_1=Double.parseDouble(weather.getTmax());
	     double temp_2=Double.parseDouble(weather.getTmin());
	     t_max_sum=t_max_sum+temp_1+rd.nextInt(3);
	     t_min_sum=t_min_sum+temp_2+rd.nextInt(3);
	     count_years++;
	    }
	 }
	    t_max_sum/=count_years;
	    t_min_sum/=count_years;
	    Weather temp=new Weather();
	    temp.setDate(date_new);
	    temp.setTmax(String.valueOf(Math.round(t_max_sum)));
	    temp.setTmin(String.valueOf(Math.round(t_min_sum)));
	    ret.add(temp);
	    date=date_new;

	}
	}

	return ret;

	}
}
