import { useEffect, useState } from "react"
import ReviewCard from "../ReviewCard"
import type { Review } from "../ReviewCard"
import { api } from "../../services/api"

export default function Reviews() {
    const [reviews, setReviews] = useState<Review[]>([]);

    useEffect(() => {
        const fetchReviews = async () => {
            const userId = Number(localStorage.getItem("gamevault_user_id"));
            
            try {
                const [reviewsData, gamesData] = await Promise.all([
                    api.getReviewsByUser(userId),
                    api.getGames()
                ]);

                const enrichedReviews = reviewsData.map((rev: any) => {
                    const game = gamesData.find((g: any) => g.id === rev.gameId);
                    return {
                        ...rev,
                        gameTitle: game ? game.title : "Jogo Desconhecido",
                        coverUrl: game ? game.coverUrl : "" 
                    };
                });

                setReviews(enrichedReviews.reverse());
            } catch (err) {
                console.error("Erro ao carregar diário", err);
            }
        };

        fetchReviews();
    }, []);

    return (
        <div className="w-full h-full">
            <div className="py-2 flex justify-between mb-4">
                <div className="flex flex-col gap-2">
                    <span className="text-sm text-primary">DIÁRIO</span>
                    <span className="text-2xl font-bold">Minhas Reviews</span>
                </div>

                <span className="text-sm text-gray-400 mt-auto">{reviews.length} avaliações</span>
            </div>

            <div className="flex flex-col gap-4">
                {reviews.map((rev) => (
                    <ReviewCard key={rev.id} review={rev} />
                ))}
            </div>
        </div>
    )
}