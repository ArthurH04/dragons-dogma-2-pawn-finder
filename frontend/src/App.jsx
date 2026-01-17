import { Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import PawnsPage from "./pages/PawnsList/PawnsPage";
import Navbar from "./components/Navbar";
import PawnPage from "./pages/PawnPage/PawnPage";
import ForgotPassword from "./pages/ForgotPassword/ForgotPassword";
import ResetPassword from "./pages/ResetPassword/ResetPassword";

export default function App() {
  return (
    <div>
      
      <Navbar/>

      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/pawn/:id" element={<PawnPage/>} />
        <Route path="/" element={<PawnsPage/>} />
      </Routes>
    </div>
  );
}