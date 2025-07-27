class ContactC {
    String name;
    String phone;

    ContactC(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    void display() {
        System.out.println("Name: " + name + ", Phone: " + phone);
    }
}
