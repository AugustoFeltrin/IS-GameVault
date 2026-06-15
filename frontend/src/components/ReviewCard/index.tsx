export interface Review {
    id: number;
    rating: number;
    comment: string;
    reviewDate: string;
    gameTitle: string; 
    coverUrl: string;
}

interface ReviewCardProps {
    review: Review;
}

export default function ReviewCard({ review }: ReviewCardProps) {
    return(
        <div className="bg-surface border border-gray-500/20 hover:border-primary/60 h-38 w-full p-6 rounded-3xl flex">
            <div className="h-full w-full flex gap-4 items-center">
                <img src={review.coverUrl} alt={review.gameTitle} className="h-30 w-24 rounded-lg" />
                <div className="flex flex-col gap-2">
                    <div className="flex flex-col">
                        <span className="text-lg font-bold">{review.gameTitle}</span>
                    </div>
                    <span className="text-sm text-gray-400">{review.comment}</span>
                    <span className="text-sm text-gray-400">Avaliado em: {new Date(review.reviewDate).toLocaleDateString('pt-BR')}</span>
                </div>
            </div>
            <div className="flex gap-4">
                <span className="text-sm text-emerald-400">Jogado!</span>
             <div className="flex flex-col gap-4">
            <div 
                className="text-sm font-bold text-primary"
                data-cy="review-rating"
                data-rating={review.rating}
            >
                {"⭐".repeat(review.rating)}
            </div>
            </div>
            </div>
        </div>
    )
}