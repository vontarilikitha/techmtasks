package springex;

public class Studentimp implements Student{
    private String name;
    private int age;
    private String grade;
    public Studentimp() {}
    public Studentimp(String name,int age) {
    	this.name=name;
    	this.age=age;
    
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getgrade() {
		return grade;
	}
	public void setgrade(String grade) {
		this.grade = grade;
	}
	public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade='" + grade + '\'' +
                '}';
    }
}
