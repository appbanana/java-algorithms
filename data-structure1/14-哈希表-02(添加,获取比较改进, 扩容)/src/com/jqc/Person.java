package com.jqc;

public class Person  {
	private int age;
	private float height;
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(int age) {
		this.age = age;
	}

	public Person(int age, float height, String name) {
		this.age = age;
		this.name = name;
		this.height = height;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || obj.getClass() != getClass()) return false;

		Person person = (Person) obj;
		// 比较成员变量 如果每一个成员变量都相等则相等
		return person.name == name && person.age == age && person.height == height;
	}

	@Override
	public int hashCode() {
		int hashCode = Integer.hashCode(age);
		hashCode = hashCode * 31 + Float.hashCode(height);
		hashCode = hashCode * 31 + (name == null ? 0 : name.hashCode());
		return hashCode;
	}

	@Override
	public String toString() {
		return name + "_" + age  + "_" + height;
	}
	
}
