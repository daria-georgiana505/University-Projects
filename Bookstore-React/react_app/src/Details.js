import { useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import { Alert, List, Button, Modal, Form, Input } from "antd";

export default function Details() {
    const { title } = useParams();
    const [book, setBook] = useState(null);
    const [error, setError] = useState(null);
    const [addModalVisible, setAddModalVisible] = useState(false);
    const [updateModalVisible, setUpdateModalVisible] = useState(false);
    const [selectedReviewIndex, setSelectedReviewIndex] = useState(null);
    const [newReview, setNewReview] = useState({ reviewerName: '', review: '' });
    const [addButtonDisabled, setAddButtonDisabled] = useState(true);
    const [updateButtonDisabled, setUpdateButtonDisabled] = useState(true);

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        const isAddButtonDisabled = !newReview.reviewerName || !newReview.review;
        setAddButtonDisabled(isAddButtonDisabled);
    }, [newReview]);

    useEffect(() => {
        const isUpdateButtonDisabled = !newReview.reviewerName || !newReview.review;
        setUpdateButtonDisabled(isUpdateButtonDisabled);
    }, [newReview]);

    const fetchData = async () => {
        try {
            if (!navigator.onLine) {
                throw new Error('No internet connection. Please check your internet connection.');
            }

            const response = await fetch(`http://localhost:8080/details/${title}`, { method: 'GET' });
            if (!response.ok) {
                throw new Error('Failed to fetch book details');
            }
            const data = await response.json();
            setBook(data);
        } catch (error) {
            if (error instanceof TypeError && error.message === 'Failed to fetch') {
              setError('Failed to fetch data from the server. The server might be down.');
            } else {
              setError(error.message);
            }
        }
    };

    const handleAddReview = () => {
        setAddModalVisible(true);
    };

    const handleAddModalOk = async () => {
        try {
            const response = await fetch(`http://localhost:8080/addReview/${title}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newReview),
            });
            if (!response.ok) {
                throw new Error('Failed to add review');
            }
            fetchData(); // Fetch updated book details
            setNewReview({ reviewerName: '', review: '' });
            setAddModalVisible(false);
        } catch (error) {
            setError('Failed to add review');
        }
    };

    const handleAddModalCancel = () => {
        setAddModalVisible(false);
    };

    const handleDetailsReview = (index) => {
        setSelectedReviewIndex(index);
        setNewReview(book.reviews[index]);
        setUpdateModalVisible(true);
    };

    const handleUpdateModalOk = async () => {
        try {
            const response = await fetch(`http://localhost:8080/updateReview/${book.reviews[selectedReviewIndex].id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newReview),
            });
            if (!response.ok) {
                throw new Error('Failed to update review');
            }
            fetchData(); // Fetch updated book details
            setNewReview({ reviewerName: '', review: '' });
            setUpdateModalVisible(false);
        } catch (error) {
            setError('Failed to update review');
        }
    };
    

    const handleUpdateModalCancel = () => {
        setUpdateModalVisible(false);
    };

    const handleDeleteReview = async () => {
        try {
            const response = await fetch(`http://localhost:8080/deleteReview/${book.reviews[selectedReviewIndex].id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete review');
            }
            fetchData(); // Fetch updated book details
            setNewReview({ reviewerName: '', review: '' });
            setUpdateModalVisible(false);
        } catch (error) {
            setError('Failed to delete review');
        }
    };

    const handleInputChange = (key, value) => {
        setNewReview(prevReview => ({
            ...prevReview,
            [key]: value
        }));
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
                
                <p><b>Reviews:</b></p>

                <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '10px' }}>
                    <Button type="primary" onClick={handleAddReview}>Add Review</Button>
                </div>

                <List
                    style={{ backgroundColor: 'white' }}
                    itemLayout="horizontal"
                    dataSource={book.reviews}
                    renderItem={(review, index) => (
                        <List.Item style={{ padding: '16px' }}>
                            <List.Item.Meta
                                title={<span style={{ fontSize: '18px', fontWeight: 'bold' }}>{review.reviewerName}</span>}
                                description={<span style={{ fontSize: '16px', fontWeight: 'bold' }}>{review.review}</span>}
                            />
                            <div>
                                <p></p>
                                <Button type="primary" onClick={() => handleDetailsReview(index)}>Details</Button>
                            </div>
                        </List.Item>
                    )}
                />

                <p></p>
                
                <Modal
                    title="Add Review"
                    visible={addModalVisible}
                    onOk={handleAddModalOk}
                    onCancel={handleAddModalCancel}
                    okButtonProps={{ disabled: addButtonDisabled }}
                >
                    <Form>
                        <Form.Item label="Reviewer Name">
                            <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
                        </Form.Item>
                        <Form.Item label="Review">
                            <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
                        </Form.Item>
                    </Form>
                </Modal>

                <Modal
                    title="Review Details"
                    visible={updateModalVisible}
                    onOk={handleUpdateModalOk}
                    onCancel={handleUpdateModalCancel}
                    okButtonProps={{ disabled: updateButtonDisabled }}
                    footer={[
                        <Button key="delete" type="danger" onClick={handleDeleteReview}>Delete</Button>,
                        <Button key="cancel" onClick={handleUpdateModalCancel}>Cancel</Button>,
                        <Button key="submit" type="primary" onClick={handleUpdateModalOk} disabled={!newReview.reviewerName && !newReview.review}>OK</Button>,
                    ]}
                >
                    <Form>
                        <Form.Item label="Reviewer Name">
                            <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
                        </Form.Item>
                        <Form.Item label="Review">
                            <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
                        </Form.Item>
                    </Form>
                </Modal>
            </body>
        </div>
    </div>
);
}






// import { useParams } from "react-router-dom";
// import React, { useState, useEffect } from 'react';
// import { Alert, List, Button, Modal, Form, Input } from "antd";

// export default function Details() {
//     const { title } = useParams();
//     const [book, setBook] = useState(null);
//     const [error, setError] = useState(null);
//     const [addModalVisible, setAddModalVisible] = useState(false);
//     const [updateModalVisible, setUpdateModalVisible] = useState(false);
//     const [selectedReviewIndex, setSelectedReviewIndex] = useState(null);
//     const [newReview, setNewReview] = useState({ reviewerName: '', review: '' });

//     useEffect(() => {
//         fetchData();
//     }, []);

//     const fetchData = async () => {
//         try {
//             if (!navigator.onLine) {
//                 throw new Error('No internet connection. Please check your internet connection.');
//             }

//             const response = await fetch(`http://localhost:8080/details/${title}`, { method: 'GET' });
//             if (!response.ok) {
//                 throw new Error('Failed to fetch book details');
//             }
//             const data = await response.json();
//             setBook(data);
//         } catch (error) {
//             if (error instanceof TypeError && error.message === 'Failed to fetch') {
//               setError('Failed to fetch data from the server. The server might be down.');
//             } else {
//               setError(error.message);
//             }
//         }
//     };

//     const handleAddReview = () => {
//         setAddModalVisible(true);
//     };

//     const handleAddModalOk = async () => {
//         try {
//             const response = await fetch(`http://localhost:8080/addReview/${title}`, {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(newReview),
//             });
//             if (!response.ok) {
//                 throw new Error('Failed to add review');
//             }
//             fetchData(); // Fetch updated book details
//             setNewReview({ reviewerName: '', review: '' });
//             setAddModalVisible(false);
//         } catch (error) {
//             setError('Failed to add review');
//         }
//     };

//     const handleAddModalCancel = () => {
//         setAddModalVisible(false);
//     };

//     const handleDetailsReview = (index) => {
//         setSelectedReviewIndex(index);
//         setNewReview(book.reviews[index]);
//         setUpdateModalVisible(true);
//     };

//     const handleUpdateModalOk = async () => {
//         try {
//             const response = await fetch(`http://localhost:8080/updateReview/${book.reviews[selectedReviewIndex].id}`, {
//                 method: 'PUT',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(newReview),
//             });
//             if (!response.ok) {
//                 throw new Error('Failed to update review');
//             }
//             fetchData(); // Fetch updated book details
//             setNewReview({ reviewerName: '', review: '' });
//             setUpdateModalVisible(false);
//         } catch (error) {
//             setError('Failed to update review');
//         }
//     };
    

//     const handleUpdateModalCancel = () => {
//         setUpdateModalVisible(false);
//     };

//     const handleDeleteReview = async () => {
//         try {
//             const response = await fetch(`http://localhost:8080/deleteReview/${book.reviews[selectedReviewIndex].id}`, {
//                 method: 'DELETE',
//             });
//             if (!response.ok) {
//                 throw new Error('Failed to delete review');
//             }
//             fetchData(); // Fetch updated book details
//             setNewReview({ reviewerName: '', review: '' });
//             setUpdateModalVisible(false);
//         } catch (error) {
//             setError('Failed to delete review');
//         }
//     };

//     const handleInputChange = (key, value) => {
//         setNewReview(prevReview => ({
//             ...prevReview,
//             [key]: value
//         }));
//     };

//     if (!book) {
//         return <div>Loading...</div>;
//     }

// return (
//     <div>
//         <header>
//             {error && (
//                 <Alert
//                     message="Error"
//                     description={error}
//                     type="error"
//                     closable
//                     onClose={() => setError(null)}
//                 />
//             )}
//             <h2>Details</h2>
//         </header>
//         <div>
//             <body>
//                 <p><b>Title:</b> {book.title}</p>
//                 <p><b>Author:</b> {book.author}</p>
//                 <p><b>Genre:</b> {book.genre}</p>
//                 <p><b>Number of Pages:</b> {book.nr_pages}</p>
                
//                 <p><b>Reviews:</b></p>

//                 <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '10px' }}>
//                     <Button type="primary" onClick={handleAddReview}>Add Review</Button>
//                 </div>

//                 <List
//                     style={{ backgroundColor: 'white' }}
//                     itemLayout="horizontal"
//                     dataSource={book.reviews}
//                     renderItem={(review, index) => (
//                         <List.Item style={{ padding: '16px' }}>
//                             <List.Item.Meta
//                                 title={<span style={{ fontSize: '18px', fontWeight: 'bold' }}>{review.reviewerName}</span>}
//                                 description={<span style={{ fontSize: '16px', fontWeight: 'bold' }}>{review.review}</span>}
//                             />
//                             <div>
//                                 <p></p>
//                                 <Button type="primary" onClick={() => handleDetailsReview(index)}>Details</Button>
//                             </div>
//                         </List.Item>
//                     )}
//                 />
                
//                 <Modal
//                     title="Add Review"
//                     visible={addModalVisible}
//                     onOk={handleAddModalOk}
//                     onCancel={handleAddModalCancel}
//                 >
//                     <Form>
//                         <Form.Item label="Reviewer Name">
//                             <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                         </Form.Item>
//                         <Form.Item label="Review">
//                             <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                         </Form.Item>
//                     </Form>
//                 </Modal>

//                 <Modal
//                     title="Review Details"
//                     visible={updateModalVisible}
//                     onOk={handleUpdateModalOk}
//                     onCancel={handleUpdateModalCancel}
//                     footer={[
//                         <Button key="delete" type="danger" onClick={handleDeleteReview}>Delete</Button>,
//                         <Button key="cancel" onClick={handleUpdateModalCancel}>Cancel</Button>,
//                         <Button key="submit" type="primary" onClick={handleUpdateModalOk}>OK</Button>,
//                     ]}
//                 >
//                     <Form>
//                         <Form.Item label="Reviewer Name">
//                             <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                         </Form.Item>
//                         <Form.Item label="Review">
//                             <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                         </Form.Item>
//                     </Form>
//                 </Modal>
//             </body>
//         </div>
//     </div>
// );
// }






//     return (
//         <div>
//             <header>
//                 {error && (
//                     <Alert
//                         message="Error"
//                         description={error}
//                         type="error"
//                         closable
//                         onClose={() => setError(null)}
//                     />
//                 )}
//                 <h2>Details</h2>
//             </header>
//             <div>
//                 <body>
//                     <p><b>Title:</b> {book.title}</p>
//                     <p><b>Author:</b> {book.author}</p>
//                     <p><b>Genre:</b> {book.genre}</p>
//                     <p><b>Number of Pages:</b> {book.nr_pages}</p>
                    
//                     <Button type="primary" onClick={handleAddReview} style={{ float: 'right', marginBottom: '10px' }}>Add Review</Button>
                    
//                     <p><b>Reviews:</b></p>
//                     <List
//                         style={{ backgroundColor: 'white' }}
//                         itemLayout="horizontal"
//                         dataSource={book.reviews}
//                         renderItem={(review, index) => (
//                             <List.Item style={{ padding: '16px' }}>
//                                 <List.Item.Meta
//                                     title={<span style={{ fontSize: '18px', fontWeight: 'bold' }}>{review.reviewerName}</span>}
//                                     description={<span style={{ fontSize: '16px', fontWeight: 'bold' }}>{review.review}</span>}
//                                 />
//                                 <div>
//                                     <p></p>
//                                     <Button type="primary" onClick={() => handleDetailsReview(index)}>Details</Button>
//                                 </div>
//                             </List.Item>
//                         )}
//                     />
                    
//                     <Modal
//                         title="Add Review"
//                         visible={addModalVisible}
//                         onOk={handleAddModalOk}
//                         onCancel={handleAddModalCancel}
//                     >
//                         <Form>
//                             <Form.Item label="Reviewer Name">
//                                 <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                             </Form.Item>
//                             <Form.Item label="Review">
//                                 <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                             </Form.Item>
//                         </Form>
//                     </Modal>

//                     <Modal
//                         title="Review Details"
//                         visible={updateModalVisible}
//                         onOk={handleUpdateModalOk}
//                         onCancel={handleUpdateModalCancel}
//                         footer={[
//                             <Button key="delete" type="danger" onClick={handleDeleteReview}>Delete</Button>,
//                             <Button key="cancel" onClick={handleUpdateModalCancel}>Cancel</Button>,
//                             <Button key="submit" type="primary" onClick={handleUpdateModalOk}>OK</Button>,
//                         ]}
//                     >
//                         <Form>
//                             <Form.Item label="Reviewer Name">
//                                 <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                             </Form.Item>
//                             <Form.Item label="Review">
//                                 <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                             </Form.Item>
//                         </Form>
//                     </Modal>
//                 </body>
//             </div>
//         </div>
//     );
// }


// return (
//     <div>
//         <header>
//             {error && (
//                 <Alert
//                     message="Error"
//                     description={error}
//                     type="error"
//                     closable
//                     onClose={() => setError(null)}
//                 />
//             )}
//             <h2>Details</h2>
//         </header>
//         <div>
//             <body>
//                 <p><b>Title:</b> {book.title}</p>
//                 <p><b>Author:</b> {book.author}</p>
//                 <p><b>Genre:</b> {book.genre}</p>
//                 <p><b>Number of Pages:</b> {book.nr_pages}</p>
                
//                 <p><b>Reviews:</b></p>
//                 <Button type="primary" onClick={handleAddReview} style={{ float: 'right', marginBottom: '10px' }}>Add Review</Button>
//                 <List
//                     style={{ backgroundColor: 'white' }}
//                     itemLayout="horizontal"
//                     dataSource={book.reviews}
//                     renderItem={(review, index) => (
//                         <List.Item style={{ padding: '16px' }}>
//                             <List.Item.Meta
//                                 title={<span style={{ fontSize: '18px', fontWeight: 'bold' }}>{review.reviewerName}</span>}
//                                 description={<span style={{ fontSize: '16px', fontWeight: 'bold' }}>{review.review}</span>}
//                             />
//                             <div>
//                                 <p></p>
//                                 <Button type="primary" onClick={() => handleDetailsReview(index)}>Details</Button>
//                             </div>
//                         </List.Item>
//                     )}
//                 />

                
//                 <Modal
//                     title="Add Review"
//                     visible={addModalVisible}
//                     onOk={handleAddModalOk}
//                     onCancel={handleAddModalCancel}
//                 >
//                     <Form>
//                         <Form.Item label="Reviewer Name">
//                             <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                         </Form.Item>
//                         <Form.Item label="Review">
//                             <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                         </Form.Item>
//                     </Form>
//                 </Modal>

//                 <Modal
//                     title="Review Details"
//                     visible={updateModalVisible}
//                     onOk={handleUpdateModalOk}
//                     onCancel={handleUpdateModalCancel}
//                     footer={[
//                         <Button key="delete" type="danger" onClick={handleDeleteReview}>Delete</Button>,
//                         <Button key="cancel" onClick={handleUpdateModalCancel}>Cancel</Button>,
//                         <Button key="submit" type="primary" onClick={handleUpdateModalOk}>OK</Button>,
//                     ]}
//                 >
//                     <Form>
//                         <Form.Item label="Reviewer Name">
//                             <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                         </Form.Item>
//                         <Form.Item label="Review">
//                             <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                         </Form.Item>
//                     </Form>
//                 </Modal>
//             </body>
//         </div>
//     </div>
// );
// }



















// import { useParams } from "react-router-dom";
// import React, { useState, useEffect } from 'react';
// import { Alert, List, Button, Modal, Form, Input } from "antd";

// export default function Details() {
//     const { title } = useParams();
//     const [book, setBook] = useState(null);
//     const [error, setError] = useState(null);
//     const [addModalVisible, setAddModalVisible] = useState(false);
//     const [updateModalVisible, setUpdateModalVisible] = useState(false);
//     const [selectedReviewIndex, setSelectedReviewIndex] = useState(null);
//     const [newReview, setNewReview] = useState({ reviewerName: '', review: '' });

//     useEffect(() => {
//         fetchData();
//     }, []);

//     const fetchData = async () => {
//         try {
//             if (!navigator.onLine) {
//                 throw new Error('No internet connection. Please check your internet connection.');
//             }

//             const response = await fetch(`http://localhost:8080/details/${title}`, { method: 'GET' });
//             if (!response.ok) {
//                 throw new Error('Failed to fetch book details');
//             }
//             const data = await response.json();
//             setBook(data);
//         } catch (error) {
//             if (error instanceof TypeError && error.message === 'Failed to fetch') {
//               setError('Failed to fetch data from the server. The server might be down.');
//             } else {
//               setError(error.message);
//             }
//         }
//     };

//     const handleAddReview = () => {
//         setAddModalVisible(true);
//     };

//     const handleAddModalOk = () => {
//         // Add the new review to the book's reviews
//         setBook(prevBook => ({
//             ...prevBook,
//             reviews: [...prevBook.reviews, newReview]
//         }));
//         setNewReview({ reviewerName: '', review: '' });
//         setAddModalVisible(false);
//     };

//     const handleAddModalCancel = () => {
//         setAddModalVisible(false);
//     };

//     const handleDetailsReview = (index) => {
//         setSelectedReviewIndex(index);
//         setNewReview(book.reviews[index]);
//         setUpdateModalVisible(true);
//     };

//     const handleUpdateModalOk = () => {
//         // Update the review
//         setBook(prevBook => ({
//             ...prevBook,
//             reviews: prevBook.reviews.map((review, i) => i === selectedReviewIndex ? newReview : review)
//         }));
//         setNewReview({ reviewerName: '', review: '' });
//         setUpdateModalVisible(false);
//     };

//     const handleUpdateModalCancel = () => {
//         setUpdateModalVisible(false);
//     };

//     const handleDeleteReview = () => {
//         // Delete the review
//         setBook(prevBook => ({
//             ...prevBook,
//             reviews: prevBook.reviews.filter((_, i) => i !== selectedReviewIndex)
//         }));
//         setNewReview({ reviewerName: '', review: '' });
//         setUpdateModalVisible(false);
//     };

//     const handleInputChange = (key, value) => {
//         setNewReview(prevReview => ({
//             ...prevReview,
//             [key]: value
//         }));
//     };

//     if (!book) {
//         return <div>Loading...</div>;
//     }

//     return (
//         <div>
//             <header>
//                 {error && (
//                     <Alert
//                         message="Error"
//                         description={error}
//                         type="error"
//                         closable
//                         onClose={() => setError(null)}
//                     />
//                 )}
//                 <h2>Details</h2>
//             </header>
//             <div>
//                 <body>
//                     <p><b>Title:</b> {book.title}</p>
//                     <p><b>Author:</b> {book.author}</p>
//                     <p><b>Genre:</b> {book.genre}</p>
//                     <p><b>Number of Pages:</b> {book.nr_pages}</p>
                    
//                     <Button type="primary" onClick={handleAddReview} style={{ float: 'right', marginBottom: '10px' }}>Add Review</Button>
                    
//                     <h3>Reviews:</h3>
//                     <List
//                         style={{ backgroundColor: 'white' }}
//                         itemLayout="horizontal"
//                         dataSource={book.reviews}
//                         renderItem={(review, index) => (
//                             <List.Item style={{ padding: '16px' }}>
//                                 <List.Item.Meta
//                                     title={<span style={{ fontSize: '18px', fontWeight: 'bold' }}>{review.reviewerName}</span>}
//                                     description={<span style={{ fontSize: '16px', fontWeight: 'bold' }}>{review.review}</span>}
//                                 />
//                                 <div>
//                                     <p></p>
//                                     <Button type="primary" onClick={() => handleDetailsReview(index)}>Details</Button>
//                                 </div>
//                             </List.Item>
//                         )}
//                     />
                    
//                     <Modal
//                         title="Add Review"
//                         visible={addModalVisible}
//                         onOk={handleAddModalOk}
//                         onCancel={handleAddModalCancel}
//                     >
//                         <Form>
//                             <Form.Item label="Reviewer Name">
//                                 <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                             </Form.Item>
//                             <Form.Item label="Review">
//                                 <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                             </Form.Item>
//                         </Form>
//                     </Modal>

//                     <Modal
//                         title="Review Details"
//                         visible={updateModalVisible}
//                         onOk={handleUpdateModalOk}
//                         onCancel={handleUpdateModalCancel}
//                         footer={[
//                             <Button key="delete" type="danger" onClick={handleDeleteReview}>Delete</Button>,
//                             <Button key="cancel" onClick={handleUpdateModalCancel}>Cancel</Button>,
//                             <Button key="submit" type="primary" onClick={handleUpdateModalOk}>OK</Button>,
//                         ]}
//                     >
//                         <Form>
//                             <Form.Item label="Reviewer Name">
//                                 <Input value={newReview.reviewerName} onChange={e => handleInputChange('reviewerName', e.target.value)} />
//                             </Form.Item>
//                             <Form.Item label="Review">
//                                 <Input.TextArea value={newReview.review} onChange={e => handleInputChange('review', e.target.value)} />
//                             </Form.Item>
//                         </Form>
//                     </Modal>
//                 </body>
//             </div>
//         </div>
//     );
// }













// import { useParams } from "react-router-dom";
// import React, { useState, useEffect } from 'react';
// import { Alert } from "antd";

// export default function Details() {
//     const { title } = useParams();
//     const [book, setBook] = useState(null);
//     const [error, setError] = useState(null);

//     useEffect(() => {
//         fetchData();
//     }, []);

//     const fetchData = async () => {
//         try {
//             if (!navigator.onLine) {
//                 throw new Error('No internet connection. Please check your internet connection.');
//             }

//             const response = await fetch(`http://localhost:8080/details/${title}`, { method: 'GET' });
//             if (!response.ok) {
//                 throw new Error('Failed to fetch book details');
//             }
//             const data = await response.json();
//             setBook(data);
//         } catch (error) {
//             if (error instanceof TypeError && error.message === 'Failed to fetch') {
//               setError('Failed to fetch data from the server. The server might be down.');
//             } else {
//               setError(error.message);
//             }
//         }
//     };

//     if (!book) {
//         return <div>Loading...</div>;
//     }

//     return (
//         <div>
//             <header>
//                 {error && (
//                     <Alert
//                         message="Error"
//                         description={error}
//                         type="error"
//                         closable
//                         onClose={() => setError(null)}
//                     />
//                 )}
//                 <h2>Details</h2>
//             </header>
//             <div>
//                 <body>
//                     <p><b>Title:</b> {book.title}</p>
//                     <p><b>Author:</b> {book.author}</p>
//                     <p><b>Genre:</b> {book.genre}</p>
//                     <p><b>Number of Pages:</b> {book.nr_pages}</p>
//                 </body>
//             </div>
//         </div>
//     );
// }
