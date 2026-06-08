import GameCard from "../GameCard"
import type { Game } from "../GameCard"

interface CatalogProps {
    games: Game[];
    isLoading: boolean;
    onGameSelect: (game: Game) => void; 
}

export default function Catalog({ games, isLoading, onGameSelect }: CatalogProps) {
    
    if (isLoading) {
        return <div className="w-full text-center p-10 text-gray-400 font-bold">Buscando jogos no seu Vault...</div>;
    }

    if (games.length === 0) {
        return <div className="w-full text-center p-10 text-gray-400">Nenhum jogo encontrado no banco de dados.</div>;
    }

    return (
        <div className="h-full w-full p-6 grid grid-cols-4 gap-6 content-start">
            {games.slice(0, 8).map((game) => (
                <GameCard 
                    key={game.id} 
                    game={game} 
                    onClick={() => onGameSelect(game)} 
                />
            ))}
        </div>
    )
}