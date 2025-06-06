import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../styles/DashboardPage.css';

export default function DashboardPage() {
    const [tasks, setTasks] = useState([]);
    const [user, setUser] = useState('User');
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        async function fetchData() {
            try {
                const userRes = await fetch('http://localhost:8082/api/auth/user', {
                    method: 'GET',
                    credentials: 'include',
                });
                if (userRes.ok) {
                    const userData = await userRes.json();
                    setUser(userData.username || 'User');
                }

                const tasksRes = await fetch('http://localhost:8082/api/tasks', {
                    method: 'GET',
                    credentials: 'include',
                });
                if (tasksRes.ok) {
                    const tasksData = await tasksRes.json();
                    setTasks(tasksData);
                }
            } catch (err) {
                console.error('Failed to fetch data', err);
            }
        }
        fetchData();
    }, [location.state?.refresh]);

    async function completeTask(id) {
        try {
            const res = await fetch(`http://localhost:8082/api/tasks/${id}/complete`, {
                method: 'POST',
                credentials: 'include',
            });
            if (res.ok) {
                setTasks(tasks.map(t => (t.id === id ? { ...t, completed: true } : t)));
            } else {
                alert('Failed to complete task.');
            }
        } catch {
            alert('Error completing task.');
        }
    }

    async function deleteTask(id) {
        if (!window.confirm('Delete this task?')) return;
        try {
            const res = await fetch(`http://localhost:8082/api/tasks/${id}/delete`, {
                method: 'DELETE',
                credentials: 'include',
            });

            if (res.ok) {
                setTasks(tasks.filter(t => t.id !== id));
            } else {
                alert('Failed to delete task.');
            }
        } catch {
            alert('Error deleting task.');
        }
    }

    function logout() {
        fetch('http://localhost:8082/api/auth/logout', {
            method: 'POST',
            credentials: 'include',
        }).then(() => navigate('/login'))
            .catch(() => navigate('/login'));
    }

    return (
        <div className="dashboard-container container">
            <div className="header">
                <h2>Your Tasks</h2>
                <div className="greeting">Hello, {user}!</div>
            </div>

            <button
                className="btn btn-success btn-add-task"
                onClick={() => navigate('/tasks/add')}
            >
                + Add New Task
            </button>

            <table className="table table-striped table-hover align-middle">
                <thead className="table-dark">
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Completed</th>
                    <th style={{ width: '170px' }}>Actions</th>
                </tr>
                </thead>
                <tbody>
                {tasks.length === 0 ? (
                    <tr>
                        <td colSpan="4" className="text-center text-muted">
                            No tasks found. Add a new task!
                        </td>
                    </tr>
                ) : (
                    tasks.map(task => (
                        <tr key={task.id}>
                            <td>{task.title}</td>
                            <td>{task.description}</td>
                            <td>{task.completed ? 'Yes' : 'No'}</td>
                            <td>
                                {!task.completed && (
                                    <button
                                        className="btn btn-sm btn-primary me-1" style={{ marginLeft: '-40px' }}
                                        onClick={() => completeTask(task.id)}
                                    >
                                        Complete
                                    </button>
                                )}
                                <button
                                    className="btn btn-sm btn-warning me-1"
                                    onClick={() => navigate('/tasks/edit', {state: {task}})}
                                >
                                    Edit
                                </button>
                                <button
                                    className="btn btn-sm btn-danger"
                                    onClick={() => deleteTask(task.id)}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>

            <button className="btn btn-outline-secondary mt-4" onClick={logout}>
                Logout
            </button>
        </div>
    );
}
