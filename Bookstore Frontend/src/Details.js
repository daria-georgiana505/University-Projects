import { useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import { Alert } from "antd";

export default function Details() {
    const { title } = useParams();
    const [book, setBook] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await fetch(`http://localhost:8080/details/${title}`, { method: 'GET' });
            if (!response.ok) {
                throw new Error('Failed to fetch book details');
            }
            const data = await response.json();
            setBook(data);
        } catch (error) {
            setError(error.message);
        }
    };

    if (!book) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <header>
                {error && (
                    <Alert
                        message="Error"
                        description={error}
                        type="error"
                        closable
                        onClose={() => setError(null)}
                    />
                )}
                <h2>Details</h2>
            </header>
            <div>
                <body>
                    <p><b>Title:</b> {book.title}</p>
                    <p><b>Author:</b> {book.author}</p>
                    <p><b>Genre:</b> {book.genre}</p>
                    <p><b>Number of Pages:</b> {book.nr_pages}</p>
                </body>
            </div>
        </div>
    );
}
