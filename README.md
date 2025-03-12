# Memory Allocation Simulator

## Overview
The **Memory Allocation Simulator** is a Java Swing-based GUI application designed to demonstrate the working of various memory allocation strategies in operating systems. The simulator visually represents memory blocks and allows users to allocate memory using different strategies: **First Fit**, **Best Fit**, and **Worst Fit**.

## Features
- **Graphical User Interface (GUI)** for better visualization of memory allocation.
- Supports three memory allocation strategies:
  - **First Fit**: Allocates the first available memory block that fits the process.
  - **Best Fit**: Allocates the smallest available memory block that fits the process.
  - **Worst Fit**: Allocates the largest available memory block that fits the process.
- Provides real-time graphical representation of memory blocks.
- Allows adding and removing memory blocks dynamically.
- Displays a detailed table of memory status (Free/Occupied) with corresponding process information.
- Reset functionality to restore initial memory configuration.

## How to Run
1. **Install Java Development Kit (JDK):**
   - Ensure JDK 17 or higher is installed on your system.
2. **Compile the Code:**
   ```bash
   javac MemoryAllocationSimulator.java
   ```
3. **Run the Application:**
   ```bash
   java MemoryAllocationSimulator
   ```

## Usage
1. **Process Allocation:**
   - Enter the desired **Process Size** in the text field.
   - Click on one of the allocation strategy buttons (**First Fit**, **Best Fit**, or **Worst Fit**) to allocate memory.
2. **Add Memory Block:**
   - Enter the size of the new memory block in the **Block Size** field.
   - Click **Add Block** to add the new block.
3. **Remove Memory Block:**
   - Click **Remove Block** to remove the last memory block.
4. **Reset:**
   - Click **Reset** to restore the default memory configuration.

## Memory Allocation Strategies Explained
- **First Fit:** Allocates the first available memory block that is large enough to fit the process.
- **Best Fit:** Finds the smallest available block that can fit the process, minimizing wasted space.
- **Worst Fit:** Allocates the largest available block that can fit the process, leaving maximum unused space for future allocations.

## Example Memory Configuration
**Default Memory Blocks:**
```
100 KB | 300 KB | 40 KB | 50 KB | 150 KB | 240 KB | 200 KB | 400 KB
```

**Default Status:**
```
Occupied | Free Space | Occupied | Free Space | Occupied | Free Space | Occupied | Free Space
```

## Code Structure
- **`MemoryAllocationSimulator`**: Main JFrame class for UI and functionality.
- **`GraphPanel`**: Custom JPanel for graphical representation of memory.
- **`addListeners()`**: Adds event listeners for all control buttons.
- **`firstFit()`**, **`bestFit()`**, **`worstFit()`**: Methods for implementing respective allocation strategies.
- **`printTable()`**: Displays the updated memory table after each action.
- **`resetMemory()`**: Restores the initial memory state.

## Future Enhancements
- Implement **deallocation** for better flexibility.
- Add memory **compaction** to improve space utilization.
- Introduce **visual effects** to enhance the graphical representation.

## Credits
**Done by:** Pranjal Usulkar



