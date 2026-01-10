import { useState } from "react";
import { login, register } from "../../services/authService";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router";

export default function Register() {
    const [email, setEmail] = useState();
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [errorMessage, setErrorMessage] = useState();

    const { loginUser } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrorMessage("");

        try {
            const data = await register(email, username, password);
            
            const loginData = await login(email, password);
            loginUser(loginData);
            navigate("/", {replace: true});
        } catch (error) {
            if (error.response && error.response.data) {
                setErrorMessage(error.response.data.message || "Registration failed");
            }
            setErrorMessage("Unknown error occurred");
        }
    }

    return (<form onSubmit={handleSubmit}>
            <input type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
            <input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)} />
            <input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
            <button type="submit">Register</button>
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
        </form>
    );
}