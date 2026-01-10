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
    <nav className="bg-black text-white px-6 py-4 flex items-center justify-between shadow-md">
      <div className="text-2xl font-bold tracking-wider">Pawn Finder</div>

      <ul className="hidden md:flex space-x-8">
        <li>
          <Link
            to="/"
            className="hover:text-gray-300 transition-colors font-medium"
          >
            Home
          </Link>
        </li>
      </ul>

      <div className="flex space-x-4">
        {!loading && !user && (
          <>
            <Link to="/login">
              <button className="bg-white text-black px-4 py-2 rounded-lg font-medium hover:bg-gray-200 transition-colors">
                Login
              </button>
            </Link>
            <Link to="/register">
              <button className="bg-white text-black px-4 py-2 rounded-lg font-medium hover:bg-gray-200 transition-colors">
                Register
              </button>
            </Link>
          </>
        )}
        {!loading && user && (
          <>
            <span className="self-center font-medium">
              Hello, {user.username}
            </span>
            <button
              onClick={openModal}
              className="px-6 py-2 rounded-lg bg-yellow-400 text-zinc-900 font-semibold shadow-md hover:bg-yellow-500 transition-colors"
            >
              Create pawn
            </button>

            <Modal
              isOpen={isOpen}
              onClose={closeModal}
              onSubmit={handleFormSubmit}
            ></Modal>

            <button
              onClick={handleLogout}
              className="bg-red-500 text-white px-3 py-1 rounded-lg ml-2 hover:bg-red-600 transition-colors"
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
