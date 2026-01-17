import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { logout } from "../services/authService";
import { useState } from "react";
import Modal from "./Modal";
import { createPawn } from "../services/pawnService";

const Navbar = () => {
  const { user, logoutUser, loading } = useAuth();
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();

  const openModal = () => setIsOpen(true);
  const closeModal = () => setIsOpen(false);

  const handleLogout = async () => {
    try {
      await logout();
      logoutUser();
      navigate("/login");
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  const handleFormSubmit = async (data) => {
    try {
      const pawn = await createPawn(data);
    } catch (error) {
      if (error.response && error.response.data) {
        console.log(error.response.data);
      }
      console.log("Unknown error occurred");
    }
  };
  return (
  <nav className="bg-gradient-to-r from-zinc-900 via-zinc-900 to-neutral-950 border-b border-amber-700/40 text-white px-6 py-4 flex items-center justify-between shadow-xl">
    <div className="text-2xl font-serif tracking-wide text-amber-400 drop-shadow">
      Pawn Finder
    </div>

    <ul className="hidden md:flex space-x-8">
      <li>
        <Link
          to="/"
          className="text-gray-300 hover:text-amber-400 transition-colors font-medium"
        >
          Home
        </Link>
      </li>
    </ul>

    <div className="flex space-x-4 items-center">
      {!loading && !user && (
        <>
          <Link to="/login">
            <button className="border border-amber-700/50 text-amber-400 px-4 py-2 rounded-lg font-medium hover:bg-amber-700/10 transition-all">
              Login
            </button>
          </Link>
          <Link to="/register">
            <button className="bg-gradient-to-r from-amber-600 to-amber-700 hover:from-amber-500 hover:to-amber-600 text-black px-4 py-2 rounded-lg font-semibold shadow-md transition-all">
              Register
            </button>
          </Link>
        </>
      )}

      {!loading && user && (
        <>
          <span className="text-gray-300 font-medium">
            Hello, <span className="text-amber-400">{user.username}</span>
          </span>

          <button
            onClick={openModal}
            className="px-6 py-2 rounded-lg bg-gradient-to-r from-amber-600 to-amber-700 hover:from-amber-500 hover:to-amber-600 text-black font-bold shadow-lg transition-all"
          >
            Create pawn
          </button>

          <Modal
            isOpen={isOpen}
            onClose={closeModal}
            onSubmit={handleFormSubmit}
          />

          <button
            onClick={handleLogout}
            className="border border-red-500/60 text-red-400 px-3 py-1 rounded-lg hover:bg-red-500/10 transition-all"
          >
            Logout
          </button>
        </>
      )}
    </div>
  </nav>
);
};

export default Navbar;
