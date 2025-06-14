import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import TaskFormPage from './pages/TaskFormPage';

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/tasks" element={<DashboardPage />} />
          <Route path="/tasks/add" element={<TaskFormPage />} />
          <Route path="/tasks/edit" element={<TaskFormPage />} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
