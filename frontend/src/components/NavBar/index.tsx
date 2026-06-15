import { Link, useNavigate } from "react-router-dom"
import toast from "react-hot-toast";

export default function NavBar() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("gamevault_user_id");
        toast.success("Sessão encerrada. Até logo!");
        navigate("/login");
    };

    return (
        <header className="h-full w-full bg-background/80 backdrop-blur-md border-b border-surface">
            <div className="max-w-7xl w-full h-full mx-auto px-6 flex justify-between items-center">
                
                <div className="flex gap-2 items-center">
                    <div className="bg-primary h-8 w-8 rounded-full"></div>
                    <span>GameVault</span>
                </div>

                <nav className="flex items-center gap-8">
                    <Link to="/home" className="text-sm text-gray-300 hover:text-primary">Início</Link>
                    <Link to="/home" className="text-sm text-gray-300 hover:text-primary">Descobrir</Link>
                    <Link to="/profile" className="text-sm text-gray-300 hover:text-primary">Meu Diário</Link>
                </nav>

                <div className="flex gap-4 items-center">
                    <button 
                        onClick={handleLogout} 
                        className="text-sm text-gray-300 hover:text-primary"
                    >
                        Sair
                    </button>

                    <div className="bg-surface h-10 w-10 rounded-full"></div>
                </div>
                
            </div>
        </header>
    )
}