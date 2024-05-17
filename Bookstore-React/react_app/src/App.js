import React from 'react';
import { BrowserRouter, Routes, Route} from "react-router-dom";

import './App.css';
import BooksTable from './BooksTable.js';
import Details from './Details.js';
import UpdateBook from './UpdateBook.js';
import AddBook from './AddBook.js';
import MainPage from './MainPage.js';


function App() {
  return (
    <div className="App">
      <header className="App-header">
        <BrowserRouter>
          <Routes>
            <Route path="" element={<MainPage />} />
            <Route path="/all" element={<BooksTable />} />
            <Route path="/details/:title" element={<Details />} />
            <Route path="/update/:title" element={<UpdateBook />} />
            <Route path="/add" element={<AddBook />} />
          </Routes>
        </BrowserRouter>
      </header>
    </div>
  );
}

export default App;
