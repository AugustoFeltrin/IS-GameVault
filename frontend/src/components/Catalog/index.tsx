import { useEffect, useState } from "react"
import GameCard from "../GameCard"
import type { Game } from "../GameCard"
import { api } from "../../services/api"

export default function Catalog() {
    const [games, setGames] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchGames = async () => {
            try {
                const data = await api.getGames();
                setGames(data);
            } catch (err: any) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        }

        fetchGames();
    }, [])

    if (isLoading) {
        return <div className="w-full text-center p-10 text-gray-400 font-bold">Buscando jogos no seu Vault...</div>;
    }

    if (error) {
        return <div className="w-full text-center p-10 text-red-500 font-bold">Erro: {error}</div>;
    }

    if (games.length === 0) {
        return <div className="w-full text-center p-10 text-gray-400">Nenhum jogo encontrado no banco de dados.</div>;
    }

    return (
        <div className="h-full w-full p-6 grid grid-cols-4 gap-6 content-start">
            {games.slice(0, 8).map((game) => (
                <GameCard key={game.id} game={game} />
            ))}
        </div>
    )
}