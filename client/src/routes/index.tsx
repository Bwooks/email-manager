import * as React from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";
import { SignIn } from "./signin";
import { SignUp } from "./signup";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="signup" />,
  },
  {
    path: "signin",
    element: <SignIn />,
  },
  {
    path: "signup",
    element: <SignUp />,
  },
]);
