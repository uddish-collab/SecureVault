# 🔐 Secure Vault & Credential Ledger

A simple **Java-based console application** developed as a college project. It demonstrates the basic concepts of credential management, password encryption, and user authentication.

## 📌 Project Overview

Secure Vault & Credential Ledger is a command-line application that allows users to store and manage website credentials in one place.

The application uses a Master PIN for access and provides basic encryption and decryption functionality to demonstrate how password protection works.

## ✨ Features

- **Master PIN Authentication** – Secure access using a predefined PIN.
- **Add Credentials** – Store website names, usernames, and passwords.
- **View Credential Ledger** – Display all saved credentials.
- **Password Masking** – Hide passwords while viewing the ledger.
- **Password Encryption** – Encrypt stored passwords using a Caesar-style character-shifting technique.
- **Password Decryption** – Restore encrypted passwords.
- **Search Credentials** – Find saved accounts by website name.
- **Edit Credentials** – Update existing account details.
- **Delete Credentials** – Remove saved accounts.

## 🛠️ Technologies Used

- Java
- Java Collections (`ArrayList`)
- Java Scanner
- Object-Oriented Programming
- Basic Encryption Concepts

## 📂 Project Structure

```text
SecureVault/
│
├── VaultManager.java
├── Credential.java
├── CipherEngine.java
└── README.md
```

## ▶️ How to Run

**Requirements:** Java Development Kit (JDK) installed on your system.

1. Clone or download this repository.
2. Open the project folder in VS Code.
3. Open the terminal.
4. Compile the Java files:

```bash
javac *.java
```

5. Run the application:

```bash
java VaultManager
```

6. Enter the Master PIN when prompted.

**Default Master PIN:** `1234`

## 🔒 Encryption Note

This project uses a basic Caesar-style character-shifting technique for educational demonstration purposes.

It is **not intended for real-world password storage or security**. The Master PIN is predefined in the source code, and credentials are stored in memory only.

## ⚠️ Limitations

- Credentials are not permanently stored.
- All data is lost when the application closes.
- The encryption technique is not cryptographically secure.
- The Master PIN is hardcoded.

## 🎯 Project Objective

The objective of this project is to understand and demonstrate Java programming concepts, including classes, objects, encapsulation, ArrayLists, methods, loops, conditional statements, and basic encryption logic.

---

**Project Type:** College Mini Project  
**Language:** Java  
**Interface:** Console-Based Application
