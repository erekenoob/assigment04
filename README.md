# oop-assignment-04
Java Database Connectivity

### Introduction

1. The Java API for developing Java database applications is called JDBC. JDBC provides Java programmers with a uniform 
interface for accessing and manipulating relational databases.
2. The JDBC API consists of classes and interfaces for establishing connections with databases, sending SQL statements 
to databases, processing the results of SQL statements, and obtaining database metadata.
3. Since a JDBC driver serves as the interface to facilitate communications between JDBC and a proprietary database, 
JDBC drivers are database specific.
4. Four key interfaces are needed to develop any database application using Java: `Driver`, `Connection`, `Statement`, 
and `ResultSet`. These interfaces define a framework for generic SQL database access. The JDBC driver vendors provide 
implementation for them.
5. A JDBC application loads an appropriate driver using the `Driver` interface, connects to the database using 
the `Connection` interface, creates and executes SQL statements using the `Statement` interface, and processes 
the result using the `ResultSet` interface if the statements return results.
6. The `PreparedStatement` interface is designed to execute dynamic SQL statements with parameters. 
These SQL statements are precompiled for efficient use when repeatedly executed.

### Task Alpha (3 points)
+ Write a program that connects to a database and displays the content for a table.
+ The database name and the table name is prompted from the user.

### Task Bravo (3 points)

+ Create a table named `Quiz` as follows:
    ```sql
        create table Quiz(
            questionId int,
            question varchar(4000),
            choicea varchar(1000),
            choiceb varchar(1000),
            choicec varchar(1000),
            choiced varchar(1000),
            answer varchar(5));
    ```
+ The `Quiz` table stores multiple-choice questions.
+ Suppose the multiple-choice questions are stored in a text file in the Aiken format:

```
1. Which number is the meaning of the life and everything?
A. 42
B. 786
C. 19
D. 1
ANSWER: A
2. What is the fullname of your instructor?
A. Konstantin Latuta
B. John Constantine
C. Gariel Batistuta
D. John Wick
ANSWER: BD
...
```
+ Write a program that reads the data from the file and populates it into the `Quiz` table.

### Task Charlie (3 points)

+ Suppose the database contains a student table defined as follows:

```sql
    create table Student1 (
        username varchar(50) not null,
        password varchar(50) not null,
        fullname varchar(200) not null,
        constraint pkStudent primary key (username)
    );
```

+ Create a new table named `Student2` as follows:
```sql
    create table Student2 (
        username varchar(50) not null,
        password varchar(50) not null,
        firstname varchar(100),
        lastname varchar(100),
        constraint pkStudent primary key (username)
    );
```

+ A full name is in the form of `firstname mi lastname` or `firstname lastname`. For example, `John K Smith` is a full name.
+ Write a program that copies table `Student1` into `Student2`. Your task is to split a full name into `firstname`, `mi` , and `lastname` for each record in `Student1` and store a new
record into `Student2`.