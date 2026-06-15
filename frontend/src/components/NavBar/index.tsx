import { Link, useNavigate } from "react-router-dom"
import toast from "react-hot-toast";

export default function NavBar() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("gamevault_user_id");
        toast.success("Sessão encerrada. Até logo!");
        navigate("/login");

    };

    const userName = localStorage.getItem("gamevault_user_name") || "Jogador";
    const userInitials = userName.substring(0, 2).toUpperCase();

    return (
        <header className="h-full w-full bg-background/80 backdrop-blur-md border-b border-surface">
            <div className="max-w-7xl w-full h-full mx-auto px-6 flex justify-between items-center">
                <Link 
                    to="/home" 
                    className="flex items-center gap-2"
                >
                    <img 
                        className="h-10 w-10" 
                        src="src\assets\Logo.png"></img>
                    <span><b className="text-primary">Game</b><b className="text-secondary">Vault</b></span>
                </Link>

                <nav className="flex items-center gap-8">
                    <Link to="/home" className="text-sm text-gray-300 hover:text-primary">Início</Link>
                    <Link to="/profile" className="text-sm text-gray-300 hover:text-primary">Meu Diário</Link>
                </nav>

                <div className="flex gap-4 items-center">
                    <button 
                        onClick={handleLogout} 
                        className="text-sm text-gray-300 hover:text-primary cursor-pointer"
                    >
                        Sair
                    </button>

                    <Link 
                        to="/profile" 
                        className="bg-primary border border-black h-10 w-10 rounded-full flex justify-center items-center"
                    >
                        <span className="text-sm">{userInitials}</span>
                    </Link>
                </div>
                
            </div>
        </header>
    )
}