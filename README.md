# Java Network Programming

Coursework for the **Network Programming** course (Software Engineering, Vilnius University Šiauliai Academy, autumn 2026).
The assignments go from object-oriented Java basics up to file I/O, serialization, HTTP/REST clients, TCP and UDP sockets, and multithreading.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk) ![Sockets](https://img.shields.io/badge/TCP%20%2F%20UDP-sockets-blue) ![Threads](https://img.shields.io/badge/concurrency-threads-green)

## Assignments

| # | Topic | What it does |
|---|-------|--------------|
| [1](assignment-1) | OOP and inheritance | `Polygon → Rectangle → RectangularPrism` class hierarchy; friendly-numbers check (abundancy index) and a word/letter counter |
| [2](assignment-2) | Classes and exceptions | `Polynomial` class with evaluation, arithmetic and a custom `PolynomialException` |
| [3](assignment-3) | Streams and file I/O | Redirects `print()` output into a text file and reads it back; downloads raw bytes from a URL |
| [4](assignment-4) | Serialization and HTTP | Serializes 9 prisms to `prisms9.ser` and restores them; sends GET/POST requests to a REST API (JSONPlaceholder) |
| [5](assignment-5) | TCP sockets | Multi-client TCP **calculator server** (one thread per client) with a console client |
| [6](assignment-6) | UDP sockets | UDP calculator server that keeps per-client state (address:port) and a UDP client |
| [7](assignment-7) | Multithreading | Wallet transactions run in parallel to show a **race condition**, then fixed with `synchronized` |

## Running

Each folder is self-contained. You need JDK 17+.

```bash
cd assignment-5
javac *.java
java Task1          # start the server
java Task2          # in a second terminal: start the client
```

Assignments 3–5 have a `NOTES.txt` with exact run instructions. Assignments 3 and 4 need internet access.

## What I practised

- Designing class hierarchies and reusing them across assignments
- Byte and character streams, `Serializable`, `HttpClient`
- Client/server protocols over TCP and UDP, serving several clients
- Thread safety: reproducing a lost-update bug and fixing it with synchronization
