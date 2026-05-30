import GameCard from "../GameCard"

export default function Catalog() {
    return (
        <div className="h-full w-full p-6 grid grid-cols-4 gap-6 content-start">
            <GameCard />
            <GameCard />
            <GameCard />
            <GameCard />
            <GameCard />
            <GameCard />
            <GameCard />
            <GameCard />
        </div>
    )
}