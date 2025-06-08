# Concurrent and Parallel Programming in Java – Course Assignment

## 📘 Overview
Project: Parallel PDF File Counter

This repository contains our course assignment for Concurrent and Parallel Programming. The project focuses on the practical implementation of multithreading and concurrency control using Java.

Functionality:
The core functionality of the program is to recursively traverse a folder structure and count the number of PDF files. This task is parallelized using Java's concurrency features to improve performance, especially on systems with multiple CPU cores.

Objectives:

Demonstrate the use of Java threads, executors, and synchronization techniques.

Show how parallelism can significantly reduce execution time for I/O-bound tasks like directory traversal.

Provide a clean, well-documented example of concurrency in a real-world file system operation.

## ⚙️ Technologies Used
- Java (JDK 21)  
- IntelliJ IDEA 

## 🚀 Features Implemented
The assignment includes the following core concurrency and parallelism features in Java:

### Thread Creation and Management
- Using Thread, Runnable, and Callable  
- Custom thread 

### Thread Synchronization
- synchronized keyword  
- Locks and ReentrantLock  
- Deadlock prevention techniques

### Inter-Thread Communication
- wait(), notify(), and notifyAll() methods  
- Producer-Consumer problem implementations
- Synchronized Queue

### Thread Pools and Executors
- ExecutorService
- thread pools  
- Task scheduling and shutdown

### Concurrency Utilities
- CountDownLatch

