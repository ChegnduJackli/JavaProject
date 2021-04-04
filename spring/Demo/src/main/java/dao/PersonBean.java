package dao;

public class PersonBean {
    private String name;
    private int age;

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

    // 重写toString()方法
    public String toString() {
        return "PersonBean[name=" + name + ",age=" + age + "]";
    }

    // 默认无参的构造方法
    public PersonBean() {
        super();
    }

    // 有参的构造方法
    public PersonBean(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
}
