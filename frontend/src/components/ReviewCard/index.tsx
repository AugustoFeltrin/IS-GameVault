export default function ReviewCard() {
    return(
        <div className="bg-surface border border-gray-500/20 hover:border-primary/60 h-38 w-full p-6 rounded-3xl flex">
            <div className="h-full w-full flex gap-4 items-center">
                <img src="src\assets\CoverExample.jpg" alt="Cover-Example" className="h-30 w-24 rounded-lg" />
                <div className="flex flex-col gap-2">
                    <div className="flex flex-col">
                        <span className="text-lg font-bold">Hollow Knight</span>
                        <span className="text-sm text-gray-400">Team Cherry · 2017</span>
                    </div>
                    <span className="text-sm text-gray-400">Nunca mais confio em mariposas.</span>
                    <span className="text-sm text-gray-400">Avaliado há 3 dias</span>
                </div>
            </div>
            <div className="flex gap-4">
                <span className="text-sm text-emerald-400">Jogado!</span>
                <span className="text-sm">⭐⭐⭐⭐⭐</span>
            </div>
        </div>
    )
}