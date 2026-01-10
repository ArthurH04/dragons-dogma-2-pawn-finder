import { useState } from "react";
import { login } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

export default function Login() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [errorMessage, setErrorMessage] = useState();
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
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        placeholder="Email"
        maxLength={50}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        maxLength={50}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Login</button>
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
    </form>
  );
}
