import { Link } from "react-router-dom";

export default function Login() {
    
    return (
        <div className="flex flex-col min-h-screen">
            <main className="flex justify-center items-center my-auto gap-20 p-10">
                <div className="flex flex-col w-1/4 gap-2">
                    <p className="text-5xl font-bold">Seu diário <b className="text-primary">gamer</b> em um só lugar.</p>
                    <p className="text-1xl text-gray-400">Avalie, registre e descubra jogos com uma comunidade que ama videogames tanto quanto você.</p>
                </div>

                <form className="flex flex-col gap-3 w-1/4">
                    <div className="mb-4">
                        <h2 className="text-2xl text-bold">Bem-vindo de volta</h2>
                        <p className="text-md text-gray-400">Entre para acessar seu diário</p>           
                    </div>
                    <div className="flex flex-col gap-2">
                        <label htmlFor="email"></label>
                        <input 
                            type="email" 
                            id="email"
                            placeholder="seu@email.com" 
                            className="bg-surface border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-gray-400 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"/>
                    </div>

                    <div className="flex flex-col">
                        <label htmlFor="password"></label>
                        <input 
                            type="password" 
                            id="password"
                            placeholder="Senha" 
                            className="bg-surface border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-gray-400 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"/>
                    </div>

                    <button type="submit" className="mt-4 bg-primary py-3 rounded-lg">Entrar</button>

                    <span className="text-center text-gray-400">Não tem uma conta?<Link to="/register" className="text-primary font-bold"> Crie uma</Link></span>
                
                </form>
            </main>
        </div>
    );
}