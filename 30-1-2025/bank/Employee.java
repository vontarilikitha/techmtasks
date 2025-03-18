package bank;

public class Employee {
         private int empid;
         private String empname;
         private double sal;
         private int years;
		
		
		public Employee(int empid,String empname,double sal,int years) {
			this.empid=empid;
			this.empname=empname;
			this.sal=sal;
			this.years=years;
		}
		public void getemployeedetails() {
			System.out.println("Employee ID: " + empid);
	        System.out.println("Employee Name: " + empname);
	        System.out.println("Salary: " + sal);
	        System.out.println("Years Worked: " + years);
		}
		public String getLoanEligibility() {
	        if (years <= 5) {
	            return "Not eligible for loan.";
	        }

	        double annualSalary = sal * 12;  

	        if (annualSalary >= 600000 && annualSalary < 1000000) {
	            return "Eligible for 2 Lakhs loan.";
	        } else if (annualSalary >= 1000000 && annualSalary < 1500000) {
	            return "Eligible for 5 Lakhs loan.";
	        } else if (annualSalary >= 1500000) {
	            return "Eligible for 7 Lakhs loan.";
	        } else {
	            return "Not eligible for loan (Salary does not meet criteria).";
	        }
	    }
		public static void main(String [] args) {
		Employee emp=new Employee(1,"ram",500000,5);
		emp.getemployeedetails();
		System.out.println(emp.getLoanEligibility());
		}
}
