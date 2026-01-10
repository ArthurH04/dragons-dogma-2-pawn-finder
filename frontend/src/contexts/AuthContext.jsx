import { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser } from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getCurrentUser();
        setUser(userData);
      } catch (error) {
        setUser(null);
      } finally {
        setLoading(false);
      }
    };
    fetchUser();
  }, []);

  const loginUser = async (userData) => setUser(userData);
  const logoutUser = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, loading, loginUser, logoutUser }}>
        {children}
    </AuthContext.Provider>
  )
};

export const useAuth = () => useContext(AuthContext);