import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/LoginPage.css';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const res = await fetch('http://localhost:8082/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
                credentials: 'include',  
            });

            if (res.ok) {
                const user = await res.json();

                navigate('/tasks');
            } else {
                const data = await res.text();
                setError(data);
            }
        } catch (err) {
            setError('Login failed: ' + err.message);
        }
    };

    return (
        <div className="login-container">
            <div className="card card-custom p-4">
                <h2 className="card-title-custom">Login</h2>
                <form onSubmit={handleLogin}>
                    <div className="mb-4">
                        <label htmlFor="username" className="form-label">Username</label>
                        <input
                            type="text"
                            className="form-control"
                            id="username"
                            value={username}
                            onChange={e => setUsername(e.target.value)}
                            required
                            autoFocus
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && <div className="alert alert-danger">{error}</div>}
                    <div className="d-grid gap-3">
                        <button type="submit" className="btn btn-primary btn-lg">Login</button>
                        <button type="button" className="btn btn-secondary btn-lg" onClick={() => navigate('/register')}>
                            Register
                        </button>
                    </div>
                </form>
                <p className="footer-text">&copy; 2025 Your Company</p>
            </div>
        </div>
    );
}
