# User Registration Web Application

## **Overview**

This Java-based web application allows users to register via a simple form. The data is stored in a MySQL database, and upon successful registration, the user sees a success message. The application is built using Java Servlets, MySQL, and Apache Tomcat.

---

## **Technologies Used**

- **Java Servlet**: For handling the business logic.
- **MySQL**: For storing user data.
- **JSP**: For rendering dynamic content (success/error messages).
- **HTML/CSS/JavaScript**: For frontend user interface.
- **Apache Tomcat**: For deploying the servlet application.

---

## **Features**

- **User Registration**: Users can enter their name, email, date of birth, gender, and phone number.
- **Database Storage**: User details are saved in a MySQL database.
- **Success Message**: After registration, users receive a success message confirming their registration.
- **Responsive UI**: The registration page is built using HTML and CSS to ensure proper layout.

---

## **Database Schema**

The following table is used to store user registration data:

```sql
CREATE TABLE Registration (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10),
    PhoneNumber VARCHAR(15),
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
