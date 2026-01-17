import { useState } from 'react';
import { forgotPassword } from '../../services/authService';

export default function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleSubmit = () => {
    setErrorMessage('');
    setSuccessMessage('');

    if (!email) {
      setErrorMessage('Email is required');
      return;
    }

    forgotPassword(email)

    setSuccessMessage('If the email exists in our system, a reset link has been sent.');
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-800 via-zinc-900 to-neutral-950 flex flex-col items-center pt-16 pb-8 p-4">
      <div className="text-center mb-4">
        <h1 className="text-5xl font-serif text-amber-400 mb-2 drop-shadow-lg">Pawn Finder</h1>
        <p className="text-gray-400 text-sm">Dragon's Dogma 2</p>
      </div>

      <div className="w-full max-w-md bg-gradient-to-br from-zinc-800/95 to-zinc-900/98 border-2 border-amber-700/50 rounded-lg p-8 shadow-2xl backdrop-blur-sm">
        <div className="text-center mb-6">
          <h2 className="text-2xl font-serif text-white mb-2">Reset Password</h2>
          <p className="text-gray-400 text-sm">Enter your email to receive a reset link</p>
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

          {errorMessage && (
            <p className="text-red-400 text-sm">{errorMessage}</p>
          )}

          {successMessage && (
            <p className="text-green-400 text-sm">{successMessage}</p>
          )}

          <button
            onClick={handleSubmit}
            className="w-full bg-gradient-to-r from-amber-600 to-amber-700 hover:from-amber-500 hover:to-amber-600 text-black font-bold py-3 rounded transition-all duration-200 shadow-xl tracking-wide"
          >
            Send Reset Link
          </button>
        </div>

        <div className="text-center mt-6">
          <p className="text-gray-400 text-sm">
            Remember your password?{' '}
            <a href="/login" className="text-amber-400 hover:text-amber-300 transition-colors font-medium">
              Sign in
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}