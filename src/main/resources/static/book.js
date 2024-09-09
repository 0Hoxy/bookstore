// book.js

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("bookForm");

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // 페이지 새로고침 방지

        const formData = new FormData(form);
        const bookDTO = {
            title: formData.get("title"),
            price: parseInt(formData.get("price"), 10),
            author: {
                name: formData.get("authorName"),
                biography: "" // 필요한 경우 빈 값으로 초기화
            },
            category: {
                name: formData.get("categoryName")
            },
            stock: {
                quantity: parseInt(formData.get("stockQuantity"), 10)
            }
        };

        try {
            const response = await fetch("/api/books", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(bookDTO),
            });

            if (response.ok) {
                // 성공 시 동작
                alert("Book added successfully");
                form.reset(); // 폼 초기화
                loadBooks(); // 책 목록 새로 고침
            } else {
                // 실패 시 동작
                alert("Failed to add book");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while adding the book");
        }
    });

    // 책 목록을 동적으로 불러오는 함수
    async function loadBooks() {
        try {
            const response = await fetch("/api/books");
            if (response.ok) {
                const books = await response.json();
                const tableBody = document.querySelector("#booksTable tbody");
                tableBody.innerHTML = ""; // 기존 내용 제거

                books.forEach(book => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.price}</td>
                        <td>${book.author.name}</td>
                        <td>${book.category.name}</td>
                        <td>${book.stock.quantity}</td>
                        <td><button onclick="deleteBook(${book.id})">Delete</button></td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                console.error("Failed to load books");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    }

    // 책 삭제 함수
    window.deleteBook = async function (id) {
        try {
            const response = await fetch(`/api/books/${id}`, {
                method: "DELETE",
            });

            if (response.ok) {
                alert("Book deleted successfully");
                loadBooks(); // 책 목록 새로 고침
            } else {
                alert("Failed to delete book");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while deleting the book");
        }
    };

    // 초기 로드 시 책 목록 불러오기
    loadBooks();
});
