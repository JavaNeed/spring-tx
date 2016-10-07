Spring - Transaction Management
------
A database transaction is a sequence of actions that are treated as a single unit of work. These actions should either complete entirely or take no effect at all. Transaction management is an important part of and RDBMS oriented enterprise applications to ensure data integrity and consistency. The concept of transactions can be described with following four key properties described as ACID:

- Atomicity: A transaction should be treated as a single unit of operation which means either the entire sequence of operations is successful or unsuccessful.

- Consistency: This represents the consistency of the referential integrity of the database, unique primary keys in tables etc.

- Isolation: There may be many transactions processing with the same data set at the same time, each transaction should be isolated from others to prevent data corruption.

- Durability: Once a transaction has completed, the results of this transaction have to be made permanent and cannot be erased from the database due to system failure.

A real RDBMS database system will guarantee all the four properties for each transaction. The simplistic view of a transaction issued to the database using SQL is as follows:

1. Begin the transaction using begin transaction command.
2. Perform various deleted, update or insert operations using SQL queries.
3. If all the operation are successful then perform commit otherwise rollback all the operations


Spring framework provides an abstract layer on top of different underlying transaction management APIs. The Spring's transaction support aims to provide an alternative to EJB transactions by adding transaction capabilities to POJOs. Spring supports both programmatic and declarative transaction management. EJBs requires an application server, but Spring transaction management can be implemented without a need of application server.

