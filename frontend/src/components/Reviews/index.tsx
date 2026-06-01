import ReviewCard from "../ReviewCard"

export default function Reviews() {
    return (
        <div className="w-full h-full">
            <div className="py-2 flex justify-between mb-4">
                <div className="flex flex-col gap-2">
                    <span className="text-sm text-primary">DIÁRIO</span>
                    <span className="text-2xl font-bold">Minhas Reviews</span>
                </div>

                <span className="text-sm text-gray-400 mt-auto">5 avaliações</span>
            </div>

            <div className="flex flex-col gap-4">
                <ReviewCard/>
                <ReviewCard/>
                <ReviewCard/>
                <ReviewCard/>
                <ReviewCard/>
            </div>
        </div>
    )
}