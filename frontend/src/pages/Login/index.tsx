import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { api } from "../../services/api";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            const userData = await api.login(email, password);
            
            localStorage.setItem("gamevault_user_id", userData.id);
            localStorage.setItem("gamevault_user_name", userData.name);
            localStorage.setItem("gamevault_user_email", userData.email); 
            
            navigate("/home");
        } catch (err: any) {
            setError(err.message);
        }
    };

    return (
        <div className="flex flex-col min-h-screen">
            <main className="flex justify-center items-center my-auto gap-20 p-10">
                <div className="flex flex-col w-1/4 gap-2">
                    <p className="text-5xl font-bold">Seu diário <b className="text-primary">gamer</b> em um só lugar.</p>
                    <p className="text-1xl text-gray-400">Avalie, registre e descubra jogos com uma comunidade que ama videogames tanto quanto você.</p>
                </div>

                <form onSubmit={handleLogin} className="flex flex-col gap-3 w-1/4">
                    <div className="mb-4">
                        <h2 className="text-2xl text-bold">Bem-vindo de volta</h2>
                        <p className="text-md text-gray-400">Entre para acessar seu diário</p>           
                    </div>
                    
                    {error && <div className="text-red-500 bg-red-500/10 p-3 rounded-lg text-sm">{error}</div>}

                    <div className="flex flex-col gap-2">
                        <label htmlFor="email"></label>
                        <input 
                            type="email" 
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="seu@email.com" 
                            required
                            className="bg-surface border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-gray-400 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"/>
                    </div>

                    <div className="flex flex-col">
                        <label htmlFor="password"></label>
                        <input 
                            type="password" 
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Senha" 
                            required
                            className="bg-surface border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-gray-400 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"/>
                    </div>

                    <button type="submit" className="mt-4 bg-primary py-3 rounded-lg font-bold hover:brightness-110 transition-all">Entrar</button>

                    <span className="text-center text-gray-400">Não tem uma conta?<Link to="/register" className="text-primary font-bold hover:underline"> Crie uma</Link></span>
                </form>
            </main>
        </div>
    );
}