import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Register(){
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(name, email, password);
        if(password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }
    }

    return (
        <div>
            <h1>Register</h1>
            <form onSubmit = {handleSubmit}>
                <label>
                    Name:
                    <input type="text" name="name" required value={name}
                           onChange={(e) => setName(e.target.value)}/>
                </label>
                <label>
                    Email:
                    <input type="text" name="email" required value={email}
                           onChange={(e) => setEmail(e.target.value)}/>
                </label>
                <label>
                    Password:
                    <input type="password" name="password" required value={password}
                           onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <label>
                    Confirm Password:
                    <input type="password" name="confirmPassword" required value={confirmPassword}
                           onChange={(e) => setConfirmPassword(e.target.value)}/>
                </label>
                <input type="submit" value="Submit" />
            </form>
        </div>
    )

}

export default Register;

