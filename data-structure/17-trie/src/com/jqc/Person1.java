package com.jqc;

public class Person1 implements Comparable<Person1>{

	private String name;
	private int boneBreak;
	public Person1(String name, int boneBreak) {
		this.name = name;
		this.boneBreak = boneBreak;
	}

	public int getBoneBreak() {
		return boneBreak;
	}

	@Override
	public int compareTo(Person1 person) {
		return this.boneBreak - person.boneBreak;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", boneBreak=" + boneBreak + "]";
	}
}
