import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../styles/TaskFormPage.css';

export default function TaskFormPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const editingTask = location.state?.task;

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [dueDate, setDueDate] = useState('');

    useEffect(() => {
        if (editingTask) {
            setTitle(editingTask.title || '');
            setDescription(editingTask.description || '');
            setDueDate(editingTask.dueDate ? editingTask.dueDate.slice(0, 10) : '');
        }
    }, [editingTask]);

    async function handleSubmit(e) {
        e.preventDefault();

        const taskPayload = {
            title,
            description,
            dueDate: dueDate || null,
            completed: editingTask ? editingTask.completed : false,
        };

        let url;
        let method;

        if (editingTask) {
            url = `http://localhost:8083/api/tasks/${editingTask.id}/edit`;
            method = 'PUT';
        } else {
            url = "http://localhost:8083/api/tasks/add";
            method = 'POST';
        }



        try {
            const res = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(taskPayload),
                credentials: 'include',
            });

            if (res.ok) {
                navigate('/tasks', { state: { refresh: Date.now() } });
            } else {
                const errorText = await res.text();
                console.error('Backend error:', errorText);
                alert(editingTask ? 'Failed to update task.' : 'Failed to add task.');
            }
        } catch (error) {
            alert('Error saving task.');
            console.error(error);
        }
    }

    return (
        <div className="addtask-container">
            <div className="card shadow-sm p-4 w-100">
                <h2 className="mb-4 text-center">
                    {editingTask ? 'Edit Task' : 'Add New Task'}
                </h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label htmlFor="title" className="form-label">Title</label>
                        <input
                            type="text"
                            className="form-control"
                            id="title"
                            required
                            value={title}
                            onChange={e => setTitle(e.target.value)}
                            autoFocus
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="description" className="form-label">Description</label>
                        <textarea
                            className="form-control"
                            id="description"
                            rows="4"
                            value={description}
                            onChange={e => setDescription(e.target.value)}
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="dueDate" className="form-label">Due Date</label>
                        <input
                            type="date"
                            className="form-control"
                            id="dueDate"
                            value={dueDate}
                            onChange={e => setDueDate(e.target.value)}
                        />
                    </div>

                    <div className="d-flex gap-3">
                        <button type="submit" className="btn btn-primary flex-fill btn-lg">
                            {editingTask ? 'Update Task' : 'Add Task'}
                        </button>
                        <button
                            type="button"
                            className="btn btn-secondary flex-fill btn-lg"
                            onClick={() => navigate('/tasks')}
                        >
                            Back to Tasks
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
