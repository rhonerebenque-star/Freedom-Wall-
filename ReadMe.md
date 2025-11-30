<div align="center">

# "PAHINA'T GUNITA"

**Pahina't Gunita**  

<div align="center">

<b>IT-2108:</b><br>
GRECIA, JAMES BENEDICT P.<br>
PANALIGAN, MICHAELA C.<br>
REBENQUE, RHONE PAULENE P.<br>
</div>


------------------------------------------------------------------------
<div align="Left">

  
## 📖 Overview

**PAHINA'T GUNITA (Freedom Wall App)** 
is a Java-based console application that allows users 
to anonymously express their thoughts through: 
- **Letters**
- **Confessions**
- **Rants**

The system demonstrates core Object-Oriented Programming (OOP) principles such as:
- **Encapsulation**
- **Inheritance**
- **Abstraction**
- **Polymorphism**

------------------------------------------------------------------------

## 🚀 Features

 **Anonymous Posting**\
Users may create posts of three types: 
- Letter
- Confession
- Rant

 **Admin Tools**\
Protected by password: `ARIZONA_B`\
Admin features include: - View all posts (newest to oldest) 
- Read a specific post
- Delete a specific post
- Delete all posts

 **Search Function**\
Case-insensitive keyword search.

 **Automatic Saving**\
All posts stored in: `Freedom_Wall_posts.txt`

 **Persistent Storage**\
Saved & loaded data: - ID - Type - Timestamp - Content\
Includes escape-handling for pipes and backslashes.

------------------------------------------------------------------------

## 🛠️ Technologies Used

-   Java (Core)
-   java.io -- file read/write
-   java.time -- timestamps
-   java.util -- collections & scanner

  ------------------------------------------------------------------------

## 📂 Project Structure

FreedomWallApp.java\
├── FreedomWallApp (main class)\
├── PahinatGunita (system controller)\
├── Post (abstract class)\
├── Letter (subclass)\
├── Confession (subclass)\
└── Rant (subclass)\
Freedom_Wall_posts.txt (generated at runtime)

------------------------------------------------------------------------

## ▶️ How to Run

**Compile:**

    javac FreedomWallApp.java

**Run:**

    java FreedomWallApp

Press Enter when prompted.\
Type **YES** to continue or **NO** to exit.

------------------------------------------------------------------------

## 🔐 Admin Mode

When asked for password, enter: **ARIZONA_B**

------------------------------------------------------------------------

## 🧩 OOP Concepts Used

**Encapsulation**
- Private fields (id, content, timestamp)
- Controlled access via getters/setters

**Inheritance**
- Letter, Confession, Rant inherit from Post

**Abstraction**
- Post is abstract; subclasses implement getType()

**Polymorphism**
- Custom output per post type
- Overridden getType() methods

------------------------------------------------------------------------

## 💾 Data Storage Format

Each line inside `Freedom_Wall_posts.txt`:

`id|type|timestamp|content`

Pipes (\|) and backslashes () are escaped properly.

------------------------------------------------------------------------

## 📸 Sample Output

    ==== PAHINA'T GUNITA ====
    
    Do you want to continue? YES or NO:
    ANSWER: yes

    1) Create a Post
    2) View All Posts (admin)
    3) Search Posts by Keyword
    4) Delete a Post (admin)
    5) Save & Exit

Java OOP Project -- Freedom Wall System

------------------------------------------------------------------------

## 👤 Author & Acknowledgement

### Development
Implemented a Java-based project demonstrating OOP concepts, principles. It offers a secure platform where users can anonymously share thoughts, emotions, poems, or personal reflections, anonymously.
### Acknowledgement
Place holder for now

### Information Table

| | Name | Role |
|----------|----------|----------|
|<img src="images/James.jpg" width="120">| Grecia, James Benedict P. | Designer / Developer / Researcher / Examiner |
|<img src="images/Micha.jpg" width="120">| Panaligan, Michaela C. | Designer / Developer / Researcher / Examiner |
|<img src="images/Rhone.jpg" width="120">| Rebenque, Rhone Paulene P. | Designer / Developer / Researcher / Examiner |

------------------------------------------------------------------------

## 📜 License

This project is for educational purposes. You may modify or improve it freely.

------------------------------------------------------------------------

Thank you for opening up. Whether your words come from joy, pain, confusion, or hope, this space welcomes them all. Your story is safe and secured at "Pahina't Guinita". goodbye!.

<dev>
