# APU Supervision Management System 🎓

A standalone desktop application built in Java to automate academic supervision allocations and appointment workflows at Asia Pacific University (APU). The system implements a robust Object-Oriented Programming (OOP) design pattern and features an interactive Graphical User Interface (GUI) driven by Java Swing/AWT, running on a secure, text-file data persistence layer.

---

## 🛠️ Project Structure & Architectural Layers

The repository is organized into distinct logic modules, GUI forms, and local data tables:

### 🧱 Core OOP Entities (Data Models)
* `User.java` - The foundational base class implementing encapsulation for general user data.
* `Student.java` / `Supervisor.java` / `FacultyAdmin.java` / `SystemAdmin.java` - Specialized subclasses that inherit from the core User class to execute role-specific behaviors.
* `Appointment.java` / `TimeSlot.java` / `Feedback.java` - Logic models structuring consultation records, calendar availabilities, and grading remarks.

### 🖥️ GUI Controller Windows (Java Swing/AWT Forms)
* `LoginPage2.java` - Secure portal managing multi-tenant gateway entry and credential verification routes.
* `StudentPage.java` / `StudentAppointments.java` - Student interface for tracking supervisors, booking time slots, and reading text feedback.
* `SupervisorPage.java` / `TimeslotGUI.java` - Faculty panel for scheduling availabilities, approving requests, and appending student evaluations.
* `FacultyAdmin_FrameAssignment.java` / `_FrameReport.java` - Admin operational board managing student allocations and generating filtered intake metrics.
* `SystemadminPage.java` / `SystemAdminMainMenu.java` - Master control panel facilitating account creations, password overrides, and login log audits.

### 📂 Flat-File Storage Engine
* `Accounts.txt` / `students.txt` / `supervisor.txt` / `systemAdmin.txt` - Flat text files storing multi-tenant user access rights.
* `appointments.txt` / `timeslot.txt` - Databases capturing live consultation timelines and pending statuses.

---

## 🚀 Key Object-Oriented Features Implemented

* **Inheritance & Polymorphism:** Utilizes a structured class hierarchy extending from a unified `User` model, cleanly segregating distinct role behaviors without duplicate blocks.
* **Encapsulation:** Protects internal data states using private fields exposed selectively via strict getter and setter parameters.
* **Method Overriding:** Implements specialized execution logic across discrete frames to safely manage unique system operations.
* **Defensive GUI Validation:** Leverages dedicated validation filters (`FA_AssignmentValidation.java`, `FA_TableFilter.java`) to scan input fields, blocking type exceptions and application crashes.

---

## 💻 Technical Setup & Execution

### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* NetBeans IDE (Recommended) or any Java environment supporting Swing forms.

### Execution Steps
1. Open the project folder inside your IDE (configured with the `build.xml` parameters).
2. Compile and launch the application entry target:
   ```bash
   java -jar oopAssignment.jar
