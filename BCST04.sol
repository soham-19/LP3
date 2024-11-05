// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Student_Management {

    struct Student {
        int stud_id;
        string stud_Name;
        string department;
    }

    Student[] Students;

    // Function to add a new student
    function add_student_detail(int stud_id, string memory stud_Name, string memory department) public {
        Student memory stud_Data = Student(stud_id, stud_Name, department);
        Students.push(stud_Data);
    }

    // Function to get student details by student ID
    function getStudent_detail(int stud_id) public view returns(string memory, string memory) {
        for (uint i = 0; i < Students.length; i++) {
            Student memory stud = Students[i];
            if (stud.stud_id == stud_id) {
                return (stud.stud_Name, stud.department);
            }
        }
        return ("Not Found", "Not Found");
    }

    // Additional function to get the number of students
    function get_student_count() public view returns (uint) {
        return Students.length;
    }

    // Additional function to get the total gas used for adding students
    function estimate_gas_for_add(int stud_id, string memory stud_Name, string memory department) public returns (uint256) {
        uint256 startGas = gasleft();
        
        // Call the add_student_detail function to measure gas used
        add_student_detail(stud_id, stud_Name, department);
        
        uint256 gasUsed = startGas - gasleft();
        return gasUsed;
    }
}

/*
Certainly! Here’s a detailed theoretical overview of the concepts involved in your Solidity smart contract for student management:

### Smart Contracts
- **Definition**: Smart contracts are self-executing contracts with the terms of the agreement directly written into code. They run on blockchain networks, enabling trustless transactions without intermediaries.
- **Characteristics**:
  - **Immutable**: Once deployed, smart contracts cannot be altered.
  - **Distributed**: The contract's code and state are stored on the blockchain, making them accessible to all participants.
  - **Automatic Execution**: Conditions in the contract are executed automatically when met.

### Solidity
- **Definition**: Solidity is a high-level programming language designed specifically for writing smart contracts on platforms like Ethereum.
- **Features**:
  - **Statically Typed**: Variable types are known at compile time.
  - **Object-Oriented**: Supports concepts like inheritance, libraries, and complex user-defined types.
  - **Events**: Solidity allows contracts to emit events that can be logged on the blockchain, facilitating communication between contracts and front-end applications.

### Data Structures
- **Structs**: 
  - **Definition**: Custom data types that group related variables together. In your contract, a `Student` struct encapsulates student details like ID, name, and department.
  - **Usage**: Useful for organizing complex data in a readable and maintainable way.

### State Variables
- **Definition**: Variables that hold data in the contract's storage. In your contract, the `Students` array stores instances of the `Student` struct.
- **Storage**: Data stored in the Ethereum blockchain, persisting across function calls and transactions.

### Functions
- **Public Functions**: 
  - Functions marked as `public` can be called both internally (within the contract) and externally (from other contracts or accounts).
- **View Functions**: 
  - Functions that read data from the blockchain but do not modify the state. They are useful for retrieving information without incurring gas costs.
- **Gas Estimation**: 
  - Understanding gas consumption is crucial for optimizing contract performance and minimizing costs for users.

### Error Handling
- **Revert Mechanism**: 
  - Solidity uses the `revert` statement to revert state changes and return an error message when certain conditions are not met, ensuring contract integrity.

### Events
- **Purpose**: Events are used to log information on the blockchain, which can be listened to by external applications (e.g., DApps).
- **Structure**: An event declaration includes the event name and indexed parameters for efficient searching.

### Access Control
- **Management**: Smart contracts often incorporate access control to limit who can execute certain functions, enhancing security. This can be done using patterns like the "owner" pattern or roles.

### Example Contract Functionality
1. **Adding a Student**: The `add_student_detail` function allows users to input a student’s information, creating a new entry in the `Students` array and logging an event.
2. **Retrieving Student Details**: The `getStudent_detail` function searches the `Students` array by `stud_id`, returning the corresponding student's information or a not found message.
3. **Counting Students**: The `get_student_count` function simply returns the number of entries in the `Students` array, providing insights into how many students are currently managed by the contract.

### Best Practices
- **Data Privacy**: Since all data on the blockchain is public, sensitive information should be avoided or properly encrypted.
- **Optimization**: Writing efficient code can significantly reduce gas costs, making the contract more user-friendly.
- **Testing**: Thorough testing of smart contracts is essential before deployment to prevent bugs and vulnerabilities.

This theoretical foundation provides an overview of the concepts utilized in your Solidity smart contract for managing student records. If you have specific areas you'd like to explore further, let me know!
*/