import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';


import DashboardPage from './pages/DashboardPage';
import TaskFormPage from './pages/TaskFormPage';

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/tasks" replace />} />

          <Route path="/tasks" element={<DashboardPage />} />
          <Route path="/tasks/add" element={<TaskFormPage />} />
          <Route path="/tasks/edit" element={<TaskFormPage />} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
