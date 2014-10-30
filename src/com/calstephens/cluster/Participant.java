package com.calstephens.cluster;

import java.util.ArrayList;

public class Participant {

    static ArrayList<ClusterList> clusters = new ArrayList<ClusterList>();
    static ArrayList<String> clusterNames = new ArrayList<String>();
    static ArrayList<String> commaNames = new ArrayList<String>();

    final protected String first;
    final protected String last;
    final protected String gender;
    final protected String grade;
    final protected String cluster;
    protected String alts;

    public boolean isOk = true;
    public String reason = null;

    private Gender sex;
    private int numGrade;

    private ArrayList<ClusterList> alternatives = null;
    ClusterList currentCluster;

    public Participant(String first, String last, String gender, String grade, String cluster, String alts, int maximum) {
	this.first = first;
	this.last = last;
	this.gender = gender;
	this.grade = grade;
	this.cluster = cluster;
	this.alts = alts;

	try {
	    if(gender.toUpperCase().startsWith("G"))
		gender = "F";
	    if(gender.toUpperCase().startsWith("B"))
		gender = "M";
	    sex = Gender.valueOf(gender.toUpperCase().substring(0, 1));
	} catch(Exception e) {
	    sex = null;
	    isOk = false;
	    reason = "Invalid gender: " + gender;
	}

	try {
	    numGrade = Integer.valueOf(grade.substring(0, 1));
	} catch(NumberFormatException e) {
	    if(grade.toUpperCase().startsWith("K"))
		numGrade = 0;
	    else {
		isOk = false;
		reason = "Invalid grade: " + grade.substring(0, 1);
	    }
	}
	
	if(cluster.contains(",") && !commaNames.contains(cluster)){
	    commaNames.add(cluster);
	}
	
	if(clusterNames.contains(cluster)) {
	    for(ClusterList clusterlist : clusters) {
		if(clusterlist.name.equals(cluster)) {
		    clusterlist.participants.add(this);
		    currentCluster = clusterlist;
		    break;
		}
	    }
	} else {
	    currentCluster = new ClusterList(cluster, maximum);
	    currentCluster.participants.add(this);
	    clusters.add(currentCluster);
	    clusterNames.add(cluster);
	}

    }

    public enum Gender {

	M, F;
    }

    public int getGrade() {
	return numGrade;
    }

    public Gender getGender() {
	return sex;
    }

    public ArrayList<ClusterList> getAlts() {
	return alternatives;
    }

    @Override
    public String toString() {
	return "Participant[" + first + " " + last + "(" + numGrade + "," + sex + ")" + "]";
    }

    public void prepareAlternates() {
	if(alternatives == null) {
	    alternatives = new ArrayList<ClusterList>();
	    
	    for(String comma : Participant.commaNames){
		if(alts.contains(comma)){
		    alts = alts.replace(comma, comma.replace(",", "~"));
		}
	    }
	    String[] altsList = alts.split(", ");
	    for(int i = 0; i < altsList.length; i++){
		if(altsList[i].contains("~")){
		    altsList[i] = altsList[i].replace("~", ",");
		}
	    }
	    for(String s : altsList) {
		if(!clusterNames.contains(s)) {
		    clusterNames.add(s);
		    ClusterList clusterlist = new ClusterList(s, 15);
		    alternatives.add(clusterlist);
		    clusters.add(clusterlist);
		} else {
		    for(ClusterList clusterlist : clusters) {
			if(clusterlist.name.equals(s)) {
			    alternatives.add(clusterlist);
			    break;
			}
		    }
		}
	    }

	}
    }
}
