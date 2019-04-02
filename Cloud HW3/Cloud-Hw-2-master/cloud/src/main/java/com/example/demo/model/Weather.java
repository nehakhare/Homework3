package com.example.demo.model;

public class Weather {
private String tmax;
private String tmin;
private String date;
public String getTmax() {
	return tmax;
}
public void setTmax(String tmax) {
	this.tmax = tmax;
}
public String getTmin() {
	return tmin;
}
public void setTmin(String tmin) {
	this.tmin = tmin;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
@Override
public String toString() {
	return "Weather [tmax=" + tmax + ", tmin=" + tmin + ", date=" + date + "]";
}

}
