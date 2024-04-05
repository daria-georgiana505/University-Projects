import { useNavigate } from "react-router-dom";
import './CRUDstyle.css';
import React, { useState } from 'react';
import { Input, Button, Alert } from 'antd';

export default function AddBook() {
    const navigate = useNavigate();
    const [newBook, setNewBook] = useState({ title: '', author: '', genre: '', nr_pages: '' });
    const [error, setError] = useState(null);

    const handleInputChange = (e, key) => {
        const { value } = e.target;
        setNewBook(prevState => ({
            ...prevState,
            [key]: value
        }));
    };

    const handleAdd = async() => {
        try {
            const response = await fetch('http://localhost:8080/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newBook)
            });

            if (!response.ok) {
                throw new Error('Failed to add the book');
            }

            navigate('/all');
        } catch (error) {
            setError(error.message);
        }
    };

    const isAddDisabled = Object.values(newBook).some(value => value === '');

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
                <h2>Add</h2>
            </header>
            <div className="CRUD-style">
                <body>
                    <p><b>Title:</b> <Input placeholder="add title" size="large" value={newBook.title} onChange={(e) => handleInputChange(e, 'title')} /></p>
                    <p><b>Author:</b> <Input placeholder="add author" size="large" value={newBook.author} onChange={(e) => handleInputChange(e, 'author')} /></p>
                    <p><b>Genre:</b> <Input placeholder="add genre" size="large" value={newBook.genre} onChange={(e) => handleInputChange(e, 'genre')} /></p>
                    <p><b>Number of Pages:</b> <Input placeholder="add number of pages" size="large" value={newBook.nr_pages} onChange={(e) => handleInputChange(e, 'nr_pages')} /></p>
                    <Button size="large" onClick={handleAdd} disabled={isAddDisabled}><b>ADD</b></Button>
                </body>
            </div>
        </div>
    );
}
