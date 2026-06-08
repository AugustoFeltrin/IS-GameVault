export interface Game {
    id: number;
    title: string;
    description: string;
    coverUrl: string;
    igdbId: string;
}

interface GameCardProps {
    game: Game;
    onClick: () => void;
}

export default function GameCard({ game, onClick }: GameCardProps) {
    return (
        <div className="flex flex-col gap-1">
            <div className="w-full rounded-2xl flex items-center justify-center hover:shadow-2xl hover:shadow-primary/80 hover:-translate-y-2 transition-transform">
                <img
                    onClick={onClick} 
                    src={game.coverUrl} 
                    alt={`Capa do jogo ${game.title}`} 
                    className="rounded-2xl h-102" 
                />
            </div>

            <div className="p-1 flex flex-col gap-1">
                <h3 className="text-white font-bold text-sm">{game.title}</h3>
                <div className="text-sm">⭐⭐⭐⭐⭐</div>
            </div>
        </div>
    )
}