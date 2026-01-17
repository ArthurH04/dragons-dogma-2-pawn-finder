import { use, useState } from "react";
import { resetPassword } from "../../services/authService";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";

export default function ResetPassword() {
  const [newPassword, setPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassord, setShowConfirmPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const token = searchParams.get("token");

  const handleSubmit = async () => {
    setErrorMessage("");

    if (newPassword !== confirmedPassword) {
      setErrorMessage("Passwords do not match");
      return;
    }

    if (!token) {
      setErrorMessage("Invalid or missing token");
      return;
    }

    try {
      await resetPassword(token, newPassword);
      setSuccessMessage("Password reset successfully! Redirecting to login...");
      setTimeout(() => navigate("/login"), 2000);
    } catch (error) {
      setErrorMessage("Invalid or missing token");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-800 via-zinc-900 to-neutral-950 flex flex-col items-center pt-16 pb-8 p-4">
      <div className="text-center mb-4">
        <h1 className="text-5xl font-serif text-amber-400 mb-2 drop-shadow-lg">
          Pawn Finder
        </h1>
        <p className="text-gray-400 text-sm">Dragon's Dogma 2</p>
      </div>

      <div className="w-full max-w-md bg-gradient-to-br from-zinc-800/95 to-zinc-900/98 border-2 border-amber-700/50 rounded-lg p-8 shadow-2xl backdrop-blur-sm">
        <div className="text-center mb-6">
          <h2 className="text-2xl font-serif text-white mb-2">
            Reset Password
          </h2>
        </div>

        <div className="space-y-4">
          <div>
            <div className="relative">
              <input
                type={showNewPassword ? "text" : "password"}
                placeholder="Enter your new password"
                maxLength={50}
                value={newPassword}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="w-full bg-zinc-950/70 border border-amber-800/30 rounded px-4 py-3 text-gray-200 placeholder-gray-500 focus:outline-none focus:border-amber-600 focus:ring-1 focus:ring-amber-600/50 transition-all"
              />
              <button
                type="button"
                onClick={() => setShowNewPassword(!showNewPassword)}
                className="absolute right-3 top-1/2 -translate-y-1/2 w-6 h-6 bg-amber-700/80 rounded flex items-center justify-center hover:bg-amber-600/90 transition-colors shadow-lg"
              >
                {showNewPassword ? (
                  <EyeOff className="w-3 h-3 text-white" />
                ) : (
                  <Eye className="w-3 h-3 text-white" />
                )}
              </button>
            </div>
          </div>

          <div>
            <div className="relative">
              <input
                type={showConfirmPassord ? "text" : "password"}
                placeholder="Confirm your new password"
                maxLength={50}
                value={confirmedPassword}
                onChange={(e) => setConfirmedPassword(e.target.value)}
                required
                className="w-full bg-zinc-950/70 border border-amber-800/30 rounded px-4 py-3 text-gray-200 placeholder-gray-500 focus:outline-none focus:border-amber-600 focus:ring-1 focus:ring-amber-600/50 transition-all"
              />
              <button
                type="button"
                onClick={() => setShowConfirmPassword(!showConfirmPassord)}
                className="absolute right-3 top-1/2 -translate-y-1/2 w-6 h-6 bg-amber-700/80 rounded flex items-center justify-center hover:bg-amber-600/90 transition-colors shadow-lg"
              >
                {showConfirmPassord ? (
                  <EyeOff className="w-3 h-3 text-white" />
                ) : (
                  <Eye className="w-3 h-3 text-white" />
                )}
              </button>
            </div>
          </div>

          <button
            onClick={handleSubmit}
            className="w-full bg-amber-600 hover:bg-amber-500 text-white font-medium py-3 rounded shadow-lg transition-colors"
          >
            Reset Password
          </button>

          {errorMessage && (
            <p className="text-red-400 text-sm">{errorMessage}</p>
          )}

          {successMessage && (
            <p className="text-green-400 text-sm">{successMessage}</p>
          )}
        </div>
      </div>
    </div>
  );
}
