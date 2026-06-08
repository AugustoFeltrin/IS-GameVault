// src/routes/ProtectedRoute.tsx
import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";

interface ProtectedRouteProps {
  children: ReactNode;
}

export function ProtectedRoute({ children }: ProtectedRouteProps) {
  const isAuthenticated = localStorage.getItem("gamevault_user_id");

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}