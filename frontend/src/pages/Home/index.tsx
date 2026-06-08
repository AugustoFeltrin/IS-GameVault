import { useState, useEffect } from "react";
import NavBar from "../../components/NavBar";
import Catalog from "../../components/Catalog";
import FindYourGame from "../../components/FindYourGame";
import ReviewModal from "../../components/ReviewModal";
import { api } from "../../services/api";
import type { Game } from "../../components/GameCard"; 

export default function Home() {
    const [searchTerm, setSearchTerm] = useState("");
    const [games, setGames] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    
    const [selectedGame, setSelectedGame] = useState<Game | null>(null);

    useEffect(() => {
        const fetchGames = async () => {
            try {
                const data = await api.getGames();
                setGames(data);
            } catch (err) {
                console.error("Erro ao buscar jogos:", err);
            } finally {
                setIsLoading(false);
            }
        };
        fetchGames();
    }, []);

    return (
        <div className="flex flex-col min-h-screen relative">
            <header className="sticky top-0 z-50 w-full h-16">
                <NavBar />
            </header>

            <main className="flex-1 flex flex-col gap-8 w-full max-w-7xl mx-auto px-6 py-10">
                <div className="w-full relative z-40"> 
                    <FindYourGame 
                        searchTerm={searchTerm} 
                        setSearchTerm={setSearchTerm} 
                        games={games}
                        onGameSelect={setSelectedGame} 
                    />
                </div>

                <div className="w-full flex-1">
                    <Catalog 
                        games={games} 
                        isLoading={isLoading} 
                        onGameSelect={setSelectedGame} 
                    />
                </div>
            </main>

            {selectedGame && (
                <ReviewModal 
                    game={selectedGame} 
                    onClose={() => setSelectedGame(null)} 
                />
            )}
        </div>
    );
}