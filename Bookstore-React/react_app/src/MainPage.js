import { useNavigate } from "react-router-dom";
import React from 'react';
import { Button } from 'antd';



export default function MainPage() {
    const navigate = useNavigate();

    const handleRedirect = () => {
    navigate('/all');
    };

    return(
        <div>
            <header>
                <h2>Welcome to our page!</h2>
            </header>
            <body>
                <Button size="large" onClick={handleRedirect}><b>See books</b></Button>
            </body>
        </div>
    );
}