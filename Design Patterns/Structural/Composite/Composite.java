/*
Exercise: Replicate the Composite Design Pattern example (Organizational Chart) here.

Components:
1.  Component Interface: Employee (displayDetails(), getSalary())
2.  Leaf Node: IndividualEmployee (name, position, salary)
3.  Composite Node: Department (name, List<Employee> members, addMember(), removeMember())

Demonstrate:
-   Creating individual employees.
-   Creating departments and adding employees/other departments to them.
-   Calling displayDetails() and getSalary() uniformly on both individual employees and departments.
*/

import java.util.ArrayList;
import java.util.List;

interface Employee {
    void displayDetails();

    double getSalary();
}

class IndividualEmployee implements Employee {
    private String name, position;
    private double salary;

    public IndividualEmployee(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public void displayDetails() {
        System.out.println(" - Employee: " + name + ", position: " + position + ", Salary: $" + salary);
    }

    @Override
    public double getSalary() {
        return salary;
    }
}

class Department implements Employee {
    private String name;
    private List<Employee> members;

    public Department(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(Employee employee) {
        members.add(employee);
    }

    public void removeMember(Employee employee) {
        members.remove(employee);
    }

    @Override
    public void displayDetails() {
        System.out.println("Department name: " + name);
        for (Employee member : members) {
            member.displayDetails();
        }
    }

    @Override
    public double getSalary() {
        double salary = 0.0;
        for (Employee member : members) {
            salary += member.getSalary();
        }
        return salary;
    }
}

public class Composite {
    public static void main(String[] args) {
        IndividualEmployee dev1 = new IndividualEmployee("Alice", "Developer", 70000);
        IndividualEmployee dev2 = new IndividualEmployee("Boe", "Developer", 70000);
        IndividualEmployee qa = new IndividualEmployee("Mike", "QA", 60000);
        IndividualEmployee manager1 = new IndividualEmployee("David", "Manager", 90000);

        Department engineeringDept = new Department("Engineering");
        engineeringDept.addMember(dev1);
        engineeringDept.addMember(dev2);
        engineeringDept.addMember(qa);

        Department salesDept = new Department("Sales");
        salesDept.addMember(new IndividualEmployee("Eve", "Sales Rep", 65000));
        salesDept.addMember(new IndividualEmployee("Frank", "Sales Manager", 60000));

        Department company = new Department("Our Company");
        company.addMember(manager1); // Manager is a leaf
        company.addMember(engineeringDept); // Engineering is a composite
        company.addMember(salesDept); // Sales is a composite

        // Client treats individual objects and compositions uniformly
        System.out.println("--- Company Structure and Salaries ---");
        company.displayDetails();
        System.out.println("\nTotal Company Salary: $" + company.getSalary());

        System.out.println("\n--- Engineering Department Details ---");
        engineeringDept.displayDetails();
        System.out.println("\nTotal Engineering Salary: $" +
                engineeringDept.getSalary());
    }
}