package com.example.demo.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.example.demo.model.Weather;
@Component
public class FileRead {
	ArrayList<Weather> fr=new ArrayList();
@PostConstruct
public void getListH() throws IOException
{
	
	try
	{
	Reader inputStreamReader = new InputStreamReader(new FileInputStream("./dailyweather.csv"));
	BufferedReader buf=new BufferedReader(inputStreamReader);
	buf.readLine();
    String s;
	String[] str;
    Double d;
    while ((s = buf.readLine()) != null) 
	{
	str=s.split(",");
	Weather wea=new Weather();
	wea.setDate(str[0]);
	wea.setTmax(str[1]);
	wea.setTmin(str[2]);
	fr.add(wea);
	}
	
	}
	catch(Exception e) {
		
	}

}
public ArrayList<Weather> getList() throws IOException
{
	return fr;
}
}
