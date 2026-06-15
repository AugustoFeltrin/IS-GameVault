import { useState } from "react";
import toast from "react-hot-toast";
import type { Game } from "../GameCard";
import { api } from "../../services/api";
import { FaStar } from "react-icons/fa";

interface ReviewModalProps {
    game: Game;
    onClose: () => void; 
}

export default function ReviewModal({ game, onClose }: ReviewModalProps) {
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState("");;
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleSave = async () => {
        if (rating === 0) {
            toast.error("Por favor, selecione pelo menos 1 estrela.");
            return;
        }

        setIsSubmitting(true);

        try {
            const userId = Number(localStorage.getItem("gamevault_user_id"));
            let finalGameId = game.id;

            if (finalGameId === 0) {
                const savedLocalGame = await api.saveGameLocal(game);
                finalGameId = savedLocalGame.id; 
            }

            await api.saveReview(userId, finalGameId, rating, comment);
            
            toast.success("Avaliação salva com sucesso no seu Diário!");
            onClose(); 
        } catch (err: any) {
            toast.error(err.message || "Erro ao salvar avaliação.");
            setIsSubmitting(false);
        }
    };

    return (
        <div className="fixed inset-0 bg-black/80 z-100 flex justify-center items-center p-4">
            
            <div className="bg-surface border border-zinc-700 rounded-3xl p-8 max-w-2xl w-full flex gap-8 shadow-2xl relative">
                
                <button 
                    onClick={onClose}
                    className="absolute top-4 right-4 text-gray-400 hover:text-white text-xl font-bold"
                >
                    ✕
                </button>

                <div className="w-1/3 shrink-0">
                    <img src={game.coverUrl} alt={game.title} className="w-full rounded-2xl shadow-lg object-cover" />
                </div>

                <div className="w-2/3 flex flex-col gap-4 justify-center">
                    <div>
                        <h2 className="text-3xl font-bold text-white mb-1">{game.title}</h2>
                        <p className="text-sm text-gray-400">Como foi a sua experiência?</p>
                    </div>

                <div className="flex gap-2 my-2">
                        {[1, 2, 3, 4, 5].map((star) => (
                            <button 
                                key={star}
                                data-cy={`star-${star}`} 
                                onClick={() => setRating(star)}
                                className={`text-3xl transition-transform hover:scale-110 ${star <= rating ? 'text-secondary' : 'text-gray-600'}`}
                            >
                                <FaStar />
                            </button>
                        ))}
                </div>

                    <textarea 
                        placeholder="Escreva sua análise (opcional)..."
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        className="bg-background border border-zinc-700 rounded-xl p-4 text-white placeholder-gray-500 w-full h-32 focus:outline-none focus:border-primary resize-none"
                    ></textarea>

                    <div className="flex gap-4 mt-2">
                        <button 
                            onClick={onClose}
                            className="flex-1 py-3 rounded-xl border border-zinc-700 text-white font-bold hover:bg-zinc-800 transition-colors"
                        >
                            Cancelar
                        </button>
                        <button 
                            onClick={handleSave}
                            disabled={isSubmitting}
                            className="flex-1 bg-primary py-3 rounded-xl text-white font-bold hover:brightness-110 transition-colors disabled:opacity-50"
                        >
                            {isSubmitting ? "Salvando..." : "Salvar no Diário"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}