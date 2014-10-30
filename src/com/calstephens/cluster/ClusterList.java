package com.calstephens.cluster;

import java.util.ArrayList;

public class ClusterList {

    final String name;
    int maximum;
    boolean allow1;
    boolean allow2;
    boolean allow3;
    boolean allow4;
    boolean allow5;

    final ArrayList<Participant> participants = new ArrayList<Participant>();

    public ClusterList(String name, int maximum) {
	this.name = name;
	this.maximum = maximum + 1;
    }

    public boolean addParticipant(Participant p) {
	if(participants.size() >= maximum)
	    return false;
	else {
	    participants.add(p);
	    return true;
	}
    }

    @Override
    public String toString() {
	return "ClusterList[" + name + "; " + participants.size() + "/" + maximum + "]";
    }

    public boolean isGradeAllowed(int grade) {
	switch(grade) {
	    case 1:
		return allow1;
	    case 2:
		return allow2;
	    case 3:
		return allow3;
	    case 4:
		return allow4;
	    case 5:
		return allow5;
	    default:
		return false;
	}
    }

}
