export default function GameCard() {
    return (
        <div className="flex flex-col gap-1">
            <div className="w-full rounded-2xl flex items-center justify-center hover:shadow-2xl hover:shadow-primary/80 hover:-translate-y-2 transition-transform">
                <img src="src\assets\CoverExample.jpg" alt="Cover-Example" className="rounded-2xl h-102" />
            </div>

            <div className="p-1 flex flex-col gap-1">
                <h3 className="text-white font-bold text-sm">Hollow Knight</h3>
                <div className="text-sm">⭐⭐⭐⭐⭐</div>
            </div>
        </div>
    )
}