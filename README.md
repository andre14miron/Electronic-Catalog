# Electronic Catalog

## Overview
This project focuses on implementing an electronic catalog using object-oriented programming principles. The catalog stores various information about courses, grades, and users. Additionally, it provides functionality for adding grades, validating them, and notifying parents. The system also includes a user-friendly graphical interface for students, teacher assistants, and parents.

## Implementation Details
### Catalog
The core of the project is the Catalog class, which represents the electronic catalog. It follows the `Singleton` design pattern, ensuring that only one instance of the catalog exists. The class includes the following members:

- **catalog**: the singleton instance of the catalog
- **courses**: an ArrayList of courses in the catalog
- **observers**: an ArrayList of observers (parents) who receive updates

The class provides methods for adding and removing courses, managing observers, retrieving specific courses, and verifying users.

### User - Student, Teacher, Assistant, Parent
The User class serves as a base class for different user types. It includes common attributes and methods, such as getters and setters. The derived classes (Student, Teacher, Assistant, Parent) inherit from User and provide additional functionalities specific to each user type.

### Grade
The Grade class represents a grade for a specific course. It encapsulates the necessary properties and follows the requirements of the project.

### Group
The Group class represents a group of students. It contains the necessary attributes and adheres to the project requirements.

### Course
The Course class represents a specific course in the catalog. It utilizes the `Builder` design pattern, allowing flexible construction of course objects. The class includes getter and setter methods for its properties and implements additional functionalities specified in the project requirements. It also incorporates the `Memento` design pattern to provide a backup feature for grades.

### Selection of Best Student
The selection of the best student for each course is determined by the `Strategy` design pattern. The Strategy interface defines the getBestScore method, which is implemented by three concrete strategies: **BestExamScore**, **BestPartialScore** and **BestTotalScore**. Each strategy selects the best student based on a specific criterion. The Course class exposes the getBestStudent method for a teacher to retrieve the best student for their course.

### Grade Validation and Notification
The grade validation and notification process is implemented using the `Visitor` design pattern. The ScoreVisitor class serves as the visitor and validates grades for assistants and teachers. It maintains lists of unverified exam and partial grades. The visitor visits assistants and teachers to validate the grades, add them to the catalog, and notify parents.

### Graphical User Interface
The graphical user interface (GUI) includes different pages for students, teacher assistants, and parents. 
- **The StudentPage** displays the catalog of courses, allowing students to view their course information and grades. 
- **The TeacherAssistantPage** provides a list of grades to validate for assistants and teachers. 
- **The ParentPage** allows parents to view notifications and their child's course information.

## Testing
The project includes comprehensive testing, including parsing JSON files and verifying the functionalities of the implemented features. Test cases cover normal scenarios and interactions with the graphical user interface.
