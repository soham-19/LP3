// SPDX-License-Identifier: MET
pragma solidity ^0.8.26;

contract BankFinal {

    uint256 private balance = 0;

    event Deposited(uint256 amt);
    event Withdrawn(uint256 amt);

    function deposit(uint256 amt) public {
        balance += amt;
        emit Deposited(amt);
    }

    function withdraw(uint256 amt) public {
        require(amt <= balance, "Insufficient Balance!!");
        balance -= amt;
        emit Withdrawn(amt);
    }

    function checkBalance() public view returns (uint256){
        return  balance;
    }
}

/*
Key Concepts of Solidity Smart Contracts
Smart Contract:

What: A self-executing contract with the terms of the agreement directly written into code.
Why: Automates and enforces agreements without the need for intermediaries, reducing costs and increasing efficiency.
Real-World Applications: Used in financial services (e.g., loans, insurance), supply chain management, and digital identity verification.
State Variables:

What: Variables that store data on the blockchain (e.g., uint256 private balance).
Why: They maintain the current state of the contract, essential for tracking information (like balances in a bank).
Real-World Applications: Track user balances in cryptocurrency wallets, account balances in decentralized finance (DeFi) apps.
Events:

What: Logs that record actions that have occurred in the smart contract (e.g., event Deposited(uint256 amt)).
Why: Allow external applications (like dApps) to listen for specific changes or actions in the contract, providing real-time updates.
Real-World Applications: Notifications in financial apps when transactions occur, logging important actions for audit trails.
Functions:

What: Blocks of code that perform specific tasks (e.g., function deposit(uint256 amt)).
Why: They define the behavior of the contract and how users can interact with it, encapsulating logic for reusability and organization.
Real-World Applications: Functions for executing trades in decentralized exchanges, handling user registration in decentralized applications.
Visibility Modifiers:

What: Keywords that define who can access a function or variable (e.g., public, private).
Why: Control access to functions and data, enhancing security by exposing only necessary parts of the contract.
Real-World Applications: Protect sensitive data in applications (like user personal information) and ensure only authorized actions are taken.
Error Handling:

What: Mechanisms like require to validate conditions before executing code.
Why: Prevent invalid operations and ensure contract integrity by reverting transactions if conditions are not met.
Real-World Applications: Ensure sufficient funds for transactions in financial apps, validate input data in various contracts.
Gas and Transaction Costs:

What: Gas is the fee required to execute transactions on the Ethereum network.
Why: It incentivizes miners to include transactions in blocks, ensuring the network's security and efficiency.
Real-World Applications: Users pay gas fees when sending cryptocurrency, executing smart contracts, or interacting with decentralized applications.
Applicability to Real-World Blockchain Applications
Decentralized Finance (DeFi): Smart contracts enable lending, borrowing, and trading without intermediaries, reducing costs and increasing access to financial services.
Supply Chain Management: They allow for transparent tracking of products, verifying authenticity and reducing fraud.
Digital Identity: Smart contracts can manage identity verification processes securely and transparently, reducing the risk of identity theft.
Gaming: They power in-game economies where users can own, trade, and interact with digital assets (e.g., NFTs).
*/