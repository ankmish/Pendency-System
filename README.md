# Pendency System

Pendency is a system that is able to track counts of in-flight/in-progress entities. The system is told when to start tracking a particular entity, and when to stop. And at any point the system can be asked how many entities are in-progress (or in-flight). The system is expected to give this count, as fast as possible.

### How to run

1. Java version: 17 (Maven)
2. Runner Class: `PendencyServiceTest.java`
