export default function NavBar() {
    return (
        <header className="h-full w-full bg-background/80 backdrop-blur-md border-b border-surface">
            <div className="max-w-7xl w-full h-full mx-auto px-6 flex justify-between items-center">
                
                <div className="flex gap-2 items-center">
                    <div className="bg-primary h-8 w-8 rounded-full"></div>
                    <span>GameVault</span>
                </div>

                <ul className="flex items-center gap-8">
                    <li><a href="/" className="text-sm text-gray-300 hover:text-primary">Início</a></li>
                    <li><a href="/" className="text-sm text-gray-300 hover:text-primary">Descobrir</a></li>
                    <li><a href="/" className="text-sm text-gray-300 hover:text-primary">Meu Diário</a></li>
                </ul>

                <div className="flex gap-4 items-center">
                    <div className="flex items-center bg-surface rounded-full h-10 border border-transparent focus-within:border-primary transition-all">
                        <input 
                            type="text" 
                            placeholder="Buscar jogos..." 
                            className="pl-4 bg-transparent border-none outline-none text-white text-sm placeholder-white/70"
                        />
                    </div>

                    <div className="bg-surface h-10 w-10 rounded-full"></div>
                </div>
                
            </div>
        </header>
    )
}