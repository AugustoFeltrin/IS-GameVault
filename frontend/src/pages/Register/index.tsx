import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { api } from "../../services/api";

export default function Register() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            await api.register(name, email, password);
            alert("Conta criada com sucesso! Faça login para entrar.");
            navigate("/login");
        } catch (err: any) {
            setError(err.message);
        }
    }

    return (
        <div className="flex flex-col min-h-screen">
            <main className="flex justify-center items-center my-auto gap-20 p-10">
                <div className="flex flex-col w-1/4 gap-2">
                    <p className="text-5xl font-bold">Comece seu <b className="text-secondary">diário</b> em segundos.</p>
                    <p className="text-1xl text-gray-400">Avalie, registre e descubra jogos com uma comunidade que ama videogames tanto quanto você.</p>
                </div>

                <form onSubmit={handleRegister} className="flex flex-col gap-3 w-1/4">
                    <div className="mb-4">
                        <h2 className="text-2xl text-bold">Criar uma conta</h2>
                        <p className="text-md text-gray-400">É grátis e leva menos de um minuto.</p>           
                    </div>

                    {error && <div className="text-red-500 bg-red-500/10 p-3 rounded-lg text-sm">{error}</div>}

                    <div className="flex flex-col">
                        <label htmlFor="name"></label>
                        <input 
                            type="text" 
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Nome de Usuário" 
                            required
                            className="bg-surface border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-gray-400 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"/>
                    </div>

                    <div className="flex flex-col">
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

                    <button type="submit" className="mt-4 bg-primary py-3 rounded-lg">Criar conta</button>

                    <span className="text-center text-gray-400">Já tem uma conta?<Link to="/login" className="text-primary font-bold"> Entrar</Link></span>
                
                </form>
            </main>
        </div>
    );
}