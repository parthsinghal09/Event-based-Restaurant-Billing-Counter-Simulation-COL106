# Event-based-Restaurant-Billing-Counter-Simulation

The program performs an event based simulation of the billing counter environment of a burger restaurant with K (non-zero) number of billing counters and the size of the griddle being an non-zero integer M. The following problem is being tackled thorugh this simulation - 

_"You are the owner of a big and famous burger restaurant called McMohans Burgers. The restaurant has K billing counters numbered 1 to K. Since your restaurant is a hit amongst youngsters, you get a lot of customers leading to long queues in billing as well as food preparation. You would like to know some statistics like average waiting time, average queue length etc., so that proper steps to improve customer convenience can be taken. You want to do this by running a simulation, as it is cost/time efficient and allows for a range of experimentation on the assumed model."_

Following assumptions were used during the simulation - 
1. Customers arrive randomly.
2. A new customer always joins the billing queue with the smallest length at that time. If there are multiple billing queues with the same smallest lengths, then the lowest numbered queue of those is chosen by the customer.
3. If two customers arrive at the same time, we assume that they came one after the other at the same time instant, i.e., the first customer will already be in some queue, when the second customer is deciding which queue to join.
4. The billing specialist (who takes the order and processes payment for a customer) in billing queue k will take k units of time in completing the order.
5. After the order is completed, the customer order is printed automatically and sent to the chef, who prepares the burgers in the sequence he/she receives the orders. If two orders arrive simultaneously then the chef chooses the order from the higher numbered billing queue first.
6. Each burger patty gets cooked in exactly 10 units of time. Whenever a patty is cooked another patty starts cooking in the same time instant.
7. Upon cooking, the burger is delivered to the customer in one unit of time.
8. Whenever a customer gets all their burgers, they leave the restaurant instantaneously (it’s a take-away restaurant with no dine-in).
9. Multiple events happening at the same time instant are executed in the following order - 
  - Billing specialist prints an order and sends it to the chef; customer leaves the queue.
  - A cooked patty is removed from the griddle.
  - The chef puts another patty on the griddle.
  - A newly arrived customer joins a queue.
  - Cooked burgers are delivered to customers.
10. If there is a query at the same time instant, the query must be answered after all the events for the time instant have been executed in the above-mentioned order.

> The simulation is performed using various data structures such as Min Heap, Queue, AVL Tree and Vectors. There is one Min Heap which is used to find the queue with the minimum length and another Min Heap is used to store the events based on their priority and the time of execution. AVL Tree maintains the customer information and can be used to search customers in O(logn) time complexity, where n is the total number of customers.  

# Folders 

- Simulation Code 
  - IllegalNumberException.java => File defines a custom exception for illegal values of K and M, i.e., the value of K and M cannot be changed if the simulation has started 
  - MMBurgersInterface.java => Defines the interface of the program
  - MMBurgers.java => File containing the simulation code
  - Makefile - Used for running the program

- Tester 
  - Contains files which test the database system

> Usage - make (while using the makefile one should ensure that the path to the tester files is also provided as tester files are not in the same folder)

or 

> Usage without makefile - 

> javac -d classes/ -cp classes/ IllegalNumberException.java

> javac -d classes/ -cp classes/ MMBurgersInterface.java

> javac -d classes/ -cp classes/ MMBurgers.java

> javac -d classes/ -cp classes/ Path to Tester file

> java -cp classes/ Tester File name (without .java)
