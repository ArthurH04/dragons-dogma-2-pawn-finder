import { useState } from "react";
import { login } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import { Eye, EyeOff } from 'lucide-react';


export default function Login() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [errorMessage, setErrorMessage] = useState();
  const [showPassword, setShowPassword] = useState(false);
  const { loginUser } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setErrorMessage("");

    try {
      const data = await login(email, password);
      loginUser(data);
      navigate("/", {replace: true});
    } catch (error) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data.message || "Login failed");
      }
      setErrorMessage("Unknown error occurred");
    }
  };

return (
    <div className="min-h-screen bg-gradient-to-b from-slate-800 via-zinc-900 to-neutral-950 flex flex-col items-center justify-center pb-25 p-4">
      <div className="text-center mb-8">
        <h1 className="text-5xl font-serif text-amber-400 mb-2 drop-shadow-lg">Pawn Finder</h1>
        <p className="text-gray-400 text-sm">Dragon's Dogma 2</p>
      </div>

      <div className="w-full max-w-md bg-gradient-to-br from-zinc-800/95 to-zinc-900/98 border-2 border-amber-700/50 rounded-lg p-8 shadow-2xl backdrop-blur-sm">
        <div className="text-center mb-6">
          <h2 className="text-2xl font-serif text-white mb-2">Sign In</h2>
          <p className="text-gray-400 text-sm">Welcome back, Arisen</p>
        </div>

        <div className="space-y-4">
          <div>
            <label className="block text-white text-sm mb-2">Email</label>
            <input
              type="email"
              placeholder="Enter your email"
              maxLength={50}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full bg-zinc-950/70 border border-amber-800/30 rounded px-4 py-3 text-gray-200 placeholder-gray-500 focus:outline-none focus:border-amber-600 focus:ring-1 focus:ring-amber-600/50 transition-all"
            />
          </div>

          <div>
            <label className="block text-white text-sm mb-2">Password</label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                placeholder="Enter your password"
                maxLength={50}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-zinc-950/70 border border-amber-800/30 rounded px-4 py-3 text-gray-200 placeholder-gray-500 focus:outline-none focus:border-amber-600 focus:ring-1 focus:ring-amber-600/50 transition-all"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-1/2 -translate-y-1/2 w-6 h-6 bg-amber-700/80 rounded flex items-center justify-center hover:bg-amber-600/90 transition-colors shadow-lg"
              >
                {showPassword ? (
                  <EyeOff className="w-3 h-3 text-white" />
                ) : (
                  <Eye className="w-3 h-3 text-white" />
                )}
              </button>
            </div>
          </div>

          {errorMessage && (
            <p className="text-red-400 text-sm">{errorMessage}</p>
          )}

          <button
            onClick={handleSubmit}
            className="w-full bg-gradient-to-r from-amber-600 to-amber-700 hover:from-amber-500 hover:to-amber-600 text-black font-bold py-3 rounded transition-all duration-200 shadow-xl tracking-wide"
          >
            Login
          </button>
        </div>

        <div className="text-center mt-6">
          <p className="text-gray-400 text-sm">
            Don't have an account?{' '}
            <a href="/register" className="text-amber-400 hover:text-amber-300 transition-colors font-medium">
              Create Account
            </a>
          </p>
          <p className="text-gray-400 text-sm">
            Forgot your password?{' '}
            <a href="/forgot-password" className="text-amber-400 hover:text-amber-300 transition-colors font-medium">
              Forgot Password
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}
