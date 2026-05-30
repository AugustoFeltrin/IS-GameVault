export default function FindYourGame() {
    return (
        <div className="w-full h-full flex justify-between">
            <div className="flex flex-col gap-2 mt-auto">
                <span className="text-sm text-primary">DESCOBRIR</span>
                <span className="text-3xl font-bold">Encontre seu próximo jogo favorito</span>
                <span className="text-sm text-gray-400">8 jogos em destaque</span>
            </div>

            <div className="flex items-center bg-surface rounded-full px-4 py-2 w-full max-w-sm h-12 mt-auto border border-transparent focus-within:border-primary transition-all">
                <input 
                    type="text" 
                    placeholder="Buscar por títulos..." 
                    className="bg-transparent border-none outline-none text-white text-sm placeholder-white/70 w-full ml-2"
                />
            </div>
    
        </div>
    )
}