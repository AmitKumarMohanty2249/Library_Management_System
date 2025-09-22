CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Gender CHAR(1) CHECK (Gender IN ('M','F')),
    DOB DATE,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(15),
    Address VARCHAR(255)
);

SELECT * FROM Student

INSERT INTO Student (StudentID, FirstName, LastName, Gender, DOB, Email, Phone, Address)
VALUES
(1, 'Amit', 'Kumar', 'M', '2002-03-10', 'amit.kumar@example.com', '9876543210', 'Bhubaneswar, Odisha'),
(2, 'Sneha', 'Patel', 'F', '2001-07-22', 'sneha.patel@example.com', '9876543211', 'Ahmedabad, Gujarat'),
(3, 'Ravi', 'Sharma', 'M', '2000-12-15', 'ravi.sharma@example.com', '9876543212', 'Delhi'),
(4, 'Priya', 'Nair', 'F', '2002-05-18', 'priya.nair@example.com', '9876543213', 'Kochi, Kerala'),
(5, 'Arjun', 'Verma', 'M', '2001-09-02', 'arjun.verma@example.com', '9876543214', 'Lucknow, UP'),
(6, 'Kavita', 'Singh', 'F', '2003-01-10', 'kavita.singh@example.com', '9876543215', 'Patna, Bihar'),
(7, 'Vikram', 'Rao', 'M', '2002-06-30', 'vikram.rao@example.com', '9876543216', 'Hyderabad, Telangana');

CREATE TABLE Courses (
    CourseID INT PRIMARY KEY,
    CourseName VARCHAR(100),
    Credits INT,
    Department VARCHAR(50)
);

SELECT * FROM Courses

INSERT INTO Courses (CourseID, CourseName, Credits, Department)
VALUES
(101, 'Database Systems', 4, 'CSE'),
(102, 'Operating Systems', 3, 'CSE'),
(103, 'Computer Networks', 3, 'CSE'),
(104, 'Data Structures', 4, 'CSE'),
(105, 'Mathematics I', 3, 'Mathematics'),
(106, 'Physics I', 3, 'Physics'),
(107, 'English Communication', 2, 'Humanities');


CREATE TABLE Professor (
    ProfID INT PRIMARY KEY,
    ProfName VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Department VARCHAR(50)
);

SELECT * FROM Professor

INSERT INTO Professor (ProfID, ProfName, Email, Department)
VALUES
(201, 'Dr. Mehta', 'mehta@example.com', 'CSE'),
(202, 'Dr. Gupta', 'gupta@example.com', 'Mathematics'),
(203, 'Dr. Rao', 'rao@example.com', 'Physics'),
(204, 'Dr. Sharma', 'sharma@example.com', 'CSE'),
(205, 'Dr. Nair', 'nair@example.com', 'Humanities'),
(206, 'Dr. Banerjee', 'banerjee@example.com', 'CSE'),
(207, 'Dr. Iyer', 'iyer@example.com', 'CSE');



CREATE TABLE Enrollments (
    EnrollID INT PRIMARY KEY,
    StudentID INT,
    CourseID INT,
    ProfID INT,
    EnrollmentDate DATE,
    Grade VARCHAR(5),
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (ProfID) REFERENCES Professor(ProfID)
);

SELECT * FROM Enrollments

INSERT INTO Enrollments (EnrollID, StudentID, CourseID, ProfID, EnrollmentDate, Grade)
VALUES
(301, 1, 101, 201, '2025-01-10', 'A'),
(302, 2, 102, 204, '2025-01-11', 'B'),
(303, 3, 103, 206, '2025-01-12', 'A'),
(304, 4, 104, 207, '2025-01-13', 'A'),
(305, 5, 105, 202, '2025-01-14', 'B'),
(306, 6, 106, 203, '2025-01-15', 'C'),
(307, 7, 107, 205, '2025-01-16', 'A');

----1----

SELECT DISTINCT p.ProfName
FROM Professor p
JOIN Enrollments e ON p.ProfID = e.ProfID
JOIN Courses c ON e.CourseID = c.CourseID
WHERE c.Department = 'CSE';

----2-----

SELECT s.FirstName, s.LastName, c.CourseName, e.Grade
FROM Enrollments e
JOIN Student s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID
WHERE e.Grade = (
    SELECT MAX(e2.Grade)
    FROM Enrollments e2
    WHERE e2.CourseID = e.CourseID
);

----3------

SELECT s.FirstName, s.LastName, COUNT(e.CourseID) AS TotalEnrollments
FROM Enrollments e
JOIN Student s ON e.StudentID = s.StudentID
GROUP BY s.StudentID, s.FirstName, s.LastName
ORDER BY TotalEnrollments DESC
LIMIT 1;

-----4-----

SELECT p.ProfName, COUNT(DISTINCT e.StudentID) AS TotalStudents
FROM Enrollments e
JOIN Professor p ON e.ProfID = p.ProfID
GROUP BY p.ProfID, p.ProfName
ORDER BY TotalStudents DESC
LIMIT 1;

----5----

SELECT s.StudentID, s.FirstName, s.LastName
FROM Student s
WHERE NOT EXISTS (
    SELECT c.CourseID
    FROM Courses c
    JOIN Enrollments e ON c.CourseID = e.CourseID
    JOIN Professor p ON e.ProfID = p.ProfID
    WHERE p.ProfName = 'Dr. Mehta'
    EXCEPT
    SELECT e2.CourseID
    FROM Enrollments e2
    WHERE e2.StudentID = s.StudentID
);

-----6----

SELECT s.FirstName, s.LastName, e.Grade
FROM Enrollments e
JOIN Student s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID
WHERE c.CourseName = 'Database Systems'
  AND (CASE e.Grade
          WHEN 'A' THEN 4
          WHEN 'B' THEN 3
          WHEN 'C' THEN 2
          WHEN 'D' THEN 1
          ELSE 0
      END) >
      (
        SELECT AVG(
          CASE e2.Grade
              WHEN 'A' THEN 4
              WHEN 'B' THEN 3
              WHEN 'C' THEN 2
              WHEN 'D' THEN 1
              ELSE 0
          END
        )
        FROM Enrollments e2
        JOIN Courses c2 ON e2.CourseID = c2.CourseID
        WHERE c2.CourseName = 'Database Systems'
      );

----7-------

SELECT s.FirstName, s.LastName, SUM(c.Credits) AS TotalCredits
FROM Enrollments e
JOIN Student s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID
GROUP BY s.StudentID, s.FirstName, s.LastName;

-----8----

SELECT s.FirstName, s.LastName, c.CourseName, p.ProfName, e.Grade
FROM Enrollments e
JOIN Student s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID
JOIN Professor p ON e.ProfID = p.ProfID;