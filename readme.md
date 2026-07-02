# 𒉭 Strugglers DSA

A comprehensive repository for implementing **unique and niche data structures** and algorithms from scratch. Learn how they work, where they're applied, and why they matter in real-world applications.

---

![GitHub commit activity](https://img.shields.io/github/commit-activity/t/AhmadxSaied/Struggler-DSA?style=flat-square)

![License](https://img.shields.io/badge/license-MIT-blue?style=flat-square)

![GitHub contributors](https://img.shields.io/github/contributors/AhmadxSaied/Struggler-DSA)

![GitHub last commit](https://img.shields.io/github/last-commit/AhmadxSaied/Struggler-DSA%3F)

---

## Reasons behind the Project

This repository is built so you can master three core principles:

1. **Learn by Building** – Implement data structures and algorithms without AI assistance to truly understand them
2. **Master Git & Collaboration** – Practice proper GitHub workflows, branching, and project management
3. **Write Quality Code** – Follow best practices and maintain well-documented, maintainable code

---

## Project Structure

```
📦 Strugglers DSA
├── 📁 Order_Statistics
│   ├── 📁 lib
│   ├── 📁 src
│   │   ├── ☕ OrderMain.java
│   │   └── ☕ QuickSelect.java
│   └── 📝 README.md
├── 📁 SkipList
│   └── 📁 lib
├── 📁 Sorting
│   └── 📁 Sorting
│       ├── 📁 lib
│       ├── 📁 src
│       │   ├── ☕ App.java
│       │   ├── ☕ QuickSort.java
│       │   ├── ☕ countingSort.java
│       │   ├── ☕ heapSort.java
│       │   ├── ☕ insertionSort.java
│       │   ├── ☕ mergeSort.java
│       │   ├── ☕ radixSort.java
│       │   ├── ☕ selectionSort.java
│       │   └── ☕ sortMain.java
│       └── 📝 README.md
├── 📁 Trees
│   ├── 📁 lib
│   ├── 📁 src
│   │   ├── 📁 implementation
│   │   │   ├── ☕ BST.java
│   │   │   ├── ☕ BTree.java
│   │   │   ├── ☕ RedBlackTree.java
│   │   │   └── ☕ SplayTree.java
│   │   └── ☕ App.java
│   └── 📝 README.md
├── 📁 graph_algorithms
│   ├── 📁 lib
│   ├── 📁 src
│   │   ├── ☕ App.java
│   │   └── ☕ GraphAlgorithms.java
│   └── 📝 README.md
└── 📝 README.md
```

**Organization Model:** Each data structure/algorithm family has its own subproject. Each subproject includes its own branch for development. When complete, changes are submitted via pull request for review and merging.

---

## What's Implemented

### Order Statistics

- Quick Select algorithm
- Min & Max finding _(planned)_

### Sorting

- Basic sorting algorithms (Bubble, Selection, Insertion)
- Advanced sorting (Quick Sort, Merge Sort, Heap Sort)
- Linear time sorting (Counting Sort, Radix Sort)
- Tim Sort & Intro Sort _(planned)_

### Trees

- Binary Search Tree (BST)
- Self-balancing trees (Red-Black Tree, B-Tree)
- Self-adjusting trees (Splay Tree)
- AVL Tree _(planned)_

### Other Structures

- Skip List
- Graph Algorithms (in progress)

---

## Getting Started

### Prerequisites

- **Java 8+** (for compilation and execution)
- **Git** (for version control)
- Basic understanding of data structures and algorithms

### Running Examples

Each subproject is standalone and can be run independently:

```bash
# Example: Running Sorting algorithms
cd Sorting/Sorting
javac -d bin src/*.java
java -cp bin App
```

Refer to individual `README.md` files in each subproject for specific usage instructions.

---

## Development Workflow

We follow a structured branching model:

1. **Create a feature branch** for your data structure:

   ```bash
   git checkout -b feature/your-datastructure
   ```

   or

   ```bash
   git checkout -b your-datastructure
   ```

2. **Implement without AI assistance** – Challenge yourself!

3. **Commit regularly** with clear, descriptive messages:

   ```bash
   git commit -m "Add implementation of [DataStructure] with tests"
   ```

4. **Push and create a Pull Request** for review

5. **Address feedback** and iterate

---

## Contributing

I'd love to have you contribute! Whether you're adding a new data structure, improving existing code, or fixing documentation, here's how to get involved.

### How to Join

- **Email:** [Ahmadsaied404@gmail.com](mailto:Ahmadsaied404@gmail.com)
- **WhatsApp:** Reach out if you have the contact number

Once approved, you'll be added as a collaborator.

### Contribution Guidelines

**Do:**

- Implement data structures from scratch (without AI assistance)
- Write clean, readable, well-commented code
- Include tests and documentation
- Follow the existing project structure
- Make meaningful commits with clear messages

**Don't:**

- Use AI to generate implementations
- Violate the learning intent of the project
- Make changes that harm the repository
- Skip documentation or testing

---

## Learning Resources

Each subproject's `README.md` includes:

- Algorithm/data structure explanation
- Time and space complexity analysis
- Use cases and real-world applications
- Implementation walkthrough (if needed)

---

## 📄 License

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## About This Project

**Strugglers DSA** is a learning-focused project designed to help you master data structures and algorithms through hands-on implementation. It emphasizes understanding over copy-paste, collaboration over isolation, and quality over speed.

---

## Questions or Suggestions?

Have ideas for new data structures? Found a bug? Want to improve documentation? Open an issue or reach out to [Ahmadsaied404@gmail.com](mailto:Ahmadsaied404@gmail.com).

**Happy coding!**
