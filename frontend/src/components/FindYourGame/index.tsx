import { useState, useEffect } from "react";
import type { Game } from "../GameCard";
import { api } from "../../services/api";

interface FindYourGameProps {
    searchTerm: string;
    setSearchTerm: (term: string) => void;
    onGameSelect: (game: Game) => void;
}

export default function FindYourGame({ searchTerm, setSearchTerm, onGameSelect }: FindYourGameProps) {
    const [isFocused, setIsFocused] = useState(false);
    const [igdbResults, setIgdbResults] = useState<Game[]>([]);
    const [isSearching, setIsSearching] = useState(false);

    useEffect(() => {
        if (!searchTerm.trim()) {
            setIgdbResults([]);
            return;
        }

        setIsSearching(true);

        const delayDebounceFn = setTimeout(async () => {
            try {
                const results = await api.searchIGDB(searchTerm);
                setIgdbResults(results);
            } catch (error) {
                console.error(error);
                setIgdbResults([]);
            } finally {
                setIsSearching(false);
            }
        }, 500);

        return () => clearTimeout(delayDebounceFn);
    }, [searchTerm]);

    return (
        <div className="w-full h-full flex justify-between">
            
            <div className="flex flex-col gap-2 mt-auto">
                <span className="text-sm text-primary">DESCOBRIR</span>
                <span className="text-3xl font-bold">Encontre seu próximo jogo favorito</span>
                <span className="text-sm text-gray-400">8 jogos em destaque</span> 
            </div>

            <div className="relative w-full max-w-sm mt-auto">
                
                <div className="flex items-center bg-surface rounded-full px-4 py-2 w-full h-12 border border-transparent focus-within:border-primary transition-all">
                    <input 
                        type="text" 
                        placeholder="Buscar por títulos..." 
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        onFocus={() => setIsFocused(true)}
                        onBlur={() => setTimeout(() => setIsFocused(false), 200)} 
                        className="bg-transparent border-none outline-none text-white text-sm placeholder-white/70 w-full ml-2"
                    />
                </div>

                {isFocused && searchTerm && (
                    <div className="absolute top-[calc(100%+8px)] left-0 w-full bg-surface border border-gray-500/20 rounded-2xl shadow-2xl max-h-80 overflow-y-auto z-50">
                        {isSearching ? (
                            <div className="p-4 text-center text-sm text-primary animate-pulse">Pesquisando...</div>
                        ) : igdbResults.length === 0 ? (
                            <div className="p-4 text-center text-sm text-gray-400">Nenhum jogo encontrado.</div>
                        ) : (
                            <div className="flex flex-col p-2">
                                {igdbResults.slice(0, 5).map((game, index) => (
                                    <div 
                                        key={game.igdbId || index} // Chave alterada para suportar o ID da IGDB
                                        className="flex items-center gap-3 p-2 hover:bg-background/50 rounded-xl cursor-pointer transition-colors"
                                        onClick={() => onGameSelect(game)}
                                    >
                                        <img src={game.coverUrl} alt={game.title} className="w-8 h-10 object-cover rounded-md shadow-sm" />
                                        <div className="flex flex-col">
                                            <span className="text-white font-bold text-sm">{game.title}</span>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                )}
            </div>
            
        </div>
    );
}